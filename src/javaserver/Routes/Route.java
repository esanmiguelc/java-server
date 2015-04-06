package javaserver.Routes;

import java.util.List;

public class Route {

    private String path;
    private boolean auth;
    private List<String> methods;

    public Route(String path, boolean auth, List<String> methods) {
        this.path = path;
        this.auth = auth;
        this.methods = methods;
    }

    public String getPath() {
        return path;
    }

    public boolean isSecured() {
        return auth;
    }

    public List<String> getMethods() {
        return methods;
    }
}
