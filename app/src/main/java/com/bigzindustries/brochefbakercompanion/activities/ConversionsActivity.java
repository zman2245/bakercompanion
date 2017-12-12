package com.bigzindustries.brochefbakercompanion.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.bigzindustries.brochefbakercompanion.db.BroChefDbHelper;
import com.bigzindustries.brochefbakercompanion.dialogs.NewConversionDialog;

public class ConversionsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String NEW_CONVERSION_DIALOG_TAG = "NEW_CONVERSION_DIALOG";

    private ListView conversionsList;
    private FloatingActionButton addButton;
    private ConversionsAdapter adapter;

    BroChefDbHelper dbHelper;
    private int setId = 0; // 0 => brand new ConversionSet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_conversions);
        setTitle("Conversions");

        setId = getIntent().getIntExtra("setId", 0);

        dbHelper = new BroChefDbHelper(this);

        conversionsList = (ListView)findViewById(R.id.conversions_list);
        addButton = (FloatingActionButton) findViewById(R.id.add_button);

        addButton.setOnClickListener(view -> handleAddButtonClick());
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportLoaderManager().initLoader(1, null, this);
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
        // TODO
        Bundle args = new Bundle();
        args.putInt("setId", setId);
        NewConversionDialog dialog = new NewConversionDialog();
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), NEW_CONVERSION_DIALOG_TAG);
    }

    private void save() {
        // TODO: write it to the DB
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
                return db.rawQuery("SELECT  * FROM " + BroChefDbHelper.TABLE_NAME_CONVERSIONS +
                        " WHERE setId=?",
                        new String[] {String.valueOf(setId)});
            }
        };
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
