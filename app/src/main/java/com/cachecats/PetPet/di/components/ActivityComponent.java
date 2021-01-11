package com.cachecats.PetPet.di.components;

import com.cachecats.PetPet.app.MainActivity;
import com.cachecats.PetPet.app.home.HomeFragment;
import com.cachecats.PetPet.di.modules.ActivityModule;
import com.cachecats.PetPet.di.scopes.Scopes;

import dagger.Component;

/**
 * Created by solo on 2018/1/10.
 */

@Scopes.Activity
@Component(modules = {ActivityModule.class}, dependencies = {ApplicationComponent.class})
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(HomeFragment fragment);

}

