package com.cachecats.PetPet.di.modules;

import android.app.Application;
import android.content.Context;

import com.cachecats.data.shop.repository.GroupPackageRepoImpl;
import com.cachecats.data.shop.repository.ShopGroupInfoRepoImpl;
import com.cachecats.data.shop.repository.ShopRepositoryImpl;
import com.cachecats.domin.shop.repository.GroupPackageRepository;
import com.cachecats.domin.shop.repository.ShopGroupInfoRepo;
import com.cachecats.domin.shop.repository.ShopRepository;
import com.cachecats.PetPet.app.home.HomeFragmentContract;
import com.cachecats.PetPet.app.home.HomeFragmentPresenter;

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
