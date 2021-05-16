package com.cachecats.domin.shop.repository;

import com.cachecats.domin.shop.model.GroupPackageModel;

import java.util.List;

public interface GroupPackageRepository {

    boolean save(GroupPackageModel model);

    void saveGroupPackages(List<GroupPackageModel> models);

}
