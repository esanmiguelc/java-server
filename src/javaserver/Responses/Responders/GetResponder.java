package javaserver.Responses.Responders;

import javaserver.Requests.Logger;
import javaserver.Routes.Route;
import javaserver.StringModifier;

import java.util.ArrayList;
import java.util.List;

public class GetResponder implements Responder {
    private Route route;
    private Logger logger;
    private String content = "";

    public GetResponder(Route route, Logger logger) {
        this.route = route;
        this.logger = logger;
    }

    @Override
    public String contentBody() {
        if(route.getPath().equals("/logs")) {
            return logger.logs();
        }

        if (!route.getParams().isEmpty()) {
            route.getParams().entrySet()
                    .stream()
                    .forEach((entry) -> content += entry.getKey() + "=" + entry.getValue() + StringModifier.EOL);
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
        return new ArrayList<>();
    }
}
