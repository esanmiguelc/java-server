package javaserver.Responses;

import javaserver.Requests.RequestHandler;
import javaserver.StringModifier;

import java.util.Arrays;
import java.util.List;

public class HttpResponseBuilder implements ResponseBuilder {

    
    public static final String SERVER_NAME = "Server: Emmanuel's Java Server/1.0";
    public static final String CONTENT_TYPE_TEXT_HTML = "Content-Type: text/html";
    public static final String HTTP_VERSION = "HTTP/1.1";
    public static final String TYPE_HEADER = "Request Type:";
    private RequestHandler requestHandler;
    private String response = "";

    public HttpResponseBuilder(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public String statusLine() {
        List<String> lines = Arrays.asList(HTTP_VERSION + " " + requestHandler.status(),
                SERVER_NAME,
                CONTENT_TYPE_TEXT_HTML,
                TYPE_HEADER + " " + requestHandler.httpMethod(),
                requestHandler.availableMethods(),
                requestHandler.content()
        );
        lines.stream()
                .forEach((line) -> response += line + StringModifier.EOL);

        return response;
    }
}