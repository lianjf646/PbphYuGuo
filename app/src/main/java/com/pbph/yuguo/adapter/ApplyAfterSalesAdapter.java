package com.pbph.yuguo.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.interfaces.OnRecItemClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetSysConfigRequest;
import com.pbph.yuguo.response.GetSysConfigResponse;
import com.pbph.yuguo.response.OrderListResponse;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.MoneyHelper;
import com.pbph.yuguo.util.ToastDialog;

import java.util.List;

/**
 * Created by zyp on 2018/9/11 0011.
 * class note:
 */

public class ApplyAfterSalesAdapter extends RecyclerView.Adapter<ApplyAfterSalesAdapter
        .OrderViewHolder> {
    private Context mContext;
    private List<OrderListResponse.DataBean.OrderListBean> orderList;
    private int mOrderStatus;
    private OnRecItemClickListener mListener;

    public ApplyAfterSalesAdapter(Context context,
                                  int orderStatus,
                                  List<OrderListResponse.DataBean.OrderListBean> orderList) {
        this.mContext = context;
        this.mOrderStatus = orderStatus;
        this.orderList = orderList;
    }

    @Override
    public ApplyAfterSalesAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order_list, parent, false);
        return new ApplyAfterSalesAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ApplyAfterSalesAdapter.OrderViewHolder holder, int position) {
        OrderListResponse.DataBean.OrderListBean order = orderList.get(position);
        setOrderList(holder, mOrderStatus, order);

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

    private void setOrderList(ApplyAfterSalesAdapter.OrderViewHolder holder, int orderStatus,
                              OrderListResponse.DataBean.OrderListBean order) {
        holder.tvOrderTime.setText(String.format("???????????????%s", order.getCreateTime()));

        List<OrderListResponse.DataBean.OrderListBean.OrderGoodsInfoListBean> goodsList = order.getOrderGoodsInfoList();
        int totalSizeGoods = 0;
        if (goodsList != null) {
            if (goodsList.size() == 1) {
                holder.rlSingleGoods.setVisibility(View.VISIBLE);
                holder.layoutMultiGoods.setVisibility(View.GONE);
                OrderListResponse.DataBean.OrderListBean.OrderGoodsInfoListBean goods = goodsList.get(0);
                GlideUtil.displayImage(mContext, goods.getGoodsInfoPicUrl(), holder.ivGoodsImage);
                holder.tvGoodsName.setText(goods.getGoodsInfoName());
                holder.tvGoodsIntro.setText(goods.getGoodsInfoSpecValue());
                totalSizeGoods += goodsList.get(0).getGoodsInfoNum();
            } else {
                holder.rlSingleGoods.setVisibility(View.GONE);
                holder.layoutMultiGoods.setVisibility(View.VISIBLE);
                holder.tvMore.setVisibility(View.GONE);
                holder.llGoodsList.removeAllViews();
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup
                        .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.width = mContext.getResources().getDimensionPixelOffset(R.dimen
                        .dp_80dp);
                layoutParams.height = mContext.getResources().getDimensionPixelOffset(R.dimen
                        .dp_80dp);
                layoutParams.leftMargin = mContext.getResources().getDimensionPixelOffset(R.dimen
                        .dp_20);
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

            holder.tvGoodsNum.setText(String.format("???%s?????????", totalSizeGoods));
            holder.tvRealPay.setText(String.format("???%s", MoneyHelper.getInstance4Fen(order.getOrderDealPrice()).change2Yuan().getString()));
        }
        //????????????
        holder.tvOrderStatus.setText("?????????");
        holder.tvFunction.setText("????????????");
        holder.tvFunction.setOnClickListener(v -> {
            HttpAction.getInstance().getSysConfig(new GetSysConfigRequest()).subscribe
                    (new BaseObserver<>(mContext, response -> {
                        int code = response.getCode();
                        if (code == 200) {
                            GetSysConfigResponse.DataBean data = response.getData();
                            String phone = data.getSysConfig().getServicePhone();
                            ToastDialog.show(mContext, "???????????????", "???????????????" + phone, () -> {
                                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));//????????????????????????????????????????????????
                                mContext.startActivity(dialIntent);
                            });
                        }
                    }));

        });
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

    public void refresh(List<OrderListResponse.DataBean.OrderListBean> orderList) {
        //????????????
        this.orderList.clear();
        this.orderList.addAll(orderList);
        notifyDataSetChanged();
    }

    public void add(List<OrderListResponse.DataBean.OrderListBean> orderList) {
        //????????????
        int position = this.orderList.size();
        this.orderList.addAll(position, orderList);
        notifyItemInserted(position);
    }
}
