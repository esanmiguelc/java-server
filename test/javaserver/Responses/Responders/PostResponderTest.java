package javaserver.Responses.Responders;

import javaserver.Routes.Route;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PostResponderTest {
    @Test
    public void testSavesParamsOnRoute() {
        Route route = new Route("/form", false, Arrays.asList("GET"));
        Map<String, String> params = new HashMap<String, String>(){{
            put("data", "fatcat");
        }};

        new PostResponder(route, params);
        assertThat(route.getParams(), is(equalTo(params)));
    }
}
