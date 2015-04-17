package javaserver.Requests;

import java.util.Map;

public class HttpRequest implements Request {

    private String httpMethod;
    private String uri;
    private String protocol;
    private Map<String, String> headers;
    private Map<String, String> params;

    public HttpRequest(String httpMethod, String uri, String protocol, Map<String, String> headers, Map<String, String> params) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.protocol = protocol;
        this.headers = headers;
        this.params = params;
    }

    @Override
    public String statusCode() {
        return getHttpMethod() + " " + getUri() + " " + getProtocol();
    }

    @Override
    public String getHttpMethod() {
        return httpMethod;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public boolean containsHeader(String header) {
        return headers.containsKey(header);
    }

    @Override
    public String getHeader(String key) {
        return headers.get(key);
    }
}
