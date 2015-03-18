package javaserver.Responses;

import javaserver.Requests.HttpRequestParser;
import javaserver.config.Configuration;

public class ResponseHandler implements HttpResponseHandler {
    private static final String EOL = "\r\n";
    private HttpRequestParser requestParser;
    private Configuration config;
    private String response = "";

    public ResponseHandler(HttpRequestParser requestParser, Configuration config) {
        this.requestParser = requestParser;
        this.config = config;
    }

    @Override
    public String getStatusLine() throws Exception {
        response += "HTTP/1.1 200 OK" + EOL;
        response += "Server: Emmanuel's Java Server/1.0" + EOL;
        response += "Content-Type: text/html" + EOL;
        response += "Status: 200 OK" + EOL;
        response += "Request Type: " + requestParser.getVerb();
        response += EOL;

        return response;
    }
}
