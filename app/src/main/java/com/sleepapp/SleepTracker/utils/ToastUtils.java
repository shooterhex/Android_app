package com.sleepapp.SleepTracker.utils;

import android.widget.Toast;

import com.sleepapp.SleepTracker.MyApplication;

public class ToastUtils {

    private static Toast mToast;

    private ToastUtils(){
    }

    public static void show(String str){
        if(mToast == null){
            mToast = Toast.makeText(MyApplication.getAppContext(), str, Toast.LENGTH_SHORT);
        }else {
            mToast.setText(str);
        }
        mToast.show();
    }


}
