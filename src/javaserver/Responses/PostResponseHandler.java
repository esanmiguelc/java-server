package javaserver.Responses;

import javaserver.Requests.HttpRequestParser;
import javaserver.config.Configuration;

public class PostResponseHandler implements HttpResponseHandler {

    private HttpRequestParser requestParser;
    private Configuration config;

    public PostResponseHandler(HttpRequestParser requestParser, Configuration config) {
        this.requestParser = requestParser;
        this.config = config;
    }

    @Override
    public String getStatusLine() throws Exception {
        return null;
    }

    @Override
    public String getResponse() {
        return null;
    }
}
