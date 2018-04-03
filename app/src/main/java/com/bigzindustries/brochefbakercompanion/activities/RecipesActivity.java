package com.bigzindustries.brochefbakercompanion.activities;

import android.content.Intent;
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

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.adapters.ConversionSetsAdapter;
import com.bigzindustries.brochefbakercompanion.db.BroChefContentProvider;
import com.bigzindustries.brochefbakercompanion.db.BroChefDbHelper;

/**
 * Displays and allows management of conversion sets
 */
public class RecipesActivity extends KeepScreenOnActivity
        implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    BroChefDbHelper dbHelper;
    ListView conversionSetsList;
    FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conversion_sets);

        conversionSetsList = (ListView)findViewById(R.id.conversion_sets_list);
        addButton = (FloatingActionButton) findViewById(R.id.add_button);

        dbHelper = new BroChefDbHelper(this);

        conversionSetsList.setEmptyView(findViewById(R.id.empty_text));
        conversionSetsList.setOnItemClickListener(this);
        registerForContextMenu(conversionSetsList);

        addButton.setOnClickListener(view -> handleAddButtonClick());

        getSupportLoaderManager().initLoader(1, null, this);

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
        inflater.inflate(R.menu.menu_recipes, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_paste_recipe:
                startActivity(new Intent(this, PasteInActivity.class));
                break;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, BroChefContentProvider.CONVERSION_SETS_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        ConversionSetsAdapter adapter = new ConversionSetsAdapter(this, cursor);
        conversionSetsList.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // hacky dealing with DB this directly here but fine for now
        Cursor cursor = (Cursor)adapterView.getAdapter().getItem(i);
        long id = cursor.getLong(cursor.getColumnIndex("_id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String notes = cursor.getString(cursor.getColumnIndex("notes"));

        Intent intent = new Intent(this, ConversionsActivity.class);
        intent.putExtra(ConversionsActivity.PARAM_CONV_SET_ID, id);
        intent.putExtra(ConversionsActivity.PARAM_CONV_SET_NAME, name);
        intent.putExtra(ConversionsActivity.PARAM_CONV_SET_NOTES, notes);

        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.conversion_sets_list) {
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
                Uri uri = Uri.withAppendedPath(BroChefContentProvider.CONVERSION_SETS_URI,
                        String.valueOf(info.id));
                getContentResolver().delete(uri, null, null);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void handleAddButtonClick() {
        Intent intent = new Intent(this, ConversionsActivity.class);
        startActivity(intent);
    }
}
