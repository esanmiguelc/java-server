package javaserver.Responses;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RoutesRegistrarTest {

    private RoutesRegistrar routes = RoutesRegistrar.getInstance();

    @Before
    public void setUp() {
        routes.reset();
    }

    @Test
    public void testNoRoutes() {
        assertThat(routes.getRoutes().isEmpty(), is(equalTo(true)));
    }

    @Test
    public void testIsNotEmpty() {
        routes.registerRoute("/");
        assertThat(routes.getRoutes().isEmpty(), is(equalTo(false)));
    }

    @Test
    public void testDoesNotContainSpecificRoute() {
        String path = "/";
        assertThat(routes.containsRoute(path),is(equalTo(false)));
    }

    @Test
    public void testContainsSpecificRoute() {
        String path = "/";
        routes.registerRoute(path);
        assertThat(routes.containsRoute(path),is(equalTo(true)));
    }
}
