package javaserver.config;

import javaserver.MyFileReader;
import javaserver.Responses.Responders.*;
import javaserver.Routes.Route;
import javaserver.Routes.RoutesRegistrar;

import java.util.HashMap;

public class RoutesConfig {
    public static void seedRoutes() {
        RoutesRegistrar.getInstance().registerRoute(new Route("/", false, true, new HashMap<String, Responder>() {{
            put("GET", new RootResponder(new MyFileReader()));
        }}));
        RoutesRegistrar.getInstance().registerRoute(new Route("/form", false, false, new HashMap<String, Responder>() {{
            put("GET", new GetResponder());
            put("POST", new PostResponder());
            put("PUT", new PostResponder());
            put("OPTIONS", new OptionsResponder());
            put("DELETE", new DeleteResponder());
        }}));
        RoutesRegistrar.getInstance().registerRoute(new Route("/logs", true, false, new HashMap<String, Responder>() {{
            put("GET", new GetResponder());
        }}));
        RoutesRegistrar.getInstance().registerRoute(new Route("/method_options", false, false, new HashMap<String, Responder>() {{
            put("GET", new GetResponder());
            put("HEAD", new GetResponder());
            put("POST", new PostResponder());
            put("OPTIONS", new OptionsResponder());
            put("PUT", new PostResponder());
        }}));
    }
}
