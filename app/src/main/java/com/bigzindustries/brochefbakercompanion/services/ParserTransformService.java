package com.bigzindustries.brochefbakercompanion.services;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.bigzindustries.brochefbakercompanion.FlurryEvents;
import com.bigzindustries.brochefbakercompanion.db.BroChefContentProvider;
import com.bigzindustries.brochefbakercompanion.db.BroChefDbHelper;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.IngredientResults;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeIngredients;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeResults;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeUnits;
import com.bigzindustries.brochefbakercompanion.unitdata.Conversions;
import com.bigzindustries.brochefbakercompanion.unitdata.Ingredients;
import com.bigzindustries.brochefbakercompanion.unitdata.Units;

import java.util.HashMap;
import java.util.List;

public class ParserTransformService {

    static HashMap<RecipeIngredients, Ingredients> iMapper = new HashMap<>();
    static HashMap<RecipeUnits, Units> uMapper = new HashMap<>();

    static {
        iMapper.put(RecipeIngredients.AP_FLOUR, Ingredients.ALL_PURPOSE_FLOUR);
        iMapper.put(RecipeIngredients.BAKING_POWDER, Ingredients.BAKING_POWDER);
        iMapper.put(RecipeIngredients.BAKING_SODA, Ingredients.BAKING_SODA);
        iMapper.put(RecipeIngredients.BROWN_SUGAR, Ingredients.BROWN_SUGUAR);
        iMapper.put(RecipeIngredients.CREAM_CHEESE, Ingredients.CREAM_CHEESE);
        iMapper.put(RecipeIngredients.EGG_YOLKS, Ingredients.EGG_YOLKS);
        iMapper.put(RecipeIngredients.EGGS, Ingredients.EGGS);
        iMapper.put(RecipeIngredients.GRAHAM_CRUMBS, Ingredients.GRAHAM_CRUMBS);
        iMapper.put(RecipeIngredients.HEAVY_CREAM, Ingredients.HEAVY_CREAM);
        iMapper.put(RecipeIngredients.MILK, Ingredients.MILK);
        iMapper.put(RecipeIngredients.SALT, Ingredients.SALT);
        iMapper.put(RecipeIngredients.SUGAR, Ingredients.SUGAR);
        iMapper.put(RecipeIngredients.UNSALTED_BUTTER, Ingredients.BUTTER);
        iMapper.put(RecipeIngredients.WATER, Ingredients.WATER);

        iMapper.put(RecipeIngredients.VANILLA, Ingredients.VANILLA);
        iMapper.put(RecipeIngredients.YOGURT, Ingredients.YOGURT);
        iMapper.put(RecipeIngredients.SEMISWEET_CHOCOLATE, Ingredients.SEMI_SWEET_CHOCOLATE);
        iMapper.put(RecipeIngredients.SOUR_CREAM, Ingredients.SOUR_CREAM);

        uMapper.put(RecipeUnits.CUPS, Units.CUPS);
        uMapper.put(RecipeUnits.OUNCES, Units.OUNCES);
        uMapper.put(RecipeUnits.POUNDS, Units.POUNDS);
        uMapper.put(RecipeUnits.TBL, Units.TABLESPOONS);
        uMapper.put(RecipeUnits.TSP, Units.TEASPOONS);
        uMapper.put(RecipeUnits.OUNCES, Units.OUNCES);

        // sanity check that we have a mapping for everything
        for (RecipeIngredients i : RecipeIngredients.values()) {
            assert iMapper.containsKey(i);
        }
        for (RecipeUnits u : RecipeUnits.values()) {
            assert u.equals(RecipeUnits.GENERIC_UNIT) || uMapper.containsKey(u);
        }
    }

    public enum CONVERSION_RULE {
        NONE,
        TO_MASS,
        TO_VOLUME
    }

    /**
     *
     * @param resolver
     * @param results
     * @param recipeName
     * @param recipeNotes
     * @return id of the new ConversionSet in the DB
     */
    public long createNewRecipeFromParserResults(
            ContentResolver resolver,
            RecipeResults results,
            String recipeName,
            String recipeNotes,
            CONVERSION_RULE rule) {

        ContentValues setValues =
                BroChefDbHelper.getValsForConversionSetInsert(recipeName, recipeNotes);
        Uri setUri = resolver.insert(BroChefContentProvider.CONVERSION_SETS_URI, setValues);
        long newSetId = Long.valueOf(setUri.getLastPathSegment());

        List<IngredientResults> ingredientResults = results.getIngredients();
        for (IngredientResults ingredientResult : ingredientResults) {
            Conv conv;
            String fromUnitName = "";
            Ingredients ingredient = iMapper.get(ingredientResult.getIngredient());
            if (ingredient == null) {
                // this particular ingredient couldn't be parsed
                FlurryEvents.logIngredientParseError(ingredientResult);
                continue;
            }

            double fromValue = ingredientResult.getAmount();

            if (ingredientResult.getUnit() == RecipeUnits.GENERIC_UNIT) {
                // there's no conversion in this case because the unit is just a numerical count
                // (e.g. 2 eggs)
                conv = new Conv("", 0.0);
            } else {
                Units fromUnit = uMapper.get(ingredientResult.getUnit());
                fromUnitName = fromUnit.getName();
                conv = getVolUnit(ingredient, fromUnit, fromValue, rule);
            }

            ContentValues values = BroChefDbHelper.getValsForConversionInsert(
                    newSetId,
                    fromValue,
                    conv.value,
                    fromUnitName,
                    conv.unit,
                    ingredient.getName());

            resolver.insert(BroChefContentProvider.CONVERSIONS_URI, values);
        }

        return newSetId;
    }

    /**
     * Only considers Cups, Tablespoons, Teaspoons
     *
     * @param fromUnit
     * @param fromVal
     * @return
     */
    private Conv getVolUnit(Ingredients ingredient, Units fromUnit,
                            double fromVal, CONVERSION_RULE rule) {
        if  (rule == CONVERSION_RULE.NONE) {
            return new Conv("", 0.0);
        }

        double roundingThreshold = 0.10;
        double threshold = 3 + roundingThreshold;

        Units[] massUnitsToTry = new Units[] {Units.GRAMS};
        Units[] volumeUnitsToTry = new Units[] {Units.TEASPOONS, Units.TABLESPOONS, Units.CUPS};
        Units[] unitsToTry = (rule == CONVERSION_RULE.TO_MASS) ? massUnitsToTry : volumeUnitsToTry;

        Units toUnit = unitsToTry[0];
        double toValue = 0;

        for (int i = 0; i < unitsToTry.length; toUnit = unitsToTry[i++]) {
            toValue = Conversions.convert(ingredient, fromUnit, toUnit, fromVal);

            if (toValue < threshold) {
                break;
            }
        }

        return new Conv(toUnit.getName(), toValue);
    }

    private static class Conv {
        String unit;
        double value;

        public Conv(String unit, double value) {
            this.unit = unit;
            this.value = value;
        }
    }
}
