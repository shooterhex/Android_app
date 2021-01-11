package com.cachecats.meituan.app.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cachecats.meituan.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

public class User extends AppCompatActivity {
    private Button addPetButton;
    private Button mReturnButton;

    private UserDataManager mUserDataManager;         //用户数据管理类
//    private String [] Petdata = {};
    private ArrayList<String> Petdata = new ArrayList<String>();

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        setContentView(R.layout.user);
        mReturnButton = (Button)findViewById(R.id.returnback);
        addPetButton = (Button)findViewById(R.id.addpet);
        TextView tv = (TextView)findViewById(R.id.textView);

        tv.setText(  mUserDataManager.getCurUser().getUserName() + "您好，欢迎回来！");
        //tv.setText(R.string.user_login_success, userData.getUserName());

        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();                              //建立本地数据库
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // todo 读数据库 加到Petdata
        Petdata = mUserDataManager.findPetsByUserName(mUserDataManager.getCurUser().getUserName());
        if(Petdata.size()!=0)
            refreshPetList();

    }
    public void back_to_login(View view) {
        //setContentView(R.layout.login);
        Intent intent3 = new Intent(User.this,Login.class) ;
        startActivity(intent3);
        finish();
    }
    public void addPet(View view) {

        EditText Pname_et = new EditText(this);
        //EditText Ptype_et = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入宠物名字").setIcon(android.R.drawable.ic_dialog_info);
        builder.setView(Pname_et);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int b) {
                String PetName = Pname_et.getText().toString();
                Logger.d("dialog=" + PetName);
                Petdata.add(PetName);
                addPet2DB(PetName);

                refreshPetList();
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
    public void refreshPetList(){

        //ArraryAdapter适配器，通过泛型来指定要适配的数据类型，然后在构造函数中把要适配的数据传入。
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String> (
                User.this, android.R.layout.simple_list_item_1,Petdata);
        ListView listView = (ListView) findViewById(R.id.PETView);
        listView.setAdapter(arrayAdapter);

    }

    public void addPet2DB(String Petname){
        if(mUserDataManager.insertPetData(mUserDataManager.getCurUser().getUserName(),Petname)==0){
            Log.e("User","Can't insert pet to DB:"+ Petname);
        }
    }

}
