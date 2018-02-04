package com.bigzindustries.brochefbakercompanion.conversion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.bigzindustries.brochefbakercompanion.R;

/**
 * Composed of a ConversionController and a CustomIngredientController
 * Can switch between the two
 */
public class IngredientController {
    ConversionController conversionController;
    CustomIngredientController customController;

    FrameLayout container;
    View conversionView;
    View customView;

    public IngredientController(Context context, FrameLayout container) {
        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.container = container;

        this.conversionView = inflater.inflate(R.layout.conversion_view_v2, null);
        this.customView = inflater.inflate(R.layout.conversion_view_custom_ingredient, null);

        conversionController = new ConversionController(context, conversionView);
        customController = new CustomIngredientController(context, customView);

        this.container.addView(conversionView);
        this.container.addView(customView);

        this.container.bringChildToFront(conversionView);
    }

    public void showConversion() {
        container.bringChildToFront(conversionView);
    }

    public void showCustom() {
        container.bringChildToFront(customView);
    }

    public boolean isCustomShowing() {
        return container.getChildAt(1) == customView;
    }

    public void addConversionToDb(Context context, long setId) {
        if (isCustomShowing()) {
            customController.addConversionToDb(context, setId);
        } else {
            conversionController.addConversionToDb(context, setId);
        }
    }
}
