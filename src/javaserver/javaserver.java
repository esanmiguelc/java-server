package javaserver;

import Runner.ServerRunner;

public class JavaServer {
    public static void main(String[] args) {
        ServerConfig config = new ServerConfig(args);
        ServerRunner server = new ServerRunner(config);

        server.run();
    }
}
