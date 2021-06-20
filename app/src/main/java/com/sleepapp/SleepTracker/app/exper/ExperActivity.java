package com.sleepapp.SleepTracker.app.exper;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.sleepapp.SleepTracker.R;
import com.sleepapp.SleepTracker.base.BaseFragment;


public class ExperActivity extends AppCompatActivity {
    BaseFragment parentFragment;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.fragment_exper1);
    }
}
