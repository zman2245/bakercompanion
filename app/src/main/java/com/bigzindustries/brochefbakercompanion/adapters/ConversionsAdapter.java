package com.bigzindustries.brochefbakercompanion.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.conversion.ConversionController;

import java.util.HashMap;

public class ConversionsAdapter extends CursorAdapter {

    HashMap<View, ConversionController> controllers = new HashMap<>();

    public ConversionsAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.conversion_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ConversionController controller = controllers.get(view);
        if (controller == null) {
            controller = new ConversionController(context, view);
            controllers.put(view, controller);
        }

        controller.setValues(
            cursor.getString(cursor.getColumnIndex("ingredient")),
            cursor.getString(cursor.getColumnIndex("fromUnit")),
            cursor.getString(cursor.getColumnIndex("toUnit")),
            cursor.getDouble(cursor.getColumnIndex("fromValue")),
            cursor.getDouble(cursor.getColumnIndex("toValue"))
        );

        // Find fields to populate in inflated template
//        Spinner ingredient = view.findViewById(R.id.ingredient_spinner);
//        Spinner fromUnit = view.findViewById(R.id.from_unit_spinner);
//        Spinner toUnit = view.findViewById(R.id.to_unit_spinner);
//        EditText fromVal = view.findViewById(R.id.from_value);
//        EditText toVal = view.findViewById(R.id.to_value);

        // Populate fields with extracted cursor properties
//        ingredient.
    }
}
