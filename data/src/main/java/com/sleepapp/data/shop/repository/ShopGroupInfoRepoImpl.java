package com.sleepapp.data.shop.repository;

import com.sleepapp.data.db.SleepTrackerDB;
import com.sleepapp.data.shop.entity.ShopGroupInfoEntity;
import com.sleepapp.data.shop.mapper.ShopGroupInfoMapper;
import com.sleepapp.domin.shop.model.ShopGroupInfoModel;
import com.sleepapp.domin.shop.repository.ShopGroupInfoRepo;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;

import java.util.List;

import javax.inject.Inject;

public class ShopGroupInfoRepoImpl implements ShopGroupInfoRepo {

    private ShopGroupInfoMapper mapper;

    @Inject
    public ShopGroupInfoRepoImpl(ShopGroupInfoMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean save(ShopGroupInfoModel model) {
        ShopGroupInfoEntity entity = mapper.toEntity(model);
        return entity.save();
    }

    @Override
    public void saveGroupInfos(List<ShopGroupInfoModel> models) {
        FastStoreModelTransaction<ShopGroupInfoEntity> transaction = FastStoreModelTransaction
                .saveBuilder(FlowManager.getModelAdapter(ShopGroupInfoEntity.class))
                .addAll(mapper.toEntities(models))
                .build();
        FlowManager.getDatabase(SleepTrackerDB.class)
                .beginTransactionAsync(transaction)
                .build().execute();
    }
}
