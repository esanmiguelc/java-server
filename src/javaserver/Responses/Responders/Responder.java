package javaserver.Responses.Responders;

import javaserver.Requests.Request;
import javaserver.Routes.Route;

import java.util.List;

public interface Responder {
    String contentBody();

    String statusCode();

    String httpMethod();

    List<String> additionalHeaders();

    Responder execute(Route route, Request request);
}
