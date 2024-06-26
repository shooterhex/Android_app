package com.sleepapp.SleepTracker.app.home.adapter;

import androidx.annotation.Nullable;

import com.sleepapp.SleepTracker.R;
import com.sleepapp.SleepTracker.app.home.model.IconTitleModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class LittleModuleAdapter extends BaseQuickAdapter<IconTitleModel, BaseViewHolder> {

    private List<IconTitleModel> list;

    public LittleModuleAdapter(int layoutResId, @Nullable List<IconTitleModel> data) {
        super(layoutResId, data);
        list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, IconTitleModel item) {
        //设置图片
        helper.setImageResource(R.id.iv_icon_title, item.getIconResource());
        //设置标题
        helper.setText(R.id.tv_icon_title, item.getTitle());
    }
}
