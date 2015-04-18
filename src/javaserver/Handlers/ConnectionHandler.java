package javaserver.Handlers;

import javaserver.MyFileReader;
import javaserver.Requests.HttpRequest;
import javaserver.Requests.HttpRequestParser;
import javaserver.Requests.Logger;
import javaserver.Requests.ResponseHandler;
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
            HttpRequest request = new HttpRequestParser(requestString).createRequest();
            logger.addLog(request.statusCode());
            Path path = Paths.get(publicPath + request.getUri());
            MyFileReader fileReader = new MyFileReader(path, writer);
            Responder responder = new ResponseHandler(request, fileReader).delegate();
            HttpResponseBuilder responseBuilder = new HttpResponseBuilder(responder);

            writer.print(responseBuilder.headers());
            writer.print(responseBuilder.responseBody());
            writer.close();
            reader.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
