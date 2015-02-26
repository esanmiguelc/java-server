package javaserver.Handlers;

import java.net.Socket;

public class ConnectionHandler extends Thread {

    private Socket socket;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
//        try {
//            String requestString = "";
//
//            while (reader.ready() || requestString.length() == 0)
//                requestString += (char) reader.read();
//
//            HttpRequestParser requestParser = new HttpRequestParser(requestString);
//
//            switch (requestParser.getVerb().toUpperCase()) {
//                case "GET":
//                    return new GetResponseHandler(requestParser, config.getDirectory());
//                    break;
//                case "POST":
//                    return new PostResponse();
//                    break;
//                case "PUT":
//                    return new PutResponse();
//                    break;
//                default:
//                    break;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
