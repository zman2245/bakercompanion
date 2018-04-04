package com.bigzindustries.brochefbakercompanion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;

import com.bigzindustries.brochefbakercompanion.R;
import com.bigzindustries.brochefbakercompanion.recipeparser.Parser;
import com.bigzindustries.brochefbakercompanion.recipeparser.models.RecipeResults;
import com.bigzindustries.brochefbakercompanion.services.ParserTransformService;

public class ParserActivity extends AppCompatActivity {

    EditText ingredientsInput, directionsInput, nameIput;
    RadioButton radioMass, radioVolume, radioNone;

    Parser parser = new Parser();
    ParserTransformService service = new ParserTransformService();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_paste_in);

        ingredientsInput = (EditText)findViewById(R.id.ingredientsInput);
        directionsInput = (EditText)findViewById(R.id.directionsInput);
        nameIput = (EditText)findViewById(R.id.nameIput);

        radioMass = (RadioButton) findViewById(R.id.conversionGroupMass);
        radioVolume = (RadioButton) findViewById(R.id.conversionGroupVolume);
        radioNone = (RadioButton) findViewById(R.id.conversionGroupNone);

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
        // collect inputs
        String name = nameIput.getText().toString();
        String notes = directionsInput.getText().toString();
        String inputString = ingredientsInput.getText().toString();

        // parse the ingredients
        RecipeResults results = parser.parse(inputString);

        // create the new recipe in DB
        long newRecipeId = service.createNewRecipeFromParserResults(
                getContentResolver(),
                results,
                name,
                notes,
                getRuleFromRadioGroup());

        // start activity for the new recipe
        Intent intent = new Intent(this, ConversionsActivity.class);
        intent.putExtra(ConversionsActivity.PARAM_CONV_SET_ID, newRecipeId);
        intent.putExtra(ConversionsActivity.PARAM_CONV_SET_NAME, name);
        intent.putExtra(ConversionsActivity.PARAM_CONV_SET_NOTES, notes);
        startActivity(intent);
    }

    private ParserTransformService.CONVERSION_RULE getRuleFromRadioGroup() {
        if (radioNone.isChecked()) {
            return ParserTransformService.CONVERSION_RULE.NONE;
        }

        if (radioVolume.isChecked()) {
            return ParserTransformService.CONVERSION_RULE.TO_VOLUME;
        }

        return ParserTransformService.CONVERSION_RULE.TO_MASS;
    }
}
