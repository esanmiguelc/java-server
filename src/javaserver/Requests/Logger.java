package javaserver.Requests;

public class Logger {

    private String string = "";

    public String logs() {
        return string;
    }

    public void addLog(String request) {
        string += request + "\r\n";
    }
}
