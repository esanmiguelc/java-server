package javaserver.Responses.Responders;

import javaserver.Requests.Request;
import javaserver.Routes.Route;

import java.util.Arrays;
import java.util.List;

public class OptionsResponder implements Responder {

    private Route route;

    public OptionsResponder(Route route) {
        this.route = route;
    }

    public OptionsResponder() {

    }

    @Override
    public String contentBody() {
        return "Allow: " + String.join(",", route.getMethods());
    }

    @Override
    public String statusCode() {
        return "200 OK";
    }

    @Override
    public String httpMethod() {
        return "OPTIONS";
    }

    @Override
    public List<String> additionalHeaders() {
        return Arrays.asList(contentBody());
    }

    @Override
    public Responder execute(Route route, Request request) {
        this.route = route;
        return this;
    }
}
