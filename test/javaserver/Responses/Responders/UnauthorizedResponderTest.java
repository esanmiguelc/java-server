package javaserver.Responses.Responders;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UnauthorizedResponderTest {
    @Test
    public void testReturnsStatusCodeCorrectly() {
        Responder responder = new UnauthorizedResponder();
        assertThat(responder.statusCode(), is(equalTo("401 Unauthorized")));
    }

    @Test
    public void testReturnsContentCorrectly() {
        Responder responder = new UnauthorizedResponder();
        assertThat(responder.contentBody(), is(equalTo("Authentication required")));
    }
}
