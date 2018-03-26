package com.bigzindustries.brochefbakercompanion.recipeparser;

import com.bigzindustries.brochefbakercompanion.recipeparser.models.IngredientResults;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeIngredients;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeUnits;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SeriousEatsLineParserTest {
    @Test
    public void testSeriousEatsCookies1() {
        Parser p = new Parser();
        String input;
        IngredientResults results;

        input = "8 ounces unsalted butter (2 sticks; 225g)";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.OUNCES, results.getUnit());
        assertEquals(RecipeIngredients.UNSALTED_BUTTER, results.getIngredient());
        assertEquals(8.0, results.getAmount(), 0.01);

        input = "10 ounces all-purpose flour (about 2 cups; 280g)";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.OUNCES, results.getUnit());
        assertEquals(RecipeIngredients.AP_FLOUR, results.getIngredient());
        assertEquals(10.0, results.getAmount(), 0.01);

        input = "3/4 teaspoon (3g) baking soda";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.TSP, results.getUnit());
        assertEquals(RecipeIngredients.BAKING_SODA, results.getIngredient());
        assertEquals(0.75, results.getAmount(), 0.01);

        input = "2 teaspoons Diamond Crystal kosher salt or 1 teaspoon table salt (4g)";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.TSP, results.getUnit());
        assertEquals(RecipeIngredients.SALT, results.getIngredient());
        assertEquals(1.0, results.getAmount(), 0.01);

        input = "5 ounces granulated sugar (about 3/4 cup; 140g)";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.OUNCES, results.getUnit());
        assertEquals(RecipeIngredients.SUGAR, results.getIngredient());
        assertEquals(5.0, results.getAmount(), 0.01);

        input = "2 teaspoons (10mL) vanilla extract";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.TSP, results.getUnit());
        assertEquals(RecipeIngredients.VANILLA, results.getIngredient());
        assertEquals(2.0, results.getAmount(), 0.01);
    }

    @Test
    public void testSeriousEatsCookies2() {
        Parser p = new Parser();
        String input;
        IngredientResults results;

        input = "2 large eggs (100g)";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.GENERIC_UNIT, results.getUnit());
        assertEquals(RecipeIngredients.EGGS, results.getIngredient());
        assertEquals(2.0, results.getAmount(), 0.01);

        input = "5.2 ounces dark brown sugar (about 1/2 tightly packed cup plus 2 tablespoons; 140g)";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.OUNCES, results.getUnit());
        assertEquals(RecipeIngredients.BROWN_SUGAR, results.getIngredient());
        assertEquals(5.2, results.getAmount(), 0.01);

        input = "8 ounces (225g) semisweet chocolate, roughly chopped with a knife into 1/2- to 1/4-inch chunks";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.OUNCES, results.getUnit());
        assertEquals(RecipeIngredients.SEMISWEET_CHOCOLATE, results.getIngredient());
        assertEquals(8.0, results.getAmount(), 0.01);
    }

    @Test
    public void testSeriousEatsFluffyBiscuit() {
        Parser p = new Parser();
        String input;
        IngredientResults results;

        input = "9 ounces all-purpose flour, such as Gold Medal (about 2 cups, spooned; 255g)";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.OUNCES, results.getUnit());
        assertEquals(RecipeIngredients.AP_FLOUR, results.getIngredient());
        assertEquals(9.0, results.getAmount(), 0.01);

        input = "1/2 ounce sugar (about 1 tablespoon; 15g)";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.OUNCES, results.getUnit());
        assertEquals(RecipeIngredients.SUGAR, results.getIngredient());
        assertEquals(0.5, results.getAmount(), 0.01);

        input = "1 tablespoon baking powder";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.TBL, results.getUnit());
        assertEquals(RecipeIngredients.BAKING_POWDER, results.getIngredient());
        assertEquals(1.0, results.getAmount(), 0.01);

        input = "1/2 teaspoon baking soda";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.TSP, results.getUnit());
        assertEquals(RecipeIngredients.BAKING_SODA, results.getIngredient());
        assertEquals(0.5, results.getAmount(), 0.01);

        input = "1 1/2 teaspoons (6g) Diamond Crystal kosher salt; for table salt, use about half as much by volume or the same weight";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.TSP, results.getUnit());
        assertEquals(RecipeIngredients.SALT, results.getIngredient());
        assertEquals(1.5, results.getAmount(), 0.01);

        input = "4 ounces cold unsalted butter, cut into 1/2-inch cubes (about 8 tablespoons; 110g)";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.OUNCES, results.getUnit());
        assertEquals(RecipeIngredients.UNSALTED_BUTTER, results.getIngredient());
        assertEquals(4.0, results.getAmount(), 0.01);

        input = "9 ounces plain yogurt, straight from the fridge (about 1 cup plus 2 tablespoons; 255g), see note";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.OUNCES, results.getUnit());
        assertEquals(RecipeIngredients.YOGURT, results.getIngredient());
        assertEquals(9.0, results.getAmount(), 0.01);
    }

    @Test
    public void testSeriousEatsCaramelFlan() {
        Parser p = new Parser();
        String input;
        IngredientResults results;

        input = "3/4 cup (5 1/2 ounces;150g) sugar";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.CUPS, results.getUnit());
        assertEquals(RecipeIngredients.SUGAR, results.getIngredient());
        assertEquals(0.75, results.getAmount(), 0.01);

        input = "1/2 cup (3 1/2 ounces; 100g) sugar";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.CUPS, results.getUnit());
        assertEquals(RecipeIngredients.SUGAR, results.getIngredient());
        assertEquals(0.50, results.getAmount(), 0.01);

        input = "2 cups (17 ounces; 470g) heavy cream (for toasted cream version see note)";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.CUPS, results.getUnit());
        assertEquals(RecipeIngredients.HEAVY_CREAM, results.getIngredient());
        assertEquals(2.0, results.getAmount(), 0.01);

        input = "1 cup (9 ounces; 250g) milk";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.CUPS, results.getUnit());
        assertEquals(RecipeIngredients.MILK, results.getIngredient());
        assertEquals(1.0, results.getAmount(), 0.01);

        input = "3 large eggs (about 5 3/4 ounces; 165g)";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.GENERIC_UNIT, results.getUnit());
        assertEquals(RecipeIngredients.EGGS, results.getIngredient());
        assertEquals(3, results.getAmount(), 0.01);

        input = "3 egg yolks (about 1 1/2 ounces; 40g)";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.GENERIC_UNIT, results.getUnit());
        assertEquals(RecipeIngredients.EGG_YOLKS, results.getIngredient());
        assertEquals(3, results.getAmount(), 0.01);

        input = "1 teaspoon (4g) Diamond Crystal kosher salt; for iodized salt, use half as much by volume or the same weight";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.TSP, results.getUnit());
        assertEquals(RecipeIngredients.SALT, results.getIngredient());
        assertEquals(1, results.getAmount(), 0.01);

        input = "1 teaspoon (5g) vanilla extract";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.TSP, results.getUnit());
        assertEquals(RecipeIngredients.VANILLA, results.getIngredient());
        assertEquals(1, results.getAmount(), 0.01);

        input = "Maldon salt to taste";
        results = p.parseLine(input);
        assertEquals(RecipeUnits.GENERIC_UNIT, results.getUnit());
        assertEquals(RecipeIngredients.SALT, results.getIngredient());
        assertEquals(0, results.getAmount(), 0.01);
    }
}
