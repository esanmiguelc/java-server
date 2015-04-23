package javaserver.Requests;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
        List<String> params = request.stream()
                .filter((line) -> line.matches("\\w+=.+"))
                .collect(Collectors.toList());
        if (params.isEmpty()) {
            return new HashMap<>();
        }
        return parseParams(params.get(0));
    }

    private Map<String,String> parseParams(String params) {
        HashMap<String, String> parsed = new HashMap<>();
        if (!params.isEmpty()) {
            String[] splitParams = params.split("&");
            for (String param : splitParams) {
                String[] split = param.split("=");
                try {
                    parsed.put(split[0], URLDecoder.decode(split[1], "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return parsed;
    }

    public HttpRequest createRequest() {
        if (hasUriParams()) {
            return new HttpRequest(httpMethod(), uri().substring(0, uri().indexOf("?")), protocol(), parseHeaders(), mergedParams());
        }
        return new HttpRequest(httpMethod(), uri(), protocol(), parseHeaders(), mergedParams());
    }

    private Map<String, String> mergedParams() {
        Map<String, String> mergedParams = new HashMap<>();
        mergedParams.putAll(params());
        mergedParams.putAll(uriParams());
        return mergedParams;
    }

    public Map<String, String> uriParams() {
        String params = uri().substring(uri().indexOf("?") + 1);
        return hasUriParams() ? parseParams(params) : new HashMap<>();
    }

    private boolean hasUriParams() {return uri().contains("?");}
}
