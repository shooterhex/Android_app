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
public class ShopEntity extends BaseModel {

    @PrimaryKey
    public String id;

    @Column
    public String name;

    @Column
    public String logo;

    @Column
    public String address;

    @Column
    public String tel;

    @Column
    public float serviceScore;

    @Column
    public float perConsume;

    @Column
    public String introduction;

    @Column
    public String recommendDishes;

    public List<ShopGroupInfoEntity> groupInfos;

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "groupInfos")
    public List<ShopGroupInfoEntity> getGroupInfos() {
        if (groupInfos == null || groupInfos.isEmpty()) {
            groupInfos = SQLite.select().from(ShopGroupInfoEntity.class)
                    .where(ShopGroupInfoEntity_Table.shopId.eq(id))
                    .queryList();
        }
        return groupInfos;
    }

}
