package com.sleepapp.SleepTracker.app.account;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sleepapp.SleepTracker.R;
import com.sleepapp.SleepTracker.base.BaseFragment;

import butterknife.BindView;

public class MineFragment extends BaseFragment {


    @BindView(R.id.recyclerview_little_module)
    RecyclerView littleModuleRecyclerView;
//    public Intent act;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, null);
        //View view = inflater.inflate(R.layout.activity_login, null);

        Intent intent = new Intent(getActivity().getApplicationContext(),Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
        getActivity().getApplicationContext().startActivity(intent);
//        act = intent;
        return view;

    }
//
//    @Nullable
//    @Override
//    public void onStart() {
//        super.onStart();
//        getActivity().getApplicationContext().startActivity(act);
//    }
}
