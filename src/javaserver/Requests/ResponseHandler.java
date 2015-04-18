package javaserver.Requests;

import javaserver.FileReader;
import javaserver.Responses.Responders.*;
import javaserver.Routes.Route;
import javaserver.Routes.RoutesRegistrar;

public class ResponseHandler {

    public static final String ENCRYPTED = "Basic YWRtaW46aHVudGVyMg==";

    private final RoutesRegistrar routes = RoutesRegistrar.getInstance();

    private HttpRequest request;
    private Logger logger;
    private FileReader file;
    private Route route;

    public ResponseHandler(HttpRequest request, Logger logger, FileReader file) {
        this.request = request;
        this.logger = logger;
        this.file = file;
        this.route = RoutesRegistrar.getInstance().getRoute(request.getUri());
    }

    public Responder delegate() {
        if (containsRoute()) {
            if (!route.hasMethod(request.getHttpMethod())) {
                return new MethodNotAllowedResponder().execute(route,request);
            } else {
                if (route.isSecured()) {
                    if (!isAuthenticated()) {
                        return new UnauthorizedResponder().execute(route,request);
                    }
                }
                return route.responder(request.getHttpMethod()).execute(route, request);
            }
        } else if (containsFile()) {
            return fileResponderFactory().execute(route,request);
        } else {
            return new NotFoundResponder().execute(route,request);
        }
    }

    private Responder fileResponderFactory() {
        return (request.getHttpMethod().equals("GET")) ? new FileResponder(file).execute(route,request) : new MethodNotAllowedResponder().execute(route,request);
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

//        {
//          route => responder
//        }

// new_game = makes empty board
// post take_turn makes a move on the board + passes the current state to server
// get play returns the current state