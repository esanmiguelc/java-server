package javaserver;

public class HttpRequestParser {
    private String verb;
    private String[] request;

    public HttpRequestParser(String request) {
        this.request = request.split(" ");
        verb = request[0].split(" ")[0];
    }


    public String getVerb() {
        return verb;
    }
}
