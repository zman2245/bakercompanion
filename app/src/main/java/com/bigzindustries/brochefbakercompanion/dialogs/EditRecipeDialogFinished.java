package com.bigzindustries.brochefbakercompanion.dialogs;

public interface EditRecipeDialogFinished {
    void onRecipeChanged(boolean isNewDbEntry, long setId, String newName, String newNotes);
    void onCanceledNewEntry();
}
