package com.sanbeg.java.parsable;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple paradigm for parsing text with regular expressions, inspired by Perl
 * regular expression parsing.
 */
public class Parsable {
    private final Matcher matcher;
    private int pos = 0;
    private int length;

    /**
     * Create a new Parsable, initialised to parse the specified text.
     * @param text the text to parse
     */
    public Parsable(CharSequence text) {
        length = text.length();
        matcher = Pattern
                .compile("")
                .matcher(text)
                .useTransparentBounds(true)
                .useAnchoringBounds(false);
    }

    /**
     * Reset the position, so the next match will start from the beginning of the text.
     */
    public void reset() {
        pos = 0;
        matcher.reset();
    }

    /**
     * Replace the text that is searched.
     * @param text the new next to search
     */
    public void reset(CharSequence text) {
        length = text.length();
        pos = 0;
        matcher.reset(text);
    }

    private boolean takeMatch(boolean match) {
        if (match) {
            pos = matcher.end();
            return true;
        }
        return false;
    }

    /**
     * A non-anchored search, looks for the pattern starting anywhere after the last search left off.
     * The pattern can be explicitly anchored with \G to anchor to the exact position, which is
     * equivalent to the {@link #match(Pattern p) match} method.
     *
     * @param p the pattern to look for
     * @return true if found
     */
    public boolean find(Pattern p) {
        matcher.region(pos, length);
        return takeMatch (matcher.usePattern(p).find());
    }

    /**
     * An anchored search, looks for the pattern starting where the last search left off.
     * The pattern is implicitly anchored where the last search left off; it's not necessary to
     * supply \G at the beginning of the pattern.
     * @param p the pattern to look for
     * @return true if found
     */
    public boolean match(Pattern p) {
        matcher.region(pos, length);
        return takeMatch(matcher.usePattern(p).lookingAt());
    }

    /**
     * Retrieve the text that was matched by the last search.
     * @return the text
     */
    public String group() {
        return matcher.group();
    }

    public MatchResult result() {
        return matcher.toMatchResult();
    }

    /**
     * Returns the position where the last match or find left off.  A failed search does not reset
     * the position.
     * @return position
     */
    public int position() {
        return pos;
    }

    /**
     * Reset the search position to use for the next search operation.
     * @param position offset in the string to anchor the next search
     */
    public void setPosition(int position) {
        if (position > length) {
            throw new IndexOutOfBoundsException("pos > length");
        }
        pos = position;
    }

}
