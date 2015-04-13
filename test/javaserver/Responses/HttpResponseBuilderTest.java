package javaserver.Responses;

import javaserver.Requests.Logger;
import javaserver.Responses.Responders.GetResponder;
import javaserver.Responses.Responders.Responder;
import javaserver.Routes.Route;
import javaserver.StringModifier;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class HttpResponseBuilderTest {
    @Test
    public void testReturnsTheCorrectResponseHeadersForGet() {
        String response = "HTTP/1.1 200 OK" + StringModifier.EOL;
        response += "Server: Emmanuel's Java Server/1.0"+ StringModifier.EOL;
        response += "Content-Type: text/html"+ StringModifier.EOL;
        response += StringModifier.EOL;
        Route route = new Route("/", false, Arrays.asList("GET"));
        Responder responder = new GetResponder(route, new Logger());
        HttpResponseBuilder builder = new HttpResponseBuilder(responder);
        assertThat(builder.headers(), is(equalTo(response)));
    }
}
