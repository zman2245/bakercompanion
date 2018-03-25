package com.bigzindustries.brochefbakercompanion.recipeparser.models;

public enum RecipeIngredients {
    UNSALTED_BUTTER("Unsalted Butter"),
    AP_FLOUR("All-purpose Flour"),
    WATER("Water"),
    BAKING_SODA("Baking Soda"),
    BAKING_POWDER("Baking Powder"),
    SALT("Salt"),
    SUGAR("Sugar"),
    EGGS("Eggs"),
    EGG_YOLKS("Egg Yolks"),
    VANILLA("Vanilla"),
    BROWN_SUGAR("Brown Sugar"),
    SEMISWEET_CHOCOLATE("Semi-sweet Chocolate"),
    HEAVY_CREAM("Heavy Cream"),
    MILK("Milk"),
    YOGURT("Plain Yogurt");

    public String name;

    RecipeIngredients(String name) {
        this.name = name;
    }
}
