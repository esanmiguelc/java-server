package javaserver.Handlers;

import javaserver.Requests.HttpRequestParser;
import javaserver.Responses.HttpResponseHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler extends Thread {

    private PrintWriter writer;
    private Socket socket;
    private BufferedReader reader;

    public ConnectionHandler(Socket socket) throws Exception {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader((socket.getInputStream())));
        writer = new PrintWriter(socket.getOutputStream());
    }

    public void run() {
        try {
            String requestString = "";

            while (reader.ready() || requestString.length() == 0)
                requestString += (char) reader.read();

            System.out.println(requestString);
            HttpRequestParser requestParser = new HttpRequestParser(requestString);
            HttpResponseHandler responseHandler = new HttpResponseHandler(requestParser);

            writer.write(responseHandler.statusLine().toCharArray());
            writer.close();
            reader.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
