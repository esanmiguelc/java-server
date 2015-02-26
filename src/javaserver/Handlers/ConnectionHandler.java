package javaserver.Handlers;

import java.net.Socket;

public class ConnectionHandler extends Thread {
    private Socket socket;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    public void start() {
    }
}
