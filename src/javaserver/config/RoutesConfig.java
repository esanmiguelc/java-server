package javaserver.config;

import javaserver.Routes.RoutesRegistrar;

public class RoutesConfig {
    public static void seedRoutes() {
        RoutesRegistrar.getInstance().registerRoute("/",false, true, "GET", "OPTIONS");
        RoutesRegistrar.getInstance().registerRoute("/form", false, "GET", "POST", "PUT", "OPTIONS", "DELETE");
        RoutesRegistrar.getInstance().registerRoute("/logs", true, "GET");
        RoutesRegistrar.getInstance().registerRoute("/method_options", false, "GET","HEAD","POST","OPTIONS","PUT");
    }
}
