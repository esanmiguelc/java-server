package javaserver.Responses;

import javaserver.Requests.HttpRequestParser;

public class HttpResponseHandler {
    private HttpRequestParser requestParser;

    public HttpResponseHandler(HttpRequestParser requestParser) {
        this.requestParser = requestParser;
    }
}
