package javaserver.Responses.Responders;

import javaserver.Requests.Request;
import javaserver.Routes.Route;

import java.util.ArrayList;
import java.util.List;

public class DeleteResponder implements Responder {

    public DeleteResponder() {
    }

    @Override
    public String contentBody() {
        return "Content Deleted";
    }

    @Override
    public String statusCode() {
        return "200 OK";
    }

    @Override
    public String httpMethod() {
        return "DELETE";
    }

    @Override
    public List<String> additionalHeaders() {
        return new ArrayList<>();
    }

    @Override
    public Responder execute(Route route, Request request) {
        deleteParams(route);
        return this;
    }

    private void deleteParams(Route route) {
        route.resetParams();
    }
}
