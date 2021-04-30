package com.pbph.yuguo.myview;

/**
 * created by yhao on 2017/8/11.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.pbph.yuguo.adapter.shoppingcaradapter.ShoppingCarAdapter;


public class SlidingMenuItem extends HorizontalScrollView {

    ShoppingCarAdapter adapter;

    //菜单占屏幕宽度比
    private static final float radio = 0.2f;

    private final int mScreenWidth;
    private final int mMenuWidth;

    private final int halfMenuWidth;


    private boolean isOpen;

    private OnContentClickListener onContentClickListener;


    private static SlidingMenuItem mOpenMenu;

    public SlidingMenuItem(final Context context, AttributeSet attrs) {
        super(context, attrs);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mScreenWidth = outMetrics.widthPixels;
        mMenuWidth = (int) (mScreenWidth * radio);

        halfMenuWidth = mMenuWidth >> 1;

        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setHorizontalScrollBarEnabled(false);

    }

    public boolean isOpen() {
        return isOpen;
    }

    public void close() {
//        if (!isOpen) return;
        this.smoothScrollTo(0, 0);
//        adapter.mOpenMenu = null;
        mOpenMenu = null;
        isOpen = false;
    }

    public void open() {
//        if (isOpen) return;
        this.smoothScrollTo(mMenuWidth, 0);
//        adapter.mOpenMenu = this;
        mOpenMenu = this;
        isOpen = true;
    }


    private boolean once = true;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (once) {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            wrapper.getChildAt(0).getLayoutParams().width = mScreenWidth;
            wrapper.getChildAt(1).getLayoutParams().width = mMenuWidth;
            once = false;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private long downTime = 0;


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();

//                if (adapter.mOpenMenu != null) adapter.mOpenMenu.close();
                if (mOpenMenu != null) {
                    if (mOpenMenu == this) {

                        mOpenMenu.close();
                        return false;
                    } else {
                        mOpenMenu.close();
                    }

                }

                break;
            case MotionEvent.ACTION_UP:

                int scrollX = getScrollX();
                if (System.currentTimeMillis() - downTime <= 100 && scrollX == 0) {

                    if (onContentClickListener != null) onContentClickListener.onClick();

                    return false;
                }
                if (Math.abs(scrollX) > halfMenuWidth) {
                    open();
                } else {
                    close();
                }
                return false;
        }
        return super.onTouchEvent(ev);
    }


    public void setAdapter(ShoppingCarAdapter adapter) {
        this.adapter = adapter;
    }

    public void setOnContentClickListener(OnContentClickListener onContentClickListener) {
        this.onContentClickListener = onContentClickListener;
    }

    public interface OnContentClickListener {
        void onClick();
    }
}
