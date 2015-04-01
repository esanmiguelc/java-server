package javaserver.Responses;

import javaserver.Requests.HttpRequestParser;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class ResponseHandlerTest {

    @Test
    public void testReturns200ForAvailableRoute() throws Exception {
        ResponseHandler handler = new HttpResponseHandler(new HttpRequestParser("GET / HTTP/1.1 \r\n"));
        assertThat(handler.statusLine().contains("HTTP/1.1 200 OK"), is(equalTo(true)));
    }

    @Test
    public void testReturns404ForUnavailableRoute() throws Exception {
        ResponseHandler handler = new HttpResponseHandler(new HttpRequestParser("GET /foobar HTTP/1.1 \r\n"));
        assertTrue(handler.statusLine().contains("HTTP/1.1 404 Not Found"));
    }
}