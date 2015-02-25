package javaserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpRequestParserTest {
    @Test
    public void testGetsTheCorrectVerb() throws Exception {
        String request = "GET / HTTP/1.1";
        String verb = "GET";
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertEquals(verb, requestParser.getVerb());
    }
}
