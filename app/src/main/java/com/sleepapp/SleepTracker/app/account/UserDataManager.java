package com.sleepapp.SleepTracker.app.account;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

public class UserDataManager {             //用户数据管理类
    //一些宏定义和声明
    private static final String TAG = "UserDataManager";
    private static final String DB_NAME = "user_data";
    private static final String TABLE_NAME = "users";
    public static final String ID = "_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PWD = "user_pwd";
//    public static final String SILENT = "silent";
//    public static final String VIBRATE = "vibrate";
    private static final int DB_VERSION = 2;
    private Context mContext = null;
    private static final String TABLE_NAME_SUGGESTIONS = "SUGGESTIONS";
    public static final String SID = "_sid";
    public static final String SUGGESTIONS_CONTENT = "content";

    private static final String TABLE_NAME_DIARY = "DIARY";
    public static final String DID = "_did";
    public static final String DIARY_CONTENT = "content";

    private static UserData CurUser;




// 用户----
    private static final String DB_CREATE = "CREATE TABLE " + TABLE_NAME + " ("
            + ID + " integer," + USER_NAME + " varchar  primary key,"
            + USER_PWD + " varchar" + ");";

// 建议----
    private static final String DB_CREATE_SUGGESTIONS = "CREATE TABLE " + TABLE_NAME_SUGGESTIONS + " ("
            + SID + " integer primary key," + SUGGESTIONS_CONTENT + " varchar,"
            + USER_NAME + " varchar REFERENCES users(user_name) ON DELETE CASCADE" + ");";

//  日记----
    private static final String DB_CREATE_DIARY = "CREATE TABLE " + TABLE_NAME_DIARY + " ("
            + DID + " integer primary key," + DIARY_CONTENT + " varchar,"
            + USER_NAME + " varchar REFERENCES users(user_name) ON DELETE CASCADE" + ");";

    //INTEGER REFERENCES  ON DELETE CASCADE
    private static SQLiteDatabase mSQLiteDatabase = null;
    private DataBaseManagementHelper mDatabaseHelper = null;

    //DataBaseManagementHelper继承自SQLiteOpenHelper
    private static class DataBaseManagementHelper extends SQLiteOpenHelper {

        DataBaseManagementHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
//            onCreate(mSQLiteDatabase);
        }

        public static void update(){
            SQLiteDatabase db = mSQLiteDatabase;
            boolean isopendb = db.isOpen();
            if(!isopendb){
                Logger.d("db is not Open");
            }

            // 初始化 UserTable
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
            try{
                db.execSQL(DB_CREATE);
                Log.i(TAG, "db.execSQL(DB_CREATE)");
            }
            catch (SQLException e) {
                Log.e(TAG, DB_CREATE);
            }

            // 初始化 Suggestions table
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SUGGESTIONS + ";");
            try{
                db.execSQL(DB_CREATE_SUGGESTIONS);
                Log.i(TAG, "db.execSQL(DB_CREATE_SUGGESTIONS)");
                Logger.d("@@@db.execSQL(DB_CREATE_SUGGESTIONS)");
            }
            catch (SQLException e) {
                Log.e(TAG, DB_CREATE_SUGGESTIONS);
            }

            // 初始化 diary Table
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DIARY + ";");
            try{
                db.execSQL(DB_CREATE_DIARY);
                Log.i(TAG, "db.execSQL(DB_CREATE_DIARY)");
                Logger.d("@@@db.execSQL(DB_CREATE_DIARY)");
            }
            catch (SQLException e) {
                Log.e(TAG, DB_CREATE_DIARY);
            }
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i(TAG,"db.getVersion()="+db.getVersion());
            boolean isopendb = db.isOpen();
            if(!isopendb){
                Logger.d("db is not Open");
            }
            // 初始化 UserTable
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
            try{
                db.execSQL(DB_CREATE);
                Log.i(TAG, "db.execSQL(DB_CREATE)");
            }
            catch (SQLException e) {
                Log.e(TAG, DB_CREATE);
            }

            // 初始化 Suggestions table
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SUGGESTIONS + ";");
            try{
                db.execSQL(DB_CREATE_SUGGESTIONS);
                Log.i(TAG, "db.execSQL(DB_CREATE_SUGGESTIONS)");
                Logger.d("@@@db.execSQL(DB_CREATE_SUGGESTIONS)");
            }
            catch (SQLException e) {
                Log.e(TAG, DB_CREATE_SUGGESTIONS);
            }

            // 初始化 diary Table
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DIARY + ";");
            try{
                db.execSQL(DB_CREATE_DIARY);
                Log.i(TAG, "db.execSQL(DB_CREATE_DIARY)");
                Logger.d("@@@db.execSQL(DB_CREATE_DIARY)");
            }
            catch (SQLException e) {
                Log.e(TAG, DB_CREATE_DIARY);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i(TAG, "DataBaseManagementHelper onUpgrade");

        }
    }


    public void update(){
        DataBaseManagementHelper.update();
    }

    public UserDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "UserDataManager construction!");

    }
    //打开数据库
    public void openDataBase() throws SQLException {

        mDatabaseHelper = new DataBaseManagementHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
        //mDatabaseHelper.onCreate(mSQLiteDatabase);
        Logger.d("@DataBaseManagementHelper");
    }
    //关闭数据库
    public void closeDataBase() throws SQLException {
        mDatabaseHelper.close();
    }
    //添加新用户，即注册
    public long insertUserData(UserData userData) {
        String userName=userData.getUserName();
        String userPwd=userData.getUserPwd();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName);
        values.put(USER_PWD, userPwd);
        return mSQLiteDatabase.insert(TABLE_NAME, ID, values);
    }
    //更新用户信息，如修改密码
    public boolean updateUserData(UserData userData) {
        //int id = userData.getUserId();
        String userName = userData.getUserName();
        String userPwd = userData.getUserPwd();
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName);
        values.put(USER_PWD, userPwd);
        return mSQLiteDatabase.update(TABLE_NAME, values,null, null) > 0;
        //return mSQLiteDatabase.update(TABLE_NAME, values, ID + "=" + id, null) > 0;
    }
    //
    public Cursor fetchUserData(int id) throws SQLException {
        Cursor mCursor = mSQLiteDatabase.query(false, TABLE_NAME, null, ID
                + "=" + id, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //
    public Cursor fetchAllUserDatas() {
        return mSQLiteDatabase.query(TABLE_NAME, null, null, null, null, null,
                null);
    }
    //根据id删除用户
    public boolean deleteUserData(int id) {
        return mSQLiteDatabase.delete(TABLE_NAME, ID + "=" + id, null) > 0;
    }
    //根据用户名注销
    public boolean deleteUserDatabyname(String name) {
        String name_r = "'" + name + "'";
        return mSQLiteDatabase.delete(TABLE_NAME, USER_NAME + "=" + name_r, null) > 0;
    }
    //删除所有用户
    public boolean deleteAllUserDatas() {
        return mSQLiteDatabase.delete(TABLE_NAME, null, null) > 0;
    }

    //
    public String getStringByColumnName(String columnName, int id) {
        Cursor mCursor = fetchUserData(id);
        int columnIndex = mCursor.getColumnIndex(columnName);
        String columnValue = mCursor.getString(columnIndex);
        mCursor.close();
        return columnValue;
    }
    //
    public boolean updateUserDataById(String columnName, int id,
                                      String columnValue) {
        ContentValues values = new ContentValues();
        values.put(columnName, columnValue);
        return mSQLiteDatabase.update(TABLE_NAME, values, ID + "=" + id, null) > 0;
    }
    //根据用户名找用户，可以判断注册时用户名是否已经存在
    public int findUserByName(String userName){
        Log.i(TAG,"findUserByName , userName="+userName);
        int result=0;
        Cursor mCursor;
        String userName_r ="'"+ userName+"'";
            mCursor=mSQLiteDatabase.query(TABLE_NAME, null, USER_NAME+"="+userName_r, null, null, null, null);


        if(mCursor!=null){
            result=mCursor.getCount();
            mCursor.close();
            Log.i(TAG,"findUserByName , result="+result);
        }
        return result;
    }
    //根据用户名和密码找用户，用于登录
    public int findUserByNameAndPwd(String userName,String pwd){
        Log.i(TAG,"findUserByNameAndPwd");
        int result=0;
        String userName_r = "'"+userName + "'";
        String pwd_r = "'"+pwd + "'";
        Cursor mCursor=mSQLiteDatabase.query(TABLE_NAME, null, USER_NAME+"="+userName_r+" and "+USER_PWD+"="+pwd_r,
                null, null, null, null);
        if(mCursor!=null){
            result=mCursor.getCount();
            mCursor.close();
            Log.i(TAG,"findUserByNameAndPwd , result="+result);
        }
        return result;
    }

    public static void SetCurUser(UserData User){
        CurUser = User;
    }

    public static UserData getCurUser() {
        return CurUser;
    }


    //添加新日记
    public long insertDiaryData(String userName, String DiaryName) {
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName);
        values.put(DIARY_CONTENT, DiaryName);
        return mSQLiteDatabase.insert(TABLE_NAME_DIARY, DID, values);
    }

    public ArrayList<String> findDiaryByUserName(String userName){
        Log.i(TAG,"findDiaryByUserName , userName="+userName);
        int result=0;
        Cursor mCursor;
        String userName_r ="'"+ userName+"'";
        mCursor=mSQLiteDatabase.query(TABLE_NAME_DIARY, null, USER_NAME+"="+userName_r, null, null, null, null);
        ArrayList<String> petlists = new ArrayList<String>();
        while(mCursor.moveToNext()){
            petlists.add(mCursor.getString(mCursor.getColumnIndex(DIARY_CONTENT)));
        }

        if(mCursor!=null){
            result=mCursor.getCount();
            mCursor.close();
            Log.i(TAG,"findUserByName , result="+result);
        }
        return petlists;
    }

    //添加新建议
    public long insertSuggestionData(String userName, String SuggestionName) {
        ContentValues values = new ContentValues();
        values.put(USER_NAME, userName);
        values.put(SUGGESTIONS_CONTENT, SuggestionName);
        return mSQLiteDatabase.insert(TABLE_NAME_SUGGESTIONS, SID, values);
    }

    //删除建议
    public boolean deleteAllSuggestionData(String userName) {
        String name_r = "'" + userName + "'";
        return mSQLiteDatabase.delete(TABLE_NAME_SUGGESTIONS, USER_NAME + "=" + name_r, null) > 0;
    }

    public ArrayList<String> findSuggestionByUserName(String userName){
        Log.i(TAG,"findSuggestionByUserName , userName="+userName);
        int result=0;
        Cursor mCursor;
        String userName_r ="'"+ userName+"'";
        mCursor=mSQLiteDatabase.query(TABLE_NAME_SUGGESTIONS, null, USER_NAME+"="+userName_r, null, null, null, null);
        ArrayList<String> petlists = new ArrayList<String>();
        while(mCursor.moveToNext()){
            petlists.add(mCursor.getString(mCursor.getColumnIndex(SUGGESTIONS_CONTENT)));
        }

        if(mCursor!=null){
            result=mCursor.getCount();
            mCursor.close();
            Log.i(TAG,"findUserByName , result="+result);
        }
        return petlists;
    }


}
