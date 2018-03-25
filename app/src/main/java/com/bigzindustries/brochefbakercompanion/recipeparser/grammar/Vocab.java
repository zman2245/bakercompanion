package com.bigzindustries.brochefbakercompanion.recipeparser.grammar;

import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeIngredients;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeUnits;

import java.util.HashMap;

/**
 * TODO: need to have transforms that turn some terms, especially multi-word phrases into
 * recognizable keys that are programmed here.
 */
public class Vocab {

    public static String TRANS_KEY_BAKING_SODA = "bakingsoda";
    public static String TRANS_KEY_BAKING_POWDER = "bakingpowder";
    public static String TRANS_KEY_AP_FLOUR = "apflour";
    public static String TRANS_KEY_SUGAR = "sugar";
    public static String TRANS_KEY_EGGS = "eggs";
    public static String TRANS_KEY_EGG_YOLKS = "eggyolks";
    public static String TRANS_KEY_VANILLA = "vanilla";
    public static String TRANS_KEY_BROWN_SUGAR = "brownsugar";
    public static String TRANS_KEY_SEMISWEET_CHOC = "semichocolate";
    public static String TRANS_KEY_HEAVY_CREAM = "heavycream";
    public static String TRANS_KEY_YOGURT = "yogurt";

    public static HashMap<String, Object> wordsMap = new HashMap<>();
    static {
        wordsMap.put("c", RecipeUnits.CUPS);
        wordsMap.put("cups", RecipeUnits.CUPS);
        wordsMap.put("cup", RecipeUnits.CUPS);

        wordsMap.put("ounce", RecipeUnits.OUNCES);
        wordsMap.put("ounces", RecipeUnits.OUNCES);
        wordsMap.put("oz", RecipeUnits.OUNCES);

        wordsMap.put("tsp", RecipeUnits.TSP);
        wordsMap.put("tsps", RecipeUnits.TSP);
        wordsMap.put("teaspoon", RecipeUnits.TSP);
        wordsMap.put("teaspoons", RecipeUnits.TSP);
        wordsMap.put("tbl", RecipeUnits.TBL);
        wordsMap.put("tbls", RecipeUnits.TBL);
        wordsMap.put("tablespoon", RecipeUnits.TBL);
        wordsMap.put("tablespoons", RecipeUnits.TBL);

        wordsMap.put("butter", RecipeIngredients.UNSALTED_BUTTER);
        wordsMap.put("flour", RecipeIngredients.AP_FLOUR);
        wordsMap.put(TRANS_KEY_AP_FLOUR, RecipeIngredients.AP_FLOUR);
        wordsMap.put(TRANS_KEY_BAKING_SODA, RecipeIngredients.BAKING_SODA);
        wordsMap.put(TRANS_KEY_BAKING_POWDER, RecipeIngredients.BAKING_POWDER);
        wordsMap.put("salt", RecipeIngredients.SALT);
        wordsMap.put("sugar", RecipeIngredients.SUGAR);
        wordsMap.put(TRANS_KEY_EGGS, RecipeIngredients.EGGS);
        wordsMap.put(TRANS_KEY_EGG_YOLKS, RecipeIngredients.EGG_YOLKS);
        wordsMap.put(TRANS_KEY_VANILLA, RecipeIngredients.VANILLA);
        wordsMap.put(TRANS_KEY_BROWN_SUGAR, RecipeIngredients.BROWN_SUGAR);
        wordsMap.put(TRANS_KEY_SEMISWEET_CHOC, RecipeIngredients.SEMISWEET_CHOCOLATE);
        wordsMap.put(TRANS_KEY_HEAVY_CREAM, RecipeIngredients.HEAVY_CREAM);
        wordsMap.put("milk", RecipeIngredients.MILK);
        wordsMap.put(TRANS_KEY_YOGURT, RecipeIngredients.YOGURT);
    }

    // TODO: regex for matching numbers and units without spacing; e.g. 8oz
}
