package com.project2.anything2.se329.puzzlarm.alarmmanagement;

import android.net.Uri;

/**
 * Created by dj on 2/18/15.
 */


public class AlarmModel {

    public static final short SUNDAY = 0;
    public static final short MONDAY = 1;
    public static final short TUESDAY = 2;
    public static final short WEDNESDAY = 3;
    public static final short THURSDAY = 4;
    public static final short FRIDAY = 5;
    public static final short SATURDAY = 6;

    public long id = -1;
    public int hour;
    public int min;
    public boolean days[];
    public boolean isRepeatedWeekly;
    public Uri tone;
    public String name;
    public boolean isEnabled;

    public AlarmModel() {
        days = new boolean[7];
    }

    public void setRepeatingDay(int day, boolean isRepeated) {
        days[day] = isRepeated;
    }

    public boolean getRepeatingDay(int day) {
        return days[day];
    }


    public static int getSunday() {
        return SUNDAY;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public boolean isRepeatedWeekly() {
        return isRepeatedWeekly;
    }

    public void setRepeatedWeekly(boolean isRepeatedWeekly) {
        this.isRepeatedWeekly = isRepeatedWeekly;
    }

    public Uri getTone() {
        return tone;
    }

    public void setTone(Uri tone) {
        this.tone = tone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}
