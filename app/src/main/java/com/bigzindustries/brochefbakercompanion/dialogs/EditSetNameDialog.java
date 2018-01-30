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

public class EditSetNameDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (!(getActivity() instanceof EditNameDialogFinished)) {
            throw new IllegalAccessError("Parent Activity must implement " +
                    EditNameDialogFinished.class.toString());
        }

        final long setId = getArguments().getLong(ConversionsActivity.PARAM_CONV_SET_ID);
        final String setName = getArguments().getString(ConversionsActivity.PARAM_CONV_SET_NAME, "");

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_name_dialog, null);
        EditText name = view.findViewById(R.id.edit_name);
        name.setText(setName);
        builder.setView(view)
                .setPositiveButton("Save", (dialog, id) -> {
                    String newName = name.getText().toString();
                    if (TextUtils.isEmpty(newName)) {
                        newName = "Untitled Recipe";
                    }

                    ContentValues setValues =
                            BroChefDbHelper.getValsForConversionSetInsert(newName);
                    if (setId <= 0) {
                        Uri setUri = getActivity().getContentResolver()
                                .insert(BroChefContentProvider.CONVERSION_SETS_URI, setValues);
                        long newSetId = Long.valueOf(setUri.getLastPathSegment());

                        ((EditNameDialogFinished)getActivity()).onNameChanged(true, newSetId, newName);
                    } else {
                        getActivity().getContentResolver()
                                .update(BroChefContentProvider.CONVERSION_SETS_URI,
                                        setValues,
                                        "_id=?",
                                        new String[]{String.valueOf(setId)});

                        ((EditNameDialogFinished)getActivity()).onNameChanged(false, setId, newName);
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
