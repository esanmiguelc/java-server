package javaserver.Responses;

import javaserver.Requests.Logger;
import javaserver.Responses.Responders.GetResponder;
import javaserver.Routes.Route;
import javaserver.StringModifier;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class GetResponderTest {

    @Test
    public void testShowsTheParams() {
        Route route = new Route("/", false, new ArrayList<>());
        route.setCurrentParams(new LinkedHashMap<String, String>() {{
            put("data", "fatcat");
        }});
        GetResponder responder = new GetResponder(route, new Logger());
        assertThat(responder.contentBody(), is(equalTo("data=fatcat" + StringModifier.EOL)));
    }

    @Test
    public void testShowsMultipleParams() {
        Route route = new Route("/", false, new ArrayList<>());
        route.setCurrentParams(new LinkedHashMap<String, String>() {{
            put("data", "fatcat");
            put("other", "data");
        }});
        GetResponder responder = new GetResponder(route, new Logger());
        String data = "data=fatcat" + StringModifier.EOL;
        data += "other=data" + StringModifier.EOL;
        assertThat(responder.contentBody(), is(equalTo(data)));
    }

    @Test
    public void testLoggerRouteReturnsLogs() {
        Route route = new Route("/logs", false, new ArrayList<>());
        Logger logger = new Logger();
        logger.addLog("GET /logs HTTP/1.1");
        GetResponder responder = new GetResponder(route, logger);
        String data = "GET /logs HTTP/1.1" + StringModifier.EOL;
        assertThat(responder.contentBody(), is(equalTo(data)));
    }
}
