package org.cgrep;

public final class GrepBuffer {
    private int grepBufTokenStart;
    private int grepBufPos;
    private byte[] grepBuf;

    public GrepBuffer(int grepBufSize) {
        this.grepBuf = new byte[grepBufSize];
    }

    public int getSize() {
        return grepBuf.length;
    }

    public byte[] getGrepBuf() {
        return grepBuf;
    }

    public int getPosition() {
        return grepBufPos;
    }

    public boolean hasRemaining() {
        return grepBufPos < grepBuf.length;
    }

    public boolean isEmpty() {
        return grepBufPos == 0;
    }

    public void put(byte c) {
        grepBuf[grepBufPos++] = c;
    }

    public void clear() {
        grepBufTokenStart = 0;
        grepBufPos = 0;
    }

    public void mark() {
        grepBufTokenStart = grepBufPos;
    }

    public ByteString getMarkedToken() {
        return new ByteString(grepBuf,
                grepBufTokenStart,
                grepBufPos - grepBufTokenStart);
    }
}