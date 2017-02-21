package com.sanbeg.java.parsable;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Created by steve on 2/19/17.
 */
public class ParsableTest {


    private final Pattern reA = Pattern.compile("a");
    private final Pattern reB = Pattern.compile("b");

    @Test
    public void noReset() {
        Parsable subject = new Parsable("ab");

        assertTrue(subject.match(reA));
        assertFalse(subject.match(reA));
        assertFalse(subject.match(reA));
    }

    @Test
    public void reset() throws Exception {
        Parsable subject = new Parsable("ab");

        assertTrue(subject.match(reA));
        assertFalse(subject.match(reA));
        subject.reset();
        assertTrue(subject.match(reA));
    }

    @Test
    public void resetString() throws Exception {
        Parsable subject = new Parsable("ab");
        subject.reset("ba");
        assertTrue(subject.match(reB));
    }

    @Test
    public void find() throws Exception {
        Parsable subject = new Parsable("ab");
        assertTrue(subject.find(reB));
    }

    @Test
    public void match() throws Exception {
        Parsable subject = new Parsable("ab");
        assertFalse(subject.match(reB));
        assertTrue(subject.match(reA));
        assertTrue(subject.match(reB));
    }

    @Test
    public void group() throws Exception {
        Parsable subject = new Parsable("a1b");
        Pattern re = Pattern.compile("\\d");
        assertTrue(subject.find(re));
        assertEquals("1", subject.group());
    }

    @Test
    public void result() throws Exception {
        Parsable subject = new Parsable("a1b");
        Pattern re = Pattern.compile("\\d");
        assertTrue(subject.find(re));
        assertEquals("1", subject.result().group());
    }

    @Test
    public void position() throws Exception {
        Parsable subject = new Parsable("abcd");
        assertTrue(subject.match(reA));
        assertEquals(1, subject.position());
        assertTrue(subject.match(reB));
        assertEquals(2, subject.position());
        subject.setPosition(0);
        assertTrue(subject.match(reA));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void badPosition() {
        Parsable subject = new Parsable("ab");
        subject.setPosition(3);
    }

    private String [] parseEquation(String equation) {
        Pattern num = Pattern.compile("\\d+");
        Pattern op  = Pattern.compile("[-+*]");
        Pattern sp  = Pattern.compile("\\s+");

        Parsable text = new Parsable(equation);
        List<String> tok = new ArrayList<String>();

        while (text.position() < equation.length()) {
            if (text.match(num) || text.match(op)) {
                tok.add(text.group());
                continue;
            }
            if (text.match(sp)) {
                continue; //eat spaces
            }
            //Nothing matched - something must be wrong here!
            throw new RuntimeException("Invalid char at position " + text.position());
        }
        return tok.toArray(new String[tok.size()]);
    }

    @Test
    public void equation() {
        String eq = "1 + 2*3";
        String [] expect = {"1", "+", "2", "*", "3"};

        assertArrayEquals(expect, parseEquation(eq));
    }
}