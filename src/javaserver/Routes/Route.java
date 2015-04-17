package javaserver.Routes;

import javaserver.Responses.Responders.Responder;
import javaserver.Responses.Responders.RootResponder;

import java.util.*;
import java.util.stream.Collectors;

public class Route {

    private final Map<String, Responder> config;
    private Map<String, String> currentParams;

    private String path;
    private boolean auth;
    private boolean root;

    public Route(String path, boolean auth, boolean root) {
        this.path = path;
        this.auth = auth;
        this.root = root;
        currentParams = new LinkedHashMap<>();
        config = null;
    }

    public Route(String path, boolean auth) {
        this.path = path;
        this.auth = auth;
        this.root = false;
        this.config = null;
        currentParams = new LinkedHashMap<>();
    }

    public Route(String path, boolean auth, boolean root, Map<String, Responder> config) {
        this.path = path;
        this.auth = auth;
        this.root = root;
        this.config = config;
        currentParams = new LinkedHashMap<>();

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
        return currentParams;
    }

    public void setCurrentParams(Map<String, String> currentParams) {
        this.currentParams = currentParams;
    }

    public void resetParams() {
        currentParams = new LinkedHashMap<>();
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
        return currentParams.isEmpty();
    }
}
