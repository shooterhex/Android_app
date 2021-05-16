package com.cachecats.data.shop.entity;

import com.cachecats.data.db.PetPetDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = PetPetDB.class)
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
