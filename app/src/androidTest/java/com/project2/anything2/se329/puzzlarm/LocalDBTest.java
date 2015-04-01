package com.project2.anything2.se329.puzzlarm;

import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import com.project2.anything2.se329.puzzlarm.alarmmanagement.AlarmModel;
import com.project2.anything2.se329.puzzlarm.localdata.AlarmLocalDataHelper;

/**
 * Created by Emory on 3/30/2015.
 */
public class LocalDBTest extends AndroidTestCase{
    AlarmLocalDataHelper helper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        helper = new AlarmLocalDataHelper(context);
    }

    private AlarmModel getTestModel(){
        AlarmModel testModel = new AlarmModel();
        Uri r = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        testModel.name = "test";
        testModel.min = 45;
        testModel.hour = 10;
        testModel.setRepeatingDay(AlarmModel.MONDAY, true);
        testModel.setRepeatingDay(AlarmModel.TUESDAY, true);
        testModel.setRepeatingDay(AlarmModel.WEDNESDAY, false);
        testModel.setTone(r);
        testModel.id = -1;
        return testModel;
    }

    public void testAlarmCreation(){
        AlarmModel compareModel = getTestModel();
        helper.createAlarm(compareModel);
        AlarmModel test = helper.getAlarm(-1);

        assertEquals(test.name, compareModel.name);
        assertEquals(test.min, compareModel.min);
        assertEquals(test.hour, compareModel.hour);
        assertEquals(test.tone, compareModel.tone);
        assertEquals(test.getRepeatingDay(AlarmModel.MONDAY), compareModel.getRepeatingDay(AlarmModel.MONDAY));
        assertEquals(test.getRepeatingDay(AlarmModel.TUESDAY), compareModel.getRepeatingDay(AlarmModel.TUESDAY));
        assertEquals(test.getRepeatingDay(AlarmModel.WEDNESDAY), compareModel.getRepeatingDay(AlarmModel.WEDNESDAY));
    }

}
