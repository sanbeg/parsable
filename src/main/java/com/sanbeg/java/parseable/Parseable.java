package com.sanbeg.java.parseable;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by steve on 2/19/17.
 */
public class Parseable {
    private final Matcher matcher;
    private int pos = 0;
    private int length;

    public Parseable(CharSequence s) {
        length = s.length();
        matcher = Pattern
                .compile("")
                .matcher(s)
                .useTransparentBounds(true)
                .useAnchoringBounds(false);
    }

    public void reset() {
        pos = 0;
        matcher.reset();
    }

    public void reset(CharSequence s) {
        length = s.length();
        pos = 0;
        matcher.reset(s);
    }

    private boolean takeMatch(boolean match) {
        if (match) {
            pos = matcher.end();
            return true;
        }
        return false;
    }

    public boolean find(Pattern p) {
        matcher.region(pos, length);
        return takeMatch (matcher.usePattern(p).find());
    }

    public boolean match(Pattern p) {
        matcher.region(pos, length);
        return takeMatch(matcher.usePattern(p).lookingAt());
    }

    public String group() {
        return matcher.group();
    }

    public MatchResult result() {
        return matcher.toMatchResult();
    }

    /**
     * Returns the position where the last match or find left off.  A failed search doesn not reset
     * the position.
     *
     * @return position
     */
    public int position() {
        return pos;
    }

    /**
     * reset the search position to use for the next search operation.
     * @param position - offset in the string to anchor the next search
     */
    public void setPosition(int position) {
        if (position > length) {
            throw new IndexOutOfBoundsException("pos > length");
        }
        pos = position;
    }

}
