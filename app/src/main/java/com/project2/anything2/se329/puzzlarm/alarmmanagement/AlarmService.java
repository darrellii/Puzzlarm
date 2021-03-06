package com.project2.anything2.se329.puzzlarm.alarmmanagement;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.provider.AlarmClock;
import android.util.Log;

import com.project2.anything2.se329.puzzlarm.AlarmReceiverActivity;
import com.project2.anything2.se329.puzzlarm.localdata.AlarmLocalDataHelper;

import java.util.List;

/**
 * Created by dj on 2/16/15.
 */
public class AlarmService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent alarmIntent = new Intent(getBaseContext(), AlarmReceiverActivity.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        alarmIntent.putExtras(intent);
        getApplication().startActivity(alarmIntent);
        AlarmClockManagerHelper.setAlarms(this);
        Log.d("AlarmService", "Made it");
        return super.onStartCommand(intent, flags, startId);
    }
}
