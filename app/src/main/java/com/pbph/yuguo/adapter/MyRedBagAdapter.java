package com.pbph.yuguo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.response.GetAppRedPackageDealsInfoResponse;
import com.pbph.yuguo.util.MoneyHelper;

import java.util.List;

/**
 * Created by 连嘉凡 on 2018/8/14.
 */

public class MyRedBagAdapter extends RecyclerView.Adapter<MyRedBagAdapter.MyRedBagViewHolder> {

    private Context context;
    private List<GetAppRedPackageDealsInfoResponse.DataBean.DealsInfoDtoListBean>
            infoDtoListBeanList;

    public MyRedBagAdapter(Context context) {
        this.context = context;
    }

    public void setInfoDtoListBeanList(List<GetAppRedPackageDealsInfoResponse.DataBean
            .DealsInfoDtoListBean> infoDtoListBeanList) {
        this.infoDtoListBeanList = infoDtoListBeanList;
        notifyDataSetChanged();
    }

    public void addInfoDtoListBeanList(List<GetAppRedPackageDealsInfoResponse.DataBean
            .DealsInfoDtoListBean> infoDtoListBeanList) {
        if (infoDtoListBeanList == null) return;
        this.infoDtoListBeanList.addAll(infoDtoListBeanList);
        notifyDataSetChanged();


    }

    @Override
    public MyRedBagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_red_bag_list, null);
        MyRedBagViewHolder myRedBagViewHolder = new MyRedBagViewHolder(view);

        return myRedBagViewHolder;
    }

    /**
     * dealType
     * 1		收入
     * 2		支付
     * 3		提现
     * 4		退款
     * 5		储值
     * 6		邀请
     * 7		返利
     */
    @Override
    public void onBindViewHolder(MyRedBagViewHolder holder, int position) {
        GetAppRedPackageDealsInfoResponse.DataBean.DealsInfoDtoListBean dealsInfoDtoListBean =
                infoDtoListBeanList.get(position);

        holder.tvTime.setText(dealsInfoDtoListBean.getCreateTime());

        if (dealsInfoDtoListBean.getDealType() == 7) {
            holder.tvIntegral.setText("+");
            holder.tvTitle.setText("好友购买贡献");
        } else {
            holder.tvIntegral.setText("-");
            int dealWay = dealsInfoDtoListBean.getDealWay();
            holder.tvTitle.setText(dealWay == 2 ? "微信提现" : "支付宝提现");
        }

        holder.tvIntegral.append(MoneyHelper.getInstance4Fen(dealsInfoDtoListBean.getDealPrice())
                .change2Yuan().getString());
        holder.tvIntegral.append("元");

    }

    @Override
    public int getItemCount() {
        return infoDtoListBeanList != null ? infoDtoListBeanList.size() : 0;
    }

    class MyRedBagViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvTime, tvIntegral;

        public MyRedBagViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvIntegral = itemView.findViewById(R.id.tv_integral);

        }
    }
}
