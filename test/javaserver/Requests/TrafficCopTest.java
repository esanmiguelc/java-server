package javaserver.Requests;

import javaserver.Responses.Responders.*;
import javaserver.Routes.RoutesRegistrar;
import javaserver.StringModifier;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TrafficCopTest {

    @Before
    public void setUp() throws Exception {
        RoutesRegistrar.getInstance().registerRoute("/", false);
        RoutesRegistrar.getInstance().registerRoute("/logs", true);
        RoutesRegistrar.getInstance().registerRoute("/method_options", true, "GET", "POST", "OPTIONS");
        RoutesRegistrar.getInstance().registerRoute("/form", false);

    }

    @Test
    public void testReturnsTheCorrectResponderForGet() {
        Request req = new HttpRequestParser("GET / HTTP/1.1").createRequest();
        Responder responder = new TrafficCop(req).delegateTwo();
        assertThat(responder, is(instanceOf(GetResponder.class)));
    }

    @Test
    public void testReturnsTheCorrectResponderForPost() {
        Request req = new HttpRequestParser("POST /form HTTP/1.1").createRequest();
        Responder responder = new TrafficCop(req).delegateTwo();
        assertThat(responder, is(instanceOf(PostResponder.class)));
    }

    @Test
    public void testReturnsFourOhFourForUnrecognizedRoute() {
        Request req = new HttpRequestParser("POST /foobar HTTP/1.1").createRequest();
        Responder responder = new TrafficCop(req).delegateTwo();
        assertThat(responder, is(instanceOf(NotFoundResponder.class)));
    }

    @Test
    public void testReturnsUnauthorizedForWrongCredentials() {
        Request req = new HttpRequestParser("GET /logs HTTP/1.1").createRequest();
        Responder responder = new TrafficCop(req).delegateTwo();
        assertThat(responder, is(instanceOf(UnauthorizedResponder.class)));
    }

    @Test
    public void testReturnsAuthorizedGetWithCredentials() {
        String request = "GET /logs HTTP/1.1" + StringModifier.EOL;
        request += "Authorization: Basic YWRtaW46aHVudGVyMg==" + StringModifier.EOL;
        Request req = new HttpRequestParser(request).createRequest();
        Responder responder = new TrafficCop(req).delegateTwo();
        assertThat(responder, is(instanceOf(GetResponder.class)));
    }

    @Test
    public void testReturnsOptionsResponder() {
        String request = "OPTIONS /logs HTTP/1.1" + StringModifier.EOL;
        request += "Authorization: Basic YWRtaW46aHVudGVyMg==" + StringModifier.EOL;
        Request req = new HttpRequestParser(request).createRequest();
        Responder responder = new TrafficCop(req).delegateTwo();
        assertThat(responder, is(instanceOf(OptionsResponder.class)));
    }

    @Test
    public void testDeleteResponder() {
        String request = "DELETE /form HTTP/1.1" + StringModifier.EOL;
        Request req = new HttpRequestParser(request).createRequest();
        Responder responder = new TrafficCop(req).delegateTwo();
        assertThat(responder, is(instanceOf(DeleteResponder.class)));
    }

    @Test
    public void testReturnsCorrectStatusCodeForUnavailableRoute() {
        Request req = new HttpRequestParser("GET /foobar HTTP/1.1").createRequest();
        TrafficCop handler = new TrafficCop(req);
        assertThat(handler.status(), is(equalTo("404 Not Found")));
    }

    @Test
    public void testReturnsCorrectStatusCodeForAvailableRoute() {
        Request req = new HttpRequestParser("GET / HTTP/1.1").createRequest();
        TrafficCop handler = new TrafficCop(req);
        assertThat(handler.status(), is(equalTo("200 OK")));
    }

    @Test
    public void testReturnsNotAuthorizedForLockedRoutes() {
        Request req = new HttpRequestParser("GET /logs HTTP/1.1").createRequest();
        TrafficCop handler = new TrafficCop(req);
        assertThat(handler.status(), is(equalTo("401 Unauthorized")));
    }

    @Test
    public void testHasNoAvailableMethods() {
        Request req = new HttpRequestParser("GET /logs HTTP/1.1").createRequest();
        TrafficCop handler = new TrafficCop(req);
        assertThat(handler.availableMethods(), is(equalTo("")));
    }

    @Test
    public void testReturnsEmptyStringIfRouteDoesntSupportOptions() {
        Request req = new HttpRequestParser("OPTIONS /logs HTTP/1.1").createRequest();
        TrafficCop handler = new TrafficCop(req);
        assertThat(handler.availableMethods(), is(equalTo("")));
    }

    @Test
    public void testReturnsAllowHeaderWithMethods() {
        Request req = new HttpRequestParser("OPTIONS /method_options HTTP/1.1").createRequest();
        TrafficCop handler = new TrafficCop(req);
        assertThat(handler.availableMethods(), is(equalTo("Allow: GET,POST,OPTIONS" + StringModifier.EOL)));
    }

    @Test
    public void testContentsForUnauthorized() {
        String message = "Authentication required";
        Request req = new HttpRequestParser("OPTIONS /logs HTTP/1.1").createRequest();
        TrafficCop handler = new TrafficCop(req);
        assertThat(handler.delegate(), is(equalTo(message)));
    }

    @Test
    public void testNoParams() {
        String request = "GET /form HTTP/1.1";
        Request req = new HttpRequestParser(request).createRequest();
        TrafficCop handler = new TrafficCop(req);
        assertThat(handler.delegate(), is(equalTo("")));
    }

    @Test
    public void testDataParams() {
        String postRequest = "POST /form HTTP/1.1" + StringModifier.EOL;
        String data = "data=fatcat" + StringModifier.EOL;
        postRequest += data;
        Request postReq = new HttpRequestParser(postRequest).createRequest();
        TrafficCop postHandler = new TrafficCop(postReq);
        postHandler.status();
        String getRequest = "GET /form HTTP/1.1" + StringModifier.EOL;

        Request getReq = new HttpRequestParser(getRequest).createRequest();
        TrafficCop getHandler = new TrafficCop(getReq);
        assertThat(getHandler.delegate().contains(data), is(equalTo(true)));
    }
}
