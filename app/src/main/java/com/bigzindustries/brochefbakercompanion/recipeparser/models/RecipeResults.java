package com.bigzindustries.brochefbakercompanion.recipeparser.models;

import java.util.List;

public class RecipeResults {
    private List<IngredientResults> ingredients;

    public RecipeResults(List<IngredientResults> ingredients) {
        this.ingredients = ingredients;
    }

    public List<IngredientResults> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientResults> ingredients) {
        this.ingredients = ingredients;
    }
}
