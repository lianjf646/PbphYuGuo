package com.pbph.yuguo.myview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.pbph.yuguo.R;

/**
 * Created by Administrator on 2018/5/14.
 */

public class MySwipeRefreshLayout extends SwipeRefreshLayout {

    public MySwipeRefreshLayout(Context context) {
        super(context);
        initSwipeRefreshLayout();
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout() {
        setColorSchemeResources(R.color.main_color,
                R.color.main_color,
                R.color.main_color,
                R.color.main_color);
        setSize(SwipeRefreshLayout.DEFAULT);
        setProgressBackgroundColor(android.R.color.white);
    }

}
