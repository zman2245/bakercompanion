package com.bigzindustries.brochefbakercompanion.recipeparser;

import com.bigzindustries.brochefbakercompanion.recipeparser.models.IngredientResults;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeIngredients;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeResults;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeUnits;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class FoodNetworkWholeParserTest {

    @Test
    public void testCheesecake() {
        Parser p = new Parser();

        String input =
                "Crust:\n" +
                        "1 cup graham cracker crumbs\n" +
                        "1/4 cup unsalted butter, melted\n" +
                        "1 tablespoon sugar\n" +
                        "Filling:\n" +
                        "16 ounces cream cheese, at room temperature\n" +
                        "2/3 cup sugar\n" +
                        "1 cup sour cream\n" +
                        "5 large eggs, room temperature\n" +
                        "1 tablespoon vanilla extract\n" +
                        "1/2 cup heavy cream\n" +
                        "Serving suggestion: Fresh or Marinated Berries, or Raspberry Sauce";

        RecipeResults results = p.parse(input);
        List<IngredientResults> ingredients = results.getIngredients();

        // Crust
        assertEquals(10, ingredients.size());
        assertEquals(1, ingredients.get(0).getAmount(), 0.1);
        assertEquals(RecipeIngredients.GRAHAM_CRUMBS, ingredients.get(0).getIngredient());
        assertEquals(RecipeUnits.CUPS, ingredients.get(0).getUnit());
        assertEquals("(Crust)", ingredients.get(0).getNotes());

        assertEquals(0.25, ingredients.get(1).getAmount(), 0.1);
        assertEquals(RecipeIngredients.UNSALTED_BUTTER, ingredients.get(1).getIngredient());
        assertEquals(RecipeUnits.CUPS, ingredients.get(1).getUnit());
        assertEquals("(Crust)", ingredients.get(1).getNotes());

        assertEquals(1, ingredients.get(2).getAmount(), 0.1);
        assertEquals(RecipeIngredients.SUGAR, ingredients.get(2).getIngredient());
        assertEquals(RecipeUnits.TBL, ingredients.get(2).getUnit());
        assertEquals("(Crust)", ingredients.get(2).getNotes());

        // Filling
        assertEquals(16, ingredients.get(3).getAmount(), 0.1);
        assertEquals(RecipeIngredients.CREAM_CHEESE, ingredients.get(3).getIngredient());
        assertEquals(RecipeUnits.OUNCES, ingredients.get(3).getUnit());
        assertEquals("(Filling)", ingredients.get(3).getNotes());

        assertEquals(0.667, ingredients.get(4).getAmount(), 0.1);
        assertEquals(RecipeIngredients.SUGAR, ingredients.get(4).getIngredient());
        assertEquals(RecipeUnits.CUPS, ingredients.get(4).getUnit());
        assertEquals("(Filling)", ingredients.get(4).getNotes());

        assertEquals(1, ingredients.get(5).getAmount(), 0.1);
        assertEquals(RecipeIngredients.SOUR_CREAM, ingredients.get(5).getIngredient());
        assertEquals(RecipeUnits.CUPS, ingredients.get(5).getUnit());
        assertEquals("(Filling)", ingredients.get(5).getNotes());

        assertEquals(5, ingredients.get(6).getAmount(), 0.1);
        assertEquals(RecipeIngredients.EGGS, ingredients.get(6).getIngredient());
        assertEquals(RecipeUnits.GENERIC_UNIT, ingredients.get(6).getUnit());
        assertEquals("(Filling)", ingredients.get(6).getNotes());

        assertEquals(1, ingredients.get(7).getAmount(), 0.1);
        assertEquals(RecipeIngredients.VANILLA, ingredients.get(7).getIngredient());
        assertEquals(RecipeUnits.TBL, ingredients.get(7).getUnit());
        assertEquals("(Filling)", ingredients.get(7).getNotes());

        assertEquals(0.5, ingredients.get(8).getAmount(), 0.1);
        assertEquals(RecipeIngredients.HEAVY_CREAM, ingredients.get(8).getIngredient());
        assertEquals(RecipeUnits.CUPS, ingredients.get(8).getUnit());
        assertEquals("(Filling)", ingredients.get(8).getNotes());
    }
}
