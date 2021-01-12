package com.cachecats.PetPet.app.home;

import com.cachecats.domin.shop.model.ShopModel;
import com.cachecats.PetPet.app.home.model.IconTitleModel;
import com.cachecats.PetPet.base.BasePresenter;
import com.cachecats.PetPet.widget.IconTitleView;
import com.scwang.smartrefresh.layout.api.RefreshFooter;

import java.util.List;

public interface HomeFragmentContract {

    interface View {

        void addViewToBigModule(IconTitleView iconTitleView);

        void setShopListData(List<ShopModel> shopModels);

        void finishLoadmore(boolean success);

        void finishLoadmoreWithNoMoreData();

        void resetNoMoreData();

        void finishRefresh(boolean success);

        void setRefreshFooter(RefreshFooter footer);

        void addData2RecyclerView(List<ShopModel> shopModels);




    }

    interface Presenter extends BasePresenter<View>{

        List<Integer> getBannerImages();

        List<IconTitleModel> getIconTitleModels();

        void onLoadMore();

        void onRefresh();
    }
}
