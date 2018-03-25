package com.bigzindustries.brochefbakercompanion.recipeparser.models;

public enum RecipeUnits {
    OUNCES("oz"),
    POUNDS("lb"),
    CUPS("cups"),
    TSP("tsp"),
    TBL("tbl"),

    // for things that are merely counted, like "eggs"
    GENERIC_UNIT("units");

    public String name;

    RecipeUnits(String name) {
        this.name = name;
    }
}
