package javaserver.Routes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Route {

    private Map<String, String> currentParams;

    private String path;
    private boolean auth;
    private List<String> methods;
    private boolean root;

    public Route(String path, boolean auth, List<String> methods, boolean root) {
        this.path = path;
        this.auth = auth;
        this.methods = methods;
        this.root = root;
        currentParams = new LinkedHashMap<>();
    }

    public Route(String path, boolean auth, List<String> methods) {
        this.path = path;
        this.auth = auth;
        this.root = false;
        this.methods = methods;
        currentParams = new LinkedHashMap<>();
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

    public boolean isRoot() {
        return root;
    }

    public Map<String, String> getParams() {
        return currentParams;
    }

    public void setCurrentParams(Map<String, String> currentParams) {
        this.currentParams = currentParams;
    }

    public void resetParams() {
        currentParams = new LinkedHashMap<>();
    }

    public boolean hasMethod(String httpMethod) {
        return methods.contains(httpMethod);
    }
}
