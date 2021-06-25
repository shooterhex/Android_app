package com.sleepapp.SleepTracker.app.account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.sleepapp.SleepTracker.R;
import com.orhanobut.logger.Logger;
import java.util.Random;
import java.util.ArrayList;

public class User extends AppCompatActivity {
    private Button addDiaryButton;
    private Button mReturnButton;
    private boolean ToUpdate = false;

    private UserDataManager mUserDataManager;         //用户数据管理类

    private ArrayList<String> DiaryData = new ArrayList<String>();
    private ArrayList<String> SuggestionData = new ArrayList<String>();

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        setContentView(R.layout.user);
        mReturnButton = (Button)findViewById(R.id.returnback);
        addDiaryButton = (Button)findViewById(R.id.addpet);
        TextView tv = (TextView)findViewById(R.id.textView);



        if (mUserDataManager == null ) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();                              //建立本地数据库
        }

        if(ToUpdate){
            mUserDataManager.update();
        }



        tv.setText(  mUserDataManager.getCurUser().getUserName() + "您好，欢迎回来！");
        //tv.setText(R.string.user_login_success, userData.getUserName());
    }

    @Override
    protected void onStart() {
        super.onStart();

        // todo 读数据库
        DiaryData = mUserDataManager.findDiaryByUserName(mUserDataManager.getCurUser().getUserName());

        mUserDataManager.deleteAllSuggestionData(mUserDataManager.getCurUser().getUserName());
        if(DiaryData.size()!=0)
            refreshDiaryList();

        //随机生成建议
        addRandomSuggestion();

        SuggestionData = mUserDataManager.findSuggestionByUserName(mUserDataManager.getCurUser().getUserName());
        if(SuggestionData.size()!=0)
            refreshSuggestionList();

    }
    public void back_to_login(View view) {
        //setContentView(R.layout.login);
        Intent intent3 = new Intent(User.this,Login.class) ;
        startActivity(intent3);
        finish();
    }
    public void addDiary(View view) {

        EditText Diary_et = new EditText(this);
        //EditText Ptype_et = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入日记内容").setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(Diary_et);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int b) {
                String DiaryContent = Diary_et.getText().toString();
                Logger.d("dialog=" + DiaryContent);
                DiaryData.add(DiaryContent);
                addDiary2DB(DiaryContent);

                refreshDiaryList();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int b) {
                dialog.dismiss();
            }
        });

        builder.show();

    }
    public void refreshDiaryList(){

        //ArraryAdapter适配器，通过泛型来指定要适配的数据类型，然后在构造函数中把要适配的数据传入。
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String> (
                User.this, android.R.layout.simple_list_item_1, DiaryData);
        ListView listView = (ListView) findViewById(R.id.PETView);
        listView.setAdapter(arrayAdapter);

    }

    public void addDiary2DB(String Diary){
        if(mUserDataManager.insertDiaryData(mUserDataManager.getCurUser().getUserName(),Diary)==0){
            Log.e("User","Can't insert diary to DB:"+ Diary);
        }
    }


    public void refreshSuggestionList(){

        //ArraryAdapter适配器，通过泛型来指定要适配的数据类型，然后在构造函数中把要适配的数据传入。
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String> (
                User.this, android.R.layout.simple_list_item_1, SuggestionData);
        ListView listView = (ListView) findViewById(R.id.SuggestionView);
        listView.setAdapter(arrayAdapter);

    }

    public void addSuggestion2DB(String suggestion){
        if(mUserDataManager.insertSuggestionData(mUserDataManager.getCurUser().getUserName(),suggestion)==0){
            Log.e("User","Can't insert suggestion to DB:"+ suggestion);
        }
    }

    private static final Random RANDOM = new Random();

    public void addRandomSuggestion(){


        int num = RANDOM.nextInt(3);

        ArrayList<String> allSuggestion = new ArrayList<String>();
        boolean[] isUsed = new boolean[5];

        allSuggestion.add("入睡太晚：入睡时间不晚于22点为佳，长期熬夜可能引起免疫力低下，加速衰老。");
        allSuggestion.add("容易清醒：不要过度劳累，保持心情愉快，减少压力，饭后百步走，睡前来一杯热牛奶。");
        allSuggestion.add("睡眠不足：睡眠时长以7-9小时为佳，睡眠不足可能引起免疫力低下、反应迟钝。");
        allSuggestion.add("深睡较短：合理安排作息时间，积极锻炼身体增加机体抵抗力，不要过度劳累。");
        allSuggestion.add("失眠：四郎运动，规律作息，远离夜生活，养成良好的生活习惯。");

        for(int i=0;i<num;i++){
            int idx = RANDOM.nextInt(4);

            while(isUsed[idx]==true){
                idx = RANDOM.nextInt(4);
            }

            addSuggestion2DB( allSuggestion.get(idx) );
            isUsed[idx] = true;
        }


    }


}
