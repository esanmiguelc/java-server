package javaserver.Responses.Routes;

import javaserver.Responses.Responders.Responder;
import javaserver.Responses.Responders.RootResponder;
import javaserver.Routes.Route;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class RootRouteTest {
    @Test
    public void testHasResponder() {
        final String method = "GET";
        Map<String, Responder> config = new HashMap<String, Responder>() {{
                put(method, new RootResponder());
        }};
        Route route = new Route("/", false, true, config);
        assertThat(route.hasResponder(method), is(equalTo(true)));
    }

    @Test
    public void testGetsCorrectResponder() {
        final String method = "GET";
        Map<String, Responder> config = new HashMap<String, Responder>() {{
            put(method, new RootResponder());
        }};
        Route route = new Route("/", false, true, config);
        assertThat(route.responder(method), is(instanceOf(RootResponder.class)));
    }
}
