package com.efurture.wireless.defend.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.efurture.wireless.defend.DefendReporter;


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
            DefendReporter.onCrash(e);
        }
    }


}
