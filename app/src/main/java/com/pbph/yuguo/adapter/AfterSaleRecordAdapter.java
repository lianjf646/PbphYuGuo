package com.pbph.yuguo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.interfaces.OnRecItemClickListener;
import com.pbph.yuguo.response.BackOrderListResponse;
import com.pbph.yuguo.response.GoodsInfoBean;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.MoneyHelper;

import java.util.List;

/**
 * Created by zyp on 2018/9/11 0011.
 * class note:
 */

public class AfterSaleRecordAdapter extends RecyclerView.Adapter<AfterSaleRecordAdapter.OrderViewHolder> {
    private Context mContext;
    private List<BackOrderListResponse.DataBean.OrderListBean> orderList;
    private OnRecItemClickListener mListener;

    public AfterSaleRecordAdapter(Context context,
                                  List<BackOrderListResponse.DataBean.OrderListBean> orderList) {
        this.mContext = context;
        this.orderList = orderList;
    }

    @Override
    public AfterSaleRecordAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_list, parent, false);
        return new AfterSaleRecordAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AfterSaleRecordAdapter.OrderViewHolder holder, int position) {
        BackOrderListResponse.DataBean.OrderListBean order = orderList.get(position);
        setOrderList(holder, order);

        holder.view.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(v, position);
            }
        });
        holder.llGoodsList.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(v, position);
            }
        });
        holder.rlSingleGoods.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(v, position);
            }
        });
    }

    private void setOrderList(AfterSaleRecordAdapter.OrderViewHolder holder, BackOrderListResponse.DataBean.OrderListBean order) {
        holder.tvOrderTime.setText(String.format("订单时间：%s", order.getCreateTime()));
        int totalSizeGoods = 0;
        List<GoodsInfoBean> goodsList = order.getOrderGoodsInfoList();
        if (goodsList != null) {
            if (goodsList.size() == 1) {
                holder.rlSingleGoods.setVisibility(View.VISIBLE);
                holder.layoutMultiGoods.setVisibility(View.GONE);
                GoodsInfoBean goods = goodsList.get(0);
                GlideUtil.displayImage(mContext, goods.getGoodsInfoPicUrl(), holder.ivGoodsImage);
                holder.tvGoodsName.setText(goods.getGoodsInfoName());
                holder.tvGoodsIntro.setText(goods.getGoodsInfoSpecValue());
                totalSizeGoods += goodsList.get(0).getGoodsInfoNum();
            } else {
                holder.rlSingleGoods.setVisibility(View.GONE);
                holder.layoutMultiGoods.setVisibility(View.VISIBLE);
                holder.tvMore.setVisibility(View.GONE);
                holder.llGoodsList.removeAllViews();
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.width = mContext.getResources().getDimensionPixelOffset(R.dimen.dp_80dp);
                layoutParams.height = mContext.getResources().getDimensionPixelOffset(R.dimen.dp_80dp);
                layoutParams.leftMargin = mContext.getResources().getDimensionPixelOffset(R.dimen.dp_20);
                for (int i = 0; i < goodsList.size(); i++) {
                    String goodsPicUrl = goodsList.get(i).getGoodsInfoPicUrl();
                    RelativeLayout relativeLayout = new RelativeLayout(mContext);
                    ImageView imageViewZP = new ImageView(mContext);
                    ImageView imageView = new ImageView(mContext);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageViewZP.setBackgroundResource(R.drawable.complimentary);
                    relativeLayout.setLayoutParams(layoutParams);
                    GlideUtil.displayImage(mContext, goodsPicUrl, imageView);
                    relativeLayout.addView(imageView);
                    if (goodsList.get(i).getActiveType() == 2) {
                        relativeLayout.addView(imageViewZP);
                    }
                    holder.llGoodsList.addView(relativeLayout);
                    totalSizeGoods += goodsList.get(i).getGoodsInfoNum();
                }
            }

            holder.tvGoodsNum.setText(String.format("共%s件商品", totalSizeGoods));

            holder.tvRealPay.setText(String.format("￥%s", MoneyHelper.getInstance4Fen(order.getOrderDealPrice()).change2Yuan().getString()));
        }
        holder.tvFunction.setText("查看详情");
        int orderStatus = order.getBackOrderStatus();
        //（1退单申请，2重新发货，3退单结束）
        switch (orderStatus) {
            case 1:
                //申请售后
                holder.tvOrderStatus.setText("退单申请");
                break;
            case 2:
                //售后记录
                holder.tvOrderStatus.setText("重新发货");
                break;
            case 3:
                //售后记录
                holder.tvOrderStatus.setText("退单结束");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return orderList == null ? 0 : orderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        View view;
        RelativeLayout rlTopView;
        TextView tvOrderTime;
        TextView tvOrderStatus;
        ImageView ivGoodsImage;
        TextView tvGoodsName;
        TextView tvGoodsIntro;
        RelativeLayout rlMoneyView;
        RelativeLayout rlSingleGoods;
        TextView tvGoodsNum;
        TextView tvRealPay;
        TextView tvFunction;

        View layoutMultiGoods;
        LinearLayout llGoodsList;
        TextView tvMore;

        private OrderViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            rlTopView = itemView.findViewById(R.id.rl_top_view);
            tvOrderTime = itemView.findViewById(R.id.tv_order_time);
            tvOrderStatus = itemView.findViewById(R.id.tv_order_status);
            ivGoodsImage = itemView.findViewById(R.id.iv_goods_image);
            tvGoodsName = itemView.findViewById(R.id.tv_goods_name);
            tvGoodsIntro = itemView.findViewById(R.id.tv_goods_intro);
            rlMoneyView = itemView.findViewById(R.id.rl_money_view);
            rlSingleGoods = itemView.findViewById(R.id.rl_single_goods);
            tvGoodsNum = itemView.findViewById(R.id.tv_goods_num);
            tvRealPay = itemView.findViewById(R.id.tv_real_pay);
            tvFunction = itemView.findViewById(R.id.tv_function);

            layoutMultiGoods = itemView.findViewById(R.id.layout_multi_goods);
            llGoodsList = layoutMultiGoods.findViewById(R.id.ll_goods_list);
            tvMore = layoutMultiGoods.findViewById(R.id.tv_more);
        }
    }

    public void setOnRecItemClickListener(OnRecItemClickListener listener) {
        this.mListener = listener;
    }

    public void refresh(List<BackOrderListResponse.DataBean.OrderListBean> orderList) {
        //下拉刷新
        this.orderList.clear();
        this.orderList.addAll(orderList);
        notifyDataSetChanged();
    }

    public void add(List<BackOrderListResponse.DataBean.OrderListBean> orderList) {
        //上拉加载
        int position = this.orderList.size();
        this.orderList.addAll(position, orderList);
        notifyItemInserted(position);
    }
}
