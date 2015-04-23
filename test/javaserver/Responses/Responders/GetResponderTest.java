package javaserver.Responses.Responders;

import javaserver.MockRequest;
import javaserver.Requests.Logger;
import javaserver.Requests.MockFile;
import javaserver.Routes.Route;
import javaserver.StringModifier;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class GetResponderTest {

    @Test
    public void testShowsTheParams() {
        Route route = new Route("/", false, false, new HashMap<>());
        route.setCurrentParams(new LinkedHashMap<String, String>() {{
            put("data", "cat");
        }});
        MockFile file = new MockFile();
        file.setFileAvailability(false);
        GetResponder responder = new GetResponder();
        responder.execute(route, new MockRequest());
        assertThat(responder.contentBody(), is(equalTo("data=cat" + StringModifier.EOL)));
    }

    @Test
    public void testShowsMultipleParams() {
        Route route = new Route("/", false, false, new HashMap<>());
        route.setCurrentParams(new LinkedHashMap<String, String>() {{
            put("data", "fatcat");
            put("other", "data");
        }});
        GetResponder responder = new GetResponder();
        String data = "data=fatcat" + StringModifier.EOL;
        data += "other=data" + StringModifier.EOL;
        responder.execute(route, new MockRequest());
        assertThat(responder.contentBody(), is(equalTo(data)));
    }

    @Test
    public void testGetsUrlParams() {
        Route route = new Route("/", false, false, new HashMap<>());
        route.setCurrentParams(new LinkedHashMap<String, String>() {{
            put("data", "fatcat");
            put("other", "data");
        }});
        GetResponder responder = new GetResponder();
        String data = "data=fatcat" + StringModifier.EOL;
        data += "other=data" + StringModifier.EOL;
        responder.execute(route, new MockRequest());
        assertThat(responder.contentBody(), is(equalTo(data)));
    }
}