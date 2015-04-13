package javaserver.config;

import java.util.Arrays;
import java.util.List;

public class ServerConfig implements Configuration {

    private List<String> args;

    public ServerConfig(String[] args) {
        this.args = Arrays.asList(args);
    }

    @Override
    public int getPort() {
        String portCommand = "-p";
        if (args.contains(portCommand))
            return Integer.valueOf(args.get(args.indexOf(portCommand) + 1));
        return 5000;
    }

    @Override
    public String getDirectory() {
        String directoryCommand = "-d";
        if (args.contains(directoryCommand))
            return args.get(args.indexOf(directoryCommand) + 1);
        return System.getProperty("user.dir") + "/cob_spec/public";
    }
}
