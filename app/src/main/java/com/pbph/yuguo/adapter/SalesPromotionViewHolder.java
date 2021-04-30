package com.pbph.yuguo.adapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.adapter.recyclerview.ViewHolder;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.response.GetPinkageActivityGoodsResponse;
import com.pbph.yuguo.util.MoneyHelper;

import java.util.List;

/**
 * Created by Administrator on 2018/8/2.
 */

public class SalesPromotionViewHolder extends ViewHolder<GetPinkageActivityGoodsResponse.DataBean.GoodsListBean> {

    public ImageView ivAdapterGoodsListImage;
    public TextView tvAdapterGoodsListTitle;
    public TextView tvAdapterGoodsListDesc;
    public TextView tvTag1;
    public TextView tvTag2;
    public TextView tvTag3;


    public TextView tvAdapterGoodsListPrice;
    public TextView tvAdapterGoodsListDiscountPrice;
    public ImageView ivAdapterGoodsListShoppingCart;


    @Override
    protected int getLayout() {
        return R.layout.item_search;
    }

    @Override
    protected void getView(View itemView) {


        ivAdapterGoodsListImage = (ImageView) itemView.findViewById(R.id.iv_adapter_goods_list_image);
        tvAdapterGoodsListTitle = (TextView) itemView.findViewById(R.id.tv_adapter_goods_list_title);
        tvAdapterGoodsListDesc = (TextView) itemView.findViewById(R.id.tv_adapter_goods_list_desc);
        tvAdapterGoodsListPrice = (TextView) itemView.findViewById(R.id.tv_adapter_goods_list_price);
        tvAdapterGoodsListDiscountPrice = (TextView) itemView.findViewById(R.id.tv_adapter_goods_list_discount_price);
//        tvAdapterGoodsListDiscountPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线


        this.tvTag1 = (TextView) itemView.findViewById(R.id.tv_tag1);
        this.tvTag2 = (TextView) itemView.findViewById(R.id.tv_tag2);
        this.tvTag3 = (TextView) itemView.findViewById(R.id.tv_tag3);

        ivAdapterGoodsListShoppingCart = (ImageView) itemView.findViewById(R.id.iv_adapter_goods_list_shopping_cart);
        ivAdapterGoodsListShoppingCart.setOnClickListener(listener);
    }

    @Override
    protected void showView() {


        tvTag1.setVisibility(View.INVISIBLE);
        tvTag2.setVisibility(View.INVISIBLE);
        tvTag3.setVisibility(View.INVISIBLE);

        List<String> tags = item.getLabelNameList();
        int i = 0;
        for (String str : tags) {
            if (i == 0) {
                tvTag1.setText(str);
                tvTag1.setVisibility(View.VISIBLE);
            } else if (i == 1) {
                tvTag2.setText(str);
                tvTag2.setVisibility(View.VISIBLE);
            } else if (i == 2) {
                tvTag3.setText(str);
                tvTag3.setVisibility(View.VISIBLE);
            }
            i++;
        }

        Glide.with(adapter.context).load(item.getGoodsPicUrl()).into(ivAdapterGoodsListImage);

        tvAdapterGoodsListTitle.setText(item.getGoodsName());
        tvAdapterGoodsListDesc.setText(item.getGoodsNameSub());

        tvAdapterGoodsListPrice.setText(MoneyHelper.getInstance4Fen(item.getMemberPrice()).change2Yuan().getString());

        tvAdapterGoodsListDiscountPrice.setText("￥");
        tvAdapterGoodsListDiscountPrice.append(MoneyHelper.getInstance4Fen(item.getGoodsSalePrice()).change2Yuan().getString());


//        if (item.hotFlag == 1)
//            tvAdapterGoodsListDiscountPrice.setVisibility(View.VISIBLE);
//        else
//        tvAdapterGoodsListDiscountPrice.setVisibility(View.INVISIBLE);

    }

    private final OnSPClickListener listener = new OnSPClickListener() {
        @Override
        public void onClickSucc(View v) {
            if (adapter.onItemViewClickListener == null) return;
            adapter.onItemViewClickListener.onItemViewClick(v.getId(), SalesPromotionViewHolder.this);
        }
    };
}
