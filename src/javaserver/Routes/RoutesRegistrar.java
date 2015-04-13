package javaserver.Routes;

import java.util.*;

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

    public void registerRoute(String path, boolean auth, String... methods) {
        routes.put(path, new Route(path, auth, Arrays.asList(methods)));
    }

    public void registerRoute(String path, boolean auth, boolean root, String... methods) {
        routes.put(path, new Route(path, auth, Arrays.asList(methods), root));
    }

    public boolean isSecured(String path) {
        return routes.get(path).isSecured();
    }

    public void reset() {
        this.routes = new HashMap<>();
    }
}
