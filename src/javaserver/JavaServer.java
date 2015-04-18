package javaserver;

import javaserver.Requests.Logger;
import javaserver.Runner.ServerRunner;
import javaserver.config.RoutesConfig;
import javaserver.config.ServerConfig;

public class JavaServer {
    public static void main(String[] args) throws Exception {
        Logger logger = new Logger();
        ServerConfig config = new ServerConfig(args);
        ServerRunner server = new ServerRunner(config, logger);
        RoutesConfig.seedRoutes(config, logger);

        server.run();
    }
}
