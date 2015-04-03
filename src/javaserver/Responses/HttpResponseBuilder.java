package javaserver.Responses;

import javaserver.Requests.RequestHandler;

public class HttpResponseBuilder implements ResponseBuilder {

    private static final String EOL = "\r\n";
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
        response += HTTP_VERSION + " " + requestHandler.status() + EOL;
        response += SERVER_NAME + EOL;
        response += CONTENT_TYPE_TEXT_HTML + EOL;
        response += TYPE_HEADER + " " + requestHandler.httpMethod() + EOL;
        response += requestHandler.availableMethods() + EOL;
        response += EOL;
        response += requestHandler.content() + EOL;
        return response;
    }
}
