package com.bigzindustries.brochefbakercompanion.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bigzindustries.brochefbakercompanion.R;

public class ConversionSetsAdapter extends CursorAdapter {

    public ConversionSetsAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.conversion_set_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView name = view.findViewById(R.id.name);

        // Populate fields with extracted cursor properties
        name.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
    }
}
