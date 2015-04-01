package javaserver.Requests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestParser {

    public static final int VERB_LOC = 0;
    public static final int URI_LOC = 1;
    public static final int HTTP_LOC = 2;
    private final List<String> request;
    private final Map<String, String> headers;


    public HttpRequestParser(String request) {
        this.request = requestList(request);
        this.headers = parseHeaders();
    }

    private List<String> requestList(String request) {
        return Arrays.asList(request.split("\n")).stream()
                .map((string) -> string.replaceAll("[\n\r]+", ""))
                .collect(Collectors.toList());

    }

    public String httpMethod() {
        return splitHeaderVerb()[VERB_LOC];
    }


    public String uri() {
        return splitHeaderVerb()[URI_LOC];
    }

    public String protocol() {
        return splitHeaderVerb()[HTTP_LOC];
    }

    private String[] splitHeaderVerb() {
        return request.get(0).split(" ");
    }

    private HashMap<String, String> parseHeaders() {
        HashMap<String, String> parsed = new HashMap<>();
        List<String> headers = request.stream()
                .filter((line) -> line.matches("\\w+:\\s.+"))
                .collect(Collectors.toList());
        for(String header : headers) {
            String[] splitHeader = header.split(" ");
            parsed.put(splitHeader[0].replaceAll("\\W", ""), splitHeader[1]);
        }
        return parsed;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }
}
