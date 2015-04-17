package javaserver.Responses.Responders;

import javaserver.FileReader;
import javaserver.MockRequest;
import javaserver.Requests.Logger;
import javaserver.Requests.MockFile;
import javaserver.Routes.Route;
import javaserver.StringModifier;
import org.junit.Test;

import java.util.ArrayList;
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
            put("data", "fatcat");
        }});
        MockFile file = new MockFile();
        file.setFileAvailability(false);
        GetResponder responder = new GetResponder(route, new Logger());
        responder.execute(route, new MockRequest());
        assertThat(responder.contentBody(), is(equalTo("data=fatcat" + StringModifier.EOL)));
    }

    @Test
    public void testShowsMultipleParams() {
        Route route = new Route("/", false, false, new HashMap<>());
        route.setCurrentParams(new LinkedHashMap<String, String>() {{
            put("data", "fatcat");
            put("other", "data");
        }});
        GetResponder responder = new GetResponder(route, new Logger());
        String data = "data=fatcat" + StringModifier.EOL;
        data += "other=data" + StringModifier.EOL;
        responder.execute(route, new MockRequest());
        assertThat(responder.contentBody(), is(equalTo(data)));
    }

    @Test
    public void testLoggerRouteReturnsLogs() {
        Route route = new Route("/logs", false, false, new HashMap<>());
        Logger logger = new Logger();
        logger.addLog("GET /logs HTTP/1.1");
        MockFile file = new MockFile();
        GetResponder responder = new GetResponder(route, logger);
        String data = "GET /logs HTTP/1.1" + StringModifier.EOL;
        responder.execute(route, new MockRequest());
        assertThat(responder.contentBody(), is(equalTo(data)));
    }
}
