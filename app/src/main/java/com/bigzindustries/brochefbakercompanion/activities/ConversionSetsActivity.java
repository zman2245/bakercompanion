package com.bigzindustries.brochefbakercompanion.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.adapters.ConversionSetsAdapter;
import com.bigzindustries.brochefbakercompanion.db.BroChefDbHelper;

/**
 * Displays and allows management of conversion sets
 */
public class ConversionSetsActivity extends AppCompatActivity {

    BroChefDbHelper dbHelper;
    ListView conversionSetsList;
    FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conversionSetsList = (ListView)findViewById(R.id.conversion_sets_list);
        addButton = (FloatingActionButton) findViewById(R.id.add_button);

        dbHelper = new BroChefDbHelper(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + BroChefDbHelper.TABLE_NAME_CONVERSION_SETS,
                null);

        ConversionSetsAdapter adapter = new ConversionSetsAdapter(this, cursor);
        conversionSetsList.setAdapter(adapter);
        conversionSetsList.setEmptyView(findViewById(R.id.empty_text));

        addButton.setOnClickListener(view -> handleAddButtonClick());
    }

    private void handleAddButtonClick() {
        // TBD: Launch activity for adding!
    }
}
