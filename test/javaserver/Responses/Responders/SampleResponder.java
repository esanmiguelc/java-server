package javaserver.Responses.Responders;

import javaserver.Requests.Request;
import javaserver.Routes.Route;

import java.util.ArrayList;
import java.util.List;

public class SampleResponder implements Responder {
    @Override
    public String contentBody() {
        return "Sample Content";
    }

    @Override
    public String statusCode() {
        return "200 OK";
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
