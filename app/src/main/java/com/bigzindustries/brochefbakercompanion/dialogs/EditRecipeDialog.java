package com.bigzindustries.brochefbakercompanion.dialogs;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
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
import com.bigzindustries.brochefbakercompanion.activities.ParserActivity;
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
        final boolean isNewRecipe = setId <= 0;
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

                    ContentValues setValues =
                            BroChefDbHelper.getValsForConversionSetInsert(newName, newNotes);

                    if (isNewRecipe) {
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
                .setNegativeButton("Cancel", (dialog, id) -> {
                    dismiss();

                    if (isNewRecipe) {
                        ((EditRecipeDialogFinished)getActivity()).onCanceledNewEntry();
                    }
                });

        if (isNewRecipe) {
            builder.setNeutralButton("Paste In Recipe", (dialog, id) -> {
                dismiss();
                ((EditRecipeDialogFinished)getActivity()).onCanceledNewEntry();
                startActivity(new Intent(getActivity(), ParserActivity.class));
            });
        }

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
