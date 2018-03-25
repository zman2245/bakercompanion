package com.bigzindustries.brochefbakercompanion.recipeparser.transforms;

import com.bigzindustries.brochefbakercompanion.recipeparser.grammar.Vocab;

public class IngredientPhraseTransform implements RecipeTransform {

    @Override
    public String transform(String input) {
        String result = input;

        result = result.replace("baking soda", Vocab.TRANS_KEY_BAKING_SODA);
        result = result.replace("baking powder", Vocab.TRANS_KEY_BAKING_POWDER);

        result = result.replace("all-purpose flour", Vocab.TRANS_KEY_AP_FLOUR);

        result = result.replace("granulated sugar", Vocab.TRANS_KEY_SUGAR);

        result = result.replace("large eggs", Vocab.TRANS_KEY_EGGS);
        result = result.replace("large egg", Vocab.TRANS_KEY_EGGS);
        result = result.replace("egg yolks", Vocab.TRANS_KEY_EGG_YOLKS);
        result = result.replace("egg yolk", Vocab.TRANS_KEY_EGG_YOLKS);

        result = result.replace("vanilla extract", Vocab.TRANS_KEY_VANILLA);

        result = result.replace("dark brown sugar", Vocab.TRANS_KEY_BROWN_SUGAR);
        result = result.replace("brown sugar", Vocab.TRANS_KEY_BROWN_SUGAR);

        result = result.replace("semisweet chocolate", Vocab.TRANS_KEY_SEMISWEET_CHOC);
        result = result.replace("semi-sweet chocolate", Vocab.TRANS_KEY_SEMISWEET_CHOC);

        result = result.replace("heavy cream", Vocab.TRANS_KEY_HEAVY_CREAM);
        result = result.replace("whipping cream", Vocab.TRANS_KEY_HEAVY_CREAM);
        result = result.replace("heavy whipping cream", Vocab.TRANS_KEY_HEAVY_CREAM);

        result = result.replace("plain yogurt", Vocab.TRANS_KEY_YOGURT);

        return result;
    }
}
