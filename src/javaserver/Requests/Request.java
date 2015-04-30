package javaserver.Requests;

import javaserver.Parser.RangeParser;

import java.util.Map;

public interface Request {
    String statusCode();

    String getHttpMethod();

    String getUri();

    String getProtocol();

    Map<String, String> getHeaders();

    Map<String, String> getParams();

    boolean containsHeader(String header);

    String getHeader(String key);

    String content();
}
