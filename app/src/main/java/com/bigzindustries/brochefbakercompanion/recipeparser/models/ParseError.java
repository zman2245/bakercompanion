package com.bigzindustries.brochefbakercompanion.recipeparser.models;

/**
 * Should give some indication of what went wrong; e.g. "We were unable to find a unit for the 4th
 * ingredient."
 */
public class ParseError {
    String message;
    Exception exception;
    int recipeLineNumber;
}
