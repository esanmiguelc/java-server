package javaserver.Requests;

import javaserver.StringModifier;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class HttpRequestParserTest {

    private String request = "GET / HTTP/1.1 \r\n";

    @Test
    public void testGetsTheCorrectVerbGet() {
        String verb = "GET";
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertEquals(verb, requestParser.httpMethod());
    }

    @Test
    public void testGetsTheCorrectVerbPost() {
        String verb = "POST";
        String request = "POST / HTTP/1.1 \r\n";
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertEquals(verb, requestParser.httpMethod());
    }

    @Test
    public void testGetsTheURIRoute() {
        String uri = "/";
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertEquals(uri, requestParser.uri());
    }

    @Test
    public void testGetsTheURIRouteWithPath() {
        String uri = "/foobar";
        String request = "GET /foobar HTTP/1.1" + StringModifier.EOL;
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertEquals(uri, requestParser.uri());
    }

    @Test
    public void testGetsHttpVersion() {
        String version = "HTTP/1.1";
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertEquals(version, requestParser.protocol());
    }

    @Test
    public void testRequestHeaders() {
        String host = "localhost:5000";
        request += "Host: " + host + StringModifier.EOL;
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertEquals(host, requestParser.getHeader("Host"));
    }

    @Test
    public void testDoesNotContainHeader() {
        HttpRequestParser requestParser = new HttpRequestParser(request);
        assertThat(requestParser.containsHeader("SomeHeader"), is(equalTo(false)));
    }

    @Test
    public void testContainsHeader() {
        String host = "localhost:5000";
        request += "Host: " + host + StringModifier.EOL;
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertThat(requestParser.containsHeader("Host"), is(equalTo(true)));
    }

    @Test
    public void testDoesntHaveParams() {
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertThat(requestParser.params().isEmpty(), is(equalTo(true)));
    }

    @Test
    public void testParsesParams() {
        Map<String, String> parameters = new HashMap<>();
        String paramKey = "data";
        parameters.put(paramKey, "fatcat");
        request += StringModifier.EOL;
        request += paramKey + "=" + parameters.get(paramKey);
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertThat(requestParser.params(), is(equalTo(parameters)));
    }
}
