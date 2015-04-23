package javaserver.Responses.Responders;

import javaserver.Requests.Request;
import javaserver.Routes.Route;
import javaserver.StringModifier;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GetResponder implements Responder {

    private String content;

    public GetResponder() {
    }

    @Override
    public String contentBody() {
        return content;
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
        return Arrays.asList("Content-Type: text/html");
    }

    @Override
    public Responder execute(Route route, Request request) {
        this.content = "";
        if (route.hasParams()) {
            displayParams(route.getParams());
        } else {
            displayParams(request.getParams());
        }
        return this;
    }

    private void displayParams(Map<String, String> params) {
        params.entrySet()
                .stream()
                .forEach((entry) -> content += entry.getKey() + "=" + entry.getValue() + StringModifier.EOL);
    }
}
