package com.sleepapp.SleepTracker.widget.bottomtab;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.sleepapp.SleepTracker.R;
import com.sleepapp.SleepTracker.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomBottomTabWidget extends LinearLayout {

    @BindView(R.id.ll_menu_home_page)
    LinearLayout llMenuHome;
    @BindView(R.id.ll_menu_music)
    LinearLayout llMenuMusic;
    @BindView(R.id.ll_menu_alarm)
    LinearLayout llMenuAlarm;
    @BindView(R.id.ll_menu_exper)
    LinearLayout llMenuExper;
    @BindView(R.id.ll_menu_account)
    LinearLayout llMenuAccount;
    @BindView(R.id.vp_tab_widget)
    ViewPager viewPager;

    private FragmentManager mFragmentManager;
    private List<BaseFragment> mFragmentList;
    private TabPagerAdapter mAdapter;

    public CustomBottomTabWidget(Context context) {
        this(context, null, 0);
    }

    public CustomBottomTabWidget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomBottomTabWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.widget_custom_bottom_tab, this);
        ButterKnife.bind(view);

        //设置默认的选中项
        selectTab(MenuTab.HOME);

    }

    /**
     * 外部调用初始化，传入必要的参数
     *
     * @param fm
     */
    public void init(FragmentManager fm, List<BaseFragment> fragmentList) {
        mFragmentManager = fm;
        mFragmentList = fragmentList;
        initViewPager();
    }

    /**
     * 初始化 ViewPager
     */
    private void initViewPager() {
        mAdapter = new TabPagerAdapter(mFragmentManager, mFragmentList);
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("msg", "hahaha");
            }

            @Override
            public void onPageSelected(int position) {
                //将ViewPager与下面的tab关联起来
                switch (position) {
                    case 0:
                        selectTab(MenuTab.ALARM);
                        break;
                    case 1:
                        selectTab(MenuTab.MUSIC);
                        break;
                    case 2:
                        selectTab(MenuTab.HOME);
                        break;
                    case 3:
                        selectTab(MenuTab.EXPER);
                        break;
                    case 4:
                        selectTab(MenuTab.ACCOUNT);
                        break;
                    default:
                        selectTab(MenuTab.HOME);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d("msg", "blablabla");
            }
        });
    }

    /**
     * 点击事件集合
     */
    @OnClick({R.id.ll_menu_home_page, R.id.ll_menu_music, R.id.ll_menu_alarm, R.id.ll_menu_exper, R.id.ll_menu_account})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.ll_menu_alarm:
                selectTab(MenuTab.ALARM);
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_menu_music:
                selectTab(MenuTab.MUSIC);
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_menu_home_page:
                selectTab(MenuTab.HOME);
                //使ViewPager跟随tab点击事件滑动
                viewPager.setCurrentItem(2);
                break;
            case R.id.ll_menu_exper:
                selectTab(MenuTab.EXPER);
                viewPager.setCurrentItem(3);
                break;
            case R.id.ll_menu_account:
                selectTab(MenuTab.ACCOUNT);
                viewPager.setCurrentItem(4);
                break;
        }
    }

    /**
     * 设置 Tab 的选中状态
     *
     * @param tab 要选中的标签
     */
    public void selectTab(MenuTab tab) {
        //先将所有tab取消选中，再单独设置要选中的tab
        unCheckedAll();

        switch (tab) {
            case ALARM:
                llMenuAlarm.setActivated(true);
                break;
            case MUSIC:
                llMenuMusic.setActivated(true);
                break;
            case HOME:
                llMenuHome.setActivated(true);
                break;
            case EXPER:
                llMenuExper.setActivated(true);
                break;
            case ACCOUNT:
                llMenuAccount.setActivated(true);
        }

    }


    //让所有tab都取消选中
    private void unCheckedAll() {
        llMenuHome.setActivated(false);
        llMenuMusic.setActivated(false);
        llMenuAlarm.setActivated(false);
        llMenuExper.setActivated(false);
        llMenuAccount.setActivated(false);
    }

    /**
     * tab的枚举类型
     */
    public enum MenuTab {
        ALARM,
        MUSIC,
        HOME,
        EXPER,
        ACCOUNT
    }
}
