package com.project2.anything2.se329.puzzlarm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class AlarmReceiverActivity extends ActionBarActivity {

    @Override
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
