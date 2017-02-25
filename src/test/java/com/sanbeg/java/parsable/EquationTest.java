package com.sanbeg.java.parsable;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by steve on 2/24/17.
 */
public class EquationTest {

    Pattern num = Pattern.compile("\\d+");
    Pattern op  = Pattern.compile("[-+*]");
    Pattern sp  = Pattern.compile("\\s+");

    private List<Object> parseEquation(String equation) {
        Parsable text = new Parsable(equation);
        List<Object> tok = new ArrayList<Object>();

        while (text.position() < equation.length()) {
            if (text.match(num)) {
                tok.add(Integer.valueOf(text.group()));
                continue;
            }
            if (text.match(op)) {
                tok.add(text.group().charAt(0));
                continue;
            }
            if (text.match(sp)) {
                continue; //eat spaces
            }
            //Nothing matched - something must be wrong here!
            throw new RuntimeException("Invalid char at position " + text.position());
        }
        return tok;
    }


    @Test
    public void equation() {
        String eq = "1 + 2*3";
        Object [] expect = {1, '+', 2, '*', 3};

        List<Object> tok = parseEquation(eq);
        Object [] result = tok.toArray(new Object[tok.size()]);
        assertArrayEquals(expect, result);
    }
}
