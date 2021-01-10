package com.cachecats.meituan.app.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cachecats.meituan.R;
import com.cachecats.meituan.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by solo on 2018/1/8.
 */

public class MineFragment extends BaseFragment {


    @BindView(R.id.recyclerview_little_module)
    RecyclerView littleModuleRecyclerView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_mine, null);
        View view = inflater.inflate(R.layout.activity_login, null);
        return view;

    }
}
