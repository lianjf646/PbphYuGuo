package com.pbph.yuguo.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.activity.ShoppingCarActivity;
import com.pbph.yuguo.application.UserInfo;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseFragment;
import com.pbph.yuguo.dialog.BothBtnCenterPopWin;
import com.pbph.yuguo.fragment.OrderStatusFragment;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.interfaces.OnPopWinBothBtnClickListener;
import com.pbph.yuguo.interfaces.OnRecItemClickListener;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetConfirmReceiptRequest;
import com.pbph.yuguo.request.OrderAgainRequest;
import com.pbph.yuguo.response.OrderListResponse;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.MoneyHelper;
import com.sobot.chat.utils.ToastUtil;

import java.util.List;

/**
 * 订单adapter
 * Created by zyp on 2018/8/10 0010.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private BaseFragment fragment;
    private List<OrderListResponse.DataBean.OrderListBean> mOrderList;
    private int mOrderStatus;
    private OnRecItemClickListener mListener;

    public OrderAdapter(BaseFragment fragment, int orderStatus, List<OrderListResponse.DataBean.OrderListBean> orderList) {
        this.fragment = fragment;
        this.mOrderList = orderList;
        this.mOrderStatus = orderStatus;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragment.mContext).inflate(R.layout.item_order_list, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        OrderListResponse.DataBean.OrderListBean order = mOrderList.get(position);

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
        holder.tvSingleAgain.setOnClickListener(v -> orderAgain(order));
    }

    private void orderAgain(OrderListResponse.DataBean.OrderListBean order) {
        int orderType = order.getOrderType();
        if (orderType == 2) {
            //团购订单  提示不可再来一单
            ToastUtil.showToast(fragment.mContext, "团购订单不支持再来一单哦~");
            return;
        }
        WaitUI.Show(fragment.getContext());
        OrderAgainRequest request = new OrderAgainRequest(YuGuoApplication.userInfo.getCustomerId(), order.getOrderId());
        HttpAction.getInstance().orderAgain(request).subscribe(new BaseObserver<>(fragment.mContext, response -> {
            WaitUI.Cancel();
            int code = response.getCode();
            String msg = response.getMsg();
            if (200 == code) {
                fragment.startActivity(new Intent(fragment.getContext(), ShoppingCarActivity.class));
            } else {
                ToastUtil.showToast(fragment.getContext(), msg);
            }
        }));
    }

    private void setOrderList(OrderViewHolder holder, int orderStatus, OrderListResponse.DataBean.OrderListBean order) {
        holder.tvOrderTime.setText(String.format("订单时间：%s", order.getCreateTime()));

        List<OrderListResponse.DataBean.OrderListBean.OrderGoodsInfoListBean> goodsList = order.getOrderGoodsInfoList();
        int totalSizeGoods = 0;

        if (goodsList != null) {
            if (goodsList.size() == 1) {
                holder.rlSingleGoods.setVisibility(View.VISIBLE);
                holder.layoutMultiGoods.setVisibility(View.GONE);
                OrderListResponse.DataBean.OrderListBean.OrderGoodsInfoListBean goods = goodsList.get(0);
                GlideUtil.displayImage(fragment.mContext, goods.getGoodsInfoPicUrl(), holder.ivGoodsImage);
                holder.tvGoodsName.setText(goods.getGoodsInfoName());
                holder.tvGoodsIntro.setText(goods.getGoodsInfoSpecValue());
                totalSizeGoods += goodsList.get(0).getGoodsInfoNum();
            } else {
                holder.rlSingleGoods.setVisibility(View.GONE);
                holder.layoutMultiGoods.setVisibility(View.VISIBLE);
                holder.tvMore.setVisibility(View.GONE);
                holder.llGoodsList.removeAllViews();

                int measure = fragment.getResources().getDimensionPixelOffset(R.dimen.dp_80dp);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(measure, measure);

                for (int i = 0; i < goodsList.size(); i++) {
                    String goodsPicUrl = goodsList.get(i).getGoodsInfoPicUrl();
                    RelativeLayout relativeLayout = new RelativeLayout(fragment.mContext);
                    ImageView imageViewZP = new ImageView(fragment.mContext);
                    ImageView imageView = new ImageView(fragment.mContext);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageViewZP.setBackgroundResource(R.drawable.complimentary);
                    relativeLayout.setPadding(fragment.mContext.getResources().getDimensionPixelOffset(R.dimen.dp_20), 0, 0, 0);
                    GlideUtil.displayImage(fragment.mContext, goodsPicUrl, imageView);
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

        switch (orderStatus) {
            case 0:
                //全部订单
                setAllOrderList(holder, order.getOrderStatus(), order);
                break;
            case 1:
                //待付款
                holder.tvOrderStatus.setText("待付款");
                holder.tvFunction.setText("去付款");
                break;
            case 2:
                //待发货
                holder.tvOrderStatus.setText("待发货");
                holder.tvFunction.setText("查看详情");
                break;
            case 3:
                //待收货
                holder.tvOrderStatus.setText("待收货");
                holder.tvFunction.setText("确认收货");
                if (order.getOrderType() == 1) {
                    holder.tvSingleAgain.setVisibility(View.VISIBLE);
                } else {
                    holder.tvSingleAgain.setVisibility(View.GONE);
                }
                holder.tvFunction.setOnClickListener(v -> {
                    BothBtnCenterPopWin popWin = new BothBtnCenterPopWin(fragment.mContext, v, "确认收货", "确认是本人收货?", "取消", "确定", true);
                    popWin.setOnPopWinBothBtnClickListener(new OnPopWinBothBtnClickListener() {
                        @Override
                        public void onFirstBtnClick() {
                            popWin.dismiss();
                        }

                        @Override
                        public void onSecondBtnClick() {
                            popWin.dismiss();
                            confirmReceipt(order.getOrderId());
                        }
                    });
                });
                break;
        }
    }

    //全部的订单列表
    private void setAllOrderList(OrderViewHolder holder, int orderStatus, OrderListResponse.DataBean.OrderListBean order) {
        switch (orderStatus) {
            case 1:
                //待付款
                holder.tvOrderStatus.setText("待付款");
                holder.tvFunction.setText("去付款");
                break;
            case 2:
                //待发货
                holder.tvOrderStatus.setText("待发货");
                holder.tvFunction.setText("查看详情");
                break;
            case 3:
                //待收货
                holder.tvOrderStatus.setText("待收货");
                holder.tvFunction.setText("确认收货");
                if (order.getOrderType() == 1) {
                    holder.tvSingleAgain.setVisibility(View.VISIBLE);
                } else {
                    holder.tvSingleAgain.setVisibility(View.GONE);
                }
                holder.tvFunction.setOnClickListener(v -> {
                    BothBtnCenterPopWin popWin = new BothBtnCenterPopWin(fragment.mContext, v, "确认收货", "确认是本人收货?", "取消", "确定", true);
                    popWin.setOnPopWinBothBtnClickListener(new OnPopWinBothBtnClickListener() {
                        @Override
                        public void onFirstBtnClick() {
                            popWin.dismiss();
                        }

                        @Override
                        public void onSecondBtnClick() {
                            popWin.dismiss();
                            confirmReceipt(order.getOrderId());
                        }
                    });
                });
                break;
            case 4:
                //已签收
                holder.tvOrderStatus.setText("已签收");
                holder.tvFunction.setText("查看详情");
                if (order.getOrderType() == 1) {
                    holder.tvSingleAgain.setVisibility(View.VISIBLE);
                } else {
                    holder.tvSingleAgain.setVisibility(View.GONE);
                }
                break;
            case 5:
                //已完成
                holder.tvOrderStatus.setText("已完成");
                holder.tvFunction.setText("查看详情");
                if (order.getOrderType() == 1) {
                    holder.tvSingleAgain.setVisibility(View.VISIBLE);
                } else {
                    holder.tvSingleAgain.setVisibility(View.GONE);
                }
                break;
            case 6:
                //已取消
                holder.tvOrderStatus.setText("已取消");
                holder.tvFunction.setText("查看详情");
                if (order.getOrderType() == 1) {
                    holder.tvSingleAgain.setVisibility(View.VISIBLE);
                } else {
                    holder.tvSingleAgain.setVisibility(View.GONE);
                }
                break;
        }
    }

    //确认收货
    private void confirmReceipt(int orderId) {
        UserInfo userInfo = YuGuoApplication.userInfo;
        if (userInfo == null || userInfo.getCustomerId() == -1) {
            ToastUtil.showToast(fragment.mContext, "登陆后才能确认收货");
            return;
        }
        WaitUI.Show(fragment.mContext);
        GetConfirmReceiptRequest request = new GetConfirmReceiptRequest(orderId, userInfo.getCustomerId());
        HttpAction.getInstance().confirmReceipt(request).subscribe(new BaseObserver<>(fragment.mContext, response -> {
            WaitUI.Cancel();
            int code = response.getCode();
            String msg = response.getMsg();
            if (200 == code) {
                ToastUtil.showToast(fragment.mContext, "收货成功");
                if (fragment instanceof OrderStatusFragment) {
                    ((OrderStatusFragment) fragment).initData();
                }
            } else {
                ToastUtil.showToast(fragment.mContext, TextUtils.isEmpty(msg) ? "收货失败" : msg);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return mOrderList == null ? 0 : mOrderList.size();
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
        TextView tvSingleAgain;
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
            tvSingleAgain = itemView.findViewById(R.id.tv_single_again);
            layoutMultiGoods = itemView.findViewById(R.id.layout_multi_goods);
            llGoodsList = layoutMultiGoods.findViewById(R.id.ll_goods_list);
            tvMore = layoutMultiGoods.findViewById(R.id.tv_more);
        }
    }

    public void setOnRecItemClickListener(OnRecItemClickListener listener) {
        this.mListener = listener;
    }

    public void refresh(List<OrderListResponse.DataBean.OrderListBean> orderList) {
        //下拉刷新
        mOrderList.clear();
        mOrderList.addAll(orderList);
        notifyDataSetChanged();
    }

    public void add(List<OrderListResponse.DataBean.OrderListBean> orderList) {
        //上拉加载
        int position = this.mOrderList.size();
        mOrderList.addAll(position, orderList);
        notifyItemInserted(position);
    }
}
