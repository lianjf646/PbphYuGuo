package com.pbph.yuguo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.interfaces.OnRecItemClickListener;
import com.pbph.yuguo.response.CouponResponse;
import com.pbph.yuguo.util.DateUtils;
import com.pbph.yuguo.util.MoneyHelper;

import java.text.ParseException;
import java.util.List;

/**
 * Created by zyp on 2018/9/5 0005.
 * class note:
 */

public class UsableCouponAdapter extends RecyclerView.Adapter<UsableCouponAdapter.CouponViewHolder> {
    private Context context;
    private List<CouponResponse.DataBean.ListBean> couponList;
    private OnRecItemClickListener mListener;

    public UsableCouponAdapter(Context context, List<CouponResponse.DataBean.ListBean> couponList) {
        this.context = context;
        this.couponList = couponList;
    }

    @Override
    public CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_usable_coupon_list, parent, false);
        return new UsableCouponAdapter.CouponViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CouponViewHolder holder, int position) {
        CouponResponse.DataBean.ListBean voucher = couponList.get(position);

        holder.tvPrice.setText(MoneyHelper.getInstance4Fen(voucher.getCouponPrice()).change2Yuan().getStringDelZero());
        //优惠券规则类型(1:满减;2:直降;3:无门槛；4抵用券)
        int couponRuleType = voucher.getCouponRuleType();
        String couponTypeStr = "";
        switch (couponRuleType) {
            case 1:
                couponTypeStr = String.format("满%s元可用", MoneyHelper.getInstance4Fen(voucher.getCouponXPrice()).change2Yuan().getStringDelZero());
                break;
            case 2:
                couponTypeStr = "直降";
                break;
            case 3:
                couponTypeStr = "无门槛";
                break;
            case 4:
                couponTypeStr = "抵用券";
                break;
        }
        holder.tvIntro.setText(couponTypeStr);
        holder.tvVoucherName.setText(voucher.getCouponName());
        String startTimeStamp = "";
        String endTimeStamp = "";
        try {
            startTimeStamp = DateUtils.dateToStamp(voucher.getCouponStartTime());
            endTimeStamp = DateUtils.dateToStamp(voucher.getCouponEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(startTimeStamp) && !TextUtils.isEmpty(endTimeStamp)) {
            holder.tvTime.setText(String.format("%s-%s", DateUtils.stampToDate(startTimeStamp), DateUtils.stampToDate(endTimeStamp)));
        }

        int receiveFlag = voucher.getReceiveFlag();
        if (0 == receiveFlag) {
            holder.tvReceive.setText("立即领取");
            holder.tvReceive.setBackgroundResource(R.drawable.bg_button_usable_coupon_corner);
        } else {
            holder.tvReceive.setText("已领取");
            holder.tvReceive.setBackgroundResource(R.drawable.bg_button_unusable_coupon_corner);
        }
        holder.tvReceive.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return couponList == null ? 0 : couponList.size();
    }

    class CouponViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout main;
        TextView tvPrice;
        TextView tvIntro;
        TextView tvVoucherName;
        TextView tvTime;
        TextView tvReceive;

        private CouponViewHolder(View itemView) {
            super(itemView);
            main = itemView.findViewById(R.id.main);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvIntro = itemView.findViewById(R.id.tv_intro);
            tvVoucherName = itemView.findViewById(R.id.tv_voucher_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvReceive = itemView.findViewById(R.id.tv_receive);
        }
    }

    public void recyclerViewItemClickListener(OnRecItemClickListener listener) {
        this.mListener = listener;
    }

    public void refresh(List<CouponResponse.DataBean.ListBean> couponList) {
        //下拉刷新
        this.couponList.clear();
        this.couponList.addAll(couponList);
        notifyDataSetChanged();
    }
}
