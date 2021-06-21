package com.sleepapp.SleepTracker.di.components;

import com.sleepapp.SleepTracker.app.MainActivity;
import com.sleepapp.SleepTracker.app.home.HomeFragment;
import com.sleepapp.SleepTracker.di.modules.ActivityModule;
import com.sleepapp.SleepTracker.di.scopes.Scopes;

import dagger.Component;

@Scopes.Activity
@Component(modules = {ActivityModule.class}, dependencies = {ApplicationComponent.class})
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(HomeFragment fragment);

}

