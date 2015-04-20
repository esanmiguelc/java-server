package javaserver;

import javaserver.Responses.Responders.Responder;
import javaserver.Routes.Route;

import java.util.HashMap;

public class TestHelper {

    public static Route createRoute(String path, boolean auth, boolean root, HashMap<String, Responder> config) {
        return new Route(path, auth, root, config);
    }
}
