package com.bigzindustries.brochefbakercompanion.recipeparser.transforms;

/**
 * Generic transformations applied to all inputs
 */
public class UniformTransform implements RecipeTransform {
    @Override
    public String transform(String input) {
        return input.toLowerCase().replace(";", ",").replace(",", "").trim();
    }
}
