package com.pbph.yuguo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.interfaces.OnRecItemClickListener;
import com.pbph.yuguo.response.WaitEvaluateListResponse;
import com.pbph.yuguo.util.GlideUtil;

import java.util.List;

/**
 * Created by zyp on 2018/9/6 0006.
 * class note:待评价商品列表adapter
 */

public class WaitEvaluateGoodsAdapter extends RecyclerView.Adapter<WaitEvaluateGoodsAdapter.WaitEvaluateGoodsViewHolder> {

    private Context context;
    private List<WaitEvaluateListResponse.DataBean.WaitEvaluateListBean> mWaitEvaluateList;
    private OnRecItemClickListener mListener;

    public WaitEvaluateGoodsAdapter(Context context, List<WaitEvaluateListResponse.DataBean.WaitEvaluateListBean> waitEvaluateList) {
        this.context = context;
        this.mWaitEvaluateList = waitEvaluateList;
    }

    @Override
    public WaitEvaluateGoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wait_evaluate_list, parent, false);
        return new WaitEvaluateGoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WaitEvaluateGoodsViewHolder holder, int position) {
        WaitEvaluateListResponse.DataBean.WaitEvaluateListBean waitEvaluate = getItem(position);
        int evaluateStatus = waitEvaluate.getEvaluateFlag();
        int shareStatus = waitEvaluate.getShareFlag();

        if (evaluateStatus == 0) {
            //待评价
            holder.tvFunction.setText("评价");
        } else {
            if (shareStatus == 0) {
                //待晒单
                holder.tvFunction.setText("晒单");
            }
        }

        GlideUtil.displayImage(context, waitEvaluate.getGoodsImg(), holder.ivGoodsImage);
        holder.tvGoodsName.setText(waitEvaluate.getGoodsName());
        holder.tvGoodsIntro.setText(waitEvaluate.getGoodsSpec());

        holder.view.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWaitEvaluateList == null ? 0 : mWaitEvaluateList.size();
    }

    public WaitEvaluateListResponse.DataBean.WaitEvaluateListBean getItem(int position) {
        if (position == -1 || mWaitEvaluateList == null) {
            return null;
        }
        return mWaitEvaluateList.get(position);
    }

    class WaitEvaluateGoodsViewHolder extends RecyclerView.ViewHolder {
        ImageView ivGoodsImage;
        TextView tvGoodsName;
        TextView tvGoodsIntro;
        TextView tvFunction;
        View view;

        private WaitEvaluateGoodsViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ivGoodsImage = itemView.findViewById(R.id.iv_goods_image);
            tvGoodsName = itemView.findViewById(R.id.tv_goods_name);
            tvGoodsIntro = itemView.findViewById(R.id.tv_goods_intro);
            tvFunction = itemView.findViewById(R.id.tv_function);
        }
    }

    public void setOnRecItemClickListener(OnRecItemClickListener listener) {
        this.mListener = listener;
    }

    public void refresh(List<WaitEvaluateListResponse.DataBean.WaitEvaluateListBean> waitEvaluateList) {
        //下拉刷新
        mWaitEvaluateList.clear();
        mWaitEvaluateList.addAll(waitEvaluateList);
        notifyDataSetChanged();
    }

    public void add(List<WaitEvaluateListResponse.DataBean.WaitEvaluateListBean> waitEvaluateList) {
        //上拉加载
        int position = this.mWaitEvaluateList.size();
        mWaitEvaluateList.addAll(position, waitEvaluateList);
        notifyItemInserted(position);
    }
}
