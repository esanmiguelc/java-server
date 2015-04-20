package javaserver.Responses.Responders;

import javaserver.Requests.Request;
import javaserver.Routes.Route;

import java.util.ArrayList;
import java.util.List;

public class MethodNotAllowedResponder implements Responder {
    @Override
    public String contentBody() {
        return "Method Not Allowed";
    }

    @Override
    public String statusCode() {
        return "405 Method Not Allowed";
    }

    @Override
    public String httpMethod() {
        return "GET";
    }

    @Override
    public List<String> additionalHeaders() {
        return new ArrayList<>();
    }

    @Override
    public Responder execute(Route route, Request request) {
        return this;
    }
}
