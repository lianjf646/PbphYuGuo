package com.pbph.yuguo.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.pbph.yuguo.R;
import com.pbph.yuguo.response.GetGoodsDetailResponse;
import com.pbph.yuguo.util.AMUtils;
import com.pbph.yuguo.util.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */

public class GoodsEvaluateListAdapter extends BaseAdapter {
    private Context context = null;
    private List<GetGoodsDetailResponse.DataBean.EvaluateListBean> list;
    private LayoutInflater inflater = null;
    private int width = 0;


    public GoodsEvaluateListAdapter(Context context, List<GetGoodsDetailResponse.DataBean.EvaluateListBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (null == view) {
            view = inflater.inflate(R.layout.adapter_evaluate_list, null);
        }

        ImageView mIvAdapterEvaluateListHead = (ImageView) ViewHolder.get(view, R.id.iv_adapter_evaluate_list_head);
        GridView imageViewGrid = (GridView) ViewHolder.get(view, R.id.gv_evaluate_image_grid);
        TextView mTvAdapterEvaluateListNickname = (TextView) ViewHolder.get(view, R.id.tv_adapter_evaluate_list_nickname);
        TextView mTvAdapterEvaluateListTime = (TextView) ViewHolder.get(view, R.id.tv_adapter_evaluate_list_time);
        TextView mTvAdapterEvaluateListContent = (TextView) ViewHolder.get(view, R.id.tv_adapter_evaluate_list_content);

        GetGoodsDetailResponse.DataBean.EvaluateListBean evaluateListBean = list.get(i);

        int anonymousFlag = evaluateListBean.getAnonymousFlag();
        String anonymousStr = "";

//        mIvAdapterEvaluateListHead.setImageResource(R.drawable.default_photo);
        switch (anonymousFlag) {
            case 0:
                anonymousStr = evaluateListBean.getCustomerName();
                if (AMUtils.isMobile(anonymousStr)) {
                    anonymousStr = anonymousStr.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                }
                GlideUtil.displayCircleBitmap(context, evaluateListBean.getCustomerImgUrl(), mIvAdapterEvaluateListHead);
                break;
            case 1:
                anonymousStr = "匿名用户";
//                mIvAdapterEvaluateListHead.setImageResource(R.drawable.default_photo);
                GlideUtil.displayCircleBitmapLocal(context, R.drawable.wodeyidenglu_touxiang, mIvAdapterEvaluateListHead);
                break;
        }
        mTvAdapterEvaluateListNickname.setText(anonymousStr);

        mTvAdapterEvaluateListTime.setText(evaluateListBean.getCreateTime());
        mTvAdapterEvaluateListContent.setText(evaluateListBean.getEvaluateContent());

//        mIvAdapterEvaluateListHead.getLayoutParams().width = width / 8;
//        mIvAdapterEvaluateListHead.getLayoutParams().height = width / 8;
        if (evaluateListBean.getShareImgList().isEmpty()) {
            imageViewGrid.setVisibility(View.GONE);
        } else {
            imageViewGrid.setVisibility(View.VISIBLE);
            imageViewGrid.setNumColumns(evaluateListBean.getShareImgList().size());
            int horizontalSpacing = 10;
            int gridViewWidth = ((width / 4) + horizontalSpacing) * evaluateListBean.getShareImgList().size();
            imageViewGrid.getLayoutParams().width = gridViewWidth;
            imageViewGrid.setHorizontalSpacing(horizontalSpacing);
            imageViewGrid.setAdapter(new EvaluateListImageAdapter(context, evaluateListBean.getShareImgList()));
        }
        return view;
    }

    private static class ViewHolder {

        public static View get(View view, int id) {

            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return childView;
        }
    }
}
