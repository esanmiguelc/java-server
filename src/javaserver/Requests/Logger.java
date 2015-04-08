package javaserver.Requests;

import javaserver.StringModifier;

public class Logger {

    private String string = "";

    public String logs() {
        return string;
    }

    public void addLog(String request) {
        string += request + StringModifier.EOL;
    }
}
