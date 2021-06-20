package com.sleepapp.SleepTracker;

import android.app.Application;
import android.content.Context;

import com.sleepapp.SleepTracker.di.components.ApplicationComponent;
import com.sleepapp.SleepTracker.di.components.DaggerApplicationComponent;
import com.sleepapp.SleepTracker.di.modules.ApplicationModule;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.raizlabs.android.dbflow.config.FlowManager;

public class MyApplication extends Application {

    private static Context mContext;
    private static MyApplication application;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
        mContext = getApplicationContext();

        initLogger();

        //Dagger注入
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);

        //初始化数据库
        FlowManager.init(this);

        Stetho.initializeWithDefaults(this);

    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(1)         // (Optional) How many method line to show. Default 2
//                .methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
//                .tag("my-logger")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    /**
     * 获取ApplicationContext
     *
     * @return
     */
    public static Context getAppContext() {
        return mContext;
    }


    private ApplicationComponent getComponent(){
        return mApplicationComponent;
    }

    /**
     * 获取 ApplicationComponent
     *
     * @return
     */
    public static ApplicationComponent getApplicationComponent() {
        return application.getComponent();
    }

}
