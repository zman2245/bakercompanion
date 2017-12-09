package com.bigzindustries.brochefbakercompanion.conversion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.bigzindustries.brochefbakercompanion.unitdata.Units;

public class UnitSpinnerAdapter extends ArrayAdapter<Units> {
    public UnitSpinnerAdapter(Context context) {
        super(context, 0, Units.values());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheckedTextView text= (CheckedTextView) convertView;

        if (text== null) {
            text = (CheckedTextView) LayoutInflater.from(
                        getContext()).inflate(
                            android.R.layout.simple_spinner_dropdown_item,  parent, false);
        }

        text.setText(getItem(position).name);

        return text;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        CheckedTextView text = (CheckedTextView) convertView;

        if (text == null) {
            text = (CheckedTextView)
                    LayoutInflater.from(
                        getContext()).inflate(
                            android.R.layout.simple_spinner_dropdown_item,  parent, false);
        }

        text.setText(getItem(position).name);

        return text;
    }
}
