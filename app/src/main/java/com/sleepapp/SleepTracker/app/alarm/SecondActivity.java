package com.sleepapp.SleepTracker.app.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import com.sleepapp.SleepTracker.R;

public class SecondActivity extends AppCompatActivity {

    public int h,m,s,det;

    public EditText hour,min,sec,earlierWake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm2);

        h = 8;
        m = 8;
        s = 8;

        hour = findViewById(R.id.hour);
        min = findViewById(R.id.minute);
        sec = findViewById(R.id.second);
        earlierWake = findViewById(R.id.earlierWake);

        earlierWake.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    det = Integer.parseInt(earlierWake.getText().toString());
                    if(det<0){
                        earlierWake.setText("00");
                        det = 0;
                    }
                    else if(det>60){
                        earlierWake.setText("60");
                        det = 60;
                    }

                    return false;   //返回true，保留软键盘。false，隐藏软键盘
                }
                return true;
            }
        });

        hour.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    h = Integer.parseInt(hour.getText().toString());
                    if(h<0){
                        hour.setText("00");
                        h = 0;
                    }
                    else if(h>23){
                        hour.setText("23");
                        h = 23;
                    }

                    return false;   //返回true，保留软键盘。false，隐藏软键盘
                }
                return true;
            }
        });

        hour.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    h = Integer.parseInt(hour.getText().toString());
                    if(h<0){
                        hour.setText("00");
                        h = 0;
                    }
                    else if(h>23){
                        hour.setText("23");
                        h = 23;
                    }

                    return false;   //返回true，保留软键盘。false，隐藏软键盘
                }
                return true;
            }
        });

        min.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    m = Integer.parseInt(min.getText().toString());
                    if(m<0){
                        min.setText("00");
                        m = 0;
                    }
                    else if(m>59){
                        min.setText("59");
                        m = 59;
                    }
                    return false;   //返回true，保留软键盘。false，隐藏软键盘
                }
                return true;
            }
        });

        sec.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    s = Integer.parseInt(sec.getText().toString());
                    if(s<0){
                        sec.setText("00");
                        s = 0;
                    }
                    else if(s>59){
                        sec.setText("59");
                        s = 59;
                    }
                    return false;   //返回true，保留软键盘。false，隐藏软键盘
                }
                return true;
            }
        });





        Button button;
        button = findViewById(R.id.button);;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swap();
            }
        });
    }

    public void swap(){
        Bundle bundle = new Bundle();
        bundle.putString("time", String.valueOf(h*3600+m*60+s));
        bundle.putString("det", String.valueOf(det));
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
