package com.example.android.myworkout.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by yoko.fujikuro on 5/7/2018.
 * What does this class do?
 * 1. Create a SQLite db when it is first accessed.
 * 2. Gives you a connection to that database.
 * 3. Manages updating the db schema if version changes.
 */

public class WorkoutDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = WorkoutDbHelper.class.getSimpleName();
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "myWorkout.db";

    public WorkoutDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_WORKOUT_TABLE = "CREATE TABLE " + WorkoutContract.WorkoutEntry.TABLE_NAME + " ("
                + WorkoutContract.WorkoutEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WorkoutContract.WorkoutEntry.COLUMN_DATE + " TEXT NOT NULL, "
                + WorkoutContract.WorkoutEntry.COLUMN_WEIGHT + " DECIMAL NOT NULL DEFAULT 0, "
                + WorkoutContract.WorkoutEntry.COLUMN_ACTIVITY + " INTEGER NOT NULL, "
                + WorkoutContract.WorkoutEntry.COLUMN_DURATION + " DECIMAL NOT NULL, "
                + WorkoutContract.WorkoutEntry.COLUMN_DISTANCE + " DECIMAL NOT NULL, "
                + WorkoutContract.WorkoutEntry.COLUMN_WEATHER + " TEXT, "
                + WorkoutContract.WorkoutEntry.COLUMN_MESSAGE + " TEXT);";

        db.execSQL(SQL_CREATE_WORKOUT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
