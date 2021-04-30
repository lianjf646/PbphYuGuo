package com.pbph.yuguo.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.interfaces.OnRecItemClickListener;
import com.pbph.yuguo.response.CouponResponse;
import com.pbph.yuguo.util.DateUtils;
import com.pbph.yuguo.util.MoneyHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 优惠券adapter
 * Created by Administrator on 2018/8/6 0006.
 */

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherVieHolder> {
    private Context mContext;
    public List<CouponResponse.DataBean.ListBean> mVoucherList = new ArrayList<>();
    private OnRecItemClickListener mListener;

    public int pos_id = -1;

    public VoucherAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public VoucherVieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_voucher_list, parent, false);
        return new VoucherVieHolder(view);
    }

    @Override
    public void onBindViewHolder(final VoucherVieHolder holder, int position) {
        CouponResponse.DataBean.ListBean voucher = mVoucherList.get(position);

        int usableFlag = voucher.getUsableFlag();
//        if (usableFlag == 1) {
            holder.ivVoucher.setBackground(ContextCompat.getDrawable(mContext, R.drawable.coupons1));
            holder.itemView.setEnabled(true);
//        } else {
//            holder.ivVoucher.setBackground(ContextCompat.getDrawable(mContext, R.drawable.coupons2));
//            holder.itemView.setEnabled(false);
//        }

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
        holder.tvTime.setText(String.format("%s-%s", DateUtils.stampToDate(startTimeStamp), DateUtils.stampToDate(endTimeStamp)));

        holder.cbCheck.setChecked(pos_id == voucher.getCouponId());

        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(v, voucher.getCouponId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mVoucherList.size();
    }


    public void recyclerViewItemClickListener(OnRecItemClickListener listener) {
        this.mListener = listener;
    }

    class VoucherVieHolder extends RecyclerView.ViewHolder {
        RelativeLayout main;
        ImageView ivVoucher;
        TextView tvPrice;
        TextView tvIntro;
        TextView tvVoucherName;
        TextView tvTime;
        CheckBox cbCheck;

        private VoucherVieHolder(View itemView) {
            super(itemView);
            ivVoucher = itemView.findViewById(R.id.iv_voucher);
            main = itemView.findViewById(R.id.main);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvIntro = itemView.findViewById(R.id.tv_intro);
            tvVoucherName = itemView.findViewById(R.id.tv_voucher_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            cbCheck = itemView.findViewById(R.id.cb_check);
        }
    }
}


