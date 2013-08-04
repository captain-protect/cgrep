package org.cgrep.delimiters;

public final class BasicDelimiters implements Delimiters {
    @Override
    public boolean isNewLine(int c) {
        return c == '\n' || c == '\r';
    }

    @Override
    public boolean isWhitespace(int c) {
        return c == ' ' || c == '\t';
    }
}