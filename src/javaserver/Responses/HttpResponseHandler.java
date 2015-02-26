package javaserver.Responses;

import javaserver.Requests.HttpRequestParser;

public class HttpResponseHandler {

    private HttpRequestParser requestParser;

    public HttpResponseHandler(HttpRequestParser requestParser) {
        this.requestParser = requestParser;
    }

    public String getResponse() {
        return "HTTP/1.1 200 OK";
    }
}
