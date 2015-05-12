package javaserver.Responses.Responders;

import javaserver.Requests.Request;
import javaserver.Routes.Route;

import java.util.Arrays;
import java.util.List;

public class RedirectResponder implements Responder {
    @Override
    public String contentBody() {
        return "";
    }

    @Override
    public String statusCode() {
        return "302 Found";
    }

    @Override
    public String httpMethod() {
        return "GET";
    }

    @Override
    public List<String> additionalHeaders() {
        return Arrays.asList("Location: /");
    }

    @Override
    public Responder execute(Route route, Request request) {
        return this;
    }
}
