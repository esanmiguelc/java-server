package javaserver.Responses;

import javaserver.Requests.HttpRequestParser;
import javaserver.Requests.Request;
import javaserver.Requests.TrafficCop;
import javaserver.Routes.RoutesRegistrar;
import javaserver.StringModifier;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResponseBuilderTest {

    @Test
    public void testReturns200ForAvailableRoute() throws Exception {
        TrafficCop trafficCop = new TrafficCop(new HttpRequestParser("GET / HTTP/1.1 \r\n").createRequest());
        ResponseBuilder handler = new HttpResponseBuilder(trafficCop);
        assertThat(handler.statusLine().contains("HTTP/1.1 200 OK"), is(equalTo(true)));
    }

    @Test
    public void testReturns404ForUnavailableRoute() throws Exception {
        Request requestObject = new HttpRequestParser("GET /foobar HTTP/1.1 \r\n").createRequest();
        ResponseBuilder handler = new HttpResponseBuilder(new TrafficCop(requestObject));
        assertThat(handler.statusLine().contains("HTTP/1.1 404 Not Found"), is(equalTo(true)));
    }


    @Test
    public void testLogsShouldBeUnauthorized() throws Exception {
        String route = "/logs";
        RoutesRegistrar.getInstance().registerRoute(route, true);
        Request requestObject = new HttpRequestParser("GET " + route + " HTTP/1.1").createRequest();
        ResponseBuilder handler = new HttpResponseBuilder(new TrafficCop(requestObject));
        assertThat(handler.statusLine().contains("HTTP/1.1 401 Unauthorized"), is(equalTo(true)));
    }

    @Test
    public void testLogsShouldBeAuthorized() throws Exception {
        String route = "/logs";
        RoutesRegistrar.getInstance().registerRoute(route, true);
        String request = "GET " + route + " HTTP/1.1" + StringModifier.EOL;
        request += "Authorization: Basic YWRtaW46aHVudGVyMg==" + StringModifier.EOL;
        Request requestObject = new HttpRequestParser(request).createRequest();
        ResponseBuilder handler = new HttpResponseBuilder(new TrafficCop(requestObject));
        assertThat(handler.statusLine().contains("HTTP/1.1 200 OK"), is(equalTo(true)));
    }
}