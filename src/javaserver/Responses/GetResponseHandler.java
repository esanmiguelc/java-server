package javaserver.Responses;

import javaserver.Requests.HttpRequestParser;
import javaserver.config.Configuration;
import javaserver.config.ServerConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GetResponseHandler implements HttpResponseHandler {

    public static final String EOL = "\r\n";
    private HttpRequestParser requestParser;
    private Configuration config;
    private String response = "";

    public GetResponseHandler(HttpRequestParser requestParser, Configuration config) {
        this.requestParser = requestParser;
        this.config = config;
    }

    @Override
    public String getStatusLine() {
        File file = new File(config.getDirectory() + requestParser.getURI());
        try {
            response = "HTTP/1.1 200 OK" + EOL;
            response += "Java Server/1.0" + EOL;
            response += "Content-Type: text/html" + EOL;
            response += "Content-Length: " + file.length() +  EOL;
            response += EOL;
            FileInputStream fileInputStream = new FileInputStream(file);
            int fileChars;
            while ((fileChars = fileInputStream.read()) != -1) {
                response += (char) fileChars;
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
