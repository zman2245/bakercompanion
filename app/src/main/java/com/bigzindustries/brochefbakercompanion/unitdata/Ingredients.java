package com.bigzindustries.brochefbakercompanion.unitdata;

/**
 * Good site for checking these values:
 * http://www.traditionaloven.com/culinary-arts/cooking/butter/convert-gram-g-to-milliliter-ml.html
 */
public enum Ingredients {
    WATER("Water", 1, true),
    OIL("Oil", 1, true),
    ALL_PURPOSE_FLOUR("AP Flour", 1.89, true),
    SUGAR("Sugar", 1.18, true),
    BUTTER("Butter", 1.043158009, false);

    public String name;
    public double massToVolumeFactor; // gram to milliliter conversion factor
    public boolean isStandard;

    Ingredients(String name, double massToVolumeFactor, boolean isStandard) {
        this.name = name;
        this.massToVolumeFactor = massToVolumeFactor;
        this.isStandard = isStandard;
    }
}
