package com.pbph.yuguo.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pbph.yuguo.R;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.response.GoodsInfoBean;

import java.util.List;


public class OrderFillShopingCarGoodsAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsInfoBean> storeGoodsListBeans;
    private com.pbph.yuguo.adapter.MyAddressListAdapter.DataListener dataListener;
    private int widith;

    public OrderFillShopingCarGoodsAdapter(Context context, List<GoodsInfoBean> storeGoodsListBeans) {
        this.context = context;
        this.storeGoodsListBeans = storeGoodsListBeans;
        BaseActivity activity = (BaseActivity) context;
        widith = activity.getScreenWidth();
    }


    public void setDataListener(com.pbph.yuguo.adapter.MyAddressListAdapter.DataListener dataListener) {
        this.dataListener = dataListener;
    }

    @Override
    public int getCount() {
        return storeGoodsListBeans != null ? storeGoodsListBeans.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return storeGoodsListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_order_fill_shoppingcar_goods_grid, null);
        }
        GoodsInfoBean bean = storeGoodsListBeans.get(position);
        TextView invalidBg = (TextView) ViewHolder.get(convertView, R.id.tv_order_fill_shopping_car_goods_adapter_invalid_bg);
        TextView invalid = (TextView) ViewHolder.get(convertView, R.id.tv_order_fill_shopping_car_goods_adapter_invalid);
        ImageView goodsImage = (ImageView) ViewHolder.get(convertView, R.id.iv_order_fill_shopping_car_goods_adapter_image);
        ImageView zengpin = (ImageView) ViewHolder.get(convertView, R.id.iv_order_fill_zp);


        goodsImage.getLayoutParams().width = widith / 4;
        goodsImage.getLayoutParams().height = widith / 4;
        if (bean.getAvaliableFlag() == 0) {
            invalidBg.setVisibility(View.VISIBLE);
            invalid.setVisibility(View.VISIBLE);
        } else {
            invalidBg.setVisibility(View.GONE);
            invalid.setVisibility(View.GONE);
        }
//        GlideUtil.displayCircleBitmap(context, bean.getGoodsPicUrl(), goodsImage);
        Glide.with(context).load(bean.getGoodsInfoPicUrl()).into(goodsImage);


        zengpin.setVisibility(bean.gifts ? View.VISIBLE : View.GONE);
        return convertView;
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


