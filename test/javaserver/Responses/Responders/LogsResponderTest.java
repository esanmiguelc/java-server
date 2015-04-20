package javaserver.Responses.Responders;

import javaserver.Requests.Logger;
import javaserver.StringModifier;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LogsResponderTest {

    @Test
    public void testReturnsTheLogs() {
        Logger logger = new Logger();
        String msg = "Some Log";
        logger.addLog(msg);
        LogsResponder logsResponder = new LogsResponder(logger);
        assertThat(logsResponder.contentBody(), is(equalTo(msg + StringModifier.EOL)));
    }
}
