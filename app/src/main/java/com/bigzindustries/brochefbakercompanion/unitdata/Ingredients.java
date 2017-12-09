package com.bigzindustries.brochefbakercompanion.unitdata;

/**
 * Good site for checking these values:
 * http://www.traditionaloven.com/culinary-arts/cooking/butter/convert-gram-g-to-milliliter-ml.html
 */
public enum Ingredients {
    WATER("Water", 1),
    OIL("Oil", 1),
    ALL_PURPOSE_FLOUR("AP Flour", 1.89),
    SUGAR("Sugar", 1.18),
    BUTTER("Butter", 1.043158009);

    public String name;
    public double massToVolumeFactor; // in common units

    Ingredients(String name, double massToVolumeFactor) {
        this.name = name;
        this.massToVolumeFactor = massToVolumeFactor;
    }
}
