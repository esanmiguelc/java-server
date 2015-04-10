package javaserver.Responses.Responders;

import javaserver.Routes.Route;

import java.util.ArrayList;
import java.util.List;

public class DeleteResponder implements Responder {

    private Route route;

    public DeleteResponder(Route route) {
        this.route = route;
        deleteParams();
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

    private void deleteParams() {
        route.resetParams();
    }
}
