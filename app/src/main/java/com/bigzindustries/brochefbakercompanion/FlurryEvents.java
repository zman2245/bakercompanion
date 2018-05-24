package com.bigzindustries.brochefbakercompanion;

import com.bigzindustries.brochefbakercompanion.recipeparser.models.IngredientResults;
import com.bigzindustries.brochefbakercompanion.unitdata.Ingredients;
import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.Map;

public class FlurryEvents {
    public static final String PRO_TIPS_VIEWED = "ProTipsViewed";
    public static final String RECIPE_LIST_VIEWED = "RecipeListViewed";
    public static final String CONVERSION_CALCULATED = "ConversionCalculated";
    public static final String ADD_RECIPE_TAPPED = "AddRecipeTapped";
    public static final String PASTE_IN_RECIPE_ENTERED = "PasteInRecipeEntered";
    public static final String SAVE_PASTE_IN_RECIPE_TAPPED = "SavePasteInRecipeTapped";
    public static final String SAVE_PASTE_IN_RECIPE_INGREDIENT_PARSE_ERROR =
            "PasteIngredientParseError";
    public static final String SAVE_PASTE_IN_RECIPE_COMPLETED = "SavePasteInRecipeCompleted";

    public static final String APP_LOCATION = "AppLocation";

    public static final String APP_LOCATION_RECIPE_LIST_MENU = "RecipeListMenu";
    public static final String APP_LOCATION_EDIT_RECIPE_DIALOG = "EditRecipeDialog";

    public static void logConversion(String ingredient, String fromUnit, String toUnit,
                                     double value) {
        Map<String, String> args = new HashMap<>();
        args.put("ingredient", ingredient);
        args.put("fromUnit", fromUnit);
        args.put("toUnit", toUnit);
        args.put("value", String.valueOf(value));

        FlurryAgent.logEvent(CONVERSION_CALCULATED, args);
    }

    public static void logIngredientParseError(IngredientResults ingredient) {
        Map<String, String> args = new HashMap<>();
        args.put("ingredientNum", String.valueOf(ingredient.getHumanReadableIngredientNumber()));
        args.put("recipeLine", ingredient.getOriginalLine());

        FlurryAgent.logEvent(SAVE_PASTE_IN_RECIPE_INGREDIENT_PARSE_ERROR, args);
    }

    public static void logPasteInRecipeEntered(String location) {
        Map<String, String> args = new HashMap<>();
        args.put(APP_LOCATION, location);

        FlurryAgent.logEvent(PASTE_IN_RECIPE_ENTERED, args);
    }

    public static void logPasteInRecipeCompleted(String input, String name, String notes) {
        Map<String, String> args = new HashMap<>();
        args.put("input", input);
        args.put("name", name);
        args.put("notes", notes);

        FlurryAgent.logEvent(SAVE_PASTE_IN_RECIPE_COMPLETED, args);
    }
}
