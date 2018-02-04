package com.bigzindustries.brochefbakercompanion.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BroChefDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 9;
    public static final String DATABASE_NAME = "BroChef.db";
    public static final String TABLE_NAME_CONVERSION_SETS = "ConversionSets";
    public static final String TABLE_NAME_CONVERSIONS = "Conversions";

    // Custom values are allowed, so we don't enforce ingredient/unit enums at DB level
    // Alternatively, we could create another separate table for pure ingredients but I don't
    // think that much sophistication is necessary at this time and the model isn't 100% settled.
    private static final String SQL_CREATE_CONVERSIONS =
            "CREATE TABLE " + TABLE_NAME_CONVERSIONS + " (" +
                    "_id INTEGER PRIMARY KEY," +
                    "ingredient TEXT," +
                    "fromUnit TEXT," +
                    "toUnit TEXT," +
                    "fromValue REAL," +
                    "toValue READ," +
                    "priority INTEGER," +
                    "setId INTEGER DEFAULT 0," + // points to ConversionSets but can be 0 for "global" items
                    "modified TIMESTAMP DEFAULT (DATETIME('now')))";

    private static final String SQL_CREATE_CONVERSION_SETS =
            "CREATE TABLE " + TABLE_NAME_CONVERSION_SETS + " (" +
                    "_id INTEGER PRIMARY KEY," +
                    "name TEXT," +
                    "notes TEXT NOT NULL DEFAULT \"\"," +
                    "priority INTEGER," +
                    "modified TIMESTAMP DEFAULT (DATETIME('now')))";

    private static final String SQL_DELETE_CONVERSIONS =
            "DROP TABLE IF EXISTS " + TABLE_NAME_CONVERSIONS;
    private static final String SQL_DELETE_CONVERSION_SETS =
            "DROP TABLE IF EXISTS " + TABLE_NAME_CONVERSION_SETS;
    private static final String SQL_DELETE_TRIGGERS_DELETE_CASCADE =
            "DROP TRIGGER IF EXISTS before_delete_conv_sets";
    private static final String SQL_DELETE_TRIGGERS_CONV_MOD =
            "DROP TRIGGER IF EXISTS modified_conversions";
    private static final String SQL_DELETE_TRIGGERS_CONV_SET_MOD =
            "DROP TRIGGER IF EXISTS modified_conversion_sets";

    // Triggers
    private static final String SQL_CASCADE_DELETE_CONVERSIONS_TRIGGER =
            "CREATE TRIGGER before_delete_conv_sets " +
                    "BEFORE DELETE ON " + TABLE_NAME_CONVERSION_SETS +
                    " FOR EACH ROW BEGIN " +
                    "DELETE FROM " + TABLE_NAME_CONVERSIONS +
                    " WHERE " + TABLE_NAME_CONVERSIONS + ".setId = old._id; " +
                    "END";
    private static final String SQL_MODIFIED_CONVERSIONS_TRIGGER =
            "CREATE TRIGGER modified_conversions AFTER UPDATE ON " + TABLE_NAME_CONVERSIONS +
                    " BEGIN" +
                    " UPDATE " + TABLE_NAME_CONVERSIONS + " SET modified = datetime('now') " +
                    "WHERE _id = NEW._id;" +
                    " END;";
    // Note: this trigger only handles direct mods to a ConversionSet, would need another trigger
    // if we want to update a ConversionSet's modified timestamp when a Conversion is added to it
    private static final String SQL_MODIFIED_CONVERSION_SETS_TRIGGER =
            "CREATE TRIGGER modified_conversion_sets AFTER UPDATE ON " + TABLE_NAME_CONVERSION_SETS +
                    " BEGIN" +
                    " UPDATE " + TABLE_NAME_CONVERSION_SETS + " SET modified = datetime('now') " +
                    "WHERE _id = NEW._id;" +
                    " END;";

    public long insertConversion(long setId, double fromValue, double toValue,
                                 String fromUnit, String toUnit, String ingredient) {
        ContentValues values = new ContentValues();
        values.put("ingredient", ingredient);
        values.put("fromUnit", fromUnit);
        values.put("toUnit", toUnit);
        values.put("fromValue", fromValue);
        values.put("toValue", toValue);

        return getWritableDatabase().insertOrThrow(TABLE_NAME_CONVERSIONS, null, values);
    }

    public static ContentValues getValsForConversionSetInsert(String name, String notes) {
        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("notes", notes);

        return values;
    }

    public static ContentValues getValsForConversionInsert(
            long setId, double fromValue, double toValue,
            String fromUnit, String toUnit, String ingredient) {

        ContentValues values = new ContentValues();
        values.put("setId", setId);
        values.put("ingredient", ingredient);
        values.put("fromUnit", fromUnit);
        values.put("toUnit", toUnit);
        values.put("fromValue", fromValue);
        values.put("toValue", toValue);

        return values;
    }

    public long insertConversionSet(String name) {
        ContentValues values = new ContentValues();
        values.put("name", name);

        return getWritableDatabase().insertOrThrow(TABLE_NAME_CONVERSION_SETS, null, values);
    }

    public BroChefDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CONVERSIONS);
        db.execSQL(SQL_CREATE_CONVERSION_SETS);
        db.execSQL(SQL_CASCADE_DELETE_CONVERSIONS_TRIGGER);
        db.execSQL(SQL_MODIFIED_CONVERSIONS_TRIGGER);
        db.execSQL(SQL_MODIFIED_CONVERSION_SETS_TRIGGER);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Just trash it for now
        db.execSQL(SQL_DELETE_CONVERSIONS);
        db.execSQL(SQL_DELETE_CONVERSION_SETS);
        db.execSQL(SQL_DELETE_TRIGGERS_DELETE_CASCADE);
        db.execSQL(SQL_DELETE_TRIGGERS_CONV_MOD);
        db.execSQL(SQL_DELETE_TRIGGERS_CONV_SET_MOD);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
