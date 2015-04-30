package javaserver;

import javaserver.Requests.Request;

import java.util.Map;

public class MockRequest implements Request {
    private String method = "GET";
    private String URI;

    @Override
    public String statusCode() {
        return null;
    }

    @Override
    public String getHttpMethod() {
        return method;
    }

    @Override
    public String getUri() {
        return URI;
    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public Map<String, String> getHeaders() {
        return null;
    }

    @Override
    public Map<String, String> getParams() {
        return null;
    }

    @Override
    public boolean containsHeader(String header) {
        return false;
    }

    @Override
    public String getHeader(String key) {
        return null;
    }

    @Override
    public String content() {
        return null;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }
}
