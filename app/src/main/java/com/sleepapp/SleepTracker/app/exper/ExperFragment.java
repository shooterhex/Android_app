package com.sleepapp.SleepTracker.app.exper;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import com.sleepapp.SleepTracker.R;
import com.sleepapp.SleepTracker.base.BaseFragment;

public class ExperFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exper0, null);
        Intent intent = new Intent(getActivity().getApplicationContext(), ExperActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().getApplicationContext().startActivity(intent);
        return view;
    }
}
