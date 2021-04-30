package com.pbph.yuguo.adapter;


import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.adapter.abslistview.ViewHolder;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.response.GetActivityGoodsInfoListResponse;
import com.pbph.yuguo.util.MoneyHelper;

import java.util.List;

/**
 * Created by Administrator on 2018/8/2.
 */

public class EventCoudanGiftViewHolder extends ViewHolder<GetActivityGoodsInfoListResponse.DataBean.GoodsInfoListBean> {

    public CheckBox cb_shoppingcar;

    public ImageView iv_adapter_goods_list_image;

    public View iv_order_fill_goods_image_state;
    public TextView tv_adapter_goods_list_state;

    public TextView tv_adapter_goods_list_title;
    public TextView tv_adapter_goods_list_desc;

    public TextView tv_tag1;
    public TextView tv_tag2;
    public TextView tv_tag3;

    public LinearLayout linear;
    public TextView tv_adapter_goods_list_price_pre;
    public TextView tv_adapter_goods_list_price;
    public TextView tv_adapter_goods_list_discount_price;
    public TextView iv_adapter_goods_list_shopping_cart;

    @Override
    protected int getLayout() {
        return R.layout.item_eventcoudangift;
    }

    @Override
    protected void getView(View rootView) {

        this.cb_shoppingcar = (CheckBox) rootView.findViewById(R.id.cb_shoppingcar);
        cb_shoppingcar.setOnClickListener(listener);

        this.iv_adapter_goods_list_image = (ImageView) rootView.findViewById(R.id.iv_adapter_goods_list_image);
        this.iv_order_fill_goods_image_state = (View) rootView.findViewById(R.id.iv_order_fill_goods_image_state);
        this.tv_adapter_goods_list_state = (TextView) rootView.findViewById(R.id.tv_adapter_goods_list_state);
        this.tv_adapter_goods_list_title = (TextView) rootView.findViewById(R.id.tv_adapter_goods_list_title);
        this.tv_adapter_goods_list_desc = (TextView) rootView.findViewById(R.id.tv_adapter_goods_list_desc);
        this.tv_tag1 = (TextView) rootView.findViewById(R.id.tv_tag1);
        this.tv_tag2 = (TextView) rootView.findViewById(R.id.tv_tag2);
        this.tv_tag3 = (TextView) rootView.findViewById(R.id.tv_tag3);
        this.linear = (LinearLayout) rootView.findViewById(R.id.linear);
        this.tv_adapter_goods_list_price_pre = (TextView) rootView.findViewById(R.id.tv_adapter_goods_list_price_pre);
        this.tv_adapter_goods_list_price = (TextView) rootView.findViewById(R.id.tv_adapter_goods_list_price);
        this.tv_adapter_goods_list_discount_price = (TextView) rootView.findViewById(R.id.tv_adapter_goods_list_discount_price);
        this.iv_adapter_goods_list_shopping_cart = (TextView) rootView.findViewById(R.id.iv_adapter_goods_list_shopping_cart);

    }

    @Override
    protected void showView() {

        cb_shoppingcar.setChecked(adapter.choiceHelper.isChoiced(position));

        tv_tag1.setVisibility(View.INVISIBLE);
        tv_tag2.setVisibility(View.INVISIBLE);
        tv_tag3.setVisibility(View.INVISIBLE);

        List<String> tags = item.getLabelNameList();
        int i = 0;
        for (String str : tags) {
            if (i == 0) {
                tv_tag1.setText(str);
                tv_tag1.setVisibility(View.VISIBLE);
            } else if (i == 1) {
                tv_tag2.setText(str);
                tv_tag2.setVisibility(View.VISIBLE);
            } else if (i == 2) {
                tv_tag3.setText(str);
                tv_tag3.setVisibility(View.VISIBLE);
            }
            i++;
        }

        Glide.with(adapter.context).load(item.getGoodsInfoPicUrl()).into(iv_adapter_goods_list_image);

        tv_adapter_goods_list_title.setText(item.getGoodsInfoName());
        tv_adapter_goods_list_desc.setText(item.getGoodsSpec());

        tv_adapter_goods_list_price.setText(MoneyHelper.getInstance4Fen(item.getGoodsInfoMemberPrice()).change2Yuan().getString
                ());

        tv_adapter_goods_list_discount_price.setText("￥");
        tv_adapter_goods_list_discount_price.append(MoneyHelper.getInstance4Fen(item.getGoodsInfoSalePrice()).change2Yuan()
                .getString());

        iv_adapter_goods_list_shopping_cart.setText("×");
        iv_adapter_goods_list_shopping_cart.append(String.valueOf(item.getNumber()));


        if (item.getIsNotEnough() == 1) {
            cb_shoppingcar.setEnabled(false);
            cb_shoppingcar.setClickable(false);
            iv_order_fill_goods_image_state.setVisibility(View.VISIBLE);
            tv_adapter_goods_list_state.setVisibility(View.VISIBLE);
            if (cb_shoppingcar.isChecked() == true) {
                cb_shoppingcar.setEnabled(true);
                cb_shoppingcar.setClickable(true);
            }

        } else {
            cb_shoppingcar.setEnabled(true);
            cb_shoppingcar.setClickable(true);
            iv_order_fill_goods_image_state.setVisibility(View.INVISIBLE);
            tv_adapter_goods_list_state.setVisibility(View.INVISIBLE);
        }
    }

    private final OnSPClickListener listener = new OnSPClickListener() {
        @Override
        public void onClickSucc(View v) {
            if (adapter.onItemViewClickListener == null) return;
            adapter.onItemViewClickListener.onItemViewClick(v.getId(), EventCoudanGiftViewHolder.this);
        }
    };


}
