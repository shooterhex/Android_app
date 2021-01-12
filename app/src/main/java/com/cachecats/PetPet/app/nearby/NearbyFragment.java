package com.cachecats.PetPet.app.nearby;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cachecats.PetPet.R;
import com.cachecats.PetPet.base.BaseFragment;

/**
 * Created by solo on 2018/1/8.
 */

public class NearbyFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, null);
        ImageView image = (ImageView) view.findViewById(R.id.dis_sample);             //使用ImageView显示logo
        image.setImageResource(R.drawable.dis_sample);
        return view;

    }
}