package javaserver.Requests;

import javaserver.StringModifier;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HttpRequestTest {
    private String requestString = "GET / HTTP1/1\r\n";

    @Test
    public void testDoesNotContainHeader() {
        HttpRequest requestObject = new HttpRequestParser(requestString).createRequest();
        assertThat(requestObject.containsHeader("SomeHeader"), is(equalTo(false)));
    }

    @Test
    public void testContainsHeader() {
        String host = "localhost:5000";
        requestString += "Host: " + host + StringModifier.EOL;
        HttpRequest requestObject = new HttpRequestParser(requestString).createRequest();
        requestObject.getHeaders();
        assertThat(requestObject.containsHeader("Host"), is(equalTo(true)));
    }

    @Test
    public void testGetsTheHeader() {
        String host = "localhost:5000";
        requestString += "Host: " + host + StringModifier.EOL;
        HttpRequest requestObject = new HttpRequestParser(requestString).createRequest();
        assertThat(requestObject.getHeader("Host"), is(equalTo(host)));
    }
}
