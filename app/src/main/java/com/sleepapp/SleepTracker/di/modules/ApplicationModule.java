package com.sleepapp.SleepTracker.di.modules;

import android.app.Application;
import android.content.Context;

import com.sleepapp.data.shop.repository.GroupPackageRepoImpl;
import com.sleepapp.data.shop.repository.ShopGroupInfoRepoImpl;
import com.sleepapp.data.shop.repository.ShopRepositoryImpl;
import com.sleepapp.domin.shop.repository.GroupPackageRepository;
import com.sleepapp.domin.shop.repository.ShopGroupInfoRepo;
import com.sleepapp.domin.shop.repository.ShopRepository;
import com.sleepapp.SleepTracker.app.home.HomeFragmentContract;
import com.sleepapp.SleepTracker.app.home.HomeFragmentPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Provides
    public Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    HomeFragmentContract.Presenter providePresenter(HomeFragmentPresenter presenter) {
        return presenter;
    }

    @Provides
    @Singleton
    ShopRepository provideShopRepository(ShopRepositoryImpl impl) {
        return impl;
    }

    @Provides
    @Singleton
    GroupPackageRepository provideGroupPackageRepository(GroupPackageRepoImpl impl) {
        return impl;
    }

    @Provides
    @Singleton
    ShopGroupInfoRepo provideShopGroupInfoRepo(ShopGroupInfoRepoImpl impl) {
        return impl;
    }


}
