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
    public void testDoesNotHaveParams() {
        HttpRequestParser requestParser = new HttpRequestParser(request);
        assertThat(requestParser.params().isEmpty(), is(equalTo(true)));
    }

    @Test
    public void testParsesParams() {
        Map<String, String> parameters = new HashMap<>();
        String paramKey = "data";
        parameters.put(paramKey, "FatCat");
        request += StringModifier.EOL;
        request += paramKey + "=" + parameters.get(paramKey);
        HttpRequestParser requestParser = new HttpRequestParser(request);
        System.out.println(requestParser.params());

        assertThat(requestParser.params(), is(equalTo(parameters)));
    }

    @Test
    public void testParsesWithOne() {
        Map<String, String> parameters = new HashMap<>();
        String paramKey = "data";
        parameters.put(paramKey, "1");
        request += StringModifier.EOL;
        request += paramKey + "=" + parameters.get(paramKey);
        HttpRequestParser requestParser = new HttpRequestParser(request);
        System.out.println(requestParser.params());

        assertThat(requestParser.params(), is(equalTo(parameters)));
    }

    @Test
    public void testParsesMultipleParams() {
        Map<String, String> parameters = new HashMap<>();
        String paramKey = "data";
        parameters.put(paramKey, "FatCat");
        parameters.put("data1", "HeathCliff");
        request += StringModifier.EOL;
        request += paramKey + "=" + parameters.get(paramKey) + "&" + "data1=HeathCliff";
        HttpRequestParser requestParser = new HttpRequestParser(request);

        assertThat(requestParser.params(), is(equalTo(parameters)));
    }
}
