package javaserver.Parser;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RangeParserTest {
    @Test
    public void testParsesARangeWithStartAndEnd() {
        String rangeHeaderValue = "bytes=0-4";
        RangeParser range = new RangeParser(rangeHeaderValue);
        assertThat(range.getStart(), is(equalTo(0)));
        assertThat(range.getEnd(), is(equalTo(4)));
    }

    @Test
    public void testHasStart() {
        String rangeHeaderValue = "bytes=0-4";
        RangeParser range = new RangeParser(rangeHeaderValue);
        assertThat(range.hasStart(), is(equalTo(true)));
    }

    @Test
    public void testDoesNotHaveStart() {
        String rangeHeaderValue = "bytes=-4";
        RangeParser range = new RangeParser(rangeHeaderValue);
        assertThat(range.hasStart(), is(equalTo(false)));
    }

    @Test
    public void testHasEnd() {
        String rangeHeaderValue = "bytes=0-4";
        RangeParser range = new RangeParser(rangeHeaderValue);
        assertThat(range.hasEnd(), is(equalTo(true)));
    }

    @Test
    public void testDoesNotHaveEnd() {
        String rangeHeaderValue = "bytes=4-";
        RangeParser range = new RangeParser(rangeHeaderValue);
        assertThat(range.hasEnd(), is(equalTo(false)));
    }
}
