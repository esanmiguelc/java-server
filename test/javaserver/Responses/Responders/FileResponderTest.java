package javaserver.Responses.Responders;

import javaserver.FileReader;
import javaserver.MockRequest;
import javaserver.MyFileReader;
import javaserver.Requests.Request;
import javaserver.Routes.Route;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FileResponderTest {

    @Test
    public void testReadsAllTheContentsOfFile() {
        String path = "test/javaserver/sample_file.txt";
        FileReader file = new MyFileReader(path);
        Route route = new Route("/sample_file.txt", false, false, new HashMap<>());
        Request request = new MockRequest();

        Responder responder = new FileResponder(file).execute(route, request);

        assertThat(responder.contentBody(), is(equalTo("Sample Text")));
    }
}
