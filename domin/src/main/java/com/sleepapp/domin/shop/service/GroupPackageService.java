package com.sleepapp.domin.shop.service;

import com.sleepapp.domin.shop.model.GroupPackageModel;
import com.sleepapp.domin.shop.repository.GroupPackageRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class GroupPackageService {

    private GroupPackageRepository repository;

    @Inject
    public GroupPackageService(GroupPackageRepository repository) {
        this.repository = repository;
    }

    /**
     * 保存单个对象
     *
     * @param model
     * @return
     */
    public Single<Boolean> save(GroupPackageModel model) {
        return Single.create(emitter -> {
            emitter.onSuccess(repository.save(model));
        });
    }

    /**
     * 开启事务异步保存多个对象
     * 注意由于是异步保存，当发送 onComplete 时不一定代表此时存储已经执行完毕
     *
     * @param models
     * @return
     */
    public Completable saveGroupPackages(List<GroupPackageModel> models) {
        return Completable.create(emitter -> {
            repository.saveGroupPackages(models);
            emitter.onComplete();
        });
    }
}
