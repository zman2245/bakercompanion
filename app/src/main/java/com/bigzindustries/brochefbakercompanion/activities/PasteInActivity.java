package com.bigzindustries.brochefbakercompanion.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.recipeparser.Parser;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeResults;

public class PasteInActivity extends AppCompatActivity {

    EditText input;
    Parser parser = new Parser();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_paste_in);

        input = (EditText)findViewById(R.id.recipeIn);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_paste, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_save:
                parseRecipe();;
                break;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void parseRecipe() {
        String inputString = input.getText().toString();

        RecipeResults results = parser.parse(inputString);

        transformRecipeResults(results);
    }

    private void transformRecipeResults(RecipeResults recipeResults) {

    }
}
