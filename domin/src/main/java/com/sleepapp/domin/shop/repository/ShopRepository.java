package com.sleepapp.domin.shop.repository;

import com.sleepapp.domin.shop.model.ShopModel;

import java.util.List;

public interface ShopRepository {

    /**
     * 获取所有商店信息
     */
    List<ShopModel> getAllShops();

    /**
     * 分页获取商店信息
     * @param pageSize 每页的数据条数
     * @return
     */
    List<ShopModel> getShopsByPage(int page, int pageSize);

    /**
     * 保存单个商店
     * @param model
     * @return
     */
    boolean saveShop(ShopModel model);

    /**
     * 批量保存
     * @param models
     * @return
     */
    void saveShops(List<ShopModel> models);

    /**
     * 清空数据库
     */
    void clear();
}
