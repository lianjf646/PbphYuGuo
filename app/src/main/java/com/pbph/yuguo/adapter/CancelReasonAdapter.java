package com.pbph.yuguo.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.interfaces.OnRecItemClickListener;
import com.pbph.yuguo.response.CouponResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 取消订单确认弹出框
 * Created by zyp on 2018/8/16 0016.
 */

public class CancelReasonAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mReasonStr;

    private Map<Integer, Boolean> positionMap;
    private int mPosition = -1;

    public CancelReasonAdapter(Context context, List<String> reasonStr) {
        this.mContext = context;
        this.mReasonStr = reasonStr;
        positionMap = new HashMap<>();
        for (int i = 0; i < mReasonStr.size(); i++) {
            positionMap.put(i, false);
        }
    }

    @Override
    public int getCount() {
        return mReasonStr.size();
    }

    @Override
    public Object getItem(int position) {
        return mReasonStr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_cancel_reason_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String item = mReasonStr.get(position);
        holder.tvReasonItem.setText(item);

        //设置checkBox改变监听
        holder.cbCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //用map集合保存
            positionMap.put(position, isChecked);

            if (isChecked) {
                holder.tvReasonItem.setBackgroundResource(R.drawable.text_view_hollow_border_green);
                holder.tvReasonItem.setTextColor(ContextCompat.getColor(mContext, R.color.main_color));
            } else {
                holder.tvReasonItem.setBackgroundResource(R.drawable.text_view_hollow_border_gray);
                holder.tvReasonItem.setTextColor(ContextCompat.getColor(mContext, R.color.dark_gray));
            }
        });
        // 设置CheckBox的状态
        if (positionMap.get(position) == null) {
            positionMap.put(position, false);
        }
        holder.cbCheck.setChecked(positionMap.get(position));

        return convertView;
    }

    //点击item选中CheckBox
    public void setSelectItem(int position) {
        this.mPosition = position;
        for (int i = 0; i < positionMap.size(); i++) {
            positionMap.put(i, false);
        }
        positionMap.put(position, true);
        notifyDataSetChanged();
    }

    //获取选中的Voucher实体
    public String getReasonItem() {
        if (mPosition == -1) {
            return null;
        }
        return mReasonStr.get(mPosition);
    }

    class ViewHolder {
        TextView tvReasonItem;
        CheckBox cbCheck;
        View view;

        public ViewHolder(View view) {
            this.view = view;
            tvReasonItem = view.findViewById(R.id.tv_reason_item);
            cbCheck = view.findViewById(R.id.cb_check);
        }
    }
}
