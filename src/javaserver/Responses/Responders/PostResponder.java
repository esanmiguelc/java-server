package javaserver.Responses.Responders;

import javaserver.Requests.Logger;
import javaserver.Routes.Route;

import java.util.List;

public class PostResponder implements Responder {
    private Route route;
    private Logger logger;

    public PostResponder(Route route, Logger logger) {
        this.route = route;
        this.logger = logger;
    }

    @Override
    public String contentBody() {
        return null;
    }

    @Override
    public String statusCode() {
        return null;
    }

    @Override
    public String httpMethod() {
        return null;
    }

    @Override
    public List<String> additionalHeaders() {
        return null;
    }
}
