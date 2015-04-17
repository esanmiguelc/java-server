package javaserver;

import javaserver.Requests.Request;

import java.util.Map;

public class MockRequest implements Request {
    @Override
    public String statusCode() {
        return null;
    }

    @Override
    public String getHttpMethod() {
        return null;
    }

    @Override
    public String getUri() {
        return null;
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
}
