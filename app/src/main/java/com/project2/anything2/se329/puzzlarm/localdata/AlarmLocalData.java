package com.project2.anything2.se329.puzzlarm.localdata;

import android.provider.BaseColumns;

/**
 * Created by Emory on 3/30/2015.
 */
public final class AlarmLocalData {
    public AlarmLocalData() {}

    public static abstract class Alarm implements BaseColumns{
            public static final String TABLE_NAME = "alarm";
            public static final String COLUMN_NAME_ALARM_NAME = "name";
            public static final String COLUMN_NAME_ALARM_TIME_HOUR = "hour";
            public static final String COLUMN_NAME_ALARM_TIME_MINUTE = "minute";
            public static final String COLUMN_NAME_ALARM_REPEAT_DAYS = "days";
            public static final String COLUMN_NAME_ALARM_REPEAT_WEEKLY = "weekly";
            public static final String COLUMN_NAME_ALARM_TONE = "tone";
            public static final String COLUMN_NAME_ALARM_ENABLED = "isEnabled";
    }
}
