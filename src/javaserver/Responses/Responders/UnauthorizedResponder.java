package javaserver.Responses.Responders;

import java.util.ArrayList;
import java.util.List;

public class UnauthorizedResponder implements Responder {
    @Override
    public String contentBody() {
        return "Authentication required";
    }

    @Override
    public String statusCode() {
        return "401 Unauthorized";
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
