package com.bigzindustries.brochefbakercompanion.unitdata;

import java.util.HashMap;

/**
 * Encodes unit conversion data
 */
public class Unit {
    private String name;
    private HashMap<String, Float> conversionFactors;

    protected Unit(String name, HashMap<String, Float> conversionFactors) {
        this.name = name;
        this.conversionFactors = conversionFactors;
    }

    public float convert(Unit to, float value) {
        if (!conversionFactors.containsKey(to.name)) {
            throw new IllegalArgumentException("No conversion factor found for " +
                    this.name + " to " + to.name);
        }

        return value * conversionFactors.get(to.name);
    }

    public float reverseConvert(Unit to, float value) {
        if (!to.conversionFactors.containsKey(this.name)) {
            throw new IllegalArgumentException("No conversion factor found for " +
                    to.name + " to " + this.name);
        }

        return value * to.conversionFactors.get(this.name);
    }
}
