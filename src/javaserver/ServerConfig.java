package javaserver;

import java.util.Arrays;
import java.util.List;

public class ServerConfig {

    private List<String> args;

    public ServerConfig(String[] args) {
        this.args = Arrays.asList(args);
    }

    public int getPort() {
        String portCommand = "-p";
        if (args.contains(portCommand))
            return Integer.valueOf(args.get(args.indexOf(portCommand) + 1));
        return 5000;
    }

    public String getDirectory() {
        String directoryCommand = "-d";
        if (args.contains(directoryCommand))
            return args.get(args.indexOf(directoryCommand) + 1);
        return "/";
    }
}
