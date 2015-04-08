package javaserver.Requests;

import javaserver.Responses.GetResponder;
import javaserver.Routes.Route;
import javaserver.Routes.RoutesRegistrar;
import javaserver.StringModifier;

public class RequestHandler {

    public static final String OK = "200 OK";
    public static final String NOT_FOUND = "404 Not Found";
    public static final String UNAUTHORIZED = "401 Unauthorized";
    public static final String ENCRYPTED = "Basic YWRtaW46aHVudGVyMg==";

    private final RoutesRegistrar routes = RoutesRegistrar.getInstance();

    private HttpRequestParser request;
    private Logger logger;
    private Route route;

    public RequestHandler(HttpRequestParser request) {
        this.request = request;
        this.logger = new Logger();
        this.route = RoutesRegistrar.getInstance().getRoute(request.uri());
    }

    public RequestHandler(HttpRequestParser request, Logger logger) {
        this.request = request;
        this.logger = logger;
        this.route = RoutesRegistrar.getInstance().getRoute(request.uri());
    }

    public String status() {
        if(containsRoute()) {
            if(!request.params().isEmpty()) {
                route.setCurrentParams(request.params());
            }
            if (routes.isSecured(request.uri())) {
                if (request.containsHeader("Authorization")) {
                    return (isAuthenticated()) ? OK : UNAUTHORIZED;
                } else {
                    return UNAUTHORIZED;
                }
            }
            return OK;
        }
        return NOT_FOUND;
    }

    public boolean containsRoute() {
        return routes.containsRoute(request.uri());
    }

    public String httpMethod() {
        return request.httpMethod();
    }

    public String availableMethods() {
        if(containsRoute()
                && request.httpMethod().equals("OPTIONS")
                && route.getMethods().contains("OPTIONS")) {
            return "Allow: " + String.join(",", route.getMethods()) + StringModifier.EOL;
        }
        return "";
    }

    public String content() {
        switch (status()) {
            case UNAUTHORIZED:
                return "Authentication required";
            case OK:
                switch (request.httpMethod()) {
                    case "DELETE":
                        route.resetParams();
                        break;
                    case "GET":
                        return new GetResponder(route, logger).content();
                    default:
                        return "";
                }
                if(request.httpMethod().equals("DELETE")) {
                    route.resetParams();
                }
                return new GetResponder(route, logger).content();

            default:
                return "";
        }
    }

    private boolean isAuthenticated() {
        return request.getHeader("Authorization").equals(ENCRYPTED);
    }
}
