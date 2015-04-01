package javaserver.Requests;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpRequestParserTest {

    private String request = "GET / HTTP/1.1 \r\n";
    @Test
    public void testGetsTheCorrectVerbGet() {
        String verb = "GET";
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertEquals(verb, requestParser.getVerb());
    }

    @Test
    public void testGetsTheCorrectVerbPost() {
        String verb = "POST";
        String request = "POST / HTTP/1.1 \r\n";
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertEquals(verb, requestParser.getVerb());
    }

    @Test
    public void testGetsTheURIRoute() {
        String uri = "/";
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertEquals(uri, requestParser.getURI());
    }

    @Test
    public void testGetsTheURIRouteWithPath() {
        String uri = "/foobar";
        String request = "GET /foobar HTTP/1.1 \r\n";
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertEquals(uri, requestParser.getURI());
    }

    @Test
    public void testGetsHttpVersion() {
        String version = "HTTP/1.1";
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertEquals(version, requestParser.getProtocol());
    }
}
