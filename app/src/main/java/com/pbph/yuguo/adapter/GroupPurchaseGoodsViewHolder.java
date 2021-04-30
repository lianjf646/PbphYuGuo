package com.pbph.yuguo.adapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.adapter.abslistview.ViewHolder;
import com.pbph.yuguo.response.GroupListResponse;
import com.pbph.yuguo.util.LongTime2HMS;
import com.pbph.yuguo.util.MoneyHelper;

/**
 * Created by Administrator on 2018/8/2.
 */

public class GroupPurchaseGoodsViewHolder extends ViewHolder<GroupListResponse.DataBean.ListBean> implements TimeChangeDataAdapter.OnTimeChangeListener {


    private ImageView ivAdapterGroupGoodsImage;

    private TextView tvAdapterGroupGoodsTitle;


    private ProgressBar pbAdapterGroupGoodsProgress;

    private TextView tvAdapterGroupGoodsCount;
    private TextView tvAdapterGroupGoodsSurplusCount;

    private TextView tv_adapter_group_goods_time_hour;
    private TextView tv_adapter_group_goods_time_min;
    private TextView tv_adapter_group_goods_time_sec;

    private TextView tvAdapterGroupGoodsDiscountPrice;

    private TextView tvAdapterGroupGoodsPrice;

    private TextView tvAdapterGoodsListShoppingCart;


    @Override
    protected int getLayout() {
        return R.layout.adapter_gourp_purchase_goods_list;
    }

    @Override
    protected void getView(View view) {
        int height = adapter.bundle.getInt("img_height");


        ivAdapterGroupGoodsImage = (ImageView) view.findViewById(R.id.iv_adapter_group_goods_image);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivAdapterGroupGoodsImage.getLayoutParams();
        params.height = height;
        ivAdapterGroupGoodsImage.setLayoutParams(params);

        tvAdapterGroupGoodsTitle = (TextView) view.findViewById(R.id.tv_adapter_group_goods_title);

        pbAdapterGroupGoodsProgress = (ProgressBar) view.findViewById(R.id.pb_adapter_group_goods_progress);
        tvAdapterGroupGoodsCount = (TextView) view.findViewById(R.id.tv_adapter_group_goods_count);
        tvAdapterGroupGoodsSurplusCount = (TextView) view.findViewById(R.id.tv_adapter_group_goods_surplus_count);

        tv_adapter_group_goods_time_hour = (TextView) view.findViewById(R.id.tv_adapter_group_goods_time_hour);
        tv_adapter_group_goods_time_min = (TextView) view.findViewById(R.id.tv_adapter_group_goods_time_min);
        tv_adapter_group_goods_time_sec = (TextView) view.findViewById(R.id.tv_adapter_group_goods_time_sec);

        tvAdapterGroupGoodsDiscountPrice = (TextView) view.findViewById(R.id.tv_adapter_group_goods_discount_price);
        tvAdapterGroupGoodsPrice = (TextView) view.findViewById(R.id.tv_adapter_group_goods_price);
//        tvAdapterGroupGoodsPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        tvAdapterGoodsListShoppingCart = (TextView) view.findViewById(R.id.tv_adapter_goods_list_shopping_cart);
//        tvAdapterGoodsListShoppingCart.setOnClickListener(listener);
    }

    @Override
    protected void showView() {
        Glide.with(adapter.context).load(item.getGroupImg()).into(ivAdapterGroupGoodsImage);

        tvAdapterGroupGoodsTitle.setText(item.getPromotionName());
        pbAdapterGroupGoodsProgress.setMax(item.getGroupNumber());
        pbAdapterGroupGoodsProgress.setProgress(item.getAlreadyGroupNumber());


        if (item.getGroupNumber() > item.getAlreadyGroupNumber()) {
            tvAdapterGroupGoodsCount.setText("参团");
            tvAdapterGroupGoodsCount.append(String.valueOf(item.getAlreadyGroupNumber()));
            tvAdapterGroupGoodsCount.append("人");

            tvAdapterGroupGoodsSurplusCount.setText("剩余");
            tvAdapterGroupGoodsSurplusCount.append(String.valueOf(item.getOfferedCount()));
            tvAdapterGroupGoodsSurplusCount.append("人");
        } else {
            tvAdapterGroupGoodsCount.setText("本团已满");
            tvAdapterGroupGoodsCount.append(String.valueOf(item.getAlreadyGroupNumber()));
            tvAdapterGroupGoodsCount.append("人");

            tvAdapterGroupGoodsSurplusCount.setText("");
        }


        tvAdapterGroupGoodsDiscountPrice.setText(MoneyHelper.getInstance4Fen(item.getGroupPrice()).change2Yuan().getString());

        tvAdapterGroupGoodsPrice.setText("市场价￥");
        tvAdapterGroupGoodsPrice.append(MoneyHelper.getInstance4Fen(item.getGoodsGroupMarketPrice()).change2Yuan().getString());


        //  灰色
        if (item.type == 2) {
            tvAdapterGoodsListShoppingCart.setText("已参团"); //绿色
            tvAdapterGoodsListShoppingCart.setBackgroundResource(R.drawable.bg_button_main_color_corner);
        } else if (item.getEndDatetime() - item.getCurrentTime() - item.passTime <= 0) {
            tvAdapterGoodsListShoppingCart.setText("已结束"); //  灰色
            tvAdapterGoodsListShoppingCart.setBackgroundResource(R.drawable.bg_button_main_color_corner1);
        } else if (item.getGroupNumber() - item.getAlreadyGroupNumber() <= 0) {
            tvAdapterGoodsListShoppingCart.setText("已团满"); //  灰色
            tvAdapterGoodsListShoppingCart.setBackgroundResource(R.drawable.bg_button_main_color_corner1);
        } else {
            tvAdapterGoodsListShoppingCart.setText("立即参团"); //绿色
            tvAdapterGoodsListShoppingCart.setBackgroundResource(R.drawable.bg_button_main_color_corner);
        }
    }


    @Override
    public void onTimeChange(long passTime) {
        item.passTime = passTime;
        long stime = item.getEndDatetime() - item.getCurrentTime() - passTime;
        if (stime <= 0) {
            tv_adapter_group_goods_time_hour.setText("00");
            tv_adapter_group_goods_time_min.setText("00");
            tv_adapter_group_goods_time_sec.setText("00");

            if (item.type == 2) {
                tvAdapterGoodsListShoppingCart.setText("已参团"); //绿色
                tvAdapterGoodsListShoppingCart.setBackgroundResource(R.drawable.bg_button_main_color_corner);
            } else {
                tvAdapterGoodsListShoppingCart.setText("已结束");
                tvAdapterGoodsListShoppingCart.setBackgroundResource(R.drawable.bg_button_main_color_corner1);
            }
            return;
        }

        LongTime2HMS time = LongTime2HMS.longTime2HMS(stime);

        tv_adapter_group_goods_time_hour.setText(time.getH());
        tv_adapter_group_goods_time_min.setText(time.getM());
        tv_adapter_group_goods_time_sec.setText(time.getS());
    }


//    private final OnSPClickListener listener = new OnSPClickListener() {
//        @Override
//        public void onClickSucc(View v) {
//            if (adapter.onItemViewClickListener == null) return;
//
//            adapter.onItemViewClickListener.onItemViewClick(v.getId(), GroupPurchaseGoodsViewHolder.this);
//        }
//    };
}
