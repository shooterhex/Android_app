package com.sleepapp.data.shop.repository;

import com.sleepapp.data.db.SleepTrackerDB;
import com.sleepapp.data.shop.entity.GroupPackageEntity;
import com.sleepapp.data.shop.mapper.GroupPackageMapper;
import com.sleepapp.domin.shop.model.GroupPackageModel;
import com.sleepapp.domin.shop.repository.GroupPackageRepository;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;

import java.util.List;

import javax.inject.Inject;

public class GroupPackageRepoImpl implements GroupPackageRepository {

    private GroupPackageMapper mapper;

    @Inject
    public GroupPackageRepoImpl(GroupPackageMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean save(GroupPackageModel model) {
        GroupPackageEntity entity = mapper.toEntity(model);
        return entity.save();
    }

    @Override
    public void saveGroupPackages(List<GroupPackageModel> models) {
        //快速异步事务存储
        FastStoreModelTransaction<GroupPackageEntity> transaction = FastStoreModelTransaction
                .saveBuilder(FlowManager.getModelAdapter(GroupPackageEntity.class))
                .addAll(mapper.toEntities(models))
                .build();
        FlowManager.getDatabase(SleepTrackerDB.class)
                .beginTransactionAsync(transaction)
                .build()
                .execute();
    }
}
