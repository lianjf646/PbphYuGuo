package com.pbph.yuguo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.response.MemberBenefitsListResponse;
import com.pbph.yuguo.util.GlideUtil;

import java.util.List;

/**
 * Created by zyp on 2018/12/20 0020.
 * class note:会员权益adapter
 */

public class VipEquityAdapter extends BaseAdapter {

    private Context context;
    private List<MemberBenefitsListResponse.DataBean.MemberBenefitsListBean> listBean;

    public VipEquityAdapter(Context context, List<MemberBenefitsListResponse.DataBean.MemberBenefitsListBean> listBean) {
        this.context = context;
        this.listBean = listBean;
    }

    @Override
    public int getCount() {
        return listBean == null || listBean.size() == 0 ? 0 : listBean.size();
    }

    @Override
    public MemberBenefitsListResponse.DataBean.MemberBenefitsListBean getItem(int position) {
        return listBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_vip_equity_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MemberBenefitsListResponse.DataBean.MemberBenefitsListBean benefitsListBean = getItem(position);
        GlideUtil.displayImage(context, benefitsListBean.getIcon(), holder.iv_vip_picture);
        holder.tv_vip_name.setText(benefitsListBean.getMemberBenefitsName());

        return convertView;
    }

    class ViewHolder {
        ImageView iv_vip_picture;
        TextView tv_vip_name;

        public ViewHolder(View view) {
            iv_vip_picture = view.findViewById(R.id.iv_vip_picture);
            tv_vip_name = view.findViewById(R.id.tv_vip_name);
        }
    }
}
