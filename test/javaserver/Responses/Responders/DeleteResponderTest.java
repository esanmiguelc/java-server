package javaserver.Responses.Responders;

import javaserver.MockRequest;
import javaserver.Requests.Request;
import javaserver.Routes.Route;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeleteResponderTest {

    private final Route route = new Route("/", false, false, new HashMap<>());

    @Test
    public void testReturnsAppropriateStatusCode() {
        Responder responder = new DeleteResponder();
        assertThat(responder.statusCode(), is(equalTo("200 OK")));
    }

    @Test
    public void testReturnsAppropriateContent() {
        Responder responder = new DeleteResponder();
        assertThat(responder.contentBody(), is(equalTo("Content Deleted")));
    }

    @Test
    public void testDeletesTheParamsInARoute() {
        route.setCurrentParams(new HashMap<String, String>() {{
            put("key", "value");
        }});
        Request request = new MockRequest();
        new DeleteResponder().execute(route, request);
        assertThat(route.getParams().isEmpty(), is(equalTo(true)));
    }
}
