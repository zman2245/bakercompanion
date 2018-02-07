package com.bigzindustries.brochefbakercompanion.unitdata;

/**
 * The common units are Grams for mass; Milliliters for volume
 */
public enum Units {
    GRAMS("Gram", true, true, 1),
    KILOGRAMS("Kilograms", true, false, 1000),
    OUNCES("Ounces", true, true, 28.3495),
    POUNDS("Pounds", true, false, 453.592),

    MILLILITERS("Milliliters", false, true, 1),
    LITERS("Liters", false, false, 1000),
    CUPS("Cups", false, true, 240f),
    TEASPOONS("Teaspoons", false, true, 4.92892),
    TABLESPOONS("Tablespoons", false, true, 14.78676),
    FLUID_OUNCES("Fluid Ounces", false, true, 29.5735);

    public String name; // display name throughout the app
    public boolean isMass; // if false, Volume
    public boolean isStandard;
    public double toCommonFactor; // factor used to convert from this unit to the common unit

    Units(String name, boolean isMass, boolean isStandard, double toCommonFactor) {
        this.name = name;
        this.isMass = isMass;
        this.isStandard = isStandard;
        this.toCommonFactor = toCommonFactor;
    }

    public String getName() {
        return name;
    }
}
