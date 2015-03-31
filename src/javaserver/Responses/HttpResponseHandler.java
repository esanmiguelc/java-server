package javaserver.Responses;

import javaserver.Requests.HttpRequestParser;
import javaserver.config.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpResponseHandler implements ResponseHandler {
    private static final String EOL = "\r\n";
    private HttpRequestParser requestParser;
    private String response = "";

    public HttpResponseHandler(HttpRequestParser requestParser) {
        this.requestParser = requestParser;
    }

    @Override
    public String getStatusLine() {
        response += "HTTP/1.1 200 OK" + EOL;
        response += "Server: Emmanuel's Java Server/1.0" + EOL;
        response += "Content-Type: text/html" + EOL;
        response += "Request Type: " + requestParser.getVerb() + EOL;
        response += EOL;
        response += "Protocol: " + requestParser.getProtocol() + EOL;
        response += "URI: " + requestParser.getURI() + EOL;
        response += "Method: " + requestParser.getVerb() + EOL;
        if(!availableRoutes().contains(requestParser.getURI())) {
            response = response.replace("200 OK", "404 Not Found");
        }
        return response;
    }

    private List<String> availableRoutes() {
        return Arrays.asList("/");
    }
}
