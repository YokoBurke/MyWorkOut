package com.example.android.myworkout;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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

    private void displayDatabaseInfo(){

        //SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                WorkoutContract.WorkoutEntry.COLUMN_DATE,
                WorkoutContract.WorkoutEntry.COLUMN_WEATHER,
                WorkoutContract.WorkoutEntry.COLUMN_ACTIVITY,
                WorkoutContract.WorkoutEntry.COLUMN_DURATION,
                WorkoutContract.WorkoutEntry.COLUMN_DISTANCE,
                WorkoutContract.WorkoutEntry.COLUMN_WEIGHT,
                WorkoutContract.WorkoutEntry.COLUMN_MESSAGE
        };

        /** Cursor cursor = db.query(
                WorkoutContract.WorkoutEntry.TABLE_NAME,
                projection,
                null, null, null, null, null);
         */


         Cursor cursor = getContentResolver().query(
                 WorkoutContract.WorkoutEntry.CONTENT_URI,
                    projection,
                    null,
                    null,
                    null);

        TextView displayView = (TextView) findViewById(R.id.temporary_text);

        try {
            displayView.setText("The workout table contains " + cursor.getCount() + " pets." + "\n\n");
            displayView.append(WorkoutContract.WorkoutEntry.COLUMN_DATE + " - " +
                    WorkoutContract.WorkoutEntry.COLUMN_WEATHER + " - " +
                    WorkoutContract.WorkoutEntry.COLUMN_ACTIVITY + " - " +
                    WorkoutContract.WorkoutEntry.COLUMN_DURATION + " - " +
                    WorkoutContract.WorkoutEntry.COLUMN_DISTANCE + " - " +
                    WorkoutContract.WorkoutEntry.COLUMN_WEIGHT + " - " +
                    WorkoutContract.WorkoutEntry.COLUMN_MESSAGE + "\n");

            int dateColumnIndex = cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_DATE);
            int weatherColumnIndex = cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_WEATHER);
            int activityColumnIndex = cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_ACTIVITY);
            int durationColumnIndex = cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_DURATION);
            int distanceColumnIndex = cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_DISTANCE);
            int weightColumnIndex = cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_WEIGHT);
            int messageColumnIndex = cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_MESSAGE);

            while (cursor.moveToNext()) {
                String currentDate = cursor.getString(dateColumnIndex);
                String currentWeather = cursor.getString(weatherColumnIndex);
                int currentActivity = cursor.getInt(activityColumnIndex);
                Double currentDuration = cursor.getDouble(durationColumnIndex);
                Double currentDistance = cursor.getDouble(distanceColumnIndex);
                Double currentWeight = cursor.getDouble(weightColumnIndex);
                String currentMessage = cursor.getString(messageColumnIndex);

                displayView.append(("\n" + currentDate + " - " +
                        currentWeather + " - " +
                        currentActivity + " - " +
                        currentDuration + " - " +
                        currentDistance + " - " +
                        currentWeight + " - " +
                        currentMessage));
             }
            } finally {
                cursor.close();
                        }

        }

    }

