package com.pbph.yuguo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.MyGridView;
import com.pbph.yuguo.response.GetStoredConfigResponse;
import com.pbph.yuguo.util.MoneyHelper;

import java.util.List;

/**
 * Created by 连嘉凡 on 2018/9/27.
 */

public class MyBalanceAdapterEx extends BaseExpandableListAdapter {
    private Context context;
    List<GetStoredConfigResponse.DataBean.StoredConfigDtoListBean> list;

    public MyBalanceAdapterEx(Context context) {
        this.context = context;
    }

    public void setList(List<GetStoredConfigResponse.DataBean.StoredConfigDtoListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        return list.get(groupPosition).getCouponActivityDtoList().size();
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getCouponActivityDtoList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup
            parent) {

        FViewHolder fViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_balance_f_layout,
                    null);
            fViewHolder = new FViewHolder(convertView);
            convertView.setTag(fViewHolder);
        } else {
            fViewHolder = (FViewHolder) convertView.getTag();
        }
        fViewHolder.tv.setText("储值");
        fViewHolder.tv.append(String.valueOf(MoneyHelper.getInstance4Fen(list.get(groupPosition)
                .getStoredMoney())
                .change2Yuan().getInt()));
        fViewHolder.tv.append("元");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View
            convertView, ViewGroup parent) {
        CViewHolder cViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_balance_c_layout,
                    null);
            cViewHolder = new CViewHolder(convertView);
            convertView.setTag(cViewHolder);
        } else {
            cViewHolder = (CViewHolder) convertView.getTag();
        }
        cViewHolder.myGridView.setAdapter(cViewHolder.myBalanceChildAdapter);
        cViewHolder.myBalanceChildAdapter.setActivityDtoListBeanList(list.get(groupPosition)
                .getCouponActivityDtoList(), list.get(groupPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class FViewHolder {
        TextView tv;

        public FViewHolder(View view) {
            tv = view.findViewById(R.id.tv);
        }
    }

    class CViewHolder {
        MyGridView myGridView;
        MyBalanceChildAdapter myBalanceChildAdapter;

        public CViewHolder(View view) {
            myGridView = view.findViewById(R.id.myGridView);
            myBalanceChildAdapter = new MyBalanceChildAdapter(context);
        }
    }
}
