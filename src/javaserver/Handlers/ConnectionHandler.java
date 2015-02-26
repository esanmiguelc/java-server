package javaserver.Handlers;

import javaserver.Requests.HttpRequestParser;
import javaserver.Responses.GetResponseHandler;
import javaserver.Responses.HttpResponseHandler;
import javaserver.Responses.PostResponseHandler;
import javaserver.config.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler extends Thread {

    private PrintWriter writer;
    private Socket socket;
    private BufferedReader reader;
    private Configuration config;

    public ConnectionHandler(Socket socket, Configuration config) throws Exception {
        this.socket = socket;
        this.config = config;
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
            HttpResponseHandler responseHandler = null;
            switch (requestParser.getVerb().toUpperCase()) {
                case "GET":
                    responseHandler = new GetResponseHandler(requestParser, config);
                    break;
                case "POST":
                    responseHandler = new PostResponseHandler(requestParser, config);
                    break;
//                case "PUT":
//                    return new PutResponse();
//                    break;
                default:
                    break;
            }

            writer.write(responseHandler.getStatusLine().toCharArray());
            writer.close();
            reader.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
