package com.pbph.yuguo.myview;

/**
 * Created by Administrator on 2017/8/24.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.pbph.yuguo.R;


public class SmartListView extends ListView implements AbsListView.OnScrollListener {
    private View footView, emtpyView = null;
    private LayoutInflater layoutInflater = LayoutInflater.from(getContext());
    private OnScrollBottom onScrollBottom = null;
    public ProgressBar progressBar;
    public LinearLayout noDataLayout;
    public final static int TYPE_NO_DATA = 2; // 没有数据
    public final static int TYPE_EMPTY_LIST = 4; // 没有数据
    public final static int TYPE_FOOTER = 3;//底部--往往是loading_more
    public final static int TYPE_NORMAL = 1; // 正常显示
    private int state = TYPE_NORMAL;

    public SmartListView(Context context) {
        super(context);
        setOnScrollListener(this);
        initFootView(R.layout.foot_layout);
    }

    public SmartListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(this);
        initFootView(R.layout.foot_layout);
    }

    public void setOnScrollBottom(OnScrollBottom onScrollBottom) {
        this.onScrollBottom = onScrollBottom;
    }

    public SmartListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnScrollListener(this);
        initFootView(R.layout.foot_layout);
    }

    public void initFootView(int resLayout) {
        footView = layoutInflater.inflate(R.layout.foot_layout, null);
        progressBar = footView.findViewById(R.id.progressBar);
        noDataLayout = footView.findViewById(R.id.ll_foot_no_data_layout);
        addFooterView(footView);
        setFootViewState(TYPE_NORMAL);
    }

//    public void viewEmpty() {
//        if (null == emtpyView) {
//            initEmptyView();
//        }
//        setEmptyView(emtpyView);
//    }

    private void initEmptyView() {
        emtpyView = layoutInflater.inflate(R.layout.list_empty_layout, null);
    }

    public boolean isFillListView() {
        View lastVisibleItemView = getChildAt(getChildCount() - 1);
//        Log.e("isFillListView", (lastVisibleItemView.getBottom() + getPaddingBottom()) + "    " + getHeight());
        return (lastVisibleItemView.getBottom() + getPaddingBottom()) < getHeight();
    }

    public void setFootViewState(int state) {
        switch (state) {
            case TYPE_FOOTER:
                footView.setVisibility(VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                noDataLayout.setVisibility(View.GONE);
                break;
            case TYPE_NO_DATA:
                footView.setVisibility(VISIBLE);
                progressBar.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.VISIBLE);
                break;
            case TYPE_NORMAL:
//                removeFooterView(footView);
                footView.setVisibility(GONE);
                setFooterDividersEnabled(false);
                break;
        }
        this.state = state;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
            View lastVisibleItemView = getChildAt(getChildCount() - 1);
            if (lastVisibleItemView != null && (lastVisibleItemView.getBottom() + getPaddingBottom()) == getHeight()) {
                if (null != onScrollBottom && state == TYPE_NORMAL) {
                    Log.e("onScroll", "到达底部加载数据");
                    setFootViewState(TYPE_FOOTER);
                    onScrollBottom.onBottom();
                } else {
                    Log.e("onScroll", "到达底部无需加载数据");
                }
            }
        }
    }


    public interface OnScrollBottom {
        void onBottom();
    }
}
