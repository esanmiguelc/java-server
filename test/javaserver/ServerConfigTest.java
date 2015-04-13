package javaserver;

import javaserver.config.ServerConfig;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ServerConfigTest {
    @Test
    public void testPortDefaultsTo5000() {
        String[] args = new String[2];
        args[0] = "";
        args[1] = "";
        ServerConfig config = new ServerConfig(args);
        assertEquals(5000, config.getPort());
    }

    @Test
    public void testChangePortWhenPassedCorrectParams() {
        String[] args = new String[2];
        args[0] = "-p";
        args[1] = "1337";
        ServerConfig config = new ServerConfig(args);
        assertEquals(1337, config.getPort());
    }

    @Test
    public void testGetRootDirectory() {
        String[] args = new String[2];
        String directory = "some/root/dir";
        args[0] = "-d";
        args[1] = directory;
        ServerConfig config = new ServerConfig(args);
        assertEquals(directory, config.getDirectory());
    }

    @Test
    public void testGetDefaultDirectory() {
        String[] args = new String[2];
        String directory = System.getProperty("user.dir") + "/cob_spec/public";
        args[0] = "";
        args[1] = "";
        ServerConfig config = new ServerConfig(args);
        assertEquals(directory, config.getDirectory());
    }
}
