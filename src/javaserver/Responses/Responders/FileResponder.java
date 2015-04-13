package javaserver.Responses.Responders;

import javaserver.FileReader;

import java.util.Arrays;
import java.util.List;

public class FileResponder implements Responder {
    private FileReader file;

    public FileResponder(FileReader file) {
        this.file = file;
    }

    @Override
    public String contentBody() {
        if(file.exists() && !file.isDirectory()) {
            file.process();
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
}
