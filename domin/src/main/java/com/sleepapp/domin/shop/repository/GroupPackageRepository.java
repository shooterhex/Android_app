package com.sleepapp.domin.shop.repository;

import com.sleepapp.domin.shop.model.GroupPackageModel;

import java.util.List;

public interface GroupPackageRepository {

    boolean save(GroupPackageModel model);

    void saveGroupPackages(List<GroupPackageModel> models);

}
