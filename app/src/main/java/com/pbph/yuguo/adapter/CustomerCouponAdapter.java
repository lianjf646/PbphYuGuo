package com.pbph.yuguo.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.response.CouponResponse;
import com.pbph.yuguo.util.DateUtils;
import com.pbph.yuguo.util.MoneyHelper;

import java.text.ParseException;
import java.util.List;

/**
 * 优惠券adapter
 * Created by zyp on 2018/8/10 0010.
 */

public class CustomerCouponAdapter extends RecyclerView.Adapter<CustomerCouponAdapter.CouponViewHolder> {
    private Context mContext;
    private int mCouponStatus;
    private List<CouponResponse.DataBean.ListBean> mCouponList;

    public CustomerCouponAdapter(Context context, int couponStatus, List<CouponResponse.DataBean.ListBean> couponList) {
        this.mContext = context;
        this.mCouponStatus = couponStatus;
        this.mCouponList = couponList;
    }

    @Override
    public CouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_coupon_list, parent, false);
        return new CouponViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CouponViewHolder holder, int position) {
        CouponResponse.DataBean.ListBean coupon = getItem(position);
        holder.tvPrice.setText(MoneyHelper.getInstance4Fen(coupon.getCouponPrice()).change2Yuan().getStringDelZero());
        //优惠券规则类型(1:满减;2:直降;3:无门槛；4抵用券)
        int couponRuleType = coupon.getCouponRuleType();
        int activeSiteType = coupon.getActiveSiteType();
        String couponTypeStr = "";
        switch (couponRuleType) {
            case 1:
                couponTypeStr = String.format("满%s元可用", MoneyHelper.getInstance4Fen(coupon.getCouponXPrice()).change2Yuan().getStringDelZero());
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
        holder.tvCouponName.setText(coupon.getCouponName());
        String startTimeStamp = "";
        String endTimeStamp = "";
        try {
            startTimeStamp = DateUtils.dateToStamp(coupon.getCouponStartTime());
            endTimeStamp = DateUtils.dateToStamp(coupon.getCouponEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(startTimeStamp) && !TextUtils.isEmpty(endTimeStamp)) {
            holder.tvBuyTime.setText(String.format("%s-%s", DateUtils.stampToDate(startTimeStamp), DateUtils.stampToDate(endTimeStamp)));
        }
        setCouponList(holder, mCouponStatus, couponRuleType,activeSiteType);

        holder.itemView.setOnClickListener(v -> {
//            UsableCouponPopwin popWin = new UsableCouponPopwin(mContext, holder.itemView, 1);

        });
    }

    private void setCouponList(CouponViewHolder holder, int couponStatus, int couponRuleType,int activeSiteType) {
        switch (couponStatus) {
            case 2:
                //未使用
                if (couponRuleType == 4) {
                    holder.ivCouponBg.setBackground(ContextCompat.getDrawable(mContext, R.drawable.coupons3));
                } else {
                    holder.ivCouponBg.setBackground(ContextCompat.getDrawable(mContext, R.drawable.coupons1));
                }
                if(activeSiteType == 2){
                    holder.ivExclusive.setVisibility(View.VISIBLE);
                } else {
                    holder.ivExclusive.setVisibility(View.INVISIBLE);
                }
                holder.ivCouponStatus.setVisibility(View.GONE);
                break;
            case 3:
                //已使用
                holder.ivCouponBg.setBackground(ContextCompat.getDrawable(mContext, R.drawable.coupons2));
                holder.ivCouponStatus.setVisibility(View.VISIBLE);
                holder.ivCouponStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.employ));
                break;
            case 4:
                //已过期
                holder.ivCouponBg.setBackground(ContextCompat.getDrawable(mContext, R.drawable.coupons2));
                holder.ivCouponStatus.setVisibility(View.VISIBLE);
                holder.ivCouponStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.past_due));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mCouponList == null ? 0 : mCouponList.size();
    }

    public CouponResponse.DataBean.ListBean getItem(int position) {
        return mCouponList.get(position);
    }

    class CouponViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCouponBg;
        ImageView ivCouponStatus;
        ImageView ivExclusive;
        TextView tvPrice;
        TextView tvIntro;
        TextView tvCouponName;
        TextView tvBuyTime;

        private CouponViewHolder(View itemView) {
            super(itemView);
            ivCouponBg = itemView.findViewById(R.id.iv_coupon_bg);
            ivCouponStatus = itemView.findViewById(R.id.iv_coupon_status);
            ivExclusive = itemView.findViewById(R.id.iv_exclusive);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvIntro = itemView.findViewById(R.id.tv_intro);
            tvCouponName = itemView.findViewById(R.id.tv_coupon_name);
            tvBuyTime = itemView.findViewById(R.id.tv_buy_time);
        }
    }


    public void refresh(List<CouponResponse.DataBean.ListBean> couponList) {
        //下拉刷新
        mCouponList.clear();
        mCouponList.addAll(couponList);
        notifyDataSetChanged();
    }

    public void add(List<CouponResponse.DataBean.ListBean> couponList) {
        //上拉加载
        int position = this.mCouponList.size();
        mCouponList.addAll(position, couponList);
        notifyItemInserted(position);
    }
}
