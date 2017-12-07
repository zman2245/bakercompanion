package com.bigzindustries.brochefbakercompanion.unitdata;

import java.util.HashMap;

/**
 * Created by zack on 12/7/17.
 */

public class Gram extends Unit {

    public static Unit GRAM = new Gram();

    private static HashMap<String, Float> factors = new HashMap<>();
    static {
        factors.put("Ounce", 0.035274f);
    }

    private Gram() {
        super("Gram", factors);
    }
}
