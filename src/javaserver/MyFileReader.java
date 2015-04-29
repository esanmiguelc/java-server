package javaserver;

import javaserver.Parser.RangeParser;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class MyFileReader implements FileReader {

    private File file;
    private Path path;
    private PrintStream writer;

    public MyFileReader(Path path, PrintStream writer) {
        this.path = path;
        this.file = new File(path.toString());
        this.writer = writer;
    }

    public MyFileReader(String directory) {
        this.path = Paths.get(directory);
        file = new File(path.toString());
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
    public void path(String path) {
        this.path = Paths.get(path);
    }

    @Override
    public void process() {
        InputStream file;
        try {
            file = new FileInputStream(path.toString());
            byte[] b = new byte[size()];
            while (file.read(b) > 0) {
                writer.write(b);
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(RangeParser rangeParser) {
        InputStream file;
        try {
            file = new FileInputStream(path.toString());
            byte[] b = new byte[size()];
            while (file.read(b) > 0) {
                if (!rangeParser.hasStart()) {
                    writer.write(b, size() - rangeParser.getEnd(), rangeParser.getEnd());
                } else if (!rangeParser.hasEnd()) {
                    Integer start = rangeParser.getStart();
                    writer.write(b, start, size() - start);
                } else {
                    writer.write(b, rangeParser.getStart(), rangeParser.getEnd() + 1);
                }
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String mimeType() {
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        return mimetypesFileTypeMap.getContentType(path.toFile());
    }

    @Override
    public Integer size() {
        return (int) file.length();
    }

    @Override
    public boolean isDirectory() {
        return Files.isDirectory(path);
    }
}
