package javaserver;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileReader {
    boolean exists();

    Stream<Path> getDirectoryContents();

    void process();

    boolean isDirectory();
}
