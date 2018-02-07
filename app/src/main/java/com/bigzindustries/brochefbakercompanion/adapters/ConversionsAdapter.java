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

import org.w3c.dom.Text;

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
        String fromUnit = getUnitString(cursor.getString(cursor.getColumnIndex("fromUnit")));
        String toUnit = getUnitString(cursor.getString(cursor.getColumnIndex("toUnit")));
        String ingredient = getIngredientString(cursor.getString(cursor.getColumnIndex("ingredient")));

        ((TextView)view.findViewById(R.id.ingredient_value)).setText(ingredient);
        ((TextView)view.findViewById(R.id.from_value)).setText(
                getMeasurementString(fromVal, fromUnit)
        );
        ((TextView)view.findViewById(R.id.to_value)).setText(
                getMeasurementString(toVal, toUnit)
        );

        if (toVal <= 0.0) {
            // there's no conversion really, so hide the conversion-relevant UI elements
            view.findViewById(R.id.arrow).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.to_value).setVisibility(View.INVISIBLE);
        }
    }

    // TODO: belongs in a model class
    private String getUnitString(String unit) {
        if (TextUtils.isEmpty(unit)) {
            return unit;
        }

        try {
            Units unitEnum = Units.valueOf(unit);
            return unitEnum.getName();
        } catch (IllegalArgumentException e) {
            // This is hacky because this is expected for custom ingredients. Perhaps add type to DB?
            return unit;
        }
    }

    // TODO: belongs in a model class
    private String getIngredientString(String ingredient) {
        if (TextUtils.isEmpty(ingredient)) {
            return ingredient;
        }

        try {
            Ingredients ingEnum = Ingredients.valueOf(ingredient);
            return ingEnum.getName();
        } catch (IllegalArgumentException e) {
            // This is hacky because this is expected for custom ingredients. Perhaps add type to DB?
            return ingredient;
        }
    }

    private String getMeasurementString(double val, String unit) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%.2f", val))
                .append(" ")
                .append(unit.toLowerCase());

        return builder.toString();
    }
}
