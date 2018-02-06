package com.bigzindustries.brochefbakercompanion.conversion;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

    Activity activity;
    EditText fromVal;
    TextView toVal;
    Spinner fromUnit;
    Spinner toUnit;
    Spinner ingredient;
    View swap;

    TextWatcher fromTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            updateNumbers();
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    public ConversionController(Activity activity, View view) {
        ArrayList<Ingredients> ingredients = getIngredients();
        ArrayList<Units> units = getUnits();

        this.activity = activity;
        ingredient = view.findViewById(R.id.ingredient_spinner);
        fromUnit = view.findViewById(R.id.from_unit_spinner);
        toUnit = view.findViewById(R.id.to_unit_spinner);
        fromVal = view.findViewById(R.id.from_value);
        toVal = view.findViewById(R.id.to_value);
        swap = view.findViewById(R.id.swap);

        IngredientSpinnerAdapter ingredientsAdapter =
                new IngredientSpinnerAdapter(activity, ingredients);
        ingredient.setAdapter(ingredientsAdapter);

        UnitSpinnerAdapter unitAdapter = new UnitSpinnerAdapter(activity, units);
        fromUnit.setAdapter(unitAdapter);
        unitAdapter = new UnitSpinnerAdapter(activity, units);
        toUnit.setAdapter(unitAdapter);

        addAllChangeListeners();

        swap.setOnClickListener(clickedView -> {
            removeAllChangeListeners();

            int tmpPos = toUnit.getSelectedItemPosition();
            CharSequence tmpVal = toVal.getText();

            toUnit.setSelection(fromUnit.getSelectedItemPosition());
            toVal.setText(fromVal.getText());

            fromUnit.setSelection(tmpPos);
            fromVal.setText(tmpVal);

            addAllChangeListeners();

            updateNumbers();
        });

        int pos = ((ArrayAdapter)toUnit.getAdapter()).getPosition(Units.CUPS);
        toUnit.setSelection(pos);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        updateNumbers();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void handleFocus() {
        try {
            fromVal.requestFocus();
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(fromVal, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addConversionToDb(Context context, long setId) {
        new Thread(() -> {
            double from = TextUtils.isEmpty(fromVal.getText().toString()) ? 0.0 :
                    Double.valueOf(fromVal.getText().toString());
            double to = TextUtils.isEmpty(toVal.getText().toString()) ? 0.0 :
                    Double.valueOf(toVal.getText().toString());

            ContentValues values = BroChefDbHelper.getValsForConversionInsert(setId,
                    from,
                    to,
                    fromUnit.getSelectedItem().toString(),
                    toUnit.getSelectedItem().toString(),
                    ingredient.getSelectedItem().toString());

            context.getContentResolver().insert(BroChefContentProvider.CONVERSIONS_URI, values);
        }).start();
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

    }

    private void addAllChangeListeners() {
        ingredient.setOnItemSelectedListener(this);
        fromUnit.setOnItemSelectedListener(this);
        toUnit.setOnItemSelectedListener(this);
        fromVal.addTextChangedListener(fromTextWatcher);
    }

    private void updateNumbers() {
        removeAllChangeListeners();

        if (ingredient == null) {
            throw new IllegalAccessError("Controller not initialized");
        }

        Double value;
        Double conversionValue;

        value = getDoubleFromTextWidget(fromVal);

        String ingredientStr = ingredient.getSelectedItem().toString();
        String fromUnitStr = fromUnit.getSelectedItem().toString();
        String toUnitStr = toUnit.getSelectedItem().toString();

        Ingredients ingredientEnum = Ingredients.valueOf(ingredientStr);
        Units fromUnitEnum = Units.valueOf(fromUnitStr);
        Units toUnitEnum = Units.valueOf(toUnitStr);

        conversionValue = Conversions.convert(ingredientEnum, fromUnitEnum, toUnitEnum, value);

        toVal.setText(conversionValue.toString());

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

    //    public void setValues(String newIngredient, String newFromUnit, String newToUnit,
//                          double newFromVal, double newToVal) {
//        int pos = ((ArrayAdapter)ingredient.getAdapter()).getPosition(Ingredients.valueOf(newIngredient));
//        ingredient.setSelection(pos);
//
//        pos = ((ArrayAdapter)fromUnit.getAdapter()).getPosition(Units.valueOf(newFromUnit));
//        fromUnit.setSelection(pos);
//
//        pos = ((ArrayAdapter)toUnit.getAdapter()).getPosition(Units.valueOf(newToUnit));
//        toUnit.setSelection(pos);
//
//        fromVal.setText(String.valueOf(newFromVal));
//        toVal.setText(String.valueOf(newToVal));
//    }
}
