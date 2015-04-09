package javaserver.Responses.Responders;

import java.util.List;

public interface Responder {
    String contentBody();

    String statusCode();

    String httpMethod();

    List<String> additionalHeaders();
}
