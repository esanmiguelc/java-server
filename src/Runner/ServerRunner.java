package Runner;

import javaserver.Handlers.ConnectionHandler;
import javaserver.ServerConfig;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRunner {

    private ServerConfig config;
    private ServerSocket serverSocket;

    public ServerRunner(ServerConfig config) {
        this.config = config;
    }

    public void run() throws IOException {
        serverSocket = new ServerSocket(config.getPort());

        acceptRequests();
    }

    private void acceptRequests() throws IOException {
        while(true) {
            Socket socket = serverSocket.accept();

            ConnectionHandler handler = new ConnectionHandler(socket);
            handler.start();
        }
    }

}
