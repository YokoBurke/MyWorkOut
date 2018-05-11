package com.example.android.myworkout;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myworkout.data.WorkoutContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Date currentTime;
    private EditText mWegithEditText;
    private EditText mDurationEditText;
    private EditText mDistanceEditText;
    private EditText mMessageEditText;

    private Spinner mActivitySpinner;
    private int mActivity = WorkoutContract.WorkoutEntry.ACTIVITY_RUNWALK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DateFormat todayFormat = new SimpleDateFormat("MMM dd, yyyy");

        TextView timeView = (TextView) findViewById(R.id.todays_date);
        currentTime = Calendar.getInstance().getTime();
        String todaysDate = todayFormat.format(currentTime);
        timeView.setText(todaysDate);

        mWegithEditText = (EditText) findViewById(R.id.text_weight);
        mDistanceEditText = (EditText) findViewById(R.id.text_distance);
        mDurationEditText = (EditText) findViewById(R.id.text_duration);
        mMessageEditText = (EditText) findViewById(R.id.text_message);

        mActivitySpinner = (Spinner) findViewById(R.id.spinner_activity);
        setUpSpinner();

        Button mSubmitButton = (Button) findViewById(R.id.send_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertWokoutData();
            }
        } );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CatalogActivity.class);
                startActivity(intent);
            }
        });
    }

    private void insertWokoutData(){
        String weightString = mWegithEditText.getText().toString().trim();
        String distanceString = mDistanceEditText.getText().toString().trim();
        String durationString = mDurationEditText.getText().toString().trim();
        String messageString = mMessageEditText.getText().toString().trim();

        double weight = Double.parseDouble(weightString);
        double duration = Double.parseDouble(durationString);
        double distance = Double.parseDouble(distanceString);

        DateFormat todayFormat = new SimpleDateFormat("MM/dd/yyyy");
        String today = todayFormat.format(currentTime);

        //WorkoutDbHelper mDbHelper = new WorkoutDbHelper(this);
        //SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WorkoutContract.WorkoutEntry.COLUMN_DATE, today);
        values.put(WorkoutContract.WorkoutEntry.COLUMN_WEIGHT, weight);
        values.put(WorkoutContract.WorkoutEntry.COLUMN_DISTANCE, distance);
        values.put(WorkoutContract.WorkoutEntry.COLUMN_DURATION, duration);
        values.put(WorkoutContract.WorkoutEntry.COLUMN_MESSAGE, messageString);
        values.put(WorkoutContract.WorkoutEntry.COLUMN_ACTIVITY, mActivity);

        Uri newUri = getContentResolver().insert(WorkoutContract.WorkoutEntry.CONTENT_URI, values);
        //long newRowId = db.insert(WorkoutContract.WorkoutEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving data", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Your workout data is saved", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    private void setUpSpinner() {
        ArrayAdapter activityAdapter = ArrayAdapter.createFromResource(this, R.array.array_activity_options, android.R.layout.simple_spinner_item);
        activityAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mActivitySpinner.setAdapter(activityAdapter);

        mActivitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
                    if (!TextUtils.isEmpty(selection)) {
                        if (selection.equals("Run")) {
                            mActivity = WorkoutContract.WorkoutEntry.ACTIVITY_RUN;
                        } else if (selection.equals("Walk")) {
                            mActivity = WorkoutContract.WorkoutEntry.ACTIVITY_WALK;
                        } else {
                            mActivity = WorkoutContract.WorkoutEntry.ACTIVITY_RUNWALK;
                        }
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
