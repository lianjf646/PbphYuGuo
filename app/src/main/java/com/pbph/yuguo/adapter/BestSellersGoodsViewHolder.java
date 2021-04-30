package com.pbph.yuguo.adapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.adapter.abslistview.ViewHolder;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.response.GetHotSaleGoodsListResponse;
import com.pbph.yuguo.util.MoneyHelper;

/**
 * Created by Administrator on 2018/8/2.
 */

public class BestSellersGoodsViewHolder extends ViewHolder<GetHotSaleGoodsListResponse.DataBean.GoodsListBean> {


    private ImageView ivAdapterGoodsListImage;

    private TextView tvAdapterGoodsListTitle;
    private TextView tvAdapterGoodsListDesc;

    private TextView tvAdapterGoodsListPrice;
    private TextView tvAdapterGoodsListDiscountPrice;

    private ImageView ivAdapterGoodsListShoppingCart;

    private TextView tvTag1,tvTag2,tvTag3;


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

        ivAdapterGoodsListShoppingCart = (ImageView) itemView.findViewById(R.id.iv_adapter_goods_list_shopping_cart);
        tvTag1 = itemView.findViewById(R.id.tv_tag1);
        tvTag2 = itemView.findViewById(R.id.tv_tag2);
        tvTag3 = itemView.findViewById(R.id.tv_tag3);
        ivAdapterGoodsListShoppingCart.setOnClickListener(listener);
    }

    @Override
    protected void showView() {
        Glide.with(adapter.context).load(item.getGoodsPicUrl()).into(ivAdapterGoodsListImage);

        tvAdapterGoodsListTitle.setText(item.getGoodsName());
        tvAdapterGoodsListDesc.setText(item.getGoodsNameSub());

        tvAdapterGoodsListPrice.setText(MoneyHelper.getInstance4Fen(item.getMemberPrice()).change2Yuan().getString());

        tvAdapterGoodsListDiscountPrice.setText("￥");
        tvAdapterGoodsListDiscountPrice.append(MoneyHelper.getInstance4Fen(item.getGoodsSalePrice()).change2Yuan().getString());

        switch (item.getLabelNameList().size()) {
            case 3:
                tvTag1.setText(item.getLabelNameList().get(0));
                tvTag2.setText(item.getLabelNameList().get(1));
                tvTag3.setText(item.getLabelNameList().get(2));
                tvTag1.setVisibility(View.VISIBLE);
                tvTag2.setVisibility(View.VISIBLE);
                tvTag3.setVisibility(View.VISIBLE);
                break;
            case 2:
                tvTag1.setText(item.getLabelNameList().get(0));
                tvTag2.setText(item.getLabelNameList().get(1));
                tvTag1.setVisibility(View.VISIBLE);
                tvTag2.setVisibility(View.VISIBLE);
                tvTag3.setVisibility(View.INVISIBLE);
                break;
            case 1:
                tvTag1.setText(item.getLabelNameList().get(0));
                tvTag1.setVisibility(View.VISIBLE);
                tvTag2.setVisibility(View.INVISIBLE);
                tvTag3.setVisibility(View.INVISIBLE);
                break;
            default:
                tvTag1.setVisibility(View.INVISIBLE);
                tvTag2.setVisibility(View.INVISIBLE);
                tvTag3.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private final OnSPClickListener listener = new OnSPClickListener() {
        @Override
        public void onClickSucc(View v) {
            if (adapter.onItemViewClickListener == null) return;
            adapter.onItemViewClickListener.onItemViewClick(v.getId(), BestSellersGoodsViewHolder.this);
        }
    };
}
