package javaserver.Responses.Responders;

import javaserver.Requests.Request;
import javaserver.Routes.Route;
import javaserver.StringModifier;

import java.util.Arrays;
import java.util.List;

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
            route.getParams().entrySet()
                    .stream()
                    .forEach((entry) -> content += entry.getKey() + "=" + entry.getValue() + StringModifier.EOL);
        }
        return this;
    }
}
