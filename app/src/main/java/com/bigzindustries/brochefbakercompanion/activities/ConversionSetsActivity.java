package com.bigzindustries.brochefbakercompanion.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.adapters.ConversionSetsAdapter;
import com.bigzindustries.brochefbakercompanion.db.BroChefContentProvider;
import com.bigzindustries.brochefbakercompanion.db.BroChefDbHelper;

/**
 * Displays and allows management of conversion sets
 */
public class ConversionSetsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

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
        conversionSetsList.setOnItemClickListener(this);

        addButton.setOnClickListener(view -> handleAddButtonClick());

        getSupportLoaderManager().initLoader(1, null, this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, BroChefContentProvider.CONVERSION_SETS_URI,
                null, null, null, null);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // hacky dealing with DB this directly here but fine for now
        Cursor cursor = (Cursor)adapterView.getAdapter().getItem(i);
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));

        Intent intent = new Intent(this, ConversionsActivity.class);
        intent.putExtra(ConversionsActivity.PARAM_CONV_SET_ID, id);
        intent.putExtra(ConversionsActivity.PARAM_CONV_SET_NAME, name);
        startActivity(intent);
    }

    private void handleAddButtonClick() {
        Intent intent = new Intent(this, ConversionsActivity.class);
        startActivity(intent);
    }
}
