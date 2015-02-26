package javaserver.Responses;

import javaserver.Requests.HttpRequestParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpResponseHandlerTest {
    @Test
    public void testReturnsCorrectCodeWhenOK() throws Exception {
        HttpRequestParser requestParser = new HttpRequestParser("");
        HttpResponseHandler responseHandler = new HttpResponseHandler(requestParser);

        assertEquals("HTTP/1.1 200 OK", responseHandler.getResponse());
    }

    @Test
    public void testName() throws Exception {


    }
}
