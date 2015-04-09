package javaserver.Requests;

import java.util.HashMap;
import java.util.Map;

public class Request {

    private String httpMethod;
    private String uri;
    private String protocol;
    private Map<String, String> headers;
    private Map<String, String> params;

    public Request(String httpMethod, String uri, String protocol, Map<String, String> headers, Map<String, String> params) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.protocol = protocol;
        this.headers = headers;
        this.params = params;
    }

    public String statusCode() {
        return null;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public boolean containsHeader(String header) {
        return headers.containsKey(header);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }
}
