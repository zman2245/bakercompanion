package com.bigzindustries.brochefbakercompanion.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.bigzindustries.brochefbakercompanion.R;

public class ConversionsAdapter extends CursorAdapter {

    public ConversionsAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.conversion_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        Spinner ingredient = view.findViewById(R.id.ingredient_spinner);
        Spinner fromUnit = view.findViewById(R.id.from_unit_spinner);
        Spinner toUnit = null; //TBD

        // Populate fields with extracted cursor properties
    }
}
