package com.bigzindustries.brochefbakercompanion.recipeparser.transforms;

public class RemoveParentheticalTransform implements RecipeTransform {
    @Override
    public String transform(String input) {
        return input.replaceAll("\\((.+?)\\)", "").trim();
    }
}
