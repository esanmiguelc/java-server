package javaserver.Parser;

import static java.lang.System.arraycopy;

public class RangeParser {

    private final String rangeHeader;
    private String start;
    private String end;

    public RangeParser(String rangeHeaderValue) {
        this.rangeHeader = rangeHeaderValue;
        range();
    }

    private void range() {
        String rangeValues = rangeHeader.substring(rangeHeader.indexOf("=") + 1);
        String[] split = {"", ""};
        String[] values = rangeValues.split("-");
        arraycopy(values, 0, split, 0, values.length);
        start = split[0];
        end = split[1];
    }

    public Integer getStart() {
        return Integer.valueOf(start);
    }

    public Integer getEnd() {
        return Integer.valueOf(end);
    }

    public boolean hasStart() {
        return !start.isEmpty();
    }

    public boolean hasEnd() {
        return !end.isEmpty();
    }
}
