package com.project2.anything2.se329.puzzlarm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.project2.anything2.se329.puzzlarm.alarmmanagement.AlarmClockManagerHelper;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class AlarmReceiverActivity extends ActionBarActivity {
    private MediaPlayer mPlay;
    private PowerManager.WakeLock wake;

    public static final int TIMEOUT = 60 * 1000; //(60 seconds)
    public final String ID = this.getClass().getSimpleName();
    String tone = getIntent().getStringExtra("TONE");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        Button dismiss = (Button) findViewById(R.id.alarm_screen_button);
        dismiss.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mPlay.stop();
                finish();
            }
        });

        try {
            if (tone != null && !tone.equals("")) {
                Uri toneUri = Uri.parse(tone);
                if (toneUri != null) {
                    mPlay.setDataSource(this, toneUri);
                    mPlay.setAudioStreamType(AudioManager.STREAM_ALARM);
                    mPlay.setLooping(true);
                    mPlay.prepare();
                    mPlay.start();
                }
            }
        }
        catch(Exception e){

        }

        Runnable releaseWake = new Runnable(){
                @Override
                public void run() {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

                    if (wake != null && wake.isHeld()) {
                        wake.release();
                    }
                }
            };

            new Handler().postDelayed(releaseWake, TIMEOUT);


        final AlertDialog wrongAnswerDialog = new AlertDialog.Builder(this).create();
        wrongAnswerDialog.setTitle("Wrong Answer");
        wrongAnswerDialog.setButton("OK", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                wrongAnswerDialog.hide();
            }
        });

        final AlertDialog questionDialog = new AlertDialog.Builder(this).create();
        questionDialog.setTitle("WAKE UP");
        questionDialog.setMessage("What's the capital of New York?");

        questionDialog.setButton("Brooklyn", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                wrongAnswerDialog.show();
            }
        });

        questionDialog.setButton("Buffalo", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                wrongAnswerDialog.show();
            }
        });

        questionDialog.setButton("Syracuse", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                wrongAnswerDialog.show();
            }
        });

        // If the right answer is selected, it just removes the dialog alert,
        // allowing the user to access the alarm page.

        questionDialog.setButton("Albany", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                questionDialog.hide();
            }
        });

        questionDialog.show();

    }
    protected void onResume() {
        super.onResume();

        // Set the window to keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        // Acquire wakelock
        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        if (wake == null) {
            wake = pm.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), ID);
        }

        if (!wake.isHeld()) {
            wake.acquire();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (wake != null && wake.isHeld()) {
            wake.release();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receiver, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
