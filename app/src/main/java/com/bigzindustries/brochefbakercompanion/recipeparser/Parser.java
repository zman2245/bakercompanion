package com.bigzindustries.brochefbakercompanion.recipeparser;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.bigzindustries.brochefbakercompanion.recipeparser.grammar.Vocab;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.IngredientResults;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeIngredients;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeResults;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeUnits;
import com.bigzindustries.brochefbakercompanion.recipeparser.transforms.FractionsToDecimalsTransform;
import com.bigzindustries.brochefbakercompanion.recipeparser.transforms.IngredientPhraseTransform;
import com.bigzindustries.brochefbakercompanion.recipeparser.transforms.RecipeTransform;
import com.bigzindustries.brochefbakercompanion.recipeparser.transforms.RemoveParentheticalTransform;
import com.bigzindustries.brochefbakercompanion.recipeparser.transforms.UniformTransform;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static List<RecipeTransform> transformChain = new ArrayList<>();
    static {
        transformChain.add(new UniformTransform());
        transformChain.add(new FractionsToDecimalsTransform());
        transformChain.add(new RemoveParentheticalTransform());
        transformChain.add(new IngredientPhraseTransform());
    }

    public RecipeResults parse(@NonNull String input) {
        String[] lines = input.split("\n");
        IngredientResults ingredient;
        String subheading = null;
        List<IngredientResults> ingredients = new ArrayList<>();
        int humanReadableLineNumber = 0;

        for (String line : lines) {

            // check to see if this line is just a sub heading of the ingredient list
            String newSubheading = checkParseSubheading(line);
            if (newSubheading != null) {
                // this line is just a sub-heading, track it and continue to next line
                subheading = newSubheading;
                continue;
            }

            ingredient = new IngredientResults();

            humanReadableLineNumber++;
            ingredient.setHumanReadableIngredientNumber(humanReadableLineNumber);

            if (subheading != null) {
                ingredient.setNotes("(" + subheading + ")");
            }

            ingredient = parseLine(ingredient, line);
            ingredients.add(ingredient);
        }


        return new RecipeResults(ingredients);
    }

    @VisibleForTesting
    public IngredientResults parseLine(String inputLine) {
        IngredientResults ingredient = new IngredientResults();
        return parseLine(ingredient, inputLine);
    }

    private IngredientResults parseLine(IngredientResults ingredient, String inputLine) {
        ingredient.setOriginalLine(inputLine);
        String transformedInput = applyTransformChain(inputLine);

        String[] tokens = transformedInput.split(" ");

        for (String token : tokens) {
            parseToken(ingredient, token);
        }

        return ingredient;
    }

    public void parseToken(IngredientResults results, String token) {
        Object vocabWord = Vocab.wordsMap.get(token);
        if (vocabWord == null) {
            // not a vocab word
            double num;
            try {
                num = Double.parseDouble(token);
                results.setAmount(num);
            } catch (NumberFormatException nfe) {
                // it's not a number either. TODO: anything else to try?
            }
        } else if (vocabWord.getClass() == RecipeUnits.class) {
            results.setUnit((RecipeUnits)vocabWord);
        } else if (vocabWord.getClass() == RecipeIngredients.class) {
            results.setIngredient((RecipeIngredients)vocabWord);
        }
    }

    @VisibleForTesting
    public String applyTransformChain(String input) {
        String result = input;

        for (RecipeTransform transform : transformChain) {
            result = transform.transform(result);
        }

        return result;
    }

    // food network uses them, maybe others
    public String checkParseSubheading(String inputLine) {
        if (Vocab.SUBHEADING_KEYWORDS.contains(inputLine.toLowerCase())) {
            return inputLine.replace(":", "");
        }

        return null;
    }
}
