package com.bigzindustries.brochefbakercompanion.unitdata;

/**
 * Good site for checking these values:
 * http://www.traditionaloven.com/culinary-arts/cooking/butter/convert-gram-g-to-milliliter-ml.html
 *
 * Handy conversion: 1 cup == 236.588 mL
 */
public enum Ingredients {
    ALL_PURPOSE_FLOUR("AP Flour", 1.8927, true),
    CAKE_FLOUR("Cake Flour", 1.971567, false),
    BREAD_FLOUR("Bread Flour", 1.75250, false),
    SUGAR("Sugar", 1.1829, true),
    BROWN_SUGUAR("Brown Sugar", 1.1829, true),
    POWDERED_SUGUAR("Powder Sugar", 2.1508, true),
    COCOA("Cocoa Powder", 1.8927, true),
    WATER("Water", 1, true),
    OIL("Oil", 1, true),
    BUTTER("Butter", 1.043158009, true),
    CHOCOLATE_CHIPS("Chocolate Chips", 1.35193, false),
    CHEESE_GRATED("Grated Cheese", 1.89270, false);

    public String name;
    public double massToVolumeFactor; // gram to milliliter conversion factor
    public boolean isStandard;

    Ingredients(String name, double massToVolumeFactor, boolean isStandard) {
        this.name = name;
        this.massToVolumeFactor = massToVolumeFactor;
        this.isStandard = isStandard;
    }

    public String getName() {
        return name;
    }
}
