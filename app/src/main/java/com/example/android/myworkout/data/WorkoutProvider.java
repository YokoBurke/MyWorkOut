package com.example.android.myworkout.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by yoko.fujikuro on 5/8/2018.
 * https://qiita.com/mitsu9/items/1457331992e1eb02b29f
 */

public class WorkoutProvider extends ContentProvider {
    /** Tag for the log messages */
    public static final String LOG_TAG = WorkoutProvider.class.getSimpleName();

    private static final int WORKOUT = 100;
    private static final int WORKOUT_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(WorkoutContract.CONTENT_AUTHORITY, WorkoutContract.PATH_WORKOUT, WORKOUT);
        sUriMatcher.addURI(WorkoutContract.CONTENT_AUTHORITY, WorkoutContract.PATH_WORKOUT + "/#", WORKOUT_ID);
    }
    private WorkoutDbHelper mDbHelper;

    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        mDbHelper = new WorkoutDbHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case WORKOUT:
                cursor = db.query(WorkoutContract.WorkoutEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;

            case WORKOUT_ID:
                selection = WorkoutContract.WorkoutEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};

                cursor = db.query(WorkoutContract.WorkoutEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        //cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case WORKOUT:
                return insertWorkout(uri, contentValues);
                default:
                    throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }

    }

    private Uri insertWorkout(Uri uri, ContentValues values){
        Double weight = values.getAsDouble(WorkoutContract.WorkoutEntry.COLUMN_WEIGHT);
        if (weight == null || weight < 0) {
            throw new IllegalArgumentException("Weight must be entered.");
        }

        Integer activity = values.getAsInteger(WorkoutContract.WorkoutEntry.COLUMN_ACTIVITY);
        if (activity == null || !WorkoutContract.WorkoutEntry.isValidActivity(activity)) {
            throw new IllegalArgumentException("Activity type must be selected.");
        }

        Double duration = values.getAsDouble(WorkoutContract.WorkoutEntry.COLUMN_DURATION);
        if (duration == null || duration < 0) {
            throw new IllegalArgumentException("Duration must be entered.");
        }

        Double distance = values.getAsDouble(WorkoutContract.WorkoutEntry.COLUMN_DISTANCE);
        if (distance == null || distance < 0) {
            throw new IllegalArgumentException("Distance must be entered.");
        }

        String message = values.getAsString(WorkoutContract.WorkoutEntry.COLUMN_MESSAGE);
        if (message.length() > 140) {
            throw new IllegalArgumentException("Message should be 140 characters or shorter.");
        }

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        long id = db.insert(WorkoutContract.WorkoutEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri, id);

    }



    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        return null;
    }
}
