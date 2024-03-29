package com.efurture.wireless.defend.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.efurture.wireless.defend.DefendReporter;

/**
 * 解决Recycleview在部分机型上的crash问题
 *
 * Created by 剑白 on 2020/07/08 11:11.
 *
 * @author 剑白
 * @date 2020/07/08 11:11
 */
public class DLinearLayoutManager extends LinearLayoutManager {


    public DLinearLayoutManager(Context context) {
        super(context);
    }

    public DLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public DLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            return super.scrollHorizontallyBy(dx, recycler, state);
        }catch (Exception e){
            DefendReporter.onCrash(e);
            return 0;
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
           return super.scrollVerticallyBy(dy, recycler, state);
        }catch (Exception e){
            DefendReporter.onCrash(e);
            return 0;
        }
    }
}
