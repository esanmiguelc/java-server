package javaserver.Responses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoutesRegistrar {


    private static final RoutesRegistrar thisInstance = new RoutesRegistrar();
    private List<String> routes;

    public static RoutesRegistrar getInstance() {
        return thisInstance;
    }

    private RoutesRegistrar() {
        this.routes = new ArrayList<>();
    }

    public List<String> getRoutes() {
        return routes;
    }

    public void registerRoute(String path) {
        routes.add(path);
    }

    public void reset() {
        this.routes = new ArrayList<>();
    }

    public boolean containsRoute(String path) {
        return routes.contains(path);
    }
}
