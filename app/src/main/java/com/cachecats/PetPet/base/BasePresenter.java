package com.cachecats.PetPet.base;

public interface BasePresenter<T> {

    /**
     * 将View传给Presenter
     * @param view
     */
    void setContractView(T view);

    /**
     * 初始化方法
     */
    void onStart();

    void onDestroy();

}
