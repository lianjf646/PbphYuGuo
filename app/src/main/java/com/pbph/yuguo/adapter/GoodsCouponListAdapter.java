package com.pbph.yuguo.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.pbph.yuguo.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */

public class GoodsCouponListAdapter extends BaseAdapter {
    private Context context = null;
    private List<String> list;
    private LayoutInflater inflater = null;
    private int width = 0;

    public GoodsCouponListAdapter(Context context, List<String> list) {
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
            view = inflater.inflate(R.layout.adapter_coupon_list, null);
        }
        LinearLayout priceLayout = (LinearLayout) ViewHolder.get(view, R.id.ll_adapter_coupon_list_price_layout);
        priceLayout.getLayoutParams().width = width / 5;
        priceLayout.getLayoutParams().height = width / 5;
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
