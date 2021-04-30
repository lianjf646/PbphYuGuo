package com.pbph.yuguo.myview;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pbph.yuguo.R;


/**
 * Created by Administrator on 2018/3/10.
 */

public class LineDividerDecoration extends RecyclerView.ItemDecoration {

    private int dividerHeight;
    private Paint dividerPaint;

    public LineDividerDecoration(Context context) {
        dividerPaint = new Paint();
        dividerPaint.setColor(context.getResources().getColor(R.color.gray_ebebeb));
        dividerHeight = 1;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;
    }

//    @Override
//    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        int childCount = parent.getChildCount();
//        int left = parent.getPaddingLeft();
//        int right = parent.getWidth() - parent.getPaddingRight();
//
//        for (int i = 0; i < childCount - 1; i++) {
//            View view = parent.getChildAt(i);
//            float top = view.getBottom();
//            float bottom = view.getBottom() + dividerHeight;
//            c.drawRect(left, top, right, bottom, dividerPaint);
//        }
//    }
}
