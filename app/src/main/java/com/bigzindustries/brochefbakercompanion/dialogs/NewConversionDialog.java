package com.bigzindustries.brochefbakercompanion.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bigzindustries.brochefbakercompanion.activities.ConversionsActivity;
import com.bigzindustries.brochefbakercompanion.conversion.IngredientController;

public class NewConversionDialog extends DialogFragment {

    IngredientController controller;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final long setId = getArguments().getLong(ConversionsActivity.PARAM_CONV_SET_ID);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // instantiate the container view and it's controller
        FrameLayout view = new FrameLayout(getActivity());
        view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        controller = new IngredientController(getActivity(), view);

        builder.setView(view)
                .setNegativeButton("Cancel", (dialog, id) -> dismiss())
                .setPositiveButton("Add", (dialog, id) -> {
                    // Add to DB
                    controller.addConversionToDb(getActivity(), setId);
                })
                .setNeutralButton("Add Custom", (dialog, id) -> {
                    // Real implementation is in onResume, because we need to override the button's
                    // base onClickListener so that the dialog is not automatically dismissed.
                });

        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        AlertDialog d = (AlertDialog)getDialog();

        d.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(v -> {
            if (controller.isCustomShowing()) {
                controller.showConversion();
                d.getButton(AlertDialog.BUTTON_NEUTRAL).setText("Add Custom");
            } else {
                controller.showCustom();
                d.getButton(AlertDialog.BUTTON_NEUTRAL).setText("Add Conversion");
            }
        });
    }
}
