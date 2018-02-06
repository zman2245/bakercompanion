package com.bigzindustries.brochefbakercompanion.activities;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.adapters.ConversionsAdapter;
import com.bigzindustries.brochefbakercompanion.db.BroChefContentProvider;
import com.bigzindustries.brochefbakercompanion.dialogs.EditRecipeDialogFinished;
import com.bigzindustries.brochefbakercompanion.dialogs.EditRecipeDialog;
import com.bigzindustries.brochefbakercompanion.dialogs.NewConversionDialog;

/**
 * Manages a list of conversions, AKA a recipe or conversion set
 *
 * If no set id is passed, assumed to be a new set in the DB
 */
public class ConversionsActivity extends KeepScreenOnActivity
        implements LoaderManager.LoaderCallbacks<Cursor>, EditRecipeDialogFinished {

    public static final String PARAM_CONV_SET_ID = "conversionSetId";
    public static final String PARAM_CONV_SET_NAME = "name";
    public static final String PARAM_CONV_SET_NOTES = "notes";

    private static final String NEW_CONVERSION_DIALOG_TAG = "NEW_CONVERSION_DIALOG";
    private static final String EDIT_NAME_DIALOG_TAG = "EDIT_NAME_DIALOG_TAG";

    private ListView conversionsList;
    private FloatingActionButton addButton;
    private TextView notesView;

    private long setId = 0; // 0 => brand new ConversionSet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conversions);
        setTitle("");

        conversionsList = (ListView)findViewById(R.id.conversions_list);
        addButton = (FloatingActionButton) findViewById(R.id.add_button);
        notesView = (TextView)findViewById(R.id.notes);

        registerForContextMenu(conversionsList);

        addButton.setOnClickListener(view -> handleAddButtonClick());

        configureConversionSet();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_conversions, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_edit:
                showEditDialog();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                BroChefContentProvider.CONVERSIONS_URI,
                null,
                "setId=?",
                new String[]{String.valueOf(setId)},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        ConversionsAdapter adapter = new ConversionsAdapter(this, cursor);
        conversionsList.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onRecipeChanged(boolean isNewDbEntry, long newSetId,
                                String newName, String newNotes) {
        setTitle(newName);
        notesView.setText(newNotes);

        if (isNewDbEntry) {
            // the conversion set is now in the DB so we can load it/add to it
            setId = newSetId;
            getSupportLoaderManager().initLoader(1, null, this);
            addButton.setEnabled(true);
        }
    }

    @Override
    public void onCanceledNewEntry() {
        // the user decided not to create a new recipe after all
        finish();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.conversions_list) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_contextual_delete, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.delete:
                // remove stuff here
                Uri uri = Uri.withAppendedPath(BroChefContentProvider.CONVERSIONS_URI,
                        String.valueOf(info.id));
                getContentResolver().delete(uri, null, null);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void configureConversionSet() {
        addButton.setEnabled(false);
        setId = getIntent().getLongExtra(PARAM_CONV_SET_ID, 0);
        if (setId == 0) {
            showEditDialog();
        } else {
            setTitle(getIntent().getStringExtra(PARAM_CONV_SET_NAME));
            notesView.setText(getIntent().getStringExtra(PARAM_CONV_SET_NOTES));
            getSupportLoaderManager().initLoader(1, null, this);
            addButton.setEnabled(true);
        }
    }

    private void showEditDialog() {
        Bundle args = new Bundle();
        EditRecipeDialog dialog = new EditRecipeDialog();

        args.putLong(PARAM_CONV_SET_ID, setId);
        args.putString(PARAM_CONV_SET_NAME, getTitle().toString());
        args.putString(PARAM_CONV_SET_NOTES, notesView.getText().toString());

        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), EDIT_NAME_DIALOG_TAG);
    }

    private void handleAddButtonClick() {
        Bundle args = new Bundle();
        args.putLong(PARAM_CONV_SET_ID, setId);
        NewConversionDialog dialog = new NewConversionDialog();
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), NEW_CONVERSION_DIALOG_TAG);
    }
}
