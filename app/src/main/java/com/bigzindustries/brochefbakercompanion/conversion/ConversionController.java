package com.bigzindustries.brochefbakercompanion.conversion;

import android.content.ContentValues;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.db.BroChefContentProvider;
import com.bigzindustries.brochefbakercompanion.db.BroChefDbHelper;
import com.bigzindustries.brochefbakercompanion.unitdata.Conversions;
import com.bigzindustries.brochefbakercompanion.unitdata.Ingredients;
import com.bigzindustries.brochefbakercompanion.unitdata.Units;

import java.util.ArrayList;

/**
 * Disable Add button if info not all filled in?
 *
 * Setup the conversion calculation, listening for each change
 */
public class ConversionController implements AdapterView.OnItemSelectedListener {

    EditText fromVal;
    EditText toVal;
    Spinner fromUnit;
    Spinner toUnit;
    Spinner ingredient;

    TextWatcher fromTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            updateNumbers(false);
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    TextWatcher toTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            updateNumbers(true);
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    public ConversionController(Context context, View view) {
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

        addAllChangeListeners();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        updateNumbers(false);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

    public void setValues(String newIngredient, String newFromUnit, String newToUnit,
                          double newFromVal, double newToVal) {
        int pos = ((ArrayAdapter)ingredient.getAdapter()).getPosition(Ingredients.valueOf(newIngredient));
        ingredient.setSelection(pos);

        pos = ((ArrayAdapter)fromUnit.getAdapter()).getPosition(Units.valueOf(newFromUnit));
        fromUnit.setSelection(pos);

        pos = ((ArrayAdapter)toUnit.getAdapter()).getPosition(Units.valueOf(newToUnit));
        toUnit.setSelection(pos);

        fromVal.setText(String.valueOf(newFromVal));
        toVal.setText(String.valueOf(newToVal));
    }

    private Double getDoubleFromTextWidget(EditText text) {
        if (TextUtils.isEmpty(text.getText().toString())) {
            return 0.0;
        } else {
            try {
                return Double.valueOf(text.getText().toString());
            } catch (Exception e) {
                return 0.0;
            }
        }
    }

    private void removeAllChangeListeners() {
        ingredient.setOnItemSelectedListener(null);
        fromUnit.setOnItemSelectedListener(null);
        toUnit.setOnItemSelectedListener(null);
        fromVal.removeTextChangedListener(fromTextWatcher);
        toVal.removeTextChangedListener(toTextWatcher);

    }

    private void addAllChangeListeners() {
        ingredient.setOnItemSelectedListener(this);
        fromUnit.setOnItemSelectedListener(this);
        toUnit.setOnItemSelectedListener(this);
        fromVal.addTextChangedListener(fromTextWatcher);
        toVal.addTextChangedListener(toTextWatcher);
    }

    private void updateNumbers(boolean backwards) {
        removeAllChangeListeners();

        if (ingredient == null) {
            throw new IllegalAccessError("Controller not initialized");
        }

        Double value;
        Double conversionValue;

        if (backwards) {
            value = getDoubleFromTextWidget(toVal);
        } else {
            value = getDoubleFromTextWidget(fromVal);
        }

        String ingredientStr = ingredient.getSelectedItem().toString();
        String fromUnitStr = fromUnit.getSelectedItem().toString();
        String toUnitStr = toUnit.getSelectedItem().toString();

        Ingredients ingredientEnum = Ingredients.valueOf(ingredientStr);
        Units fromUnitEnum = Units.valueOf(fromUnitStr);
        Units toUnitEnum = Units.valueOf(toUnitStr);

        conversionValue = Conversions.convert(ingredientEnum, fromUnitEnum, toUnitEnum, value);

        if (backwards) {
            fromVal.setText(conversionValue.toString());
        } else {
            toVal.setText(conversionValue.toString());
        }

        addAllChangeListeners();
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
