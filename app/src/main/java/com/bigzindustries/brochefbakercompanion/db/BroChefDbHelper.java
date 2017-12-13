package com.bigzindustries.brochefbakercompanion.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bigzindustries.brochefbakercompanion.models.Conversion;

public class BroChefDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "BroChef.db";
    public static final String TABLE_NAME_CONVERSION_SETS = "ConversionSets";
    public static final String TABLE_NAME_CONVERSIONS = "Conversions";

    private static final String SQL_CREATE_CONVERSIONS =
            "CREATE TABLE " + TABLE_NAME_CONVERSIONS + " (" +
                    "_id INTEGER PRIMARY KEY," +
                    "ingredient TEXT," +
                    "fromUnit TEXT," +
                    "toUnit TEXT," +
                    "fromValue REAL," +
                    "toValue READ," +
                    "priority INTEGER," +
                    "setId INTEGER)"; // points to ConversionSets but can be 0 for "global" items

    private static final String SQL_CREATE_CONVERSION_SETS =
            "CREATE TABLE " + TABLE_NAME_CONVERSION_SETS + " (" +
                    "_id INTEGER PRIMARY KEY," +
                    "name TEXT," +
                    "priority INTEGER)";

    private static final String SQL_DELETE_CONVERSIONS =
            "DROP TABLE IF EXISTS " + TABLE_NAME_CONVERSIONS;
    private static final String SQL_DELETE_CONVERSION_SETS =
            "DROP TABLE IF EXISTS " + TABLE_NAME_CONVERSION_SETS;

    public static Conversion buildConversion(Cursor cursor) {
        return null;
    }

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

    public static ContentValues getValsForConversionSetInsert(String name) {
        ContentValues values = new ContentValues();
        values.put("name", name);
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
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Just trash it for now
        db.execSQL(SQL_DELETE_CONVERSIONS);
        db.execSQL(SQL_DELETE_CONVERSION_SETS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
