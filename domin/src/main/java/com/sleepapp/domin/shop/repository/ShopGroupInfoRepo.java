package com.sleepapp.domin.shop.repository;

import com.sleepapp.domin.shop.model.ShopGroupInfoModel;

import java.util.List;

public interface ShopGroupInfoRepo {

    boolean save(ShopGroupInfoModel model);

    void saveGroupInfos(List<ShopGroupInfoModel> models);
}
