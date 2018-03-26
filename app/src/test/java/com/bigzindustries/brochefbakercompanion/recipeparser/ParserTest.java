package com.bigzindustries.brochefbakercompanion.recipeparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Test
    public void testTransformChain() {
        Parser p = new Parser();

        assertEquals("hello", p.applyTransformChain("hello"));
        assertEquals("0.5cup of something", p.applyTransformChain("  1/2cup Of Something "));
        assertEquals("0.75 0.667", p.applyTransformChain("3/4 2/3 "));
    }
}
