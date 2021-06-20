package com.sleepapp.SleepTracker.widget.decoration;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.sleepapp.SleepTracker.utils.DensityUtils;

public class HomeGridDecoration extends RecyclerView.ItemDecoration {

    private float space;

    public HomeGridDecoration(float space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = DensityUtils.dip2px(space);
    }
}
