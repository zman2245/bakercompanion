package com.bigzindustries.brochefbakercompanion.unitdata;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConversionsUnitTest {

    @Test
    public void testVolumeToVolume() {
        double val = Conversions.convert(Ingredients.WATER, Units.MILLILITERS, Units.MILLILITERS, 53);
        assertEquals(53, val, 0.01);

        val = Conversions.convert(Ingredients.WATER, Units.MILLILITERS, Units.TEASPOONS, 53);
        assertEquals(10.7529, val, 0.01);

        val = Conversions.convert(Ingredients.WATER, Units.TEASPOONS, Units.MILLILITERS, 53);
        assertEquals(261.233, val, 0.01);

        val = Conversions.convert(Ingredients.WATER, Units.TEASPOONS, Units.CUPS, 2);
        assertEquals(0.0410743, val, 0.01);

        val = Conversions.convert(Ingredients.WATER, Units.CUPS, Units.TEASPOONS, 1);
        assertEquals(48.6922, val, 0.01);
    }

    @Test
    public void testMassToMass() {
        double val = Conversions.convert(Ingredients.WATER, Units.GRAMS, Units.GRAMS, 17);
        assertEquals(17, val, 0.01);

        val = Conversions.convert(Ingredients.WATER, Units.OUNCES, Units.GRAMS, 17);
        assertEquals(481.942, val, 0.01);

        val = Conversions.convert(Ingredients.WATER, Units.GRAMS, Units.OUNCES, 17);
        assertEquals(0.599657, val, 0.01);
    }

    @Test
    public void testMassToVolume() {
        double val = Conversions.convert(Ingredients.WATER, Units.GRAMS, Units.MILLILITERS, 53);
        assertEquals(53, val, 0.01);

        val = Conversions.convert(Ingredients.ALL_PURPOSE_FLOUR, Units.OUNCES, Units.CUPS, 3);
        assertEquals(0.667, val, 0.01);

        val = Conversions.convert(Ingredients.BUTTER, Units.GRAMS, Units.MILLILITERS, 1000);
        assertEquals(1042.64, val, 0.01);
    }

    @Test
    public void testVolumeToMass() {
        double val = Conversions.convert(Ingredients.WATER, Units.MILLILITERS, Units.GRAMS, 53);
        assertEquals(53, val, 0.01);

        val = Conversions.convert(Ingredients.ALL_PURPOSE_FLOUR, Units.CUPS, Units.OUNCES, 0.75);
        assertEquals(3.36, val, 0.01);
    }
}
