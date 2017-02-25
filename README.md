[![Build Status](https://travis-ci.org/sanbeg/parsable.svg?branch=master)](https://travis-ci.org/sanbeg/parsable)
[![codecov](https://codecov.io/gh/sanbeg/parsable/branch/master/graph/badge.svg)](https://codecov.io/gh/sanbeg/parsable)
[![Download](https://api.bintray.com/packages/steve-sanbeg/maven/parsable/images/download.svg) ](https://bintray.com/steve-sanbeg/maven/parsable/_latestVersion)

# Parsable
Java parsable strings, inspired by Perl.

Perl has a paradigm that allows parsing a string by repeatedly testing against different regular
expressions to extract tokens.  Although Java implements perl-compatible regular expressions, this
implementation neglects a few features which are necessary for this style of parsing; namely
the ability to preserve the matched position after a failed match, and the ability to access
the position via a setter and getter.

This library re-purposes Java Pattern's range feature to implement these missing features, to
allow this style of parsing.

##Example
```java
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
```
