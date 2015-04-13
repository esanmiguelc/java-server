package javaserver.Requests;

import javaserver.FileReader;

public class MockFile implements FileReader {

    private static boolean fileAvailability = false;

    public void setFileAvailability(boolean fileAvailability) {
        this.fileAvailability = fileAvailability;
    }

    @Override
    public boolean exists() {
        return fileAvailability;
    }

    @Override
    public void process() {
    }

    @Override
    public boolean isDirectory() {
        return false;
    }
}
