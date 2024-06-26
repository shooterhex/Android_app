package com.sleepapp.SleepTracker.app.home.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sleepapp.domin.shop.model.ShopGroupInfoModel;
import com.sleepapp.domin.shop.model.ShopModel;
import com.sleepapp.SleepTracker.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ShopListAdapter extends BaseQuickAdapter<ShopModel,BaseViewHolder> {

    private Context context;

    public ShopListAdapter(Context context, int layoutResId, @Nullable List<ShopModel> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopModel item) {
        //设置logo
        ImageView ivLogo = helper.getView(R.id.iv_shop_logo);
        Glide.with(context).load(item.getLogo()).into(ivLogo);
        //设置标题
        helper.setText(R.id.tv_shop_title, item.getName());
        //设置地址
        helper.setText(R.id.tv_shop_location, item.getAddress());
        //设置现价
        List<ShopGroupInfoModel> groupInfos = item.getGroupInfos();

        if(!groupInfos.isEmpty()){
            ShopGroupInfoModel groupInfo = groupInfos.get(0);
            helper.setText(R.id.tv_price, "¥" + groupInfo.getCurrentPrice());
            //设置原价
            helper.setText(R.id.tv_original_price, "门市价:¥" + groupInfo.getOriginalPrice());
            //已售
            helper.setText(R.id.tv_shop_sold, "已售" + groupInfo.getSoldNum());
        }

    }




}
