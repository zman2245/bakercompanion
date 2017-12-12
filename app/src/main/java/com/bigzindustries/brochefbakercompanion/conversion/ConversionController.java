package com.bigzindustries.brochefbakercompanion.conversion;

import android.content.Context;
import android.view.View;
import android.widget.Spinner;

import com.bigzindustries.brochefbakercompanion.R;

public class ConversionController {

    public void setupView(Context context, View view) {
        IngredientSpinnerAdapter ingredientsAdapter = new IngredientSpinnerAdapter(context);
        ((Spinner)view.findViewById(R.id.ingredient_spinner)).setAdapter(ingredientsAdapter);

        UnitSpinnerAdapter unitAdapter = new UnitSpinnerAdapter(context);
        ((Spinner)view.findViewById(R.id.from_unit_spinner)).setAdapter(unitAdapter);
    }
}
