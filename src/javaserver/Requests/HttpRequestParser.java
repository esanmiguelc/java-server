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
        List<String> headers = filterHeaders();
        headers.stream()
                .map((header) -> header.split(":\\s"))
                .forEach((header) -> parsed.put(header[0], header[1]));
        return parsed;
    }

    private List<String> filterHeaders() {
        return request.stream()
                .filter((line) -> line.contains(": "))
                .collect(Collectors.toList());
    }

    public Map<String, String> params() {
        HashMap<String, String> parsed = new HashMap<>();
        List<String> params = request.stream()
                .filter((line) -> line.matches("\\w+=.+"))
                .collect(Collectors.toList());
        if(!params.isEmpty()) {
            String[] splitParams = params.get(0).split("&");
            for (String param : splitParams) {
                String[] split = param.split("=");
                parsed.put(split[0], split[1]);
            }
        }
        return parsed;
    }

    public HttpRequest createRequest() {
        return new HttpRequest(httpMethod(),uri(),protocol(), parseHeaders(), params());
    }
}
