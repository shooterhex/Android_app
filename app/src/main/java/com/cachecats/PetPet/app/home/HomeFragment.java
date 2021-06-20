package com.cachecats.PetPet.app.home;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.cachecats.PetPet.app.MainActivity;
import com.cachecats.PetPet.app.alarm.SecondActivity;
import com.cachecats.domin.shop.model.ShopModel;
import com.cachecats.PetPet.MyApplication;
import com.cachecats.PetPet.R;
import com.cachecats.PetPet.app.home.adapter.LittleModuleAdapter;
import com.cachecats.PetPet.app.home.adapter.ShopListAdapter;
import com.cachecats.PetPet.app.home.model.IconTitleModel;
import com.cachecats.PetPet.base.BaseFragment;
import com.cachecats.PetPet.di.components.DaggerActivityComponent;
import com.cachecats.PetPet.utils.GlideImageLoader;
import com.cachecats.PetPet.utils.ToastUtils;
import com.cachecats.PetPet.widget.refresh.CustomRefreshFooter;
import com.cachecats.PetPet.widget.refresh.CustomRefreshHeader;
import com.cachecats.PetPet.widget.IconTitleView;
import com.cachecats.PetPet.widget.decoration.DividerItemDecoration;
import com.cachecats.PetPet.widget.decoration.HomeGridDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements HomeFragmentContract.View {


    @BindView(R.id.home_banner)
    Banner banner;
    //數據的LinearLayout布局
    @BindView(R.id.data_plot_linearLayout)
    LinearLayout graph_layout;
    //==========================================================================
    //數據的image布局
    @BindView(R.id.graph)
    GraphView graph;
    BarGraphSeries<DataPoint> series;

    //數據的image布局
    @BindView(R.id.graph_realtime)
    GraphView graph_realtime;
    LineGraphSeries<DataPoint> series_realtime;

    //==========================================================================
    //大模块的LinearLayout布局
    @BindView(R.id.ll_big_module_fragment_home)
    LinearLayout llBigModule;
    //小模块GridView布局
    @BindView(R.id.recyclerview_little_module)
    RecyclerView littleModuleRecyclerView;
    //4块广告封装成的自定义View
//    @BindView(R.id.home_ads_view)
//    HomeAdsView homeAdsView;
    //团购商店列表
    @BindView(R.id.recycler_view_shops)
    RecyclerView rvShopList;
    //下拉刷新组件
    @BindView(R.id.smartRefreshLayout_home)
    SmartRefreshLayout smartRefreshLayout;

    @Inject
    HomeFragmentContract.Presenter presenter;

    private ShopListAdapter mShopListAdapter;
    private List<ShopModel> mShopModels = Collections.emptyList();
    boolean flag = true;
    private int lastX = 0;
    private static final Random RANDOM = new Random();
    SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
    Timer timer;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        //绑定 ButterKnife
        ButterKnife.bind(this, view);

        DaggerActivityComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .build()
                .inject(this);

        presenter.setContractView(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        initBanner();
        initGraph();
        initGraph_real();
        initLittleModuleRecyclerView();
//        initAds();
        initShopList();
        initSmartRefreshLayout();
    }



    //初始化下拉刷新控件
    private void initSmartRefreshLayout() {
        smartRefreshLayout.setRefreshHeader(new CustomRefreshHeader(getActivity()));
        smartRefreshLayout.setRefreshFooter(new CustomRefreshFooter(getActivity(), "加载中…"));
        smartRefreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
        smartRefreshLayout.setEnableFooterFollowWhenLoadFinished(true);
        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                presenter.onLoadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.onRefresh();
            }
        });


    }

    @Override
    public void finishLoadmore(boolean success) {
        smartRefreshLayout.finishLoadmore(success);
    }

    @Override
    public void finishLoadmoreWithNoMoreData() {
        smartRefreshLayout.finishLoadmoreWithNoMoreData();
    }

    @Override
    public void finishRefresh(boolean success) {
        smartRefreshLayout.finishRefresh(success);
    }

    @Override
    public void resetNoMoreData() {
        smartRefreshLayout.resetNoMoreData();
    }

    /**
     * 加载更多后添加新的数据到RecyclerView
     * @param shopModels
     */
    @Override
    public void addData2RecyclerView(List<ShopModel> shopModels) {
        mShopListAdapter.addData(shopModels);
    }

    @Override
    public void setRefreshFooter(RefreshFooter footer) {
        smartRefreshLayout.setRefreshFooter(footer);
    }

    private void initShopList() {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvShopList.setLayoutManager(lm);
        rvShopList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        rvShopList.setItemAnimator(new DefaultItemAnimator());

        //创建临时例子
        //mShopModels = CreateExampleShopItem();

        mShopListAdapter = new ShopListAdapter(getActivity(), R.layout.item_home_shop_list, mShopModels);
        //mShopListAdapter.setUpFetchEnable(true);
        rvShopList.setAdapter(mShopListAdapter);
//        mShopListAdapter.setEmptyView();
    }

    @Override
    public void setShopListData(List<ShopModel> shopModels) {
        mShopListAdapter.setNewData(shopModels);
    }

//    private void initAds() {
//        homeAdsView.setOnAdsClickListener(new HomeAdsView.OnAdsClickListener() {
//            @Override
//            public void onAds1Click() {
//                ToastUtils.show("Ads1");
//            }
//
//            @Override
//            public void onAds2Click() {
//                ToastUtils.show("Ads2");
//            }
//
//            @Override
//            public void onAds3Click() {
//                ToastUtils.show("Ads3");
//            }
//
//            @Override
//            public void onAds4Click() {
//                ToastUtils.show("Ads4");
//            }
//        });
//    }

    /**
     * 初始化小模块的RecyclerView
     */
    private void initLittleModuleRecyclerView() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        //设置LayoutManager
        littleModuleRecyclerView.setLayoutManager(gridLayoutManager);
        //设置分割器
        littleModuleRecyclerView.addItemDecoration(new HomeGridDecoration(12));
        //设置动画
        littleModuleRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //设置Adapter
        List<IconTitleModel> iconTitleModels = presenter.getIconTitleModels();
        LittleModuleAdapter littleModuleAdapter = new LittleModuleAdapter(
                R.layout.view_icon_title_small, iconTitleModels);

        littleModuleRecyclerView.setAdapter(littleModuleAdapter);
        //设置item点击事件
        littleModuleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.show(iconTitleModels.get(position).getTitle());
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        //增加banner的体验
        banner.startAutoPlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(flag)
        {
            presenter.onStart();
            flag=false;
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                addEntry();
                Log.d("real time","called");
            }
        },0,1000);
        /*
        new Thread(new Runnable() {

            @Override
            public void run() {
                // we add 100 new entries
                for (int i = 0; i < 100; i++) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry();
                        }
                    });

                    // sleep to slow down the add of entries
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
            }
        }).start();
        */

    }




    @Override
    public void onPause() {
        super.onPause();
        //增加banner的体验
        //timer.cancel();
    }

    @Override
    public void onStop() {
        super.onStop();
        //增加banner的体验
        banner.stopAutoPlay();
    }

    public void initBanner() {
        //设置banner的各种属性
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new GlideImageLoader())
                .setImages(presenter.getBannerImages())
                .setBannerAnimation(Transformer.Default)
                .isAutoPlay(true)
                .setDelayTime(3000)
                .setIndicatorGravity(BannerConfig.CENTER)
                .start();
    }

    public void initGraph(){
        double y;
        DataPoint []dp = new DataPoint[30];
        for (int x=0;x<30;x++){
            y = x;
            dp[x] = new DataPoint(x,y);
        }
        series = new BarGraphSeries<>(dp);

        graph.addSeries(series);

        //title
        double maxY_double = series.getHighestValueY();
        double maxX_double = series.getHighestValueX();
        double minY_double = series.getLowestValueY();
        double minX_double = series.getLowestValueX();


        graph.setTitle(String.format("Your Sleep mark: %.2f", maxY_double));
        graph.setTitleTextSize(90);
        graph.setTitleColor(Color.GREEN);

        series.setSpacing(20);
        //series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.BLACK);


        // data graph line
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        //graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE );

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(maxY_double);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(minX_double);
        graph.getViewport().setMaxX(maxX_double);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        //graph.getViewport().setScalableY(true);

        //set label
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value,boolean isValueX){
                if(isValueX){
                    int minute = ((int)(value))/60;
                    int second = ((int)(value))%60;
                    return String.format("%02d:%02d",minute,second);
                }
                return super.formatLabel(value,isValueX);
            }
        });
        graph.getGridLabelRenderer().setTextSize(25);

        //set legend
        /*
        series.setTitle("Sleep quality");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setTextSize(40);
        graph.getLegendRenderer().setTextColor(Color.BLACK);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        */

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return getCurrentColor((float)(data.getY()/series.getHighestValueY()),Color.RED,Color.GREEN);
            }
        });







        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(),Pop.class));
            }
        });


    }

    private void initGraph_real(){

        series_realtime = new LineGraphSeries<DataPoint>();
        graph_realtime.addSeries(series_realtime);

        Viewport viewport = graph_realtime.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(30);
        viewport.setXAxisBoundsManual(true);
        viewport.setScrollable(true);

        //set label
        graph_realtime.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value,boolean isValueX){
                if(isValueX){
                    /*
                    int minute = ((int)(value))/60;
                    int second = ((int)(value))%60;
                    return String.format("%02d:%02d",minute,second);
                    */
                    long valueMillis = (new Double(value)).longValue();
                     return sdf.format(valueMillis);
                }
                return super.formatLabel(value,isValueX);
            }
        });

    }



    private void addEntry(){
        long millis = System.currentTimeMillis();
        series_realtime.appendData(new DataPoint((new Long(millis)).doubleValue(), getDataRealTime()),true, 10 );
    }

    public double getDataRealTime(){
        return RANDOM.nextDouble()*30d;
    }

    private int getCurrentColor(float fraction, int startColor, int endColor) {
        int redCurrent;
        int blueCurrent;
        int greenCurrent;
        int alphaCurrent;

        int redStart = Color.red(startColor);
        int blueStart = Color.blue(startColor);
        int greenStart = Color.green(startColor);
        int alphaStart = Color.alpha(startColor);

        int redEnd = Color.red(endColor);
        int blueEnd = Color.blue(endColor);
        int greenEnd = Color.green(endColor);
        int alphaEnd = Color.alpha(endColor);

        int redDifference = redEnd - redStart;
        int blueDifference = blueEnd - blueStart;
        int greenDifference = greenEnd - greenStart;
        int alphaDifference = alphaEnd - alphaStart;

        redCurrent = (int) (redStart + fraction * redDifference);
        blueCurrent = (int) (blueStart + fraction * blueDifference);
        greenCurrent = (int) (greenStart + fraction * greenDifference);
        alphaCurrent = (int) (alphaStart + fraction * alphaDifference);

        return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent);
    }



    /**
     * 往根布局上添加View
     */
    @Override
    public void addViewToBigModule(IconTitleView iconTitleView) {
        llBigModule.addView(iconTitleView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

//    public ArrayList<ShopModel> CreateExampleShopItem() {
//        ShopModel m = new ShopModel();
//        m.setAddress("西湖区灵隐街道浙大路38号");
//        m.setLogo("pet_icon.png");
//        m.setName("无恙宠物店");
//        ArrayList<ShopGroupInfoModel> ginf = new ArrayList<ShopGroupInfoModel>();
//
//        ShopGroupInfoModel inf = new ShopGroupInfoModel();
//        inf.setCurrentPrice(100);
//        inf.setOriginalPrice(1000);
//        inf.setSoldNum(9999);
//
//        ginf.add(inf);
//
//        m.setGroupInfos(ginf);
//        ArrayList<ShopModel>Lm = new ArrayList<ShopModel>();
//        Lm.add(m);
//        return Lm;
//    }
}
