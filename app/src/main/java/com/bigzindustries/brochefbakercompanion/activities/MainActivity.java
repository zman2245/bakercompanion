package com.bigzindustries.brochefbakercompanion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.conversion.ConversionController;

public class MainActivity extends KeepScreenOnActivity {

    ConversionController conversionController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        conversionController = new ConversionController(this, findViewById(R.id.conversion));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.menu_item_my_recipes:
                intent = new Intent(this, ConversionSetsActivity.class);
                startActivity(intent);
                return true;

//            case R.id.menu_item_protips:
//                intent = new Intent(this, ProTipsActivity.class);
//                startActivity(intent);
//                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
