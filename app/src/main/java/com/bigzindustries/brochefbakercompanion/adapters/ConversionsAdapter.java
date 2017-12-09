package com.bigzindustries.brochefbakercompanion.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.Spinner;

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.models.Conversion;

public class ConversionsAdapter implements ListAdapter {

    private final LayoutInflater inflater;

    // Helper class for caching
    private static class ViewHolder {
        Spinner ingredient;
        Spinner fromUnit;
        Spinner toUnit;
    }

    public ConversionsAdapter(Context context) {
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ConversionsAdapter.ViewHolder holder;
        Conversion details = (Conversion) getItem(i);

        if (view == null) {
            view = inflater.inflate(R.layout.conversion_view, viewGroup, false);
            holder = new ConversionsAdapter.ViewHolder();
            holder.ingredient = view.findViewById(R.id.ingredient_spinner);
            holder.fromUnit = view.findViewById(R.id.from_unit_spinner);
            holder.toUnit = null; //TBD

            view.setTag(holder);
        }
        else {
            holder = (ConversionsAdapter.ViewHolder) view.getTag();
        }

        return view;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
