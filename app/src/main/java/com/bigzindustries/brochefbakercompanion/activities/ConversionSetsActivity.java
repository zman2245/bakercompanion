package com.bigzindustries.brochefbakercompanion.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.adapters.ConversionSetsAdapter;
import com.bigzindustries.brochefbakercompanion.db.BroChefDbHelper;

/**
 * Displays and allows management of conversion sets
 */
public class ConversionSetsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    BroChefDbHelper dbHelper;
    ListView conversionSetsList;
    FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conversion_sets);
        setTitle("Conversion Sets");

        conversionSetsList = (ListView)findViewById(R.id.conversion_sets_list);
        addButton = (FloatingActionButton) findViewById(R.id.add_button);

        dbHelper = new BroChefDbHelper(this);

        conversionSetsList.setEmptyView(findViewById(R.id.empty_text));

        addButton.setOnClickListener(view -> handleAddButtonClick());
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportLoaderManager().initLoader(1, null, this);
    }

    private void handleAddButtonClick() {
        Intent intent = new Intent(this, ConversionsActivity.class);
        startActivity(intent);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, null, null, null, null, null)
        {
            @Override
            public Cursor loadInBackground()
            {
                // You better know how to get your database.
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                // You can use any query that returns a cursor.
                return db.rawQuery("SELECT  * FROM " + BroChefDbHelper.TABLE_NAME_CONVERSION_SETS,
                        null);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {
        cursor.moveToFirst();
        ConversionSetsAdapter adapter = new ConversionSetsAdapter(this, cursor);
        conversionSetsList.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
}
