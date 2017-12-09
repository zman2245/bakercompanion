package com.bigzindustries.brochefbakercompanion.unitdata;

/**
 * Created by zack on 12/8/17.
 */

public class Conversions {
    /**
     * Mass->Mass
     * A -> CommonMass (gram) -> B
     *
     * Volume->Volume
     * A -> CommonVolume (mL) -> B
     *
     * Volume -> Mass
     * A -> CommonVolume (mL) --per ingredient--> CommonMass (gram) -> B
     *
     * Mass -> Volume
     * A -> CommonMass (gram) --per ingredient--> CommonVolume (mL) -> B
     */
    public static double convert(Ingredients ingredient,
                                Units unitFrom,
                                Units unitTo,
                                double value) {

        double inCommonUnits = value * unitFrom.toCommonFactor;;

        if (unitFrom.isMass && !unitTo.isMass) {
            // Mass -> Volume
            inCommonUnits = inCommonUnits * ingredient.massToVolumeFactor;
        } else if (!unitFrom.isMass && unitTo.isMass) {
            // Volume -> Mass
            inCommonUnits = inCommonUnits * (1/ingredient.massToVolumeFactor);
        }

        return inCommonUnits * (1/unitTo.toCommonFactor);
    }
}
