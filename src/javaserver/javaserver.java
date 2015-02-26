package javaserver;

import javaserver.Runner.ServerRunner;
import javaserver.config.ServerConfig;

public class JavaServer {
    public static void main(String[] args) throws Exception {
        ServerConfig config = new ServerConfig(args);
        ServerRunner server = new ServerRunner(config);

        server.run();
    }
}
