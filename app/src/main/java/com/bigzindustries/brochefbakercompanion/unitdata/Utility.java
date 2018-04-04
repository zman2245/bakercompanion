package com.bigzindustries.brochefbakercompanion.unitdata;

import android.text.TextUtils;

public class Utility {
    // TODO: belongs in a model class
    public static String getUnitString(String unit) {
        if (TextUtils.isEmpty(unit)) {
            return unit;
        }

        try {
            Units unitEnum = Units.valueOf(unit);
            return unitEnum.getName();
        } catch (IllegalArgumentException e) {
            // This is hacky because this is expected for custom ingredients. Perhaps add type to DB?
            return unit;
        }
    }

    // TODO: belongs in a model class
    public static String getIngredientString(String ingredient) {
        if (TextUtils.isEmpty(ingredient)) {
            return ingredient;
        }

        try {
            Ingredients ingEnum = Ingredients.valueOf(ingredient);
            return ingEnum.getName();
        } catch (IllegalArgumentException e) {
            // This is hacky because this is expected for custom ingredients. Perhaps add type to DB?
            return ingredient;
        }
    }

    public static String getMeasurementString(double val, String unit) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%.2f", val))
                .append(" ")
                .append(unit.toLowerCase());

        return builder.toString();
    }
}
