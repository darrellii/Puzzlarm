package com.project2.anything2.se329.puzzlarm.localdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.project2.anything2.se329.puzzlarm.alarmmanagement.AlarmModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emory on 3/30/2015.
 */
public class AlarmLocalDataHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Alarm.db";

    private static final String SQL_CREATE_ALARM =
            "CREATE TABLE " + AlarmLocalData.Alarm.TABLE_NAME + " (" +
                    AlarmLocalData.Alarm._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    AlarmLocalData.Alarm.COLUMN_NAME_ALARM_NAME + " TEXT," +
                    AlarmLocalData.Alarm.COLUMN_NAME_ALARM_TIME_HOUR + " INTEGER," +
                    AlarmLocalData.Alarm.COLUMN_NAME_ALARM_TIME_MINUTE + " INTEGER," +
                    AlarmLocalData.Alarm.COLUMN_NAME_ALARM_REPEAT_DAYS + " TEXT," +
                    AlarmLocalData.Alarm.COLUMN_NAME_ALARM_REPEAT_WEEKLY + " BOOLEAN," +
                    AlarmLocalData.Alarm.COLUMN_NAME_ALARM_TONE + " TEXT," +
                    AlarmLocalData.Alarm.COLUMN_NAME_ALARM_ENABLED + " BOOLEAN" + " )";

    private static final String DELETE_ALARM =
            "DROP TABLE IF EXISTS " + AlarmLocalData.Alarm.TABLE_NAME;

    public AlarmLocalDataHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ALARM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DELETE_ALARM);
        onCreate(db);
    }

    private AlarmModel populateModel(Cursor c){
        Log.d("InPopulateModel","In Populate Model");
        AlarmModel model = new AlarmModel();
        model.id = c.getLong(c.getColumnIndex(AlarmLocalData.Alarm._ID));
        model.name = c.getString(c.getColumnIndex(AlarmLocalData.Alarm.COLUMN_NAME_ALARM_NAME));
        model.hour = c.getInt(c.getColumnIndex(AlarmLocalData.Alarm.COLUMN_NAME_ALARM_TIME_HOUR));
        model.min = c.getInt(c.getColumnIndex(AlarmLocalData.Alarm.COLUMN_NAME_ALARM_TIME_MINUTE));
        model.isRepeatedWeekly = false;
        model.tone = Uri.parse(c.getString(c.getColumnIndex(AlarmLocalData.Alarm.COLUMN_NAME_ALARM_TONE)));
        model.isEnabled = true;

        String[] repeatingDays = c.getString(c.getColumnIndex(AlarmLocalData.Alarm.COLUMN_NAME_ALARM_REPEAT_DAYS)).split(",");
        for (int i = 0; i < repeatingDays.length; ++i) {
            model.setRepeatingDay(i, repeatingDays[i].equals("false") ? false : true);
        }
        Log.d("InPopulateModel", "model information set");
        Log.d("InPopulateModel", "tone = " + model.tone.toString());
        return model;
    }
    private ContentValues populateContent(AlarmModel model) {
        ContentValues values = new ContentValues();
        values.put(AlarmLocalData.Alarm.COLUMN_NAME_ALARM_NAME, model.name);
        values.put(AlarmLocalData.Alarm.COLUMN_NAME_ALARM_TIME_HOUR, model.hour);
        values.put(AlarmLocalData.Alarm.COLUMN_NAME_ALARM_TIME_MINUTE, model.min);
        values.put(AlarmLocalData.Alarm.COLUMN_NAME_ALARM_REPEAT_WEEKLY, model.isRepeatedWeekly);
        values.put(AlarmLocalData.Alarm.COLUMN_NAME_ALARM_TONE, model.tone.toString());

        String repeatingDays = "";
        for (int i = 0; i < 7; ++i) {
            repeatingDays += model.getRepeatingDay(i) + ",";
        }
        values.put(AlarmLocalData.Alarm.COLUMN_NAME_ALARM_REPEAT_DAYS, repeatingDays);

        return values;
    }

    public long createAlarm(AlarmModel model) {
        Log.d("CreateAlarm", "In CreateAlarm");
        ContentValues values = populateContent(model);

        return getWritableDatabase().insert(AlarmLocalData.Alarm.TABLE_NAME, null, values);
    }

    public AlarmModel getAlarm(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + AlarmLocalData.Alarm.TABLE_NAME + " WHERE " + AlarmLocalData.Alarm._ID + " = " + id;

        Cursor c = db.rawQuery(select, null);

        if (c.moveToNext()) {
            return populateModel(c);
        }

        return null;
    }

    public long updateAlarm(AlarmModel model) {
        ContentValues values = populateContent(model);
        return getWritableDatabase().update(AlarmLocalData.Alarm.TABLE_NAME, values, AlarmLocalData.Alarm._ID + " = ?", new String[] { String.valueOf(model.id) });
    }

    public int deleteAlarm(long id) {
        return getWritableDatabase().delete(AlarmLocalData.Alarm.TABLE_NAME, AlarmLocalData.Alarm._ID + " = ?", new String[] { String.valueOf(id) });
    }

    public List<AlarmModel> getAlarms() {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + AlarmLocalData.Alarm.TABLE_NAME;

        Cursor c = db.rawQuery(select, null);

        List<AlarmModel> alarmList = new ArrayList<AlarmModel>();

        while (c.moveToNext()) {
            Log.d("InGetAlarms", "populateModel called");
            alarmList.add(populateModel(c));
        }

        if (!alarmList.isEmpty()) {
            Log.d("InGetAlarms", " not null");
            return alarmList;
        }
        Log.d("InGetAlarms", "null");
        return null;
    }
}
