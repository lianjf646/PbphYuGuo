package com.pbph.yuguo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.activity.OrderDetailActivity;
import com.pbph.yuguo.response.GetMessageInfoListResponse;
import com.pbph.yuguo.util.GlideUtil;

import java.util.List;

/**
 * Created by 挡风的纱窗 on 2019/4/28.
 */
public class OrderInfoAdapter extends RecyclerView.Adapter<OrderInfoAdapter.ViewHolder> {
    private List<GetMessageInfoListResponse.DataBean.ListBean> listBeanList;
    private Context context;

    public OrderInfoAdapter(Context context) {
        this.context = context;
    }

    public void setListBeanList(List<GetMessageInfoListResponse.DataBean.ListBean> listBeanList) {
        this.listBeanList = listBeanList;
        notifyDataSetChanged();
    }
    public void addListBeanList(List<GetMessageInfoListResponse.DataBean.ListBean> listBeanList) {
        this.listBeanList.addAll(listBeanList) ;
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderinfo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GetMessageInfoListResponse.DataBean.ListBean bean = listBeanList.get(position);
        holder.showView(bean);
        holder.constraint.setOnClickListener(v -> {
            context.startActivity(new Intent(context, OrderDetailActivity.class).putExtra("orderId",bean.getOrderId()));
        });
    }

    @Override
    public int getItemCount() {
        return listBeanList != null ? listBeanList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private TextView tvTitle;
        private TextView tvContent;
        private ImageView ivShop;
        private ConstraintLayout constraint;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            ivShop = itemView.findViewById(R.id.iv_shop);
            constraint = itemView.findViewById(R.id.constraint);

        }

        public void showView(GetMessageInfoListResponse.DataBean.ListBean bean){
            tvDate.setText(bean.getCreateTime());
            tvTitle.setText(bean.getMessageTitle());
            tvContent.setText(bean.getMessageContent());
            GlideUtil.displayImage(context,bean.getGoodsPicUrl(),ivShop);
        }
    }
}
