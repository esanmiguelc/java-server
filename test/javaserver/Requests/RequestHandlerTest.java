package javaserver.Requests;

import javaserver.Routes.RoutesRegistrar;
import javaserver.StringModifier;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestHandlerTest {

    @Before
    public void setUp() throws Exception {
        RoutesRegistrar.getInstance().registerRoute("/", false);
        RoutesRegistrar.getInstance().registerRoute("/logs", true);
        RoutesRegistrar.getInstance().registerRoute("/method_options", true, "GET", "POST", "OPTIONS");
    }

    @Test
    public void testReturnsCorrectStatusCodeForUnavailableRoute() {
        HttpRequestParser parser = new HttpRequestParser("GET /foobar HTTP/1.1");
        RequestHandler handler = new RequestHandler(parser);
        assertThat(handler.status(), is(equalTo("404 Not Found")));
    }

    @Test
    public void testReturnsCorrectStatusCodeForAvailableRoute() {
        HttpRequestParser parser = new HttpRequestParser("GET / HTTP/1.1");
        RequestHandler handler = new RequestHandler(parser);
        assertThat(handler.status(), is(equalTo("200 OK")));
    }

    @Test
    public void testReturnsNotAuthorizedForLockedRoutes() {
        HttpRequestParser parser = new HttpRequestParser("GET /logs HTTP/1.1");
        RequestHandler handler = new RequestHandler(parser);
        assertThat(handler.status(), is(equalTo("401 Unauthorized")));
    }

    @Test
    public void testHasNoAvailableMethods() {
        HttpRequestParser parser = new HttpRequestParser("GET /logs HTTP/1.1");
        RequestHandler handler = new RequestHandler(parser);
        assertThat(handler.availableMethods(), is(equalTo("")));
    }

    @Test
    public void testReturnsEmptyStringIfRouteDoesntSupportOptions() {
        HttpRequestParser parser = new HttpRequestParser("OPTIONS /logs HTTP/1.1");
        RequestHandler handler = new RequestHandler(parser);
        assertThat(handler.availableMethods(), is(equalTo("")));
    }

    @Test
    public void testReturnsAllowHeaderWithMethods() {
        HttpRequestParser parser = new HttpRequestParser("OPTIONS /method_options HTTP/1.1");
        RequestHandler handler = new RequestHandler(parser);
        assertThat(handler.availableMethods(), is(equalTo("Allow: GET,POST,OPTIONS" + StringModifier.EOL)));
    }

    @Test
    public void testContentsForUnauthorized() {
        String msg = "Authentication required";
        HttpRequestParser parser = new HttpRequestParser("OPTIONS /logs HTTP/1.1");
        RequestHandler handler = new RequestHandler(parser);
        assertThat(handler.content(), is(equalTo(msg)));
    }

    @Test
    public void testNoParams() {
        String request = "GET /form HTTP/1.1";
        HttpRequestParser parser = new HttpRequestParser(request);
        RequestHandler handler = new RequestHandler(parser);
        assertThat(handler.content(), is(equalTo(null)));
    }

    @Test
    public void testDataParams() {
        String postRequest = "POST /form HTTP/1.1";
        String data = "data=fatcat";
        HttpRequestParser postParser = new HttpRequestParser(postRequest);
        RequestHandler gethandler = new RequestHandler(postParser);
        String getRequest = "GET /form HTTP/1.1";
        getRequest += data;
        HttpRequestParser getParser = new HttpRequestParser(getRequest);
        RequestHandler getHandler = new RequestHandler(getParser);

        assertThat(getHandler.content().contains(data), is(equalTo(true)));
    }
}
