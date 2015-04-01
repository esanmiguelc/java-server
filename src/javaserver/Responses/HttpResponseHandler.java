package javaserver.Responses;

import javaserver.Requests.HttpRequestParser;

public class HttpResponseHandler implements ResponseHandler {
    private static final String EOL = "\r\n";
    private HttpRequestParser requestParser;
    private String response = "";
    private RoutesRegistrar routes = RoutesRegistrar.getInstance();

    public HttpResponseHandler(HttpRequestParser requestParser) {
        this.requestParser = requestParser;
        this.routes.registerRoute("/");
    }

    @Override
    public String statusLine() {
        response += "HTTP/1.1 200 OK" + EOL;
        response += "Server: Emmanuel's Java Server/1.0" + EOL;
        response += "Content-Type: text/html" + EOL;
        response += "Request Type: " + requestParser.httpMethod() + EOL;
        response += EOL;
        response += "Protocol: " + requestParser.protocol() + EOL;
        response += "URI: " + requestParser.uri() + EOL;
        response += "Method: " + requestParser.httpMethod() + EOL;
        if(!RoutesRegistrar.getInstance().containsRoute(requestParser.uri())) {
            response = response.replace("200 OK", "404 Not Found");
        }
        return response;
    }
}
