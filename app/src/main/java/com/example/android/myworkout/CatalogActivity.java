package com.example.android.myworkout;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.android.myworkout.data.WorkoutContract;
import com.example.android.myworkout.data.WorkoutDbHelper;

public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    //identifier for the data loader
    private static final int WORKOUT_LOADER = 0;

    //Adapter for the ListView
    WorkoutCursorAdapter mWorkoutAdapter;

    private WorkoutDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        mDbHelper = new WorkoutDbHelper(this);

        ListView listView = (ListView) findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        mWorkoutAdapter = new WorkoutCursorAdapter(this, null);
        listView.setAdapter(mWorkoutAdapter);

        getLoaderManager().initLoader(WORKOUT_LOADER, null, this);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
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
        return new CursorLoader(this, WorkoutContract.WorkoutEntry.CONTENT_URI, projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mWorkoutAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mWorkoutAdapter.swapCursor(null);
    }
}

