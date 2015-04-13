package javaserver.Routes;

import java.util.LinkedHashMap;
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


    public Map<String, String> getParams() {
        return currentParams;
    }

    public void setCurrentParams(Map<String, String> currentParams) {
        this.currentParams = currentParams;
    }

    public void resetParams() {
        currentParams = new LinkedHashMap<>();
    }
}
