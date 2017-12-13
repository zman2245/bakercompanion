package com.bigzindustries.brochefbakercompanion.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
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
import com.bigzindustries.brochefbakercompanion.db.BroChefDbHelper;
import com.bigzindustries.brochefbakercompanion.dialogs.NewConversionDialog;

public class ConversionsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String NEW_CONVERSION_DIALOG_TAG = "NEW_CONVERSION_DIALOG";

    private ListView conversionsList;
    private FloatingActionButton addButton;
    private ConversionsAdapter adapter;

    private long setId = 0; // 0 => brand new ConversionSet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conversions);
        setTitle("Conversions");

        conversionsList = (ListView)findViewById(R.id.conversions_list);
        addButton = (FloatingActionButton) findViewById(R.id.add_button);

        addButton.setOnClickListener(view -> handleAddButtonClick());

        configureConversionSet();
    }

    private void configureConversionSet() {
        addButton.setEnabled(false);
        setId = getIntent().getIntExtra("setId", 0);
        if (setId == 0) {
            // insert new set TODO: move to background thread
            ContentValues setValues =
                    BroChefDbHelper.getValsForConversionSetInsert("Untitled");
            Uri setUri = getContentResolver()
                    .insert(BroChefContentProvider.CONVERSION_SETS_URI, setValues);
            setId = Long.valueOf(setUri.getLastPathSegment());
        }

        getSupportLoaderManager().initLoader(1, null, this);
        addButton.setEnabled(true);
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
            case R.id.menu_item_save:
                save();
                return true;
        }

        return false;
    }

    private void handleAddButtonClick() {
        Bundle args = new Bundle();
        args.putLong("setId", setId);
        NewConversionDialog dialog = new NewConversionDialog();
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), NEW_CONVERSION_DIALOG_TAG);
    }

    private void save() {
        // TODO: write it to the DB
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
}
