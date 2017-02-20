package com.sanbeg.java.parseable;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Created by steve on 2/19/17.
 */
public class ParseableTest {


    private final Pattern reA = Pattern.compile("a");
    private final Pattern reB = Pattern.compile("b");

    @Test
    public void noReset() {
        Parseable subject = new Parseable("ab");

        assertTrue(subject.match(reA));
        assertFalse(subject.match(reA));
        assertFalse(subject.match(reA));
    }

    @Test
    public void reset() throws Exception {
        Parseable subject = new Parseable("ab");

        assertTrue(subject.match(reA));
        assertFalse(subject.match(reA));
        subject.reset();
        assertTrue(subject.match(reA));
    }

    @Test
    public void resetString() throws Exception {
        Parseable subject = new Parseable("ab");
        subject.reset("ba");
        assertTrue(subject.match(reB));
    }

    @Test
    public void find() throws Exception {
        Parseable subject = new Parseable("ab");
        assertTrue(subject.find(reB));
    }

    @Test
    public void match() throws Exception {
        Parseable subject = new Parseable("ab");
        assertFalse(subject.match(reB));
        assertTrue(subject.match(reA));
        assertTrue(subject.match(reB));
    }

    @Test
    public void group() throws Exception {
        Parseable subject = new Parseable("a1b");
        Pattern re = Pattern.compile("\\d");
        assertTrue(subject.find(re));
        assertEquals("1", subject.group());
    }

    @Test
    public void result() throws Exception {
        Parseable subject = new Parseable("a1b");
        Pattern re = Pattern.compile("\\d");
        assertTrue(subject.find(re));
        assertEquals("1", subject.result().group());
    }

    @Test
    public void position() throws Exception {
        Parseable subject = new Parseable("abcd");
        assertTrue(subject.match(reA));
        assertEquals(1, subject.position());
        assertTrue(subject.match(reB));
        assertEquals(2, subject.position());
        subject.setPosition(0);
        assertTrue(subject.match(reA));
    }
}