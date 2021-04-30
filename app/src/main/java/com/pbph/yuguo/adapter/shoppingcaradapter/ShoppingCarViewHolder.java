package com.pbph.yuguo.adapter.shoppingcaradapter;


import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.SlidingMenuItem;
import com.pbph.yuguo.myview.adapter.recyclerview.ViewHolder;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.response.GetShoppingCartResponse;
import com.pbph.yuguo.util.MoneyHelper;

import java.util.List;

/**
 * Created by Administrator on 2018/8/2.
 */

public class ShoppingCarViewHolder extends ViewHolder<GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean> {

    SlidingMenuItem slidingMenuItem;


    CheckBox cbShoppingcar;

    private ImageView ivAdapterGoodsListImage;
    private View iv_order_fill_goods_image_state;
    private TextView tv_adapter_goods_list_state;

    private TextView tvAdapterGoodsListTitle;
    private TextView tvAdapterGoodsListDesc;

    private TextView tv_adapter_goods_list_xianzhi;

    private TextView tvAdapterGoodsListPrice;

    private ImageView tvJian;
    private TextView tvNum;
    private ImageView tvJia;


    TextView menuText;


    @Override
    protected int getLayout() {
        return R.layout.adapter_shoppingcar;
    }

    @Override
    protected void getView(View view) {

        slidingMenuItem = view.findViewById(R.id.slidingItemMenu);
        slidingMenuItem.setAdapter((ShoppingCarAdapter) adapter);

        cbShoppingcar = view.findViewById(R.id.cb_shoppingcar);
        cbShoppingcar.setOnClickListener(listener);

        ivAdapterGoodsListImage = (ImageView) view.findViewById(R.id.iv_adapter_goods_list_image);

        tv_adapter_goods_list_state = (TextView) view.findViewById(R.id.tv_adapter_goods_list_state);
        tv_adapter_goods_list_state.setVisibility(View.INVISIBLE);

        iv_order_fill_goods_image_state = view.findViewById(R.id.iv_order_fill_goods_image_state);
        iv_order_fill_goods_image_state.setVisibility(View.INVISIBLE);


        tvAdapterGoodsListTitle = (TextView) view.findViewById(R.id.tv_adapter_goods_list_title);
        tvAdapterGoodsListDesc = (TextView) view.findViewById(R.id.tv_adapter_goods_list_desc);

        tv_adapter_goods_list_xianzhi = (TextView) view.findViewById(R.id.tv_adapter_goods_list_xianzhi);

        tvAdapterGoodsListPrice = (TextView) view.findViewById(R.id.tv_adapter_goods_list_price);

        tvJian = view.findViewById(R.id.tv_jian);
        tvNum = (TextView) view.findViewById(R.id.tv_num);
        tvJia = view.findViewById(R.id.tv_jia);

        tvJian.setOnClickListener(listener);
        tvJia.setOnClickListener(listener);

        slidingMenuItem.setOnContentClickListener(onContentClickListener);

        menuText = view.findViewById(R.id.menuText);
        menuText.setOnClickListener(listener);
    }

    @Override
    protected void showView() {

        slidingMenuItem.close();

//    .error(R.mipmap.default_image)           //设置错误图片
//                .placeholder(R.mipmap.default_image)     //设置占位图片
        Glide.with(adapter.context).load(item.getGoodsPicUrl()).into(ivAdapterGoodsListImage);

        tvAdapterGoodsListTitle.setText(item.getGoodsName());
        tvAdapterGoodsListDesc.setText(item.getGoodsSpec());

        tvAdapterGoodsListPrice.setText(MoneyHelper.getInstance4Fen(item.getGoodsMemberPrice()).change2Yuan().getString());


        if (item.getPurchaseLimitationNum()==0){
            tv_adapter_goods_list_xianzhi.setVisibility(View.GONE);
        }else {
            tv_adapter_goods_list_xianzhi.setVisibility(View.VISIBLE);
        }
        tv_adapter_goods_list_xianzhi.setText("限购" + item.getPurchaseLimitationNum() + "件");

        cbShoppingcar.setChecked(adapter.choiceHelper.isChoiced(position));

        tvNum.setText(String.valueOf(item.getGoodsNum()));

        if (item.getGoodsNum() <= 1) {
            tvJian.setImageLevel(1);
        } else {
            tvJian.setImageLevel(0);
        }

        if (item.getGoodsNum() >= item.getStorageNum() || (item.getPurchaseLimitationNum() > 0 && item.getGoodsNum() >= item.getPurchaseLimitationNum())) {
            tvJia.setImageLevel(1);
        } else {
            tvJia.setImageLevel(0);
        }

        if (item.getAvaliableFlag() == 0) {
            cbShoppingcar.setEnabled(false);
            cbShoppingcar.setClickable(false);
            iv_order_fill_goods_image_state.setVisibility(View.VISIBLE);
            tv_adapter_goods_list_state.setVisibility(View.VISIBLE);

            tvJian.setEnabled(false);
            tvJia.setEnabled(false);

            tvJian.setClickable(false);
            tvJia.setClickable(false);

            tvAdapterGoodsListTitle.setTextColor(adapter.context.getResources().getColor(R.color.dark_gray));
            tvAdapterGoodsListDesc.setTextColor(adapter.context.getResources().getColor(R.color.dark_gray));
        } else {
            cbShoppingcar.setEnabled(true);
            cbShoppingcar.setClickable(true);
            iv_order_fill_goods_image_state.setVisibility(View.INVISIBLE);
            tv_adapter_goods_list_state.setVisibility(View.INVISIBLE);


            tvJian.setEnabled(true);
            tvJia.setEnabled(true);

            tvJian.setClickable(true);
            tvJia.setClickable(true);

            tvAdapterGoodsListTitle.setTextColor(adapter.context.getResources().getColor(R.color.black));
            tvAdapterGoodsListDesc.setTextColor(adapter.context.getResources().getColor(R.color.dark_gray));
        }
    }

    @Override
    protected void changeView(List<Object> payloads) {
        int type = (int) payloads.get(0);
        switch (type) {
            case 1:
                tvNum.setText(String.valueOf(item.getGoodsNum()));
                if (item.getGoodsNum() <= 1) {
                    tvJian.setImageLevel(1);
                } else {
                    tvJian.setImageLevel(0);
                }

                if (item.getPurchaseLimitationNum() > 0 && item.getGoodsNum() >= item.getPurchaseLimitationNum()) {
                    tvJia.setImageLevel(1);
                } else {
                    tvJia.setImageLevel(0);
                }
                break;
            case -1:
                cbShoppingcar.setChecked(adapter.choiceHelper.isChoiced(position));
                break;
        }

    }

    private final OnSPClickListener listener = new OnSPClickListener() {
        @Override
        public void onClickSucc(View v) {
            if (adapter.onItemViewClickListener == null) return;

            adapter.onItemViewClickListener.onItemViewClick(v.getId(), ShoppingCarViewHolder.this);
        }
    };

    private final SlidingMenuItem.OnContentClickListener onContentClickListener = () -> {
        if (adapter == null) return;

        if (adapter.onItemClickListener == null) return;
        adapter.onItemClickListener.onItemClick(adapter.recyclerView, ShoppingCarViewHolder.this);
    };


}
