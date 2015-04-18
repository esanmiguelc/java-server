package javaserver.Responses;

import javaserver.Routes.Route;
import javaserver.Routes.RoutesRegistrar;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

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
        assertThat(routes.routes().isEmpty(), is(equalTo(true)));
    }

    @Test
    public void testIsNotEmpty() {
        routes.registerRoute(new Route("/", false, false, new HashMap<>()));

        assertThat(routes.routes().isEmpty(), is(equalTo(false)));
    }

    @Test
    public void testDoesNotContainSpecificRoute() {
        String path = "/";
        assertThat(routes.containsRoute(path), is(equalTo(false)));
    }

    @Test
    public void testContainsSpecificRoute() {
        String path = "/";
        routes.registerRoute(new Route("/", false, false, new HashMap<>()));
        assertThat(routes.containsRoute(path), is(equalTo(true)));
    }

    @Test
    public void testFindRoute() {
        String path = "/";
        routes.registerRoute(new Route("/", false, false, new HashMap<>()));
        assertThat(routes.containsRoute(path), is(equalTo(true)));
    }

    @Test
    public void testGetRoute() {
        String path = "/";
        routes.registerRoute(new Route("/", false, false, new HashMap<>()));
        assertThat(routes.getRoute(path).getPath(), is(equalTo(path)));
    }
}
