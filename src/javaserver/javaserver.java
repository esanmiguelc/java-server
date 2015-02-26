package javaserver;

import javaserver.Runner.ServerRunner;
import javaserver.config.ServerConfig;

import java.io.IOException;

public class JavaServer {
    public static void main(String[] args) throws IOException {
        ServerConfig config = new ServerConfig(args);
        ServerRunner server = new ServerRunner(config);

        server.run();
    }
}
