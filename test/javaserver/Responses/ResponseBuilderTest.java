package javaserver.Responses;

import javaserver.Requests.*;
import javaserver.Responses.Responders.GetResponder;
import javaserver.Responses.Responders.OptionsResponder;
import javaserver.Responses.Responders.PostResponder;
import javaserver.Responses.Responders.Responder;
import javaserver.Routes.Route;
import javaserver.Routes.RoutesRegistrar;
import javaserver.StringModifier;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResponseBuilderTest {

    @Test
    public void testReturns200ForAvailableRoute() throws Exception {
        HttpRequest request = new HttpRequestParser("GET / HTTP/1.1 \r\n").createRequest();
        Responder getRequest = new TrafficCop(request, new Logger(), new MockFile()).delegate();
        ResponseBuilder handler = new HttpResponseBuilder(getRequest);
        assertThat(handler.headers().contains("HTTP/1.1 200 OK"), is(equalTo(true)));
    }

    @Test
    public void testReturns404ForUnavailableRoute() throws Exception {
        HttpRequest requestObject = new HttpRequestParser("GET /foobar HTTP/1.1 \r\n").createRequest();
        Responder getResponder = new TrafficCop(requestObject, new Logger(), new MockFile()).delegate();
        ResponseBuilder handler = new HttpResponseBuilder(getResponder);
        assertThat(handler.headers().contains("HTTP/1.1 404 Not Found"), is(equalTo(true)));
    }


    @Test
    public void testLogsShouldBeUnauthorized() throws Exception {
        String route = "/logs";
        RoutesRegistrar.getInstance().registerRoute(new Route(route, true, false, new HashMap<String, Responder>(){{
            put("GET", new GetResponder());
        }}));
        HttpRequest requestObject = new HttpRequestParser("GET " + route + " HTTP/1.1").createRequest();
        Responder getResponder = new TrafficCop(requestObject, new Logger(), new MockFile()).delegate();
        ResponseBuilder handler = new HttpResponseBuilder(getResponder);
        assertThat(handler.headers().contains("HTTP/1.1 401 Unauthorized"), is(equalTo(true)));
    }

    @Test
    public void testLogsShouldBeAuthorized() throws Exception {
        String route = "/logs";
        RoutesRegistrar.getInstance().registerRoute(new Route(route, true, false, new HashMap<String, Responder>(){{
            put("GET", new GetResponder());
        }}));
        String request = "GET " + route + " HTTP/1.1" + StringModifier.EOL;
        request += "Authorization: Basic YWRtaW46aHVudGVyMg==" + StringModifier.EOL;
        HttpRequest requestObject = new HttpRequestParser(request).createRequest();
        Responder getResponder = new TrafficCop(requestObject, new Logger(), new MockFile()).delegate();
        ResponseBuilder handler = new HttpResponseBuilder(getResponder);
        assertThat(handler.headers().contains("HTTP/1.1 200 OK"), is(equalTo(true)));
    }

    @Test
    public void testShouldContainOptionsInHeaders() throws Exception {
        String route = "/method_options";
        RoutesRegistrar.getInstance().registerRoute(new Route(route, false, false, new LinkedHashMap<String, Responder>(){{
            put("GET", new GetResponder());
            put("POST", new PostResponder());
            put("OPTIONS", new OptionsResponder());
        }}));
        String request = "OPTIONS " + route + " HTTP/1.1" + StringModifier.EOL;
        HttpRequest requestObject = new HttpRequestParser(request).createRequest();
        Responder getResponder = new TrafficCop(requestObject, new Logger(), new MockFile()).delegate();
        ResponseBuilder handler = new HttpResponseBuilder(getResponder);
        assertThat(handler.responseBody(), is(equalTo("Allow: GET,POST,OPTIONS")));
    }
}