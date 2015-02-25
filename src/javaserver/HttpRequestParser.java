package javaserver;

public class HttpRequestParser {

    public static final int VERB_LOC = 0;
    public static final int URI_LOC = 1;
    public static final int HTTP_LOC = 2;

    private String[] request;

    public HttpRequestParser(String request) {
        this.request = request.split("\n");
    }


    public String getVerb() {
        return splitHeaderVerb()[VERB_LOC];
    }

    public String getURI() {
        return splitHeaderVerb()[URI_LOC];
    }

    public String getHttpVersion() {
        return splitHeaderVerb()[HTTP_LOC];
    }

    private String[] splitHeaderVerb() {return request[0].split(" ");}
}
