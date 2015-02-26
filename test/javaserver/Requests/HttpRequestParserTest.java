package javaserver.Requests;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpRequestParserTest {

    private String request = "GET / HTTP/1.1 \r\n";
    @Test
    public void testGetsTheCorrectVerb() throws Exception {
        String verb = "GET";
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertEquals(verb, requestParser.getVerb());
    }

    @Test
    public void testGetsTheURIRoute() throws Exception {
        String uri = "/";
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertEquals(uri, requestParser.getURI());
    }

    @Test
    public void testGetsHttpVersion() throws Exception {
        String version = "HTTP/1.1";
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertEquals(version, requestParser.getHttpVersion());
    }
}
