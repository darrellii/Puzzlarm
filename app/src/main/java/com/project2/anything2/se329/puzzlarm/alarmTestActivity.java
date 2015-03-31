package com.project2.anything2.se329.puzzlarm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Daniel on 3/31/2015.
 */
public class alarmTestActivity extends ActionBarActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

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
}
