package javaserver;

public interface FileReader {
    boolean exists();

    void process();

    boolean isDirectory();
}
