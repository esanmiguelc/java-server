package javaserver.Responses;

public interface HttpResponseHandler {
    String getStatusLine() throws Exception;

    String getResponse();
}
