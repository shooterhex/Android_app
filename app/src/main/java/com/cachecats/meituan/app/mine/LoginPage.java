package com.cachecats.meituan.app.mine;

//package com.administrator.login;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.cachecats.meituan.R;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class  LoginPage extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPassWord;
    String username=null;
    String password=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Button mButton=(Button)findViewById(R.id.mButton);
        mUsername=(EditText)findViewById(R.id.mUserName);
        mPassWord=(EditText)findViewById(R.id.mPassWord);
        final CheckBox mCheckbox=(CheckBox)findViewById(R.id.mCheckBox);

        readfrominfo();     //调用读内部存储函数


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckbox.isChecked()){
                    username=mUsername.getText().toString();
                    password=mPassWord.getText().toString();
                    File file = new File(getFilesDir(),"info.txt");     //写内部存储到info.txt文件
                    Logger.d("CLICKING!!!");
                    FileOutputStream fos= null;     //创建输出流
                    try {
                        fos = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        fos.write((username+"##"+password).getBytes());     //将username##password按位写入文件中
                        fos.close();                                           //关闭流
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                Toast.makeText( LoginPage.this,"登陆成功",Toast.LENGTH_SHORT).show();        //吐司界面，参数依次为提示发出Activity,提示内容,提示时长
            }
        });

    }

    //读内部存储函数
    public void readfrominfo(){
        File file = new File(getFilesDir(),"info.txt");
        if(file.exists()){
            BufferedReader br= null;
            try {
                br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));      //将字节流转化为输入流然后转化为字符流
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String str = null;
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String [] up= str.split("##");      //将字符串按##分割的部分依次存入字符串型数组up中

            mUsername.setText(up[0]);       //填写username与password
            mPassWord.setText(up[1]);
        }
    }
}