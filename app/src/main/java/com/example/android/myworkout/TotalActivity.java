package com.example.android.myworkout;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.myworkout.data.WorkoutContract;
import com.example.android.myworkout.data.WorkoutDbHelper;

public class TotalActivity extends AppCompatActivity {

    private String startDate;
    private String lastDate;
    private long workoutCount;
    private WorkoutDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);

        mDbHelper = new WorkoutDbHelper(this);

        TextView distanceView = findViewById(R.id.summary_total_distance);
        TextView durationView = findViewById(R.id.summary_total_duration);
        TextView averageView = findViewById(R.id.summary_average_week);

        double x = sumDistanceDuration("distance");
        double y = sumDistanceDuration("duration");

        distanceView.setText(Double.toString(x) + " miles");
        durationView.setText(Double.toString(y) + " minutes");

        getDates();
        averageView.setText(startDate + " " + lastDate + " " + workoutCount);

    }

   public double sumDistanceDuration(String columnName){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        workoutCount = DatabaseUtils.queryNumEntries(db, WorkoutContract.WorkoutEntry.TABLE_NAME);
        Cursor cursor = db.rawQuery("SELECT SUM(" + columnName + ") FROM WorkoutTable", null);

        if (cursor.moveToFirst()){
            return cursor.getDouble(0);
        } else {
            return 0.00;
        }

   }

   public void getDates(){

       SQLiteDatabase dateDb = mDbHelper.getReadableDatabase();

       String[] projection = {WorkoutContract.WorkoutEntry._ID, WorkoutContract.WorkoutEntry.COLUMN_DATE};
       Cursor dateCursor = dateDb.query(WorkoutContract.WorkoutEntry.TABLE_NAME, projection,
                                        null, null, null, null, null);

       dateCursor.moveToFirst();
       int dateCursorColumnName = dateCursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_DATE);
       startDate = dateCursor.getString(dateCursorColumnName);

       if (workoutCount > 1) {
           dateCursor.moveToLast();
            lastDate = dateCursor.getString(dateCursorColumnName);

       }






   }


}
