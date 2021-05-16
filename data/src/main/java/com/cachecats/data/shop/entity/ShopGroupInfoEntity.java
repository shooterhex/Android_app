package com.cachecats.data.shop.entity;

import com.cachecats.data.db.PetPetDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

@Table(database = PetPetDB.class)
public class ShopGroupInfoEntity extends BaseModel {

    @PrimaryKey
    @Column
    public String groupId;

    @Column
    public String shopId;

    @Column
    public String name;

    @Column
    public float originalPrice;

    @Column
    public float currentPrice;

    @Column
    public int soldNum;

    @Column
    public String notes;

    @Column
    public boolean isRefundAnyTime;

    @Column
    public boolean isAutoRefund;

    @Column
    public float serviceScore;

    @Column
    public String label;

    @Column
    public String buyNotes;

    public List<GroupPackageEntity> groupPackages;

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "groupPackages")
    public List<GroupPackageEntity> getGroupPackages() {
        if (groupPackages == null || groupPackages.isEmpty()) {
            groupPackages = SQLite.select().from(GroupPackageEntity.class)
                    .where(GroupPackageEntity_Table.groupId.eq(groupId))
                    .queryList();
        }
        return groupPackages;
    }


}
