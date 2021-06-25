package com.sleepapp.SleepTracker.app.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sleepapp.SleepTracker.R;
import com.sleepapp.SleepTracker.base.BaseFragment;

import butterknife.BindView;

//public class MineFragment extends BaseFragment {
//
//
//    @BindView(R.id.recyclerview_little_module)
//    RecyclerView littleModuleRecyclerView;
////    public Intent act;
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_account, null);
//        //View view = inflater.inflate(R.layout.activity_login, null);
//
//        Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        getActivity().getApplicationContext().startActivity(intent);
////        act = intent;
//        return view;
//
//    }
////
////    @Nullable
////    @Override
////    public void onStart() {
////        super.onStart();
////        getActivity().getApplicationContext().startActivity(act);
////    }
//}


public class MineFragment extends BaseFragment {
    public int pwdresetFlag=0;
    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private Button mRegisterButton;                   //注册按钮
    private Button mLoginButton;                      //登录按钮
    private Button mCancleButton;                     //注销按钮
    private CheckBox mRememberCheck;

    private SharedPreferences login_sp;
    private String userNameValue,passwordValue;

    private View loginView;                           //登录
    private View loginSuccessView;
    private TextView loginSuccessShow;
    private TextView mChangepwdText;
    private UserDataManager mUserDataManager;         //用户数据管理类


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.login);
        View view = View.inflate(getActivity(),R.layout.login,null);
        //通过id找到相应的控件
        mAccount = (EditText) view.findViewById(R.id.login_edit_account);
        mPwd = (EditText) view.findViewById(R.id.login_edit_pwd);
        mRegisterButton = (Button) view.findViewById(R.id.login_btn_register);
        mLoginButton = (Button) view.findViewById(R.id.login_btn_login);
        mCancleButton = (Button) view.findViewById(R.id.login_btn_cancle);
        loginView=view.findViewById(R.id.login_view);
        loginSuccessView=view.findViewById(R.id.login_success_view);
        loginSuccessShow=(TextView) view.findViewById(R.id.login_success_show);

        mChangepwdText = (TextView) view.findViewById(R.id.login_text_change_pwd);

        mRememberCheck = (CheckBox) view.findViewById(R.id.Login_Remember);

        login_sp = getActivity().getSharedPreferences("userInfo", 0);
        String name=login_sp.getString("USER_NAME", "");
        String pwd =login_sp.getString("PASSWORD", "");
        boolean choseRemember =login_sp.getBoolean("mRememberCheck", false);
        boolean choseAutoLogin =login_sp.getBoolean("mAutologinCheck", false);
        //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
        if(choseRemember){
            mAccount.setText(name);
            mPwd.setText(pwd);
            mRememberCheck.setChecked(true);
        }

        mRegisterButton.setOnClickListener(mListener);                      //采用OnClickListener方法设置不同按钮按下之后的监听事件
        mLoginButton.setOnClickListener(mListener);
        mCancleButton.setOnClickListener(mListener);
        mChangepwdText.setOnClickListener(mListener);

//        ImageView image = (ImageView) findViewById(R.id.logo);             //使用ImageView显示logo
//        image.setImageResource(R.drawable.logo);

        ImageView image = (ImageView) view.findViewById(R.id.logo);             //使用ImageView显示logo
        image.setImageResource(R.drawable.ic_vector_exper_pressed);

        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(getContext());
            mUserDataManager.openDataBase();                              //建立本地数据库
        }

        return view;
    }
    View.OnClickListener mListener = new View.OnClickListener() {                  //不同按钮按下的监听事件选择
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn_register:                            //登录界面的注册按钮
                    Intent intent_Login_to_Register = new Intent(getActivity(),Register.class) ;    //切换Login Activity至User Activity
                    startActivity(intent_Login_to_Register);
                    getActivity().finish();
                    break;
                case R.id.login_btn_login:                              //登录界面的登录按钮
                    login();
                    break;
                case R.id.login_btn_cancle:                             //登录界面的注销按钮
                    cancel();
                    break;
                case R.id.login_text_change_pwd:                             //登录界面的注销按钮
                    Intent intent_Login_to_reset = new Intent(getActivity(),Resetpwd.class) ;    //切换Login Activity至User Activity
                    startActivity(intent_Login_to_reset);
                    getActivity().finish();
                    break;
            }
        }
    };

    public void login() {                                              //登录按钮监听事件
        if (isUserNameAndPwdValid()) {
            String userName = mAccount.getText().toString().trim();    //获取当前输入的用户名和密码信息
            String userPwd = mPwd.getText().toString().trim();
            SharedPreferences.Editor editor =login_sp.edit();
            int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);
            if(result==1){                                             //返回1说明用户名和密码均正确
                //保存用户名和密码
                editor.putString("USER_NAME", userName);
                editor.putString("PASSWORD", userPwd);

                mUserDataManager.SetCurUser(new UserData(userName,userPwd));


                //是否记住密码
                if(mRememberCheck.isChecked()){
                    editor.putBoolean("mRememberCheck", true);
                }else{
                    editor.putBoolean("mRememberCheck", false);
                }
                editor.commit();

                Intent intent = new Intent(getActivity(),User.class) ;    //切换Login Activity至User Activity
                startActivity(intent);
                getActivity().finish();
                Toast.makeText(getActivity(), getString(R.string.login_success),Toast.LENGTH_SHORT).show();//登录成功提示
            }else if(result==0){
                Toast.makeText(getActivity(), getString(R.string.login_fail),Toast.LENGTH_SHORT).show();  //登录失败提示
            }
        }
    }
    public void cancel() {           //注销
        if (isUserNameAndPwdValid()) {
            String userName = mAccount.getText().toString().trim();    //获取当前输入的用户名和密码信息
            String userPwd = mPwd.getText().toString().trim();
            int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);
            if(result==1){                                             //返回1说明用户名和密码均正确
//                Intent intent = new Intent(Login.this,User.class) ;    //切换Login Activity至User Activity
//                startActivity(intent);
                Toast.makeText(getActivity(), getString(R.string.cancel_success),Toast.LENGTH_SHORT).show();//登录成功提示
                mPwd.setText("");
                mAccount.setText("");
                mUserDataManager.deleteUserDatabyname(userName);
            }else if(result==0){
                Toast.makeText(getActivity(), getString(R.string.cancel_fail),Toast.LENGTH_SHORT).show();  //登录失败提示
            }
        }

    }

    public boolean isUserNameAndPwdValid() {
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(getActivity());
            mUserDataManager.openDataBase();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        if (mUserDataManager != null) {
            mUserDataManager.closeDataBase();
            mUserDataManager = null;
        }
        super.onPause();
    }
}
