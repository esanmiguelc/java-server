package javaserver.Responses.Responders;

import javaserver.FileReader;
import javaserver.Requests.Request;
import javaserver.Routes.Route;

import java.util.Arrays;
import java.util.List;

public class FileResponder implements Responder {
    private FileReader file;

    public FileResponder(FileReader file) {
        this.file = file;
    }

    @Override
    public String contentBody() {
        if (file.exists() && !file.isDirectory()) {
            return new String(file.process());
        }
        return "";
    }

    @Override
    public String statusCode() {
        return "200 OK";
    }

    @Override
    public String httpMethod() {
        return "GET";
    }

    @Override
    public List<String> additionalHeaders() {
        return Arrays.asList("Content-Type: " + file.mimeType());
    }

    @Override
    public Responder execute(Route route, Request request) {
        return this;
    }
}
