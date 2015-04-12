package javaserver.Responses;

import javaserver.Responses.Responders.Responder;
import javaserver.StringModifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HttpResponseBuilder implements ResponseBuilder {

    
    public static final String SERVER_NAME = "Server: Emmanuel's Java Server/1.0";
    public static final String HTTP_VERSION = "HTTP/1.1";
    private String response = "";
    private Responder responder;

    public HttpResponseBuilder(Responder responder) {
        this.responder = responder;
    }

    @Override
    public String headers() {
        List<String> lines = new ArrayList<>(Arrays.asList(HTTP_VERSION + " " + responder.statusCode(),
                SERVER_NAME
        ));
        lines.addAll(responder.additionalHeaders().stream().collect(Collectors.toList()));
        lines.stream()
                .forEach((line) -> response += line + StringModifier.EOL);
        response += StringModifier.EOL;
        return response;
    }

    @Override
    public String responseBody() {
        return responder.contentBody();
    }
}

