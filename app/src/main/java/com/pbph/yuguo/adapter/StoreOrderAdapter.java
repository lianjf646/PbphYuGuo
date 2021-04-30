package com.pbph.yuguo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.interfaces.OnRecItemClickListener;
import com.pbph.yuguo.response.StoreOrderListResponse;
import com.pbph.yuguo.util.MoneyHelper;

import java.util.List;

/**
 * 门店订单adapter
 * Created by zyp on 2018/8/10 0010.
 */

public class StoreOrderAdapter extends RecyclerView.Adapter<StoreOrderAdapter.OrderViewHolder> {

    private BaseActivity activity;
    private List<StoreOrderListResponse.DataBean.OrderListBean> mOrderList;
    private OnRecItemClickListener mListener;

    public StoreOrderAdapter(BaseActivity activity, List<StoreOrderListResponse.DataBean.OrderListBean> orderList) {
        this.activity = activity;
        this.mOrderList = orderList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_store_order_list, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        StoreOrderListResponse.DataBean.OrderListBean storeOrder = getItem(position);
        holder.tvStoreName.setText(storeOrder.getStoreName());
        holder.tvOrderTime.setText(String.format("订单时间：%s", storeOrder.getCreateTime()));
        List<StoreOrderListResponse.DataBean.OrderListBean.OrderGoodsListBean> goodsList = storeOrder.getOrderGoodsList();
        if (goodsList != null && goodsList.size() > 0) {
            StoreOrderListResponse.DataBean.OrderListBean.OrderGoodsListBean goodsDetail = goodsList.get(0);
            holder.tvGoodsName.setText(goodsDetail.getGoodsName());
            //售卖方式  0:称重  1:计件
            int salesMethod = goodsDetail.getSalesMethod();
            switch (salesMethod) {
                case 0:
                    holder.tvGoodsWeight.setText(String.format("%s公斤", (float) goodsDetail.getGoodsNumWeight() / 1000));
                    break;
                case 1:
                    holder.tvGoodsWeight.setText(String.format("%s件", goodsDetail.getGoodsNumWeight()));
                    break;
            }
        }

        holder.tvGoodsNum.setText(String.format("共%s种商品", storeOrder.getGoodsTotalNum()));
        holder.tvPayMoney.setText(String.format("￥%s", MoneyHelper.getInstance4Fen(storeOrder.getOrderDealPrice()).change2Yuan().getString()));

        setAllOrderList(holder, storeOrder.getOrderStatus());

        holder.view.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(v, position);
            }
        });
    }

    //全部的订单列表
    private void setAllOrderList(OrderViewHolder holder, int orderStatus) {
        switch (orderStatus) {
            case 1:
                //待付款
                holder.tvOrderStatus.setText("待付款");
                holder.tvFunction.setText("去付款");
                break;
            case 4:
                //已付款
                holder.tvOrderStatus.setText("已付款");
                holder.tvFunction.setText("查看详情");
                break;
            case 5:
                //已完成
                holder.tvOrderStatus.setText("已完成");
                holder.tvFunction.setText("查看详情");
                break;
            case 6:
                //已取消
                holder.tvOrderStatus.setText("已取消");
                holder.tvFunction.setText("查看详情");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    private StoreOrderListResponse.DataBean.OrderListBean getItem(int position) {
        return mOrderList.get(position);
    }


    class OrderViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView tvStoreName;
        TextView tvOrderTime;
        TextView tvOrderStatus;
        TextView tvGoodsName;
        TextView tvGoodsWeight;
        TextView tvGoodsNum;
        TextView tvPayMoney;
        TextView tvFunction;

        private OrderViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvStoreName = itemView.findViewById(R.id.tv_store_name);
            tvOrderTime = itemView.findViewById(R.id.tv_order_time);
            tvOrderStatus = itemView.findViewById(R.id.tv_order_status);
            tvGoodsName = itemView.findViewById(R.id.tv_goods_name);
            tvGoodsWeight = itemView.findViewById(R.id.tv_goods_weight);
            tvGoodsNum = itemView.findViewById(R.id.tv_goods_num);
            tvPayMoney = itemView.findViewById(R.id.tv_pay_money);
            tvFunction = itemView.findViewById(R.id.tv_function);
        }
    }

    public void setOnRecItemClickListener(OnRecItemClickListener listener) {
        this.mListener = listener;
    }

    public void refresh(List<StoreOrderListResponse.DataBean.OrderListBean> orderList) {
        //下拉刷新
        mOrderList.clear();
        mOrderList.addAll(orderList);
        notifyDataSetChanged();
    }

    public void add(List<StoreOrderListResponse.DataBean.OrderListBean> orderList) {
        //上拉加载
        int position = this.mOrderList.size();
        mOrderList.addAll(position, orderList);
        notifyItemInserted(position);
    }
}
