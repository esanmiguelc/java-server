package javaserver.Responses.Responders;

import javaserver.Requests.Logger;
import javaserver.Routes.Route;
import javaserver.StringModifier;

import java.util.Arrays;
import java.util.List;

public class GetResponder implements Responder {

    public static final String LOGS = "/logs";
    private Route route;
    private Logger logger;
    private String content = "";

    public GetResponder(Route route, Logger logger) {
        this.route = route;
        this.logger = logger;
    }

    @Override
    public String contentBody() {
        if(route.getPath().equals(LOGS)) {
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
        return Arrays.asList("Content-Type: text/html");
    }
}
