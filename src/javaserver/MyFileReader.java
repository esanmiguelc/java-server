package javaserver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class MyFileReader implements FileReader {
    private Path path;
    private PrintStream writer;

    public MyFileReader(Path path, PrintStream writer) {
        this.path = path;
        this.writer = writer;
    }

    @Override
    public boolean exists() {
        return Files.exists(path);
    }

    @Override
    public void process() {
        InputStream file;
        try {
            file = new FileInputStream(path.toString());
            byte[] b = new byte[4096];
            while(file.read( b ) > 0 ) {
                writer.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isDirectory() {
        return Files.isDirectory(path);
    }
}
