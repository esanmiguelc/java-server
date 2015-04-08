package javaserver.Responses;

import javaserver.Requests.Logger;
import javaserver.Routes.Route;
import javaserver.StringModifier;

import java.util.Map;

public class GetResponder implements Responder {
    private Route route;
    private Logger logger;
    private String content = "";

    public GetResponder(Route route, Logger logger) {
        this.route = route;
        this.logger = logger;
    }

    @Override
    public String content() {
        if(route.getPath().equals("/logs")) {
            return logger.logs();
        }
        if (!route.getParams().isEmpty()) {
            for (Map.Entry<String, String> param : route.getParams().entrySet()) {
                content += param.getKey() + "=" + param.getValue() + StringModifier.EOL;
            }
        }
        return content;
    }
}
