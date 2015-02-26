package javaserver.Responses;

import javaserver.Requests.HttpRequestParser;
import org.junit.Test;

public class HttpResponseHandlerTest {
    @Test
    public void testReturnsCorrectCodeWhenOK() throws Exception {
        HttpRequestParser requestParser = new HttpRequestParser("");
        HttpResponseHandler responseHandler = new HttpResponseHandler(requestParser);
    }
}
