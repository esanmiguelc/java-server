package javaserver.Handlers;

import javaserver.MyFileReader;
import javaserver.Requests.HttpRequestParser;
import javaserver.Requests.Logger;
import javaserver.Requests.Request;
import javaserver.Requests.TrafficCop;
import javaserver.Responses.HttpResponseBuilder;
import javaserver.Responses.Responders.Responder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConnectionHandler extends Thread {

    private PrintStream writer;
    private Socket socket;
    private Logger logger;
    private String publicPath;
    private BufferedReader reader;

    public ConnectionHandler(Socket socket, Logger logger, String publicPath) throws Exception {
        this.socket = socket;
        this.logger = logger;
        this.publicPath = publicPath;
        reader = new BufferedReader(new InputStreamReader((socket.getInputStream())));
        writer = new PrintStream(socket.getOutputStream());
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
            Path path = Paths.get(publicPath + request.getUri());
//            writer.print("HTTP/1.0 200 OK\r\n"+
//                    "Content-type: image/jpeg\r\n\r\n");
            MyFileReader fileReader = new MyFileReader(path, writer);
            Responder responder = new TrafficCop(request, logger, fileReader).delegate();
            HttpResponseBuilder responseBuilder = new HttpResponseBuilder(responder);

            writer.print(responseBuilder.response());
            writer.close();
            reader.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
