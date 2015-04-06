package javaserver.Responses;

import javaserver.Requests.RequestHandler;
import javaserver.StringModifier;

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
        response += HTTP_VERSION + " " + requestHandler.status() + StringModifier.EOL;
        response += SERVER_NAME + StringModifier.EOL;
        response += CONTENT_TYPE_TEXT_HTML + StringModifier.EOL;
        response += TYPE_HEADER + " " + requestHandler.httpMethod() + StringModifier.EOL;
        response += requestHandler.availableMethods() + StringModifier.EOL;
        response += StringModifier.EOL;
        response += requestHandler.content() + StringModifier.EOL;
        return response;
    }
}
