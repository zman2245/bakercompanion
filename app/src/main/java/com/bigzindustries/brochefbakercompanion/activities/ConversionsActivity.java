package com.bigzindustries.brochefbakercompanion.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.adapters.ConversionsAdapter;
import com.bigzindustries.brochefbakercompanion.db.BroChefContentProvider;
import com.bigzindustries.brochefbakercompanion.dialogs.EditNameDialogFinished;
import com.bigzindustries.brochefbakercompanion.dialogs.EditSetNameDialog;
import com.bigzindustries.brochefbakercompanion.dialogs.NewConversionDialog;

/**
 * Manages a list of conversions, AKA a recipe or conversion set
 *
 * If no set id is passed, assumed to be a new set in the DB
 */
public class ConversionsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>, EditNameDialogFinished {

    public static final String PARAM_CONV_SET_ID = "conversionSetId";
    public static final String PARAM_CONV_SET_NAME = "name";

    private static final String NEW_CONVERSION_DIALOG_TAG = "NEW_CONVERSION_DIALOG";
    private static final String EDIT_NAME_DIALOG_TAG = "EDIT_NAME_DIALOG_TAG";

    private ListView conversionsList;
    private FloatingActionButton addButton;
    private ConversionsAdapter adapter;

    private long setId = 0; // 0 => brand new ConversionSet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conversions);
        setTitle("");

        conversionsList = (ListView)findViewById(R.id.conversions_list);
        addButton = (FloatingActionButton) findViewById(R.id.add_button);

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
                showEditNameDialog();
                return true;
            case R.id.menu_item_save:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return false;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                BroChefContentProvider.CONVERSIONS_URI,
                null,
                "setId=?",
                new String[]{String.valueOf(setId)},
                null);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {
        cursor.moveToFirst();
        ConversionsAdapter adapter = new ConversionsAdapter(this, cursor);
        conversionsList.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onNameChanged(boolean isNewDbEntry, long newSetId, String newName) {
        setTitle(newName);

        if (isNewDbEntry) {
            // the conversion set is now in the DB so we can load it/add to it
            setId = newSetId;
            getSupportLoaderManager().initLoader(1, null, this);
            addButton.setEnabled(true);
        }
    }

    private void configureConversionSet() {
        addButton.setEnabled(false);
        setId = getIntent().getLongExtra(PARAM_CONV_SET_ID, 0);
        if (setId == 0) {
            showEditNameDialog();
        } else {
            setTitle(getIntent().getStringExtra(PARAM_CONV_SET_NAME));
            getSupportLoaderManager().initLoader(1, null, this);
            addButton.setEnabled(true);
        }
    }

    private void showEditNameDialog() {
        Bundle args = new Bundle();
        args.putLong(PARAM_CONV_SET_ID, setId);
        args.putString(PARAM_CONV_SET_NAME, getTitle().toString());
        EditSetNameDialog dialog = new EditSetNameDialog();
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
