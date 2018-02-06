package com.bigzindustries.brochefbakercompanion.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.Toast;

import com.bigzindustries.brochefbakercompanion.R;

/**
 * Extend this class in order to add a menu option to toggle lock screen on
 */
public class KeepScreenOnActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState,
                         @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_keep_screen_on, menu);

        Switch switchAB = menu.findItem(R.id.menu_item_lock_screen_toggle)
                .getActionView().findViewById(R.id.toggle);

        boolean keepScreenOn = getPrefKeepScreenOn();
        switchAB.setChecked(keepScreenOn);
        if (keepScreenOn) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        switchAB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setPrefKeepScreenOn(isChecked);

            if (isChecked) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                Toast.makeText(getApplication(), "Screen will stay on so you can easily see conversions while baking", Toast.LENGTH_SHORT)
                        .show();
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                Toast.makeText(getApplication(), "Screen will now turn off on its own", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_lock_screen_toggle:
                if ((getWindow().getAttributes().flags &
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON) == 0) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                } else {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }

                return true;
            case R.id.menu_item_etsy_link:
                try {
                    String url = getString(R.string.etsy_link);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, "No application can handle this request."
                            + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean getPrefKeepScreenOn() {
        SharedPreferences prefs = getSharedPreferences("BROCHEF", Context.MODE_PRIVATE);
        return prefs.getBoolean("KEEP_SCREEN_ON", true);
    }

    private void setPrefKeepScreenOn(boolean keepOn) {
        SharedPreferences prefs = getSharedPreferences("BROCHEF", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("KEEP_SCREEN_ON", keepOn).commit();
    }
}
