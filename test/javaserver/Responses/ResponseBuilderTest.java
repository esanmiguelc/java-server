package javaserver.Responses;

import javaserver.Requests.HttpRequestParser;
import javaserver.Requests.RequestHandler;
import javaserver.Routes.RoutesRegistrar;
import javaserver.StringModifier;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResponseBuilderTest {

    @Test
    public void testReturns200ForAvailableRoute() throws Exception {
        RequestHandler requestHandler = new RequestHandler(new HttpRequestParser("GET / HTTP/1.1 \r\n"));
        ResponseBuilder handler = new HttpResponseBuilder(requestHandler);
        assertThat(handler.statusLine().contains("HTTP/1.1 200 OK"), is(equalTo(true)));
    }

    @Test
    public void testReturns404ForUnavailableRoute() throws Exception {
        HttpRequestParser requestParser = new HttpRequestParser("GET /foobar HTTP/1.1 \r\n");
        ResponseBuilder handler = new HttpResponseBuilder(new RequestHandler(requestParser));
        assertThat(handler.statusLine().contains("HTTP/1.1 404 Not Found"), is(equalTo(true)));
    }


    @Test
    public void testLogsShouldBeUnauthorized() throws Exception {
        String route = "/logs";
        RoutesRegistrar.getInstance().registerRoute(route, true);
        HttpRequestParser requestParser = new HttpRequestParser("GET " + route + " HTTP/1.1");
        ResponseBuilder handler = new HttpResponseBuilder(new RequestHandler(requestParser));
        assertThat(handler.statusLine().contains("HTTP/1.1 401 Unauthorized"), is(equalTo(true)));
    }

    @Test
    public void testLogsShouldBeAuthorized() throws Exception {
        String route = "/logs";
        RoutesRegistrar.getInstance().registerRoute(route, true);
        String request = "GET " + route + " HTTP/1.1" + StringModifier.EOL;
        request += "Authorization: Basic YWRtaW46aHVudGVyMg==" + StringModifier.EOL;
        HttpRequestParser requestParser = new HttpRequestParser(request);
        ResponseBuilder handler = new HttpResponseBuilder(new RequestHandler(requestParser));
        assertThat(handler.statusLine().contains("HTTP/1.1 200 OK"), is(equalTo(true)));
    }
}