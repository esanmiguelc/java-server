package javaserver.Responses;

public interface ResponseBuilder {
    String responseBody() throws Exception;

    String headers();
}
