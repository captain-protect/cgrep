package org.grep;

import org.cgrep.io.IO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
* User: Oleksiy Pylypenko
* Date: 8/4/13
* Time: 8:23 AM
*/
class TestIO implements IO {
    private int inPos;
    private final byte[] inBuf;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    TestIO(byte[] inBuf) {
        this.inBuf = inBuf;
    }

    public static TestIO ofLines(Charset charset, String... lines) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line);
            sb.append('\n');
        }
        return new TestIO(sb.toString().getBytes(charset));
    }

    @Override
    public void putByte(byte b) throws IOException {
        out.write(b);
    }

    @Override
    public int readByte() throws IOException {
        if (inPos >= inBuf.length) return -1;
        return inBuf[inPos++];
    }

    public byte[] toByteArray() {
        return out.toByteArray();
    }

    @Override
    public void close() throws IOException {

    }
}
