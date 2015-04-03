package javaserver.Requests;

import javaserver.Routes.RoutesRegistrar;
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
        assertThat(handler.availableMethods(), is(equalTo("Allow: GET,POST,OPTIONS\r\n")));
    }

    @Test
    public void testContentsForUnauthorized() {
        String msg = "Authentication required";
        HttpRequestParser parser = new HttpRequestParser("OPTIONS /logs HTTP/1.1");
        RequestHandler handler = new RequestHandler(parser);
        assertThat(handler.content(), is(equalTo(msg)));

    }
}
