package org.cgrep;

import org.cgrep.io.ChannelByteIO;
import org.cgrep.io.IO;
import org.cgrep.matchers.Matcher;
import org.cgrep.matchers.Matchers;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * User: Oleksiy Pylypenko
 * Date: 8/4/13
 * Time: 1:25 PM
 */
public class CGrepTool {
    private static void usage() {
        System.out.println("Column grep tool");
        System.out.println("Usage: cgrep column1Matcher column2Matcher ... columnNMatcher");
        System.out.println(" columnKMatcher is applied to K-th column and could be:");
        System.out.println("  - string '" + Matchers.EVERYTHING_STR + "' to match everything");
        System.out.println("  - string 'keyword1|keyword2|" + Matchers.FILE_PREFIX +
                "path3' to match union of separate keywords and files");
    }

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            usage();
            System.exit(1);
        }
        IO io = ChannelByteIO.stdIO();
        Matcher[] matchers = buildMatcherList(args);
        CGrep cg = new CGrep(io, matchers);
        try {
            cg.scan();
            if (Options.verbose) {
                cg.report();
            }
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        } finally {
            io.close();
        }
    }

    private static Matcher[] buildMatcherList(String[] args) throws IOException {
        Matcher[] matchers = new Matcher[args.length];
        for (int i = 0; i < matchers.length; i++) {
            matchers[i] = Matchers.fromExpression(DEFAULT_CHARSET, args[i]);
            if (Options.verbose) {
                System.err.println("COLUMN" + (i+1) + " matches " + matchers[i]);
            }
        }
        return matchers;
    }
}
