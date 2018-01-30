package com.bigzindustries.brochefbakercompanion.dialogs;

public interface EditNameDialogFinished {
    void onNameChanged(boolean isNewDbEntry, long setId, String newName);
}
