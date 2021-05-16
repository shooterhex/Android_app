package com.cachecats.data.shop.entity;

import com.cachecats.data.db.PetPetDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = PetPetDB.class)
public class ShopEvaluateEntity extends BaseModel {

    @PrimaryKey
    @Column
    public String id;

    @Column
    public String userId;

    @Column
    public String userName;

    @Column
    public String userHeads;

    @Column
    public String shopId;

    @Column
    public String groupId;

    @Column
    public String evaluation;

    @Column
    public float evaluateScore;

    @Column
    public float perConsume;

    @Column
    public String pictures;

    @Column
    public String recommendDishes;


}
