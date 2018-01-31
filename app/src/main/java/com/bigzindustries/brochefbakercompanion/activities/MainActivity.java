package com.bigzindustries.brochefbakercompanion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

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

        findViewById(R.id.btn_my_recipes).setOnClickListener(view -> {
            Intent intent = new Intent(this, ConversionSetsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_cheat_sheet).setOnClickListener(view -> {
            // TODO: bring up a cheat sheet
        });
    }


}
