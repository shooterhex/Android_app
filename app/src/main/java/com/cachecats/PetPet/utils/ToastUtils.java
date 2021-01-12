package com.cachecats.PetPet.utils;

import android.widget.Toast;

import com.cachecats.PetPet.MyApplication;

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
