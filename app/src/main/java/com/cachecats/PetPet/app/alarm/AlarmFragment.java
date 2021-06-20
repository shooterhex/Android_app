package com.cachecats.PetPet.app.alarm;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.cachecats.PetPet.R;
import com.cachecats.PetPet.app.report.Login;
import com.cachecats.PetPet.base.BaseFragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

public class AlarmFragment extends BaseFragment {


    CountDownTimer timer;
    int h,m,s;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, null);
        view = inflater.inflate(R.layout.activity_alarm, container,false);
//        ImageView image = (ImageView) view.findViewById(R.id.dis_sample);             //使用ImageView显示logo
//        image.setImageResource(R.drawable.dis_sample);
        Intent intent = new Intent(getActivity().getApplicationContext(), AlarmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        getActivity().getApplicationContext().startActivity(intent);
        /*TextView currentTime = view.findViewById(R.id.time);
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
                timer.start();
            }
        }.start();
        ImageView addTime = view.findViewById(R.id.addTime);
        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.fragment_container, new fragment_alarm2());
                ft.commit();
            }
        });*/


        return view;
    }

}


