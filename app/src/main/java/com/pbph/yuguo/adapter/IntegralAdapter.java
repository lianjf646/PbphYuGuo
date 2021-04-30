package com.pbph.yuguo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.response.GetMyPointSourceListResponse;

import java.util.List;

/**
 * Created by 连嘉凡 on 2018/9/5.
 */

public class IntegralAdapter extends BaseAdapter {
    private Context context;
    private List<GetMyPointSourceListResponse.DataBean.PointListBean> pointListBeanList;

    public IntegralAdapter(Context context) {
        this.context = context;
    }

    public void setPointListBeanList(List<GetMyPointSourceListResponse.DataBean.PointListBean>
                                             pointListBeanList) {
        this.pointListBeanList = pointListBeanList;
        notifyDataSetChanged();
    }

    public void addPointListBeanList(List<GetMyPointSourceListResponse.DataBean.PointListBean>
                                             pointListBeanList) {
        this.pointListBeanList.addAll(pointListBeanList);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return pointListBeanList != null ? pointListBeanList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return pointListBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IntegralViewHolder integralViewHolder;
        GetMyPointSourceListResponse.DataBean.PointListBean pointListBean = pointListBeanList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_integral_list,
                    null);
            integralViewHolder = new IntegralViewHolder(convertView);
            convertView.setTag(integralViewHolder);
        } else {
            integralViewHolder = (IntegralViewHolder) convertView.getTag();
        }
        integralViewHolder.tvTitle.setText(pointListBean.getPointDetail());
//        DateUtils dateUtils = new DateUtils(pointListBean.getCreateTime());
//        integralViewHolder.tvTime.setText(dateUtils.getString(DateUtils.PATTERN_8));
        integralViewHolder.tvTime.setText(pointListBean.getCreateTime());
        if (pointListBean.getPointType() == 1) {
            integralViewHolder.tvIntegral.setText(String.valueOf(pointListBean.getPointScore()));

        } else {
            integralViewHolder.tvIntegral.setText("+");
            integralViewHolder.tvIntegral.append(String.valueOf(pointListBean.getPointScore()));

        }
//            integralViewHolder.tvIntegral.append("积分");
        return convertView;
    }


    public class IntegralViewHolder {
        public View rootView;
        public TextView tvTitle;
        public TextView tvTime;
        public TextView tvIntegral;

        public IntegralViewHolder(View rootView) {
            this.rootView = rootView;
            this.tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
            this.tvTime = (TextView) rootView.findViewById(R.id.tv_time);
            this.tvIntegral = (TextView) rootView.findViewById(R.id.tv_integral);
        }

    }
}
