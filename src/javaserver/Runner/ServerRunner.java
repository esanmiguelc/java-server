package javaserver.Runner;

import javaserver.Handlers.ConnectionHandler;
import javaserver.Requests.Logger;
import javaserver.config.ServerConfig;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerRunner {

    private ServerConfig config;
    private Logger logger;
    private ServerSocket serverSocket;

    public ServerRunner(ServerConfig config, Logger logger) {
        this.config = config;
        this.logger = logger;
    }

    public void run() throws Exception {
        System.out.println("Server is starting...");
        serverSocket = new ServerSocket(config.getPort());
        System.out.println("Listening on port: " + config.getPort());

        acceptRequests();
    }

    private void acceptRequests() throws Exception {
        while(true) {
            Socket socket = serverSocket.accept();

            ConnectionHandler handler = new ConnectionHandler(socket, logger, config.getDirectory());
            handler.start();
        }
    }

}
