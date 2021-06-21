package com.sleepapp.SleepTracker.app;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sleepapp.SleepTracker.MyApplication;
import com.sleepapp.SleepTracker.R;
import com.sleepapp.SleepTracker.app.alarm.AlarmFragment;
import com.sleepapp.SleepTracker.app.exper.ExperFragment;
import com.sleepapp.SleepTracker.app.home.HomeFragment;
import com.sleepapp.SleepTracker.app.account.MineFragment;
import com.sleepapp.SleepTracker.app.music.MusicFragment;
import com.sleepapp.SleepTracker.base.BaseActivity;
import com.sleepapp.SleepTracker.base.BaseFragment;
import com.sleepapp.SleepTracker.di.components.DaggerActivityComponent;
import com.sleepapp.SleepTracker.di.modules.ActivityModule;
import com.sleepapp.SleepTracker.widget.bottomtab.CustomBottomTabWidget;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tabWidget)
    CustomBottomTabWidget tabWidget;
    private List<BaseFragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        DaggerActivityComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build().inject(this);

        //初始化
        init();
    }

    private void init() {
        //构造Fragment的集合
        fragmentList = new ArrayList<>();
        fragmentList.add(new AlarmFragment());
        fragmentList.add(new MusicFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new ExperFragment());
        fragmentList.add(new MineFragment());
        //初始化CustomBottomTabWidget
        tabWidget.init(getSupportFragmentManager(), fragmentList);
    }
}
