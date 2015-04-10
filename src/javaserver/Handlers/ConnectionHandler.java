package javaserver.Handlers;

import javaserver.Requests.HttpRequestParser;
import javaserver.Requests.Logger;
import javaserver.Requests.Request;
import javaserver.Requests.TrafficCop;
import javaserver.Responses.HttpResponseBuilder;
import javaserver.Responses.Responders.Responder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler extends Thread {

    private PrintWriter writer;
    private Socket socket;
    private Logger logger;
    private BufferedReader reader;

    public ConnectionHandler(Socket socket, Logger logger) throws Exception {
        this.socket = socket;
        this.logger = logger;
        reader = new BufferedReader(new InputStreamReader((socket.getInputStream())));
        writer = new PrintWriter(socket.getOutputStream());
    }

    public void run() {
        try {
            String requestString = "";

            while (reader.ready() || requestString.length() == 0) {
                requestString += (char) reader.read();
            }

            System.out.println(requestString);
            System.out.println("");
            Request request = new HttpRequestParser(requestString).createRequest();
            logger.addLog(request.statusCode());
            Responder responder = new TrafficCop(request, logger).delegate();
            HttpResponseBuilder responseBuilder = new HttpResponseBuilder(responder);

            writer.write(responseBuilder.response().toCharArray());
            writer.close();
            reader.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
