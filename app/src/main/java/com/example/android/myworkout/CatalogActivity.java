package com.example.android.myworkout;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.android.myworkout.data.WorkoutContract;
import com.example.android.myworkout.data.WorkoutDbHelper;

public class CatalogActivity extends AppCompatActivity {

    private WorkoutDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        mDbHelper = new WorkoutDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {

        String[] projection = {
                WorkoutContract.WorkoutEntry._ID,
                WorkoutContract.WorkoutEntry.COLUMN_DATE,
                WorkoutContract.WorkoutEntry.COLUMN_WEATHER,
                WorkoutContract.WorkoutEntry.COLUMN_ACTIVITY,
                WorkoutContract.WorkoutEntry.COLUMN_DURATION,
                WorkoutContract.WorkoutEntry.COLUMN_DISTANCE,
                WorkoutContract.WorkoutEntry.COLUMN_WEIGHT,
                WorkoutContract.WorkoutEntry.COLUMN_MESSAGE
        };

        Cursor cursor = getContentResolver().query(
                WorkoutContract.WorkoutEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

        // TextView displayView = (TextView) findViewById(R.id.temporary_text);

        ListView listView = (ListView) findViewById(R.id.list);
        WorkoutCursorAdapter adapter = new WorkoutCursorAdapter(this, cursor);

        listView.setAdapter(adapter);

    }

}

