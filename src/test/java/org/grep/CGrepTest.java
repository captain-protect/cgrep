package org.grep;

import static junit.framework.Assert.*;

import org.cgrep.CGrep;
import org.cgrep.matchers.Matcher;
import org.cgrep.matchers.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;

/**
 * User: Oleksiy Pylypenko
 * Date: 8/4/13
 * Time: 8:06 AM
 */
public class CGrepTest {
    public static final String CHARSET_NAME = "UTF-8";
    public static final Charset CHARSET = Charset.forName(CHARSET_NAME);
    private Matcher abcUvwM;
    private Matcher nopXyzM;
    private Matcher ghiQrsM;

    @Before
    public void setUp() throws Exception {
        abcUvwM = Matchers.set(CHARSET, "abc", "uvw");
        nopXyzM = Matchers.set(CHARSET, "nop", "xyz");
        ghiQrsM = Matchers.set(CHARSET, "ghi", "qrs");
    }


    @Test
    public void testMatches1() throws Exception {
        TestIO io = TestIO.ofLines(CHARSET,
                "abc def ghi",
                "klm nop qrs",
                "uvw xyz");

        CGrep tool = new CGrep(io, abcUvwM);

        tool.scan();

        String str = new String(io.toByteArray(), CHARSET_NAME);
        assertEquals("abc def ghi\nuvw xyz\n", str);
    }

    @Test
    public void testMatches2() throws Exception {
        TestIO io = TestIO.ofLines(Charset.forName(CHARSET_NAME),
                "abc def ghi",
                "klm nop qrs",
                "uvw xyz",
                "123 456",
                "abc end"
                );

        CGrep tool = new CGrep(io, abcUvwM);

        tool.scan();

        String str = new String(io.toByteArray(), CHARSET_NAME);
        assertEquals("abc def ghi\nuvw xyz\nabc end\n", str);
    }

    @Test
    public void testMatches3() throws Exception {
        TestIO io = TestIO.ofLines(Charset.forName(CHARSET_NAME),
                "abc def ghi",
                " klm nop qrs",
                " uvw xyz");

        CGrep tool = new CGrep(io, abcUvwM);

        tool.scan();

        String str = new String(io.toByteArray(), CHARSET_NAME);
        assertEquals("abc def ghi\n uvw xyz\n", str);
    }

    @Test
    public void testMultiMatches3() throws Exception {
        TestIO io = TestIO.ofLines(Charset.forName(CHARSET_NAME),
                "abc def ghi",
                " klm nop qrs",
                " uvw xyz");

        CGrep tool = new CGrep(io, abcUvwM, nopXyzM);

        tool.scan();

        String str = new String(io.toByteArray(), CHARSET_NAME);
        assertEquals(" uvw xyz\n", str);
    }
}
