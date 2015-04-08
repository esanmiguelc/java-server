package javaserver.config;

import javaserver.Routes.RoutesRegistrar;

public class RoutesConfig {
    public static void seedRoutes() {
        RoutesRegistrar.getInstance().registerRoute("/",false);
        RoutesRegistrar.getInstance().registerRoute("/form", false);
        RoutesRegistrar.getInstance().registerRoute("/logs", true);
        RoutesRegistrar.getInstance().registerRoute("/method_options", false, "GET","HEAD","POST","OPTIONS","PUT");
    }
}
