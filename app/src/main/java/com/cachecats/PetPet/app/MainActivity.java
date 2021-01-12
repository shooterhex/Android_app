package com.cachecats.PetPet.app;

import android.os.Bundle;

import com.cachecats.PetPet.MyApplication;
import com.cachecats.PetPet.R;
import com.cachecats.PetPet.app.discover.DiscoverFragment;
import com.cachecats.PetPet.app.home.HomeFragment;
import com.cachecats.PetPet.app.mine.MineFragment;
import com.cachecats.PetPet.app.nearby.NearbyFragment;
import com.cachecats.PetPet.app.order.OrderFragment;
import com.cachecats.PetPet.base.BaseActivity;
import com.cachecats.PetPet.base.BaseFragment;
import com.cachecats.PetPet.di.components.DaggerActivityComponent;
import com.cachecats.PetPet.di.modules.ActivityModule;
import com.cachecats.PetPet.widget.bottomtab.CustomBottomTabWidget;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tabWidget)
    CustomBottomTabWidget tabWidget;
    private List<BaseFragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaggerActivityComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build().inject(this);

        //初始化
        init();
    }

    private void init() {
        //构造Fragment的集合
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new NearbyFragment());
        fragmentList.add(new DiscoverFragment());
        fragmentList.add(new OrderFragment());
        fragmentList.add(new MineFragment());
        //初始化CustomBottomTabWidget
        tabWidget.init(getSupportFragmentManager(), fragmentList);
    }
}