package org.cgrep.matchers;

import org.cgrep.ByteString;
import org.cgrep.util.Utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

/**
* User: Oleksiy Pylypenko
* Date: 8/4/13
* Time: 7:52 AM
*/
public class HashSetMatcherBuilder implements Utils.LineIt {
    private final Charset charset;
    private long read;
    private long unique;
    private Set<ByteString> set = new HashSet<ByteString>(100000);

    public HashSetMatcherBuilder(Charset charset) {
        this.charset = charset;
    }

    @Override
    public void it(String line) {
        read++;

        ByteString bs = toByteString(line);

        if (set.add(bs)) {
            unique++;
        }
    }

    private ByteString toByteString(String line) {
        try {
            return new ByteString(line, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("charset '" + charset + "' not found", e);
        }
    }

    public SetMatcher build() {
        return new SetMatcher(set);
    }
}
