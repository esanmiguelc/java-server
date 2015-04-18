package javaserver.Routes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoutesRegistrar {


    private static final RoutesRegistrar thisInstance = new RoutesRegistrar();
    private Map<String, Route> routes;

    public static RoutesRegistrar getInstance() {
        return thisInstance;
    }

    private RoutesRegistrar() {
        this.routes = new HashMap<>();
    }

    public Route getRoute(String path) {
        return routes.get(path);
    }

    public List<Route> routes() {
        return new ArrayList<>(routes.values());
    }

    public boolean containsRoute(String path) {
        return routes.containsKey(path);
    }

    public void reset() {
        this.routes = new HashMap<>();
    }

    public void registerRoute(Route route) {
        routes.put(route.getPath(), route);
    }
}
