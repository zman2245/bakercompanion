package com.bigzindustries.brochefbakercompanion.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.adapters.ConversionsAdapter;

public class ConversionsActivity extends AppCompatActivity {

    private ListView conversionsList;
    private FloatingActionButton addButton;
    private ConversionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_sets);

        conversionsList = (ListView)findViewById(R.id.conversions_list);
        addButton = (FloatingActionButton) findViewById(R.id.add_button);

        adapter = new ConversionsAdapter(this);
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
    }

    private void save() {
        // TODO: write it to the DB
    }
}
