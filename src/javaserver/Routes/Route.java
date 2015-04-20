package javaserver.Routes;

import javaserver.Responses.Responders.Responder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Route {

    private final Map<String, Responder> config;
    private Map<String, String> currentParams = new LinkedHashMap<>();

    private String path;
    private boolean auth;
    private boolean root;

    public Route(String path, boolean auth, boolean root) {
        this.path = path;
        this.auth = auth;
        this.root = root;
        config = null;
    }

    public Route(String path, boolean auth) {
        this.path = path;
        this.auth = auth;
        this.root = false;
        this.config = null;
    }

    public Route(String path, boolean auth, boolean root, Map<String, Responder> config) {
        this.path = path;
        this.auth = auth;
        this.root = root;
        this.config = config;
    }

    public String getPath() {
        return path;
    }

    public boolean isSecured() {
        return auth;
    }

    public List<String> getMethods() {
        return config.keySet().stream().collect(Collectors.toList());
    }

    public boolean isRoot() {
        return root;
    }

    public Map<String, String> getParams() {
        return this.currentParams;
    }

    public void setCurrentParams(Map<String, String> currentParams) {
        this.currentParams = currentParams;
    }

    public void resetParams() {
        this.currentParams.clear();
    }

    public boolean hasMethod(String httpMethod) {
        return config.containsKey(httpMethod);
    }

    public boolean hasResponder(String method) {
        return config.containsKey(method);
    }

    public Responder responder(String method) {
        return config.get(method);
    }

    public boolean hasParams() {
        return !currentParams.isEmpty();
    }
}
