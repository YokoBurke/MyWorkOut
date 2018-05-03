package com.example.android.myworkout.data;

import android.provider.BaseColumns;

/**
 * Created by yoko.fujikuro on 5/2/2018.
 */

public class WorkoutContract {

    //constructor
    private WorkoutContract() {};

    public static final class WorkoutEntry implements BaseColumns {

        public final static String TABLE_NAME = "WorkoutTable";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_DATE = "date";
        public final static String COLUMN_WEIGHT = "weight";
        public final static String COLUMN_ACTIVITY = "activity";
        public final static String COLUMN_DURATION = "duration";
        public final static String COLUMN_DISTANCE =  "distance";
        public final static String COLUMN_WEATHER = "weather";
        public final static String COLUMN_MESSAGE = "message";

        public static final int ACTIVITY_RUNWALK = 0;
        public static final int ACTIVITY_RUN = 1;
        public static final int ACTIVITY_WALK = 2;

    }
}
