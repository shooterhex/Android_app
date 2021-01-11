package com.cachecats.PetPet.app.home;

import android.content.Context;
import android.widget.LinearLayout;

import com.cachecats.domin.shop.service.GroupPackageService;
import com.cachecats.domin.shop.service.ShopService;
import com.cachecats.PetPet.R;
import com.cachecats.PetPet.app.home.model.IconTitleModel;
import com.cachecats.PetPet.mock.MockUtils;
import com.cachecats.PetPet.utils.ToastUtils;
import com.cachecats.PetPet.widget.IconTitleView;
import com.cachecats.PetPet.widget.refresh.CustomRefreshFooter;
import com.orhanobut.logger.Logger;
import com.solo.common.rxjava.CloseableRxServiceExecutor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by solo on 2018/1/10.
 */

public class HomeFragmentPresenter implements HomeFragmentContract.Presenter {

    //大模块的图片数组
    private static final int[] bigModuleDrawables = {
            R.mipmap.homepage_icon_my_pet,
            R.mipmap.homepage_icon_community,
            R.mipmap.homepage_icon_knowledge,
            R.mipmap.homepage_icon_shop,
            R.mipmap.homepage_icon_hospital,
    };

    //大模块的标题数组
    private static final String[] bigMudoleTitles = {
            "我的宠物", "宠物社区", "宠物宝典", "养宠必备", "宠物医院"
    };

    private HomeFragmentContract.View mFragment;
    private Context mContext;
    private ShopService shopService;
    private GroupPackageService groupPackageService;
    private MockUtils mockUtils;
    private CloseableRxServiceExecutor executor;

    //每页的大小
    private static final int PAGE_SIZE = 10;
    //当前是第几页
    private int mCurrentPage = 0;
    //是否没有更多数据了
    private boolean isNoMoreData = false;


    @Inject
    public HomeFragmentPresenter(Context context,
                                 ShopService shopService,
                                 GroupPackageService groupPackageService,
                                 MockUtils mockUtils,
                                 CloseableRxServiceExecutor executor) {
        mContext = context;
        this.shopService = shopService;
        this.groupPackageService = groupPackageService;
        this.mockUtils = mockUtils;
        this.executor = executor;
    }

    @Override
    public void setContractView(HomeFragmentContract.View fragment) {
        mFragment = fragment;
    }

    @Override
    public void onStart() {

        initBigModule();
//        mockUtils.mockShopDataToDB();
//        mockUtils.clearShop();
//        mockUtils.mockGroupPackagesToDB();
//        getAllShops();
//        mockUtils.mockGroupInfoData();
        getFirstPageShops();
    }

    @Override
    public void onDestroy() {
        mCurrentPage = 0;
        isNoMoreData = false;
    }


    /**
     * 获取所有的商铺信息
     */
    private void getAllShops() {
        executor.execute(shopService.getAllShops(), shopModels -> {
            Logger.d(shopModels);
            mFragment.setShopListData(shopModels);
        });
    }

    /**
     * 获取首页商店信息
     */
    private void getFirstPageShops() {
        executor.execute(shopService.getShopsByPage(0, PAGE_SIZE), shopModels -> {
            Logger.d(shopModels);
            mFragment.setShopListData(shopModels);
        });
    }


    @Override
    public void onLoadMore() {
        if (isNoMoreData) {
            return;
        }

        mCurrentPage++;
        executor.execute(
                shopService.getShopsByPage(mCurrentPage, PAGE_SIZE),
                shopModels -> {
                    Logger.d(shopModels);
                    //返回结果为空则说明没有更多数据了
                    if (shopModels.isEmpty()) {
                        isNoMoreData = true;
                        //重置Footer为没有更多数据状态
                        mFragment.setRefreshFooter(new CustomRefreshFooter(mContext, "没有更多啦"));
                        mFragment.finishLoadmoreWithNoMoreData();
                        return;
                    }
                    mFragment.addData2RecyclerView(shopModels);
                    mFragment.finishLoadmore(true);
                },
                error -> {
                    Logger.d(error);
                    mFragment.finishLoadmore(false);
                });
    }

    @Override
    public void onRefresh() {
        mCurrentPage = 0;
        isNoMoreData = false;
        mFragment.setRefreshFooter(new CustomRefreshFooter(mContext, "加载中…"));
        //重置没有更多数据状态
        mFragment.resetNoMoreData();
        executor.execute(
                shopService.getShopsByPage(0, PAGE_SIZE),
                shopModels -> {
                    Logger.d(shopModels);
                    mFragment.setShopListData(shopModels);
                    mFragment.finishRefresh(true);
                },
                error -> {
                    mFragment.finishRefresh(false);
                });
    }

    /**
     * 初始化banner下面的5个大模块
     */
    private void initBigModule() {
        for (int i = 0; i < 5; i++) {
            IconTitleView iconTitleView = IconTitleView.newInstance(mContext, bigModuleDrawables[i], bigMudoleTitles[i]);
            // 设置宽高和权重weight，使每个View占用相同的宽度
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            iconTitleView.setLayoutParams(lp);

            // 往根布局上添加View
            mFragment.addViewToBigModule(iconTitleView);

            //给View添加点击事件
            int finalI = i;
            iconTitleView.setOnClickListener((view) -> {
                Logger.d(bigMudoleTitles[finalI]);
                ToastUtils.show(bigMudoleTitles[finalI]);
            });
        }
    }

    /**
     * 获取Banner的图片资源
     *
     * @return
     */
    @Override
    public List<Integer> getBannerImages() {
        List<Integer> mBannerImages = new ArrayList<>();
        mBannerImages.add(R.mipmap.banner1);
        mBannerImages.add(R.mipmap.banner2);
        mBannerImages.add(R.mipmap.banner3);
        mBannerImages.add(R.mipmap.banner4);
        mBannerImages.add(R.mipmap.banner5);
        mBannerImages.add(R.mipmap.banner6);
        return mBannerImages;
    }

    /**
     * 获取包含两行小模块的图标、标题的对象的集合
     *
     * @return
     */
    @Override
    public List<IconTitleModel> getIconTitleModels() {
        List<IconTitleModel> datas = new ArrayList<>();
        datas.add(new IconTitleModel(R.mipmap.homepage_icon_live, "宠物直播"));
        datas.add(new IconTitleModel(R.mipmap.homepage_icon_score, "赚积分"));
        datas.add(new IconTitleModel(R.mipmap.homepage_icon_notice, "提醒"));
        datas.add(new IconTitleModel(R.mipmap.homepage_icon_pay, "记账"));
        datas.add(new IconTitleModel(R.mipmap.homepage_icon_adopt, "寄养"));
        datas.add(new IconTitleModel(R.mipmap.homepage_icon_card, "萌宠卡"));
        datas.add(new IconTitleModel(R.mipmap.homepage_icon_health, "营养师"));
        datas.add(new IconTitleModel(R.mipmap.homepage_icon_bath, "美容洗澡"));
        datas.add(new IconTitleModel(R.mipmap.homepage_icon_food, "能不能吃"));
        datas.add(new IconTitleModel(R.mipmap.homepage_icon_ill, "病症自查"));
        return datas;
    }


}
