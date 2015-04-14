package javaserver.Requests;

import javaserver.FileReader;
import javaserver.Responses.Responders.*;
import javaserver.Routes.Route;
import javaserver.Routes.RoutesRegistrar;

public class TrafficCop {

    public static final String ENCRYPTED = "Basic YWRtaW46aHVudGVyMg==";

    private final RoutesRegistrar routes = RoutesRegistrar.getInstance();

    private Request request;
    private Logger logger;
    private FileReader file;
    private Route route;

    public TrafficCop(Request request, Logger logger, FileReader file) {
        this.request = request;
        this.logger = logger;
        this.file = file;
        this.route = RoutesRegistrar.getInstance().getRoute(request.getUri());
    }

    public Responder delegate() {
        if (containsRoute()) {
            if (!route.hasMethod(request.getHttpMethod())) {
                return new MethodNotAllowedResponder();
            } else {
                if (route.isRoot()) {
                    return new RootResponder(file);
                }
                if (route.isSecured()) {
                    if (!isAuthenticated()) {
                        return new UnauthorizedResponder();
                    }
                }
                return getResponderValidRoute();
            }
        } else if (containsFile()) {
            return new FileResponder(file);
        } else {
            return new NotFoundResponder();
        }
    }

    private Responder getResponderValidRoute() {
        switch (request.getHttpMethod()) {
            case "POST":
                return new PostResponder(route, request.getParams());
            case "PUT":
                return new PostResponder(route, request.getParams());
            case "OPTIONS":
                return new OptionsResponder(route);
            case "DELETE":
                return new DeleteResponder(route);
            default:
                return new GetResponder(route, logger);
        }
    }

    private boolean containsFile() {
        return file.exists();
    }

    private boolean containsRoute() {
        return routes.containsRoute(request.getUri());
    }

    private boolean isAuthenticated() {
        return request.containsHeader("Authorization") && request.getHeader("Authorization").equals(ENCRYPTED);
    }
}
