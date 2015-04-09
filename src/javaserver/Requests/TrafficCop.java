package javaserver.Requests;

import javaserver.Responses.Responders.*;
import javaserver.Routes.Route;
import javaserver.Routes.RoutesRegistrar;
import javaserver.StringModifier;

public class TrafficCop {

    public static final String OK = "200 OK";
    public static final String NOT_FOUND = "404 Not Found";
    public static final String UNAUTHORIZED = "401 Unauthorized";
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

    public String status() {
        if(containsRoute()) {
            if(!request.getParams().isEmpty()) {
                route.setCurrentParams(request.getParams());
            }
            if (routes.isSecured(request.getUri())) {
                return (isAuthenticated()) ? OK : UNAUTHORIZED;
            }
            return OK;
        }
        return NOT_FOUND;
    }

    public boolean containsRoute() {
        return routes.containsRoute(request.getUri());
    }

    public String httpMethod() {
        return request.getHttpMethod();
    }

    public String availableMethods() {
        if(containsRoute()
                && request.getHttpMethod().equals("OPTIONS")
                && route.getMethods().contains("OPTIONS")) {
            return "Allow: " + String.join(",", route.getMethods()) + StringModifier.EOL;
        }
        return "";
    }

    public Responder delegateTwo() {
        if (!containsRoute()) {
            return new NotFoundResponder();
        }
        if (route.isSecured()) {
            if (!isAuthenticated()) {
                return new UnauthorizedResponder();
            }
        }
        if (request.getHttpMethod().equals("POST")) {
            return new PostResponder(route, logger);
        } else if (request.getHttpMethod().equals("OPTIONS")) {
            return new OptionsResponder();
        } else if (request.getHttpMethod().equals("DELETE")) {
            return new DeleteResponder();
        } else {
            return new GetResponder(route, logger);
        }
    }
    public String delegate() {
        switch (status()) {
            case UNAUTHORIZED:
                return "Authentication required";
            case OK:
                switch (request.getHttpMethod()) {
                    case "DELETE":
                        route.resetParams(); // PostResponder with callback on statuses
                        break;
                    case "GET":
                        return new GetResponder(route, logger).contentBody(); // Could this actually callback with the status?
                    default:
                        return "";
                }
            default:
                return "";
        }
    }

    private boolean isAuthenticated() {
        if (request.containsHeader("Authorization")) {
            return request.getHeader("Authorization").equals(ENCRYPTED);
        } else {
            return false;
        }
    }
}
