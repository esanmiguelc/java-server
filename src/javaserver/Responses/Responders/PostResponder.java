package javaserver.Responses.Responders;

import javaserver.Routes.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostResponder implements Responder {
    private Route route;
    private Map<String, String> params;

    public PostResponder(Route route, Map<String, String> params) {
        this.route = route;
        this.params = params;
        setParams();
    }

    @Override
    public String contentBody() {
        return route.getParams().toString();
    }

    @Override
    public String statusCode() {
        return "200 OK";
    }

    @Override
    public String httpMethod() {
        return "POST";
    }

    @Override
    public List<String> additionalHeaders() {
        return new ArrayList<>();
    }

    private void setParams() {
        route.setCurrentParams(params);
    }
}
