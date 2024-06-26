package com.sleepapp.SleepTracker.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sleepapp.SleepTracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IconTitleView extends LinearLayout {

    @BindView(R.id.iv_icon_title)
    ImageView iv;
    @BindView(R.id.tv_icon_title)
    TextView tv;

    private Context context;

    public IconTitleView(Context context) {
        this(context, null, 0);
        this.context = context;
    }

    public IconTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = View.inflate(context, R.layout.view_icon_title, this);
        ButterKnife.bind(view);

    }

    public static IconTitleView newInstance(Context context, int imageResource, String title) {
        IconTitleView iconTitleView = new IconTitleView(context);
        iconTitleView.setImageView(imageResource);
        iconTitleView.setTitleText(title);
        return iconTitleView;
    }

    private void setImageView(int drawable) {
        Glide.with(context).load(drawable).into(iv);
    }

    private void setTitleText(String title) {
        tv.setText(title);
    }
}
