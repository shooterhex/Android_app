package com.sleepapp.SleepTracker.app.alarm;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;


import com.sleepapp.SleepTracker.R;
import com.sleepapp.SleepTracker.app.account.Login;
import com.sleepapp.SleepTracker.base.BaseFragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

//public class AlarmFragment extends BaseFragment {
//
//
//    CountDownTimer timer;
//    int h,m,s;
//
//
//    @Override
//    public void onStart(){
//        super.onStart();
//        FragmentManager fm = getActivity().getSupportFragmentManager();
////        FragmentManager fm = getFragmentManager();
//        AlarmFragment_alarm AlarmF = new AlarmFragment_alarm();
////
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//
//        transaction.add( AlarmF,"AlarmFragment").commit();
//    }
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_alarm, null);
//        view = inflater.inflate(R.layout.activity_alarm, container,false);
////        ImageView image = (ImageView) view.findViewById(R.id.dis_sample);             //使用ImageView显示logo
////        image.setImageResource(R.drawable.dis_sample);
////        Intent intent = new Intent(getActivity().getApplicationContext(), AlarmActivity.class);
////        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
////        getActivity().getApplicationContext().startActivity(intent);
//
//
//
//        return view;
//    }
//
//}




public class AlarmFragment extends BaseFragment {


    CountDownTimer timer;
    int h, m, s;
    View view;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getActivity().setContentView(R.layout.fragment_alarm);
//        view = View.inflate(getActivity(), R.layout.fragment_alarm, null);

//        getActivity().setContentView(R.layout.activity_alarm);
        view = View.inflate(getActivity(),R.layout.activity_alarm, null);

        startTime();

        ImageView addTime;
        addTime = view.findViewById(R.id.addTime);


        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        addTime.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swap();
            }
        });

        return view;
    }

    public void swap() {
        timer.cancel();
        Intent intent = new Intent(getActivity(), SecondActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }


    public void startTime() {
        final TextView currentTime = (TextView) view.findViewById(R.id.time);
        timer = new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat("00");
                h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                m = Calendar.getInstance().get(Calendar.MINUTE);
                s = Calendar.getInstance().get(Calendar.SECOND);
                currentTime.setText(f.format(h) + ":" + f.format(m) + ":" + f.format(s));
            }

            public void onFinish() {
                //alertDialog();
                startTime();
            }
        }.start();
    }

    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setMessage("Please Select any option");
        dialog.setTitle("Dialog Box");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Toast.makeText(getActivity().getApplicationContext(), "Yes is clicked", Toast.LENGTH_LONG).show();
                    }
                });
        dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity().getApplicationContext(), "cancel is clicked", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }
}