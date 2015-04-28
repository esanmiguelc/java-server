package javaserver.Requests;

import javaserver.FileReader;

import java.nio.file.Path;
import java.util.stream.Stream;

public class MockFile implements FileReader {

    private static boolean fileAvailability = false;

    public void setFileAvailability(boolean fileAvailability) {
        MockFile.fileAvailability = fileAvailability;
    }

    @Override
    public boolean exists() {
        return fileAvailability;
    }

    @Override
    public Stream<Path> getDirectoryContents() {
        return null;
    }

    @Override
    public void path(String path) {

    }

    @Override
    public byte[] process() {
        return new byte[0];
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public String mimeType() {
        return null;
    }
}
