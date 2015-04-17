package javaserver.Responses.Responders;

import javaserver.Routes.Route;
import javaserver.TestHelper;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OptionsResponderTest {

    private final Route route = TestHelper.createRoute("/", false, false, new LinkedHashMap<String, Responder>(){{
        put("GET", new GetResponder());
        put("POST", new PostResponder());
        put("OPTIONS", new OptionsResponder());
    }});

    @Test
    public void testReturnsAppropriateStatusCode() {
        Responder responder = new OptionsResponder(route);
        assertThat(responder.statusCode(), is(equalTo("200 OK")));
    }

    @Test
    public void testReturnsAppropriateContent() {
        Responder responder = new OptionsResponder(route);
        assertThat(responder.contentBody(), is(equalTo("Allow: GET,POST,OPTIONS")));
    }

    @Test
    public void testContainsOptionsHeader() {
        Responder responder = new OptionsResponder(route);
        assertThat(responder.additionalHeaders().contains("Allow: GET,POST,OPTIONS"), is(equalTo(true)));
    }
}
