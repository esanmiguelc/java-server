package javaserver.Requests;

import javaserver.FileReader;
import javaserver.Parser.RangeParser;

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
    public void write() {

    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public String mimeType() {
        return null;
    }

    @Override
    public Integer size() {
        return 0;
    }

    @Override
    public void write(RangeParser rangeParser) {

    }

    @Override
    public void write(String content) {

    }
}
