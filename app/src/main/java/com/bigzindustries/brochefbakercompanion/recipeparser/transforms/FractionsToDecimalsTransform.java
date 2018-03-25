package com.bigzindustries.brochefbakercompanion.recipeparser.transforms;

public class FractionsToDecimalsTransform implements RecipeTransform {
    @Override
    public String transform(String input) {
        String result = input;

        result = result.replace("1 1/2", "1.5");
        result = result.replace("1 1/4", "1.25");
        result = result.replace("1 3/4", "1.75");
        result = result.replace("2 1/2", "2.5");
        result = result.replace("2 1/4", "2.25");
        result = result.replace("2 3/4", "2.75");

        result = result.replace("1/2", "0.5");
        result = result.replace("1/3", "0.333");
        result = result.replace("1/4", "0.25");
        result = result.replace("2/3", "0.667");
        result = result.replace("3/4", "0.75");

        return result;
    }
}
