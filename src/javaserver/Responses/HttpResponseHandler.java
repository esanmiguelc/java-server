package javaserver.Responses;

import javaserver.Requests.HttpRequestParser;
import javaserver.config.Configuration;

public class HttpResponseHandler implements ResponseHandler {
    private static final String EOL = "\r\n";
    private HttpRequestParser requestParser;
    private String response = "";

    public HttpResponseHandler(HttpRequestParser requestParser) {
        this.requestParser = requestParser;
    }

    @Override
    public String getStatusLine() {
        if(requestParser.getURI().contains("foobar")) {
            response += "HTTP/1.1 404 Not Found" + EOL;
        } else {
            response += "HTTP/1.1 200 OK" + EOL;
        }
        response += "Server: Emmanuel's Java Server/1.0" + EOL;
        response += "Content-Type: text/html" + EOL;
        response += "Status: 200 OK" + EOL;
        response += "Request Type: " + requestParser.getVerb();
        response += EOL;

        return response;
    }
}
