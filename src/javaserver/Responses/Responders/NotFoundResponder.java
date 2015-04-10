package javaserver.Responses.Responders;

import java.util.ArrayList;
import java.util.List;

public class NotFoundResponder implements Responder {
    @Override
    public String contentBody() {
        return "No content found";
    }

    @Override
    public String statusCode() {
        return "404 Not Found";
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
