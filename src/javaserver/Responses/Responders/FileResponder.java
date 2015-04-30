package javaserver.Responses.Responders;

import javaserver.FileReader;
import javaserver.Parser.RangeParser;
import javaserver.Requests.Request;
import javaserver.Routes.Route;

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
                file.write(rangeParser);
            } else if (isPatch()) {
                file.write(request.content());
            } else {
                file.write();
            }
        }
        return "";
    }

    private boolean isPatch() {
        return request.getHttpMethod().equals("PATCH");
    }

    @Override
    public String statusCode() {
        if (isPartial()) {
            return "206 Partial Content";
        } else if (isPatch()) {
            return "204 No Content";
        } else {
            return "200 OK";
        }
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
