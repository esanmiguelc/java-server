package javaserver.Responses.Responders;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotFoundResponderTest {
    @Test
    public void testReturnsAppropriateStatusCode() {
        NotFoundResponder responder = new NotFoundResponder();
        assertThat(responder.statusCode(), is(equalTo("404 Not Found")));
    }

    @Test
    public void testReturnsAppropriateContent() {
        NotFoundResponder responder = new NotFoundResponder();
        assertThat(responder.contentBody(), is(equalTo("No content found")));
    }
}
