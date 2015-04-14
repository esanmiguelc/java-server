package javaserver.Requests;

import javaserver.Responses.Responders.*;
import javaserver.Routes.RoutesRegistrar;
import javaserver.StringModifier;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TrafficCopTest {

    @Before
    public void setUp() throws Exception {
        RoutesRegistrar.getInstance().registerRoute("/", false, true, "GET");
        RoutesRegistrar.getInstance().registerRoute("/logs", true, "GET");
        RoutesRegistrar.getInstance().registerRoute("/method_options", false, "GET", "POST", "OPTIONS");
        RoutesRegistrar.getInstance().registerRoute("/form", false, "DELETE", "POST");
    }

    @Test
    public void testReturnsTheCorrectResponderForGet() {
        Request req = new HttpRequestParser("GET /method_options HTTP/1.1").createRequest();
        Responder responder = new TrafficCop(req, new Logger(), new MockFile()).delegate();
        assertThat(responder, is(instanceOf(GetResponder.class)));
    }

    @Test
    public void testMethodNotAllowedWhenRouteDoesntHaveMethod() {
        Request req = new HttpRequestParser("POST /logs HTTP/1.1").createRequest();
        Responder responder = new TrafficCop(req, new Logger(), new MockFile()).delegate();
        assertThat(responder, is(instanceOf(MethodNotAllowedResponder.class)));
    }

    @Test
    public void testReturnsTheCorrectResponderForUnavailableFile() {
        Request req = new HttpRequestParser("GET /some_file HTTP/1.1").createRequest();
        Responder responder = new TrafficCop(req, new Logger(), new MockFile()).delegate();
        assertThat(responder, is(instanceOf(NotFoundResponder.class)));
    }

    @Test
    public void testReturnsTheCorrectResponderForAvailableFile() {
        Request req = new HttpRequestParser("GET /file1 HTTP/1.1").createRequest();

        MockFile file = new MockFile();
        file.setFileAvailability(true);
        Responder responder = new TrafficCop(req, new Logger(), file).delegate();
        assertThat(responder, is(instanceOf(FileResponder.class)));
    }

    @Test
    public void testReturnsTheCorrectResponderForRoot() {
        Request req = new HttpRequestParser("GET / HTTP/1.1").createRequest();
        Responder responder = new TrafficCop(req, new Logger(), new MockFile()).delegate();
        assertThat(responder, is(instanceOf(RootResponder.class)));

    }

    @Test
    public void testReturnsTheCorrectResponderForPost() {
        Request req = new HttpRequestParser("POST /form HTTP/1.1").createRequest();
        Responder responder = new TrafficCop(req, new Logger(), new MockFile()).delegate();
        assertThat(responder, is(instanceOf(PostResponder.class)));
    }

    @Test
    public void testReturnsFourOhFourForUnrecognizedRoute() {
        Request req = new HttpRequestParser("GET /foobar HTTP/1.1").createRequest();
        MockFile file = new MockFile();
        file.setFileAvailability(false);
        Responder responder = new TrafficCop(req, new Logger(), file).delegate();
        assertThat(responder, is(instanceOf(NotFoundResponder.class)));
    }

    @Test
    public void testReturnsUnauthorizedForWrongCredentials() {
        Request req = new HttpRequestParser("GET /logs HTTP/1.1").createRequest();
        Responder responder = new TrafficCop(req, new Logger(), new MockFile()).delegate();
        assertThat(responder, is(instanceOf(UnauthorizedResponder.class)));
    }

    @Test
    public void testReturnsAuthorizedGetWithCredentials() {
        String request = "GET /logs HTTP/1.1" + StringModifier.EOL;
        request += "Authorization: Basic YWRtaW46aHVudGVyMg==" + StringModifier.EOL;
        Request req = new HttpRequestParser(request).createRequest();
        Responder responder = new TrafficCop(req, new Logger(), new MockFile()).delegate();
        assertThat(responder, is(instanceOf(GetResponder.class)));
    }

    @Test
    public void testReturnsOptionsResponder() {
        String request = "OPTIONS /method_options HTTP/1.1" + StringModifier.EOL;
        Request req = new HttpRequestParser(request).createRequest();
        Responder responder = new TrafficCop(req, new Logger(), new MockFile()).delegate();
        assertThat(responder, is(instanceOf(OptionsResponder.class)));
    }

    @Test
    public void testDeleteResponder() {
        String request = "DELETE /form HTTP/1.1" + StringModifier.EOL;
        Request req = new HttpRequestParser(request).createRequest();
        Responder responder = new TrafficCop(req, new Logger(), new MockFile()).delegate();
        assertThat(responder, is(instanceOf(DeleteResponder.class)));
    }
}
