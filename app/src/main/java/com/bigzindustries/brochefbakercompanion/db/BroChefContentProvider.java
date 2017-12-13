package com.bigzindustries.brochefbakercompanion.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class BroChefContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.bigzindustries.brochefbakercompanion.provider";

    public static final Uri CONVERSIONS_URI =
            Uri.parse("content://" + AUTHORITY + "/conversions");
    public static final Uri CONVERSION_SETS_URI =
            Uri.parse("content://" + AUTHORITY + "/conversionsets");

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, "conversions", 1);
        sUriMatcher.addURI(AUTHORITY, "conversions/#", 2);
        sUriMatcher.addURI(AUTHORITY, "conversionsets", 3);
        sUriMatcher.addURI(AUTHORITY, "conversionsets/#", 4);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        String table;
        Uri notifyUri;

        if (selection == null) {
            selection = "";
        }

        switch (sUriMatcher.match(uri)) {
            case 1:
                table = BroChefDbHelper.TABLE_NAME_CONVERSIONS;
                notifyUri = CONVERSIONS_URI;
                break;
            case 2:
                table = BroChefDbHelper.TABLE_NAME_CONVERSIONS;
                notifyUri = CONVERSIONS_URI;
                selection = selection + "_ID = " + uri.getLastPathSegment();
                break;
            case 3:
                table = BroChefDbHelper.TABLE_NAME_CONVERSION_SETS;
                notifyUri = CONVERSION_SETS_URI;
                break;
            case 4:
                table = BroChefDbHelper.TABLE_NAME_CONVERSION_SETS;
                notifyUri = CONVERSION_SETS_URI;
                selection = selection + "_ID = " + uri.getLastPathSegment();
                break;

            default:
                throw new IllegalArgumentException("Unrecognized/Unsupported URI - " + uri);
        }

        BroChefDbHelper dbHelper = new BroChefDbHelper(getContext());
        Cursor c = dbHelper.getWritableDatabase()
                .query(table, null, selection, selectionArgs, null, null, sortOrder);

        c.setNotificationUri(getContext().getContentResolver(), notifyUri);

        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        String table;
        BroChefDbHelper dbHelper = new BroChefDbHelper(getContext());

        switch (sUriMatcher.match(uri)) {
            case 1:
                table = BroChefDbHelper.TABLE_NAME_CONVERSIONS;
                Long setId = contentValues.getAsLong("setId");
                if (setId == null || setId.longValue() == 0) {
                    throw new IllegalArgumentException("setId can't be null/0 - " + setId);
                }
                break;
            case 3:
                table = BroChefDbHelper.TABLE_NAME_CONVERSION_SETS;
                break;

            default:
                throw new IllegalArgumentException("Unrecognized/Unsupported URI - " + uri);
        }

        long id = dbHelper.getWritableDatabase().insert(table, null, contentValues);
        Uri newRowUri = uri.buildUpon().appendPath(String.valueOf(id)).build();

        getContext().getContentResolver().notifyChange(uri, null);

        return newRowUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
