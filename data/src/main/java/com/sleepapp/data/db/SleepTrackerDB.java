package com.sleepapp.data.db;

import com.raizlabs.android.dbflow.annotation.Database;


@Database(name = SleepTrackerDB.NAME, version = SleepTrackerDB.VERSION, foreignKeyConstraintsEnforced = true)
public class SleepTrackerDB {
    //数据库名称
    public final static String NAME = "SleepTrackerDB";
    //版本号
    public final static int VERSION = 2;
}
