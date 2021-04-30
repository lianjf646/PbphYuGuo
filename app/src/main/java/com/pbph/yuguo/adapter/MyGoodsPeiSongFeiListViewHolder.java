package com.pbph.yuguo.adapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.adapter.abslistview.ViewHolder;
import com.pbph.yuguo.response.GoodsInfoBean;
import com.pbph.yuguo.util.MoneyHelper;

/**
 * Created by Administrator on 2018/8/2.
 */

public class MyGoodsPeiSongFeiListViewHolder extends ViewHolder<GoodsInfoBean> {


    private ImageView ivAdapterGoodsListImage;

    private TextView tv_adapter_goods_list_title_pre;
    private TextView tvAdapterGoodsListTitle;
    private TextView tvAdapterGoodsListDesc;

    private TextView tvAdapterGoodsListPrice;
    private TextView tvAdapterGoodsListDiscountPrice;


    private TextView tv_adapter_goods_list_baozhuangfei;

    private TextView tv_adapter_goods_list_num;


    @Override
    protected int getLayout() {
        return R.layout.adapter_my_goods_peisongfei_list;
    }

    @Override
    protected void getView(View itemView) {
        tv_adapter_goods_list_title_pre = itemView.findViewById(R.id.tv_adapter_goods_list_title_pre);

        ivAdapterGoodsListImage = (ImageView) itemView.findViewById(R.id.iv_adapter_goods_list_image);
        tvAdapterGoodsListTitle = (TextView) itemView.findViewById(R.id.tv_adapter_goods_list_title);
        tvAdapterGoodsListDesc = (TextView) itemView.findViewById(R.id.tv_adapter_goods_list_desc);
        tvAdapterGoodsListPrice = (TextView) itemView.findViewById(R.id.tv_adapter_goods_list_price);
        tvAdapterGoodsListDiscountPrice = (TextView) itemView.findViewById(R.id.tv_adapter_goods_list_discount_price);
//        tvAdapterGoodsListDiscountPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        tvAdapterGoodsListDiscountPrice.setVisibility(View.GONE);

        tv_adapter_goods_list_num = itemView.findViewById(R.id.tv_adapter_goods_list_num);


        tv_adapter_goods_list_baozhuangfei = (TextView) itemView.findViewById(R.id.tv_adapter_goods_list_baozhuangfei);

    }

    @Override
    protected void showView() {

        if (item.gifts) {
//            tv_adapter_goods_list_title_pre.setVisibility(View.VISIBLE);
            tv_adapter_goods_list_title_pre.setText("【赠品】");
        } else {
//            tv_adapter_goods_list_title_pre.setVisibility(View.GONE);
            tv_adapter_goods_list_title_pre.setText("");
        }

        Glide.with(adapter.context).load(item.getGoodsInfoPicUrl()).into(ivAdapterGoodsListImage);

        tvAdapterGoodsListTitle.setText(item.getGoodsInfoName());
        tvAdapterGoodsListDesc.setText(item.getGoodsInfoNameSub());

        tvAdapterGoodsListPrice.setText(MoneyHelper.getInstance4Fen(item.getGoodsInfoMemberPrice()).change2Yuan().getString());

//        tvAdapterGoodsListDiscountPrice.setText("￥");
//        tvAdapterGoodsListDiscountPrice.append(MoneyHelper.getInstance4Fen(item.getGoodsMarketPrice()).change2Yuan().getString());

        tv_adapter_goods_list_num.setText("×");
        tv_adapter_goods_list_num.append(String.valueOf(item.getGoodsInfoNum()));

        MoneyHelper moneyHelper = MoneyHelper.getInstance4Fen(item.getGoodsInfoPackCharges()).change2Yuan();
        if (moneyHelper.getDouble() == 0) {
            tv_adapter_goods_list_baozhuangfei.setText("免包装费");
        } else {
            tv_adapter_goods_list_baozhuangfei.setText("￥");
            tv_adapter_goods_list_baozhuangfei.append(moneyHelper.getString());
        }
    }

}
