package javaserver.Responses.Responders;

import javaserver.FileReader;
import javaserver.Parser.RangeParser;
import javaserver.Requests.Request;
import javaserver.Routes.Route;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileResponder implements Responder {

    private FileReader file;
    private Request request;

    public FileResponder(FileReader file) {
        this.file = file;
    }

    @Override
    public String contentBody() {
        if (file.exists() && !file.isDirectory()) {
            if (isPartial()) {
                String range = request.getHeader("Range");
                RangeParser rangeParser = new RangeParser(range);
                file.process(rangeParser);
            } else {
                file.process();
            }
        }
        return "";
    }

    @Override
    public String statusCode() {
        return isPartial() ? "206 Partial Content" : "200 OK";
    }

    private boolean isPartial() {
        return request.containsHeader("Range");
    }

    @Override
    public String httpMethod() {
        return "GET";
    }

    @Override
    public List<String> additionalHeaders() {
        return Collections.singletonList("Content-Type: " + file.mimeType());
    }

    @Override
    public Responder execute(Route route, Request request) {
        this.request = request;
        return this;
    }
}
