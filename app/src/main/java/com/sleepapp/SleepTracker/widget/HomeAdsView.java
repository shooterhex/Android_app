package com.sleepapp.SleepTracker.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sleepapp.SleepTracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeAdsView extends LinearLayout {

    @BindView(R.id.ads_1)
    ImageView ads1;
    @BindView(R.id.ads_2)
    ImageView ads2;
    @BindView(R.id.ads_3)
    ImageView ads3;
    @BindView(R.id.ads_4)
    ImageView ads4;

    public HomeAdsView(Context context) {
        this(context, null, 0);
    }

    public HomeAdsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeAdsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.view_home_ads, this);
        ButterKnife.bind(view);
    }

    @OnClick({R.id.ads_1, R.id.ads_2, R.id.ads_3, R.id.ads_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ads_1:
                onAdsClickListener.onAds1Click();
                break;
            case R.id.ads_2:
                onAdsClickListener.onAds2Click();
                break;
            case R.id.ads_3:
                onAdsClickListener.onAds3Click();
                break;
            case R.id.ads_4:
                onAdsClickListener.onAds4Click();
                break;
        }
    }

    private OnAdsClickListener onAdsClickListener;

    public interface OnAdsClickListener {
        void onAds1Click();

        void onAds2Click();

        void onAds3Click();

        void onAds4Click();
    }

    public void setOnAdsClickListener(OnAdsClickListener onAdsClickListener) {
        this.onAdsClickListener = onAdsClickListener;
    }
}
