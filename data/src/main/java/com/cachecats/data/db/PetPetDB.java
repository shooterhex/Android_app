package com.cachecats.data.db;

import com.raizlabs.android.dbflow.annotation.Database;


@Database(name = PetPetDB.NAME, version = PetPetDB.VERSION, foreignKeyConstraintsEnforced = true)
public class PetPetDB {
    //数据库名称
    public final static String NAME = "PetPetDB";
    //版本号
    public final static int VERSION = 2;
}
