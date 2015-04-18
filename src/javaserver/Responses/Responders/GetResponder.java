package javaserver.Responses.Responders;

import javaserver.Requests.Logger;
import javaserver.Requests.Request;
import javaserver.Routes.Route;
import javaserver.StringModifier;

import java.util.Arrays;
import java.util.List;

public class GetResponder implements Responder {

    private Logger logger;
    private String content = "";
    private Route route;

    public GetResponder(Route route, Logger logger) {
        this.route = route;
        this.logger = logger;
    }

    public GetResponder() {
    }

    @Override
    public String contentBody() {
        String LOGS = "/logs";
        if(route.getPath().equals(LOGS)) {
            return logger.logs();
        }
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
        this.route = route;
        if (!route.hasParams()) {
            route.getParams().entrySet()
                    .stream()
                    .forEach((entry) -> content += entry.getKey() + "=" + entry.getValue() + StringModifier.EOL);
        }
        return this;
    }
}
