package com.sleepapp.SleepTracker.app.alarm;

//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sleepapp.SleepTracker.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    public CountDownTimer timer;
    public int h,m,s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        startTime();

        ImageView addTime;
        addTime = findViewById(R.id.addTime);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swap();
            }
        });
    }

    public void swap(){
        timer.cancel();
        Intent intent = new Intent(this, SecondActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


    public void startTime(){
        final TextView currentTime = (TextView)findViewById(R.id.time);
        timer = new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat("00");
                h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                m = Calendar.getInstance().get(Calendar.MINUTE);
                s = Calendar.getInstance().get(Calendar.SECOND);
                currentTime.setText(f.format(h)+":"+f.format(m)+":"+f.format(s));
            }

            public void onFinish() {
                //alertDialog();
                startTime();
            }
        }.start();
    }

    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Please Select any option");
        dialog.setTitle("Dialog Box");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                    }
                });
        dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
}