package com.example.android.myworkout;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.myworkout.data.WorkoutContract;

/**
 * Created by yoko.fujikuro on 5/17/2018.
 */

public class WorkoutCursorAdapter extends CursorAdapter {

    public WorkoutCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textDate = view.findViewById(R.id.list_date);
        TextView textWeather = view.findViewById(R.id.list_weather_content);
        TextView textActivity = view.findViewById(R.id.list_activity_content);
        TextView textDistance = view.findViewById(R.id.list_distance_content);
        TextView textDuration = view.findViewById(R.id.list_duration_content);
        TextView textWeight = view.findViewById(R.id.list_weight_content);
        TextView textMessage = view.findViewById(R.id.list_message_content);

        int dateColumnIndex = cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_DATE);
        int weatherColumnIndex = cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_WEATHER);
        int activityColumnIndex = cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_ACTIVITY);
        int distanceColumnIndex = cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_DISTANCE);
        int durationColumnIndex = cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_DURATION);
        int weightColumnIndex = cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_WEIGHT);
        int messageColumnIndex = cursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_MESSAGE);

        Log.i("dateColumnIndex", Integer.toString(dateColumnIndex));
        Log.i("weatherColumnIndex", Integer.toString(weatherColumnIndex));
        Log.i("activityColumnIndex", Integer.toString(activityColumnIndex));
        Log.i("distanceColumnIndex", Integer.toString(distanceColumnIndex));
        Log.i("durationColumnIndex", Integer.toString(durationColumnIndex));
        Log.i("weightColumnIndex", Integer.toString(weightColumnIndex));
        Log.i("messageColumnIndex", Integer.toString(messageColumnIndex));

        //ここの説明をもう一回きく。
        String workoutDate = cursor.getString(dateColumnIndex);
        String workoutWeather = cursor.getString(weatherColumnIndex);
        String workoutActivity = cursor.getString(activityColumnIndex);
        String workoutDistance = cursor.getString(distanceColumnIndex) + "miles";
        String workoutDuration = cursor.getString(durationColumnIndex) + "minutes";
        String workoutWeight = cursor.getString(weightColumnIndex) + " lbs";
        String workoutMessage = cursor.getString(messageColumnIndex);

        textDate.setText(workoutDate);
        textWeather.setText(workoutWeather);
        textActivity.setText(workoutActivity);
        textDistance.setText(workoutDistance);
        textDuration.setText(workoutDuration);
        textWeight.setText(workoutWeight);
        textMessage.setText(workoutMessage);

    }
}
