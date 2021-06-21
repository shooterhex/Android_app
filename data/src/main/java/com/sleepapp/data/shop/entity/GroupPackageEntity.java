package com.sleepapp.data.shop.entity;

import com.sleepapp.data.db.SleepTrackerDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = SleepTrackerDB.class)
public class GroupPackageEntity extends BaseModel {

    @PrimaryKey
    @Column
    public String id;

    @Column
    public String groupId;

    @Column
    public String shopId;

    @Column
    public String title;

    @Column
    public String name;

    @Column
    public int count;

    @Column
    public float price;
}
