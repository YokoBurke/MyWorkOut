package com.example.android.myworkout;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.android.myworkout.data.WorkoutDbHelper;

public class TotalActivity extends AppCompatActivity {

    private double totalDistance;
    private double totalDuration;
    private int countWorkout;

    private WorkoutDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        mDbHelper = new WorkoutDbHelper(this);

        double x = sumDistanceDuration("duration");
        double y = sumDistanceDuration("distance");


        TextView distanceView = (TextView) findViewById(R.id.summary_total_distance);
        TextView durationView = (TextView) findViewById(R.id.summary_total_duration);

        distanceView.setText(Double.toString(y));
        durationView.setText(Double.toString(x));

    }

    public double sumDistanceDuration(String columnName){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + columnName + ") FROM WorkoutTable", null);

        if (cursor.moveToFirst()){
            Log.i("sumDistanceDuration", cursor.getString(0));
            return cursor.getDouble(0);
        } else {
            Log.i("sumDistanceDuration", "0.00");
            return 0.00;
        }

    }


}
