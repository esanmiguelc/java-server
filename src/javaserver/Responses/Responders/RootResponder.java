package javaserver.Responses.Responders;

import javaserver.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RootResponder implements Responder {
    private final FileReader fileReader;

    public RootResponder(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    @Override
    public String contentBody() {
        return fileReader.getDirectoryContents()
                .map(file -> "<a href=\"/" + file.getFileName() + "\">" + file.getFileName() + "</a></br>")
                .collect(Collectors.joining(""));
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
        return new ArrayList<>();
    }
}
