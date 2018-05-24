package com.bigzindustries.brochefbakercompanion.recipeparser.models;

public class IngredientResults {
    RecipeIngredients ingredient;
    double amount;
    RecipeUnits unit = RecipeUnits.GENERIC_UNIT;
    String notes;
    int alternateAmount;
    String alternateUnit;

    String originalLine;
    int humanReadableIngredientNumber;

    public RecipeIngredients getIngredient() {
        return ingredient;
    }

    public void setIngredient(RecipeIngredients ingredient) {
        this.ingredient = ingredient;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public RecipeUnits getUnit() {
        return unit;
    }

    public void setUnit(RecipeUnits unit) {
        this.unit = unit;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getAlternateAmount() {
        return alternateAmount;
    }

    public void setAlternateAmount(int alternateAmount) {
        this.alternateAmount = alternateAmount;
    }

    public String getAlternateUnit() {
        return alternateUnit;
    }

    public void setAlternateUnit(String alternateUnit) {
        this.alternateUnit = alternateUnit;
    }

    public String getOriginalLine() {
        return originalLine;
    }

    public void setOriginalLine(String originalLine) {
        this.originalLine = originalLine;
    }

    public int getHumanReadableIngredientNumber() {
        return humanReadableIngredientNumber;
    }

    public void setHumanReadableIngredientNumber(int humanReadableIngredientNumber) {
        this.humanReadableIngredientNumber = humanReadableIngredientNumber;
    }
}
