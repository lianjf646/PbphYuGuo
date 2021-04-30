package com.pbph.yuguo.myview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


public class MyRecyclerviewView extends RecyclerView {

    public MyRecyclerviewView(Context context) {
        super(context);
    }

    public MyRecyclerviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerviewView(Context context, AttributeSet attrs,
                              int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    /**
     * 重写该方法、达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}