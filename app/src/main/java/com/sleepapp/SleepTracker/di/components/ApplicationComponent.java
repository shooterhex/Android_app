package com.sleepapp.SleepTracker.di.components;

import android.app.Application;
import android.content.Context;

import com.sleepapp.data.shop.mapper.GroupPackageMapper;
import com.sleepapp.data.shop.mapper.ShopEvaluateMapper;
import com.sleepapp.data.shop.mapper.ShopGroupInfoMapper;
import com.sleepapp.data.shop.mapper.ShopMapper;
import com.sleepapp.domin.shop.repository.GroupPackageRepository;
import com.sleepapp.domin.shop.repository.ShopGroupInfoRepo;
import com.sleepapp.domin.shop.repository.ShopRepository;
import com.sleepapp.domin.shop.service.GroupPackageService;
import com.sleepapp.domin.shop.service.ShopGroupInfoService;
import com.sleepapp.domin.shop.service.ShopService;
import com.sleepapp.SleepTracker.MyApplication;
import com.sleepapp.SleepTracker.app.home.HomeFragmentContract;
import com.sleepapp.SleepTracker.app.home.HomeFragmentPresenter;
import com.sleepapp.SleepTracker.di.modules.ApplicationModule;
import com.sleepapp.SleepTracker.mock.MockUtils;
import com.solo.common.rxjava.CloseableRxServiceExecutor;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(MyApplication application);

    Application application();

    Context context();

    HomeFragmentPresenter presenter();

    HomeFragmentContract.Presenter homeFragmentContractPresenter();

    ShopMapper shopMapper();

    ShopRepository shopRepository();

    ShopService shopService();

    ShopEvaluateMapper shopEvaluateMapper();

    GroupPackageMapper groupPackageMapper();

    ShopGroupInfoMapper shopGroupInfoMapper();

    GroupPackageRepository groupPackageRepository();

    GroupPackageService groupPackageService();

    MockUtils mockUtils();

    ShopGroupInfoRepo shopGroupInfoRepo();

    ShopGroupInfoService shopGroupInfoService();

    CloseableRxServiceExecutor closeableRxServiceExecutor();
}
