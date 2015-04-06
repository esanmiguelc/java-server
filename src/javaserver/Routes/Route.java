package javaserver.Routes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Route {

    private Map<String, String> currentParams;

    private String path;
    private boolean auth;
    private List<String> methods;
    public Route(String path, boolean auth, List<String> methods) {
        this.path = path;
        this.auth = auth;
        this.methods = methods;
        this.currentParams = new HashMap<>();
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

    public Map<String, String> getParams() {
        return currentParams;
    }

    public void setCurrentParams(Map<String, String> currentParams) {
        this.currentParams = currentParams;
    }
}
