package com.pbph.yuguo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.response.GetStoredConfigResponse;
import com.pbph.yuguo.util.MoneyHelper;

import java.util.List;

/**
 * Created by 连嘉凡 on 2018/9/27.
 */

public class MyBalanceChildAdapter extends BaseAdapter {
    private Context context;
    private List<GetStoredConfigResponse.DataBean.StoredConfigDtoListBean
            .CouponActivityDtoListBean> activityDtoListBeanList;


    private GetStoredConfigResponse.DataBean.StoredConfigDtoListBean storedConfigDtoListBean;

    public MyBalanceChildAdapter(Context context) {
        this.context = context;
    }

    public void setActivityDtoListBeanList(List<GetStoredConfigResponse.DataBean
            .StoredConfigDtoListBean.CouponActivityDtoListBean> activityDtoListBeanList,
                                           GetStoredConfigResponse.DataBean.StoredConfigDtoListBean
                                                   storedConfigDtoListBean) {
        this.activityDtoListBeanList = activityDtoListBeanList;
        this.storedConfigDtoListBean = storedConfigDtoListBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return activityDtoListBeanList != null ? activityDtoListBeanList.size() + 1 : 1;
    }

    @Override
    public Object getItem(int position) {
        return activityDtoListBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (getItemViewType(position) == 1) {
            OneViewHolder oneViewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout
                        .adapter_mybalance_grid1, null);
                oneViewHolder = new OneViewHolder(convertView);
                convertView.setTag(oneViewHolder);
            } else {
                oneViewHolder = (OneViewHolder) convertView.getTag();
            }
            oneViewHolder.textView1.setText(String.valueOf(storedConfigDtoListBean.getMemberTime()));
            oneViewHolder.textView2.setText(storedConfigDtoListBean.getMemberFlag() == 1 ? "首充送体验会员" : "送体验会员");

        } else {

            TwoViewHolder twoViewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout
                        .adapter_mybalance_grid2, null);
                twoViewHolder = new TwoViewHolder(convertView);
                convertView.setTag(twoViewHolder);
            } else {
                twoViewHolder = (TwoViewHolder) convertView.getTag();
            }
            GetStoredConfigResponse.DataBean.StoredConfigDtoListBean
                    .CouponActivityDtoListBean vo = activityDtoListBeanList.get(position - 1);
            twoViewHolder.textView1.setText(String.valueOf(MoneyHelper.getInstance4Fen
                    (vo.getCouponPrice()).change2Yuan().getInt()));
            twoViewHolder.textView2.setText("无门槛券");
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    class OneViewHolder {
        TextView textView1, textView2;

        public OneViewHolder(View view) {
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
        }
    }

    class TwoViewHolder {
        TextView textView1, textView2;
        public TwoViewHolder(View view) {
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);

        }
    }
}
