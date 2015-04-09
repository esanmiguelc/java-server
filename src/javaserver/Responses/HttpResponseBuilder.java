package javaserver.Responses;

import javaserver.Requests.TrafficCop;
import javaserver.Responses.Responders.Responder;
import javaserver.StringModifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HttpResponseBuilder implements ResponseBuilder {

    
    public static final String SERVER_NAME = "Server: Emmanuel's Java Server/1.0";
    public static final String CONTENT_TYPE_TEXT_HTML = "Content-Type: text/html";
    public static final String HTTP_VERSION = "HTTP/1.1";
    public static final String TYPE_HEADER = "Request Type:";
    private TrafficCop trafficCop;
    private String response = "";
    private Responder responder;

    public HttpResponseBuilder(TrafficCop trafficCop) {
        this.trafficCop = trafficCop;
    }

    public HttpResponseBuilder(Responder responder) {
        this.responder = responder;
    }

    public String response() {
        List<String> lines = new ArrayList<>(Arrays.asList(HTTP_VERSION + " " + responder.statusCode(),
                SERVER_NAME,
                CONTENT_TYPE_TEXT_HTML,
                TYPE_HEADER + " " + responder.httpMethod()
                ));
        lines.addAll(responder.additionalHeaders().stream().collect(Collectors.toList()));
        lines.add(responder.contentBody());
        lines.stream()
                .forEach((line) -> response += line + StringModifier.EOL);
        return response;
    }

    @Override
    public String statusLine() {
        List<String> lines = Arrays.asList(HTTP_VERSION + " " + trafficCop.status(),
                SERVER_NAME,
                CONTENT_TYPE_TEXT_HTML,
                TYPE_HEADER + " " + trafficCop.httpMethod(),
                trafficCop.availableMethods(),
                trafficCop.delegate()
        );
        lines.stream()
                .forEach((line) -> response += line + StringModifier.EOL);

        return response;
    }
}