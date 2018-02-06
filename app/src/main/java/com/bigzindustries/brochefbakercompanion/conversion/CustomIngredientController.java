package com.bigzindustries.brochefbakercompanion.conversion;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.db.BroChefContentProvider;
import com.bigzindustries.brochefbakercompanion.db.BroChefDbHelper;
import com.bigzindustries.brochefbakercompanion.unitdata.Units;

import java.util.ArrayList;

/**
 * Disable Add button if info not all filled in?
 *
 * Setup the conversion calculation, listening for each change
 */
public class CustomIngredientController {

    Activity activity;
    EditText fromVal;
    Spinner fromUnit;
    EditText customIngredient;

    public CustomIngredientController(Activity activity, View view) {
        ArrayList<Units> units = getUnits();

        this.activity = activity;
        customIngredient = view.findViewById(R.id.custom_ingredient);
        fromUnit = view.findViewById(R.id.from_unit_spinner);
        fromVal = view.findViewById(R.id.from_value);

        UnitSpinnerAdapter unitAdapter = new UnitSpinnerAdapter(activity, units);
        fromUnit.setAdapter(unitAdapter);
    }

    public void addConversionToDb(Context context, long setId) {
        new Thread(() -> {
            double from = getDoubleFromTextWidget(fromVal);

            ContentValues values = BroChefDbHelper.getValsForConversionInsert(setId,
                    from,
                    0.0,
                    fromUnit.getSelectedItem().toString(),
                    "",
                    customIngredient.getText().toString());

            context.getContentResolver().insert(BroChefContentProvider.CONVERSIONS_URI, values);
        }).start();
    }

    public void handleFocus() {
        try {
            customIngredient.requestFocus();
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(customIngredient, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
