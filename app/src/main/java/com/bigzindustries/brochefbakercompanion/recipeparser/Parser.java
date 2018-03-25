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
        List<IngredientResults> ingredients = new ArrayList<>();

        for (String line : lines) {
            ingredient = parseLine(line);
            ingredients.add(ingredient);
        }


        return new RecipeResults(ingredients);
    }

    @VisibleForTesting
    public IngredientResults parseLine(String inputLine) {
        IngredientResults results = new IngredientResults();
        String transformedInput = applyTransformChain(inputLine);

        String[] tokens = transformedInput.split(" ");

        for (String token : tokens) {
            Object vocabWord = Vocab.wordsMap.get(token);
            if (vocabWord == null) {
                // not a vocab word
                double num;
                try {
                    num = Double.parseDouble(token);
                    results.setAmount(num);
                } catch (NumberFormatException nfe) {
                    // it's not a number either. TODO: anything else to try?
                    continue;
                }
            } else if (vocabWord.getClass() == RecipeUnits.class) {
                results.setUnit((RecipeUnits)vocabWord);
            } else if (vocabWord.getClass() == RecipeIngredients.class) {
                results.setIngredient((RecipeIngredients)vocabWord);
            }
        }

        return results;
    }

//    public  parseToken(String token) {
//        Object result = Vocab.wordsMap.get(token);
//
//        return
//    }

    @VisibleForTesting
    public String applyTransformChain(String input) {
        String result = input;

        for (RecipeTransform transform : transformChain) {
            result = transform.transform(result);
        }

        return result;
    }
}
