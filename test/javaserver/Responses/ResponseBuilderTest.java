package javaserver.Responses;

import javaserver.Requests.HttpRequestParser;
import javaserver.Requests.Request;
import javaserver.Requests.TrafficCop;
import javaserver.Responses.Responders.Responder;
import javaserver.Routes.RoutesRegistrar;
import javaserver.StringModifier;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResponseBuilderTest {

    @Test
    public void testReturns200ForAvailableRoute() throws Exception {
        Request request = new HttpRequestParser("GET / HTTP/1.1 \r\n").createRequest();
        Responder getRequest = new TrafficCop(request).delegate();
        ResponseBuilder handler = new HttpResponseBuilder(getRequest);
        assertThat(handler.response().contains("HTTP/1.1 200 OK"), is(equalTo(true)));
    }

    @Test
    public void testReturns404ForUnavailableRoute() throws Exception {
        Request requestObject = new HttpRequestParser("GET /foobar HTTP/1.1 \r\n").createRequest();
        Responder getResponder = new TrafficCop(requestObject).delegate();
        ResponseBuilder handler = new HttpResponseBuilder(getResponder);
        assertThat(handler.response().contains("HTTP/1.1 404 Not Found"), is(equalTo(true)));
    }


    @Test
    public void testLogsShouldBeUnauthorized() throws Exception {
        String route = "/logs";
        RoutesRegistrar.getInstance().registerRoute(route, true);
        Request requestObject = new HttpRequestParser("GET " + route + " HTTP/1.1").createRequest();
        Responder getResponder = new TrafficCop(requestObject).delegate();
        ResponseBuilder handler = new HttpResponseBuilder(getResponder);
        assertThat(handler.response().contains("HTTP/1.1 401 Unauthorized"), is(equalTo(true)));
    }

    @Test
    public void testLogsShouldBeAuthorized() throws Exception {
        String route = "/logs";
        RoutesRegistrar.getInstance().registerRoute(route, true);
        String request = "GET " + route + " HTTP/1.1" + StringModifier.EOL;
        request += "Authorization: Basic YWRtaW46aHVudGVyMg==" + StringModifier.EOL;
        Request requestObject = new HttpRequestParser(request).createRequest();
        Responder getResponder = new TrafficCop(requestObject).delegate();
        ResponseBuilder handler = new HttpResponseBuilder(getResponder);
        assertThat(handler.response().contains("HTTP/1.1 200 OK"), is(equalTo(true)));
    }

    @Test
    public void testShouldContainOptionsInHeaders() throws Exception {
        String route = "/method_options";
        RoutesRegistrar.getInstance().registerRoute(route, false, "GET", "POST", "OPTIONS");
        String request = "OPTIONS " + route + " HTTP/1.1" + StringModifier.EOL;
        Request requestObject = new HttpRequestParser(request).createRequest();
        Responder getResponder = new TrafficCop(requestObject).delegate();
        ResponseBuilder handler = new HttpResponseBuilder(getResponder);
        assertThat(handler.response().contains("Allow: GET,POST,OPTIONS"), is(equalTo(true)));
    }
}