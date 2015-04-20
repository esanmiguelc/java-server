package javaserver.Responses.Responders;

import javaserver.MockRequest;
import javaserver.Requests.MockFile;
import javaserver.Requests.ResponseHandler;
import javaserver.Routes.Route;
import javaserver.Routes.RoutesRegistrar;
import org.junit.Test;
import java.util.LinkedHashMap;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SampleResponderTest {
    @Test
    public void testReturnsTheAppropriateStatusCode() {
        RoutesRegistrar.getInstance().registerRoute(new Route("/sample", false, false, new LinkedHashMap<String, Responder>() {{
            put("GET", new SampleResponder());
        }}));
        MockRequest request = new MockRequest();
        request.setMethod("GET");
        request.setURI("/sample");
        ResponseHandler handler = new ResponseHandler(request, new MockFile());
        Responder delegate = handler.delegate();
        assertThat(delegate.contentBody(), is(equalTo("Sample Content")));
    }
}
