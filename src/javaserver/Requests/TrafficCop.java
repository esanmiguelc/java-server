package javaserver.Requests;

import javaserver.Responses.Responders.*;
import javaserver.Routes.Route;
import javaserver.Routes.RoutesRegistrar;

public class TrafficCop {

    public static final String ENCRYPTED = "Basic YWRtaW46aHVudGVyMg==";

    private final RoutesRegistrar routes = RoutesRegistrar.getInstance();

    private Request request;
    private Logger logger;
    private Route route;

    public TrafficCop(Request request) {
        this.request = request;
        this.logger = new Logger();
        this.route = RoutesRegistrar.getInstance().getRoute(request.getUri());
    }

    public TrafficCop(Request request, Logger logger) {
        this.request = request;
        this.logger = logger;
        this.route = RoutesRegistrar.getInstance().getRoute(request.getUri());
    }

    public Responder delegate() {
        if (!containsRoute()) {
            return new NotFoundResponder();
        }
        if (route.isSecured()) {
            if (!isAuthenticated()) {
                return new UnauthorizedResponder();
            }
        }
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

    private boolean containsRoute() {
        return routes.containsRoute(request.getUri());
    }

    private boolean isAuthenticated() {
        if (request.containsHeader("Authorization")) {
            return request.getHeader("Authorization").equals(ENCRYPTED);
        } else {
            return false;
        }
    }
}
