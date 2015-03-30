package com.project2.anything2.se329.puzzlarm;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.project2.anything2.se329.puzzlarm.alarmmanagement.AlarmModel;
import com.project2.anything2.se329.puzzlarm.localdata.AlarmLocalDataHelper;

/**
 * Created by Emory on 3/30/2015.
 */
public class LocalDBTest extends AndroidTestCase{

    RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
    AlarmLocalDataHelper helper = new AlarmLocalDataHelper(context);

    private AlarmModel getTestModel(){
        AlarmModel testModel = new AlarmModel();
        testModel.name = "test";
        testModel.min = 45;
        testModel.hour = 10;
        testModel.setRepeatingDay(AlarmModel.MONDAY, true);
        testModel.setRepeatingDay(AlarmModel.TUESDAY, true);
        testModel.setRepeatingDay(AlarmModel.WEDNESDAY, false);
        return testModel;
    }

    public void testAlarmCreation(){
        AlarmModel compareModel = getTestModel();
        long testID = helper.createAlarm(compareModel);
        AlarmModel test = helper.getAlarm(testID);

        assertEquals(test.name, compareModel.name);
        assertEquals(test.min, compareModel.min);
        assertEquals(test.hour, compareModel.hour);
        assertEquals(test.tone, compareModel.tone);
        assertEquals(test.getRepeatingDay(AlarmModel.MONDAY), compareModel.getRepeatingDay(AlarmModel.MONDAY));
        assertEquals(test.getRepeatingDay(AlarmModel.TUESDAY), compareModel.getRepeatingDay(AlarmModel.TUESDAY));
        assertEquals(test.getRepeatingDay(AlarmModel.WEDNESDAY), compareModel.getRepeatingDay(AlarmModel.WEDNESDAY));
    }

}
