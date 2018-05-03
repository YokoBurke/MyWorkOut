package com.example.android.myworkout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.myworkout.data.WorkoutContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Spinner mActivitySpinner;
    private int mActivity = WorkoutContract.WorkoutEntry.ACTIVITY_RUNWALK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DateFormat todayFormat = new SimpleDateFormat("MMM dd, yyyy");

        TextView timeView = (TextView) findViewById(R.id.todays_date);
        Date currentTime = Calendar.getInstance().getTime();
        String todaysDate = todayFormat.format(currentTime);
        timeView.setText(todaysDate);

        mActivitySpinner = (Spinner) findViewById(R.id.spinner_activity);
        setUpSpinner();
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
