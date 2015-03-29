package javaserver.Runner;

import javaserver.Handlers.ConnectionHandler;
import javaserver.config.ServerConfig;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerRunner {

    private ServerConfig config;
    private ServerSocket serverSocket;

    public ServerRunner(ServerConfig config) {
        this.config = config;
    }

    public void run() throws Exception {
        serverSocket = new ServerSocket(config.getPort());

        acceptRequests();
    }

    private void acceptRequests() throws Exception {
        while(true) {
            Socket socket = serverSocket.accept();

            ConnectionHandler handler = new ConnectionHandler(socket, config);
            handler.start();
        }
    }

}
