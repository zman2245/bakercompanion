package com.bigzindustries.brochefbakercompanion.recipeparser;

import com.bigzindustries.brochefbakercompanion.recipeparser.models.IngredientResults;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeIngredients;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeResults;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeUnits;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SeriousEatsWholeParserTest {

    @Test
    public void testChocolateChipCookies() {
        Parser p = new Parser();

        String input =
                "8 ounces unsalted butter (2 sticks; 225g)\n" +
                        "1 standard ice cube (about 2 tablespoons; 30mL frozen water)\n" +
                        "10 ounces all-purpose flour (about 2 cups; 280g)\n" +
                        "3/4 teaspoon (3g) baking soda\n" +
                        "2 teaspoons Diamond Crystal kosher salt or 1 teaspoon table salt (4g)\n" +
                        "5 ounces granulated sugar (about 3/4 cup; 140g)\n" +
                        "2 large eggs (100g)\n" +
                        "2 teaspoons (10mL) vanilla extract\n" +
                        "5 ounces dark brown sugar (about 1/2 tightly packed cup plus 2 tablespoons; 140g)\n" +
                        "8 ounces (225g) semisweet chocolate, roughly chopped with a knife into 1/2- to 1/4-inch chunks\n" +
                        "Coarse sea salt, for garnish";

        RecipeResults results = p.parse(input);
        List<IngredientResults> ingredients = results.getIngredients();

        assertEquals(11, ingredients.size());
        assertEquals(8, ingredients.get(0).getAmount(), 0.1);
        assertEquals(RecipeIngredients.UNSALTED_BUTTER, ingredients.get(0).getIngredient());
        assertEquals(RecipeUnits.OUNCES, ingredients.get(0).getUnit());
    }
}
