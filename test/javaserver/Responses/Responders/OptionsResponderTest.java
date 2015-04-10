package javaserver.Responses.Responders;

import javaserver.Routes.Route;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OptionsResponderTest {

    private final Route route = new Route("/", false, Arrays.asList("GET", "POST", "OPTIONS"));

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
