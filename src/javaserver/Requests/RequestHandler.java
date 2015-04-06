package javaserver.Requests;

import javaserver.Routes.Route;
import javaserver.Routes.RoutesRegistrar;
import javaserver.StringModifier;

import java.util.Map;

public class RequestHandler {

    public static final String OK = "200 OK";
    public static final String NOT_FOUND = "404 Not Found";
    public static final String UNAUTHORIZED = "401 Unauthorized";
    public static final String ENCRYPTED = "Basic YWRtaW46aHVudGVyMg==";
    public static final String EOL = "\r\n";

    private final RoutesRegistrar routes = RoutesRegistrar.getInstance();

    private HttpRequestParser parser;
    private Logger logger;

    public RequestHandler(HttpRequestParser parser) {
        this.parser = parser;
        this.logger = new Logger();
    }

    public RequestHandler(HttpRequestParser parser, Logger logger) {
        this.parser = parser;
        this.logger = logger;
    }

    public String status() {
        if(containsRoute()) {
            routes.getRoute(parser.uri()).setCurrentParams(parser.params());
            if (routes.isSecured(parser.uri())) {
                if (parser.containsHeader("Authorization")) {
                    if (isAuthenticated()) {
                        return OK;
                    } else {
                        return UNAUTHORIZED;
                    }
                } else {
                    return UNAUTHORIZED;
                }
            }
            return OK;
        }
        return NOT_FOUND;
    }

    public boolean containsRoute() {
        return routes.containsRoute(parser.uri());
    }

    public String httpMethod() {
        return parser.httpMethod();
    }

    public String availableMethods() {
        Route route = routes.getRoute(parser.uri());
        if(containsRoute()
                && parser.httpMethod().equals("OPTIONS")
                && route.getMethods().contains("OPTIONS")) {
            return "Allow: " + String.join(",", route.getMethods()) + EOL;
        }
        return "";
    }

    public String content() {
        switch (status()) {
            case UNAUTHORIZED:
                return "Authentication required";
            case OK:
                String content = "";
                if (parser.httpMethod().equals("GET")) {
                    if (!parser.params().isEmpty()) {
                        for (Map.Entry<String, String> param : parser.params().entrySet()) {
                            content += param.getKey() + "=" + param.getValue() + StringModifier.EOL;
                        }
                        return content;
                    } else {
                        return content;
                    }
                }
                if(parser.uri().equals("/logs")) {
                    return logger.logs();
                }
            default:
                return "";
        }
    }

    private boolean isAuthenticated() {
        return parser.getHeader("Authorization").equals(ENCRYPTED);
    }
}
