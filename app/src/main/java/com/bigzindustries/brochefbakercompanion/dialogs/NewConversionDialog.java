package com.bigzindustries.brochefbakercompanion.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.conversion.ConversionController;

public class NewConversionDialog extends DialogFragment {

    ConversionController controller = new ConversionController();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final int setId = getArguments().getInt("setId");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.conversion_view, null);
        controller.setupView(getActivity(), view);
        builder.setView(view)
                .setPositiveButton("Add", (dialog, id) -> {
                    // Add to DB
                    controller.addConversionToDb(getActivity(), setId);
                })
                .setNegativeButton("Cancel", (dialog, id) -> dismiss());

        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
