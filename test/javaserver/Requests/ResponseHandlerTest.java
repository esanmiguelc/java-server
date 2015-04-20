package javaserver.Requests;

import javaserver.Responses.Responders.*;
import javaserver.Routes.Route;
import javaserver.Routes.RoutesRegistrar;
import javaserver.StringModifier;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResponseHandlerTest {

    private Route formRoute = new Route("/form", false, false, new HashMap<String, Responder>() {{
        put("DELETE", new DeleteResponder());
        put("OPTIONS", new OptionsResponder());
        put("POST", new PostResponder());
    }});
    private Route methodOptions = new Route("/method_options", false, false, new HashMap<String, Responder>() {{
        put("GET", new GetResponder());
        put("OPTIONS", new OptionsResponder());
    }});

    @Before
    public void setUp() throws Exception {
        RoutesRegistrar.getInstance().registerRoute(new Route("/", false, true, new HashMap<String, Responder>() {{
            put("GET", new RootResponder());
        }}));
        RoutesRegistrar.getInstance().registerRoute(new Route("/logs", true, false, new HashMap<String, Responder>() {{
            put("GET", new GetResponder());
        }}));

        RoutesRegistrar.getInstance().registerRoute(methodOptions);
        RoutesRegistrar.getInstance().registerRoute(formRoute);
    }

    @Test
    public void testReturnsTheCorrectResponderForGet() {
        HttpRequest req = new HttpRequestParser("GET /method_options HTTP/1.1").createRequest();
        Responder responder = new ResponseHandler(req, new MockFile()).delegate();
        responder.execute(methodOptions, req);
        assertThat(responder, is(instanceOf(GetResponder.class)));
    }

    @Test
    public void testMethodNotAllowedWhenRouteDoesntHaveMethod() {
        HttpRequest req = new HttpRequestParser("POST /logs HTTP/1.1").createRequest();
        Responder responder = new ResponseHandler(req, new MockFile()).delegate();
        assertThat(responder, is(instanceOf(MethodNotAllowedResponder.class)));
    }

    @Test
    public void testReturnsTheCorrectResponderForUnavailableFile() {
        HttpRequest req = new HttpRequestParser("GET /some_file HTTP/1.1").createRequest();
        Responder responder = new ResponseHandler(req, new MockFile()).delegate();
        assertThat(responder, is(instanceOf(NotFoundResponder.class)));
    }

    @Test
    public void testReturnsTheCorrectResponderForAvailableFile() {
        HttpRequest req = new HttpRequestParser("GET /file1 HTTP/1.1").createRequest();

        MockFile file = new MockFile();
        file.setFileAvailability(true);
        Responder responder = new ResponseHandler(req, file).delegate();
        assertThat(responder, is(instanceOf(FileResponder.class)));
    }

    @Test
    public void testReturnsTheCorrectResponderForRoot() {
        HttpRequest req = new HttpRequestParser("GET / HTTP/1.1").createRequest();
        Responder responder = new ResponseHandler(req, new MockFile()).delegate();
        assertThat(responder, is(instanceOf(RootResponder.class)));

    }

    @Test
    public void testFileOnlyRespondsToGet() {
        HttpRequest req = new HttpRequestParser("POST /file1 HTTP/1.1").createRequest();
        MockFile file = new MockFile();
        file.setFileAvailability(true);
        Responder responder = new ResponseHandler(req, file).delegate();
        assertThat(responder, is(instanceOf(MethodNotAllowedResponder.class)));
    }

    @Test
    public void testReturnsTheCorrectResponderForPost() {
        HttpRequest req = new HttpRequestParser("POST /form HTTP/1.1").createRequest();
        Responder responder = new ResponseHandler(req, new MockFile()).delegate();
        assertThat(responder, is(instanceOf(PostResponder.class)));
    }

    @Test
    public void testReturnsFourOhFourForUnrecognizedRoute() {
        HttpRequest req = new HttpRequestParser("GET /foobar HTTP/1.1").createRequest();
        MockFile file = new MockFile();
        file.setFileAvailability(false);
        Responder responder = new ResponseHandler(req, file).delegate();
        assertThat(responder, is(instanceOf(NotFoundResponder.class)));
    }

    @Test
    public void testReturnsUnauthorizedForWrongCredentials() {
        HttpRequest req = new HttpRequestParser("GET /logs HTTP/1.1").createRequest();
        Responder responder = new ResponseHandler(req, new MockFile()).delegate();
        assertThat(responder, is(instanceOf(UnauthorizedResponder.class)));
    }

    @Test
    public void testReturnsAuthorizedGetWithCredentials() {
        String request = "GET /logs HTTP/1.1" + StringModifier.EOL;
        request += "Authorization: Basic YWRtaW46aHVudGVyMg==" + StringModifier.EOL;
        HttpRequest req = new HttpRequestParser(request).createRequest();
        Responder responder = new ResponseHandler(req, new MockFile()).delegate();
        assertThat(responder, is(instanceOf(GetResponder.class)));
    }

    @Test
    public void testReturnsOptionsResponder() {
        String request = "OPTIONS /method_options HTTP/1.1" + StringModifier.EOL;
        HttpRequest req = new HttpRequestParser(request).createRequest();
        Responder responder = new ResponseHandler(req, new MockFile()).delegate();
        assertThat(responder, is(instanceOf(OptionsResponder.class)));
    }

    @Test
    public void testDeleteResponder() {
        String request = "DELETE /form HTTP/1.1" + StringModifier.EOL;
        HttpRequest req = new HttpRequestParser(request).createRequest();
        Responder responder = new ResponseHandler(req, new MockFile()).delegate();
        assertThat(responder, is(instanceOf(DeleteResponder.class)));
    }
}
