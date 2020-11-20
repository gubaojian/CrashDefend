package com.cainiao.wireless.crashdefendkit.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.cainiao.wireless.crashdefendkit.CrashDefendKit;

public class DRecyclerView extends RecyclerView {


    public DRecyclerView(Context context) {
        super(context);
    }

    public DRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        try {
            super.onLayout(changed, l, t, r, b);
        }catch (Exception e){
            CrashDefendKit.onCrash(DRecyclerView.class, e);
        }
    }


}
