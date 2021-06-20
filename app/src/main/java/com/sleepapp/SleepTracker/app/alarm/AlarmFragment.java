package com.sleepapp.SleepTracker.app.alarm;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.sleepapp.SleepTracker.R;
import com.sleepapp.SleepTracker.base.BaseFragment;

public class AlarmFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, null);
//        ImageView image = (ImageView) view.findViewById(R.id.dis_sample);             //使用ImageView显示logo
//        image.setImageResource(R.drawable.dis_sample);
        Intent intent = new Intent(getActivity().getApplicationContext(), AlarmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        getActivity().getApplicationContext().startActivity(intent);

        return view;





    }

}
