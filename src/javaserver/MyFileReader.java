package javaserver;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

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
    public Stream<Path> getDirectoryContents() {
        Stream<Path> list = null;
        try {
            list = Files.list(getDirectory());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    private Path getDirectory() {
        return path.toAbsolutePath();
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

    public String mimeType() {
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        return mimetypesFileTypeMap.getContentType(path.toFile());
    }

    @Override
    public boolean isDirectory() {
        return Files.isDirectory(path);
    }
}
