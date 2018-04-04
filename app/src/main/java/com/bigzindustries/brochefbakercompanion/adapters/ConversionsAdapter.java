package com.bigzindustries.brochefbakercompanion.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.unitdata.Ingredients;
import com.bigzindustries.brochefbakercompanion.unitdata.Units;
import com.bigzindustries.brochefbakercompanion.unitdata.Utility;

public class ConversionsAdapter extends CursorAdapter {

    public ConversionsAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.simple_conversion, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        double toVal = cursor.getDouble(cursor.getColumnIndex("toValue"));
        double fromVal = cursor.getDouble(cursor.getColumnIndex("fromValue"));
        String fromUnit = Utility.getUnitString(cursor.getString(cursor.getColumnIndex("fromUnit")));
        String toUnit = Utility.getUnitString(cursor.getString(cursor.getColumnIndex("toUnit")));
        String ingredient = Utility.getIngredientString(cursor.getString(cursor.getColumnIndex("ingredient")));

        ((TextView)view.findViewById(R.id.ingredient_value)).setText(ingredient);
        ((TextView)view.findViewById(R.id.from_value)).setText(
                Utility.getMeasurementString(fromVal, fromUnit)
        );
        ((TextView)view.findViewById(R.id.to_value)).setText(
                Utility.getMeasurementString(toVal, toUnit)
        );

        if (toVal <= 0.0) {
            // there's no conversion really, so hide the conversion-relevant UI elements
            view.findViewById(R.id.arrow).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.to_value).setVisibility(View.INVISIBLE);
        }
    }
}
