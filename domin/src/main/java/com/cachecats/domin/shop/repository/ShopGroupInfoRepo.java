package com.cachecats.domin.shop.repository;

import com.cachecats.domin.shop.model.ShopGroupInfoModel;

import java.util.List;

public interface ShopGroupInfoRepo {

    boolean save(ShopGroupInfoModel model);

    void saveGroupInfos(List<ShopGroupInfoModel> models);
}
