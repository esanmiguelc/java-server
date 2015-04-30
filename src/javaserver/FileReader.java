package javaserver;

import javaserver.Parser.RangeParser;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileReader {
    boolean exists();

    Stream<Path> getDirectoryContents();

    void path(String path);

    void process();

    boolean isDirectory();

    String mimeType();

    Integer size();

    void process(RangeParser rangeParser);
}
