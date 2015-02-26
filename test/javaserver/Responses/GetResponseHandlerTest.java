package javaserver.Responses;

import javaserver.Requests.HttpRequestParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetResponseHandlerTest {

    @Test
    public void testReturnsCorrectCodeWhenOK() throws Exception {
        HttpRequestParser requestParser = new HttpRequestParser("");
        GetResponseHandler responseHandler = new GetResponseHandler(requestParser, new DummyConfig());

        assertEquals("HTTP/1.1 200 OK", responseHandler.getStatusLine());
    }
}