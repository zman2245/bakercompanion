package com.bigzindustries.brochefbakercompanion.conversion;

import android.content.ContentValues;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.db.BroChefContentProvider;
import com.bigzindustries.brochefbakercompanion.db.BroChefDbHelper;
import com.bigzindustries.brochefbakercompanion.unitdata.Ingredients;
import com.bigzindustries.brochefbakercompanion.unitdata.Units;

import java.util.ArrayList;

/**
 * Disable Add button if info not all filled in?
 *
 * Setup the conversion calculation, listening for each change
 */
public class ConversionController {

    EditText fromVal;
    EditText toVal;
    Spinner fromUnit;
    Spinner toUnit;
    Spinner ingredient;

    public void setupView(Context context, View view) {
        ArrayList<Ingredients> ingredients = getIngredients();
        ArrayList<Units> units = getUnits();

        ingredient = view.findViewById(R.id.ingredient_spinner);
        fromUnit = view.findViewById(R.id.from_unit_spinner);
        toUnit = view.findViewById(R.id.to_unit_spinner);
        fromVal = view.findViewById(R.id.from_value);
        toVal = view.findViewById(R.id.to_value);

        IngredientSpinnerAdapter ingredientsAdapter =
                new IngredientSpinnerAdapter(context, ingredients);
        ingredient.setAdapter(ingredientsAdapter);

        UnitSpinnerAdapter unitAdapter = new UnitSpinnerAdapter(context, units);
        fromUnit.setAdapter(unitAdapter);
        unitAdapter = new UnitSpinnerAdapter(context, units);
        toUnit.setAdapter(unitAdapter);
    }

    public void addConversionToDb(Context context, long setId) {
        new Thread(() -> {
            ContentValues values = BroChefDbHelper.getValsForConversionInsert(setId,
                    Double.valueOf(fromVal.getText().toString()),
                    Double.valueOf(toVal.getText().toString()),
                    fromUnit.getSelectedItem().toString(),
                    toUnit.getSelectedItem().toString(),
                    ingredient.getSelectedItem().toString());

            context.getContentResolver().insert(BroChefContentProvider.CONVERSIONS_URI, values);
        }).start();
    }

    // Java 8 stream API only works on API levels >= 24 :(
    private ArrayList<Ingredients> getIngredients() {
        Ingredients[] originals = Ingredients.values();
        ArrayList<Ingredients> ingredients = new ArrayList<>();
        for (Ingredients ingredient : originals) {
            if (ingredient.isStandard) {
                ingredients.add(ingredient);
            }
        }

        return ingredients;
    }

    // Java 8 stream API only works on API levels >= 24 :(
    private ArrayList<Units> getUnits() {
        Units[] originals = Units.values();
        ArrayList<Units> units = new ArrayList<>();
        for (Units unit : originals) {
            if (unit.isStandard) {
                units.add(unit);
            }
        }

        return units;
    }
}
