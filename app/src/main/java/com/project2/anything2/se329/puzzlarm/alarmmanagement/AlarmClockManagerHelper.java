package com.project2.anything2.se329.puzzlarm.alarmmanagement;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.project2.anything2.se329.puzzlarm.localdata.AlarmLocalDataHelper;

import java.util.Calendar;
import java.util.List;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Created by dj on 2/16/15.
 */
public class AlarmClockManagerHelper extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        setAlarms(context);
    }

    private static PendingIntent createPendingIntent(Context context, AlarmModel model) {
        Log.d("InPendingIntent", "In Pending Intent");

        Intent intent = new Intent(context, AlarmService.class);

        intent.putExtra("ID", model.id).toString();

        intent.putExtra("NAME", model.name).toString();

        intent.putExtra("HOUR", model.hour).toString();

        intent.putExtra("MIN", model.min).toString();

        intent.putExtra("TONE", model.tone).toString();

        return PendingIntent.getService(context, (int) model.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void cancelAlarms(Context context) {
        AlarmLocalDataHelper helper = new AlarmLocalDataHelper(context);

        List<AlarmModel> alarms =  helper.getAlarms();

        if (alarms != null) {
            for (AlarmModel alarm : alarms) {
                if (alarm.isEnabled) {
                    PendingIntent pIntent = createPendingIntent(context, alarm);

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pIntent);
                }
            }
        }
    }

    public static void setAlarms(Context context) {
        AlarmLocalDataHelper helper = new AlarmLocalDataHelper(context);
        Log.d("InSetAlarms", "In SetAlarms");
        List<AlarmModel> alarms =  helper.getAlarms();

        if (alarms != null) {
            Log.d("InSetAlarms", "Alarms not null!");
            for (AlarmModel alarm : alarms) {
                Log.d("InSetAlarms", "in for loop");

                    Log.d("InSetAlarms", "alarm enabled");
                    PendingIntent pIntent = createPendingIntent(context, alarm);
                    Log.d("InSetAlarms", "PendingIntent Created!");
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, alarm.hour);
                    calendar.set(Calendar.MINUTE, alarm.min);
                    calendar.set(Calendar.SECOND, 00);
                    //Find next time to set
                    final int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                    final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                    final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
                    boolean alarmSet = false;

                    //First check if it's later in the week
                    for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
                        if (alarm.getRepeatingDay(dayOfWeek - 1) && dayOfWeek >= nowDay &&
                                !(dayOfWeek == nowDay && alarm.hour < nowHour) &&
                                !(dayOfWeek == nowDay && alarm.hour == nowHour && alarm.min <= nowMinute)) {
                            calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

                            setAlarm(context, calendar, pIntent);
                            alarmSet = true;
                            break;
                        }
                    }

                    //Else check if it's earlier in the week
                    if (!alarmSet) {
                        for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
                            if (alarm.getRepeatingDay(dayOfWeek - 1) && dayOfWeek <= nowDay && alarm.isRepeatedWeekly) {
                                calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                                calendar.add(Calendar.WEEK_OF_YEAR, 1);

                                setAlarm(context, calendar, pIntent);
                                alarmSet = true;
                                break;
                            }
                        }
                    }

            }
        }
    }

    private static void setAlarm(Context context, Calendar calendar, PendingIntent pIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
    }
}
