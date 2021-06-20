package com.cachecats.PetPet.app.alarm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;

import com.cachecats.PetPet.R;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ThirdActivity extends AppCompatActivity {

    public int h,m,s,det;
    public int ah,am,as;
    public int ctime;
    public int time;
    public String stime;
    public String sdet;

    public EditText hour,min,sec;
    public CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm3);

        h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        m = Calendar.getInstance().get(Calendar.MINUTE);
        s = Calendar.getInstance().get(Calendar.SECOND);
        ctime = h * 3600 + m * 60 + s;
        NumberFormat f = new DecimalFormat("00");

        Bundle bundle = getIntent().getExtras();
        stime = bundle.getString("time");
        sdet = bundle.getString("det");
        det = Integer.parseInt(sdet);
        TextView tv = findViewById(R.id.det);
        tv.setText(f.format(det));


        time = Integer.parseInt(stime);
        if(time > ctime) time = time - ctime;
        else {
            time += 86400;
            time -= ctime;
        }
        ah = time / 3600;
        am = (time % 3600) / 60;
        as = time % 60;


        TextView currentTime = findViewById(R.id.time);
        currentTime.setText(f.format(ah)+":"+f.format(am)+":"+f.format(as));
        startTime();

        Button button;
        button = findViewById(R.id.button);;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swap();
                timer.cancel();
            }
        });

    }

    public void startTime(){
        final TextView currentTime = (TextView)findViewById(R.id.time);
        timer = new CountDownTimer(time * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat("00");
                time--;
                ah = time / 3600;
                am = (time % 3600) / 60;
                as = time % 60;
                currentTime.setText(f.format(ah)+":"+f.format(am)+":"+f.format(as));
            }

            public void onFinish() {
                alertDialog();
                swap();
            }
        }.start();
    }

    public void swap(){
        //Bundle bundle = new Bundle();
        //bundle.putString("time", String.valueOf(h*3600+m*60+s));
        Intent intent = new Intent(this, AlarmActivity.class);
        //intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
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
