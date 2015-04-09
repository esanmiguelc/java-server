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


    public HttpRequestParser(String request) {
        this.request = splitRequest(request);
    }

    public String httpMethod() {
        return splitStatusLine()[VERB_LOC];
    }

    public String uri() {
        return splitStatusLine()[URI_LOC];
    }

    public String protocol() {
        return splitStatusLine()[HTTP_LOC];
    }

    private List<String> splitRequest(String request) {
        return Arrays.asList(request.split("\n")).stream()
                .map((string) -> string.replaceAll("[\n\r]+", ""))
                .collect(Collectors.toList());
    }

    private String[] splitStatusLine() {
        return request.get(0).split(" ");
    }

    private HashMap<String, String> parseHeaders() {
        HashMap<String, String> parsed = new HashMap<>();
        List<String> headers = request.stream()
                .filter((line) -> line.matches("\\w+:\\s.+"))
                .collect(Collectors.toList());
        for(String header : headers) {
            String headerKey = header.substring(0, header.indexOf(" ")).replaceAll("\\W", "");
            String headerValue = header.substring(header.indexOf(" ") + 1);
            parsed.put(headerKey, headerValue);
        }
        return parsed;
    }

    public Map<String, String> params() {
        List<String> params = request.stream()
                .filter((line) -> line.matches("\\w+=\\w+"))
                .collect(Collectors.toList());
        HashMap<String, String> parsed = new HashMap<>();
        for (String param : params) {
            String[] split = param.split("=");
            parsed.put(split[0], split[1]);
        }
        return parsed;
    }

    public Request createRequest() {
        return new Request(httpMethod(),uri(),protocol(), parseHeaders(), params());
    }
}
