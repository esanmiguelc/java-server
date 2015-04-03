package javaserver.Requests;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoggerTest {
    @Test
    public void testHasNoLogs() {
        Logger logger = new Logger();
        assertThat(logger.logs(), is(equalTo("")));
    }

    @Test
    public void testHasOneLog() {
        Logger logger = new Logger();
        logger.addLog("GET /Something HTTP/1.1");
        assertThat(logger.logs(), is(equalTo("GET /Something HTTP/1.1\r\n")));
    }
}
