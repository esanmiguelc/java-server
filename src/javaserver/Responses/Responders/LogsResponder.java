package javaserver.Responses.Responders;

import javaserver.Requests.Logger;
import javaserver.Requests.Request;
import javaserver.Routes.Route;

import java.util.ArrayList;
import java.util.List;

public class LogsResponder implements Responder {

    private Logger logger;

    public LogsResponder(Logger logger) {
        this.logger = logger;
    }

    @Override
    public String contentBody() {
        return logger.logs();
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

    @Override
    public Responder execute(Route route, Request request) {
        return this;
    }
}
