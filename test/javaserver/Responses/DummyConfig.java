package javaserver.Responses;

import javaserver.config.Configuration;

public class DummyConfig implements Configuration {

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public String getDirectory() {
        return null;
    }
}
