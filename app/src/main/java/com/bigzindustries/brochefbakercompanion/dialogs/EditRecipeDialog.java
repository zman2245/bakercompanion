package com.bigzindustries.brochefbakercompanion.dialogs;

import android.app.Dialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.activities.ConversionsActivity;
import com.bigzindustries.brochefbakercompanion.db.BroChefContentProvider;
import com.bigzindustries.brochefbakercompanion.db.BroChefDbHelper;

public class EditRecipeDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (!(getActivity() instanceof EditRecipeDialogFinished)) {
            throw new IllegalAccessError("Parent Activity must implement " +
                    EditRecipeDialogFinished.class.toString());
        }

        final long setId = getArguments().getLong(ConversionsActivity.PARAM_CONV_SET_ID);
        final String recipeName = getArguments().getString(ConversionsActivity.PARAM_CONV_SET_NAME, "");
        final String recipeNotes = getArguments().getString(ConversionsActivity.PARAM_CONV_SET_NOTES, "");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_recipe_dialog, null);
        EditText name = view.findViewById(R.id.edit_name);
        EditText notes = view.findViewById(R.id.edit_notes);

        name.setText(recipeName);
        notes.setText(recipeNotes);

        builder.setView(view)
                .setPositiveButton("Save", (dialog, id) -> {
                    String newName = name.getText().toString();
                    String newNotes = notes.getText().toString();

                    if (TextUtils.isEmpty(newName)) {
                        newName = "Untitled Recipe";
                    }

                    if (TextUtils.isEmpty(newNotes)) {
                        newNotes = "";
                    }

                    ContentValues setValues =
                            BroChefDbHelper.getValsForConversionSetInsert(newName, newNotes);

                    if (setId <= 0) {
                        Uri setUri = getActivity().getContentResolver()
                                .insert(BroChefContentProvider.CONVERSION_SETS_URI, setValues);
                        long newSetId = Long.valueOf(setUri.getLastPathSegment());

                        ((EditRecipeDialogFinished)getActivity()).onRecipeChanged(true, newSetId, newName, newNotes);
                    } else {
                        getActivity().getContentResolver()
                                .update(BroChefContentProvider.CONVERSION_SETS_URI,
                                        setValues,
                                        "_id=?",
                                        new String[]{String.valueOf(setId)});

                        ((EditRecipeDialogFinished)getActivity()).onRecipeChanged(false, setId, newName, newNotes);
                    }
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

    @Override
    public void onStart() {
        super.onStart();

        boolean enableCancel = getArguments().getLong(ConversionsActivity.PARAM_CONV_SET_ID) > 0;

        ((AlertDialog)getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(enableCancel);
    }
}
