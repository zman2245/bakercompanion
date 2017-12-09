package com.bigzindustries.brochefbakercompanion.models;

import com.bigzindustries.brochefbakercompanion.unitdata.Ingredients;
import com.bigzindustries.brochefbakercompanion.unitdata.Units;

public class Conversion {
    private Ingredients ingredient;
    private Units fromUnit;
    private Units toUnit;
    private float fromValue;
    private float toValue;

    public Conversion(Ingredients ingredient, Units fromUnit, Units toUnit,
                      float fromValue, float toValue) {
        this.ingredient = ingredient;
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
        this.fromValue = fromValue;
        this.toValue = toValue;
    }

    public Ingredients getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredients ingredient) {
        this.ingredient = ingredient;
    }

    public Units getFromUnit() {
        return fromUnit;
    }

    public void setFromUnit(Units fromUnit) {
        this.fromUnit = fromUnit;
    }

    public Units getToUnit() {
        return toUnit;
    }

    public void setToUnit(Units toUnit) {
        this.toUnit = toUnit;
    }

    public float getFromValue() {
        return fromValue;
    }

    public void setFromValue(float fromValue) {
        this.fromValue = fromValue;
    }

    public float getToValue() {
        return toValue;
    }

    public void setToValue(float toValue) {
        this.toValue = toValue;
    }
}
