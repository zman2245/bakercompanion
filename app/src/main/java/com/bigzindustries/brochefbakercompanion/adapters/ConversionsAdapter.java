package com.bigzindustries.brochefbakercompanion.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigzindustries.brochefbakercompanion.R;

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
        ((TextView)view.findViewById(R.id.ingredient_value)).setText(cursor.getString(cursor.getColumnIndex("ingredient")).toLowerCase());
        ((TextView)view.findViewById(R.id.from_value)).setText(
                getMeasurementString(cursor.getDouble(cursor.getColumnIndex("fromValue")),
                        cursor.getString(cursor.getColumnIndex("fromUnit")))
        );
        ((TextView)view.findViewById(R.id.to_value)).setText(
                getMeasurementString(cursor.getDouble(cursor.getColumnIndex("toValue")),
                        cursor.getString(cursor.getColumnIndex("toUnit")))
        );
    }

    private String getMeasurementString(double val, String unit) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%.2f", val))
                .append(" ")
                .append(unit.toLowerCase());

        return builder.toString();
    }
}
