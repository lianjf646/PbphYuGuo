package com.pbph.yuguo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.response.GetAddressListByCustomerIdResponse;

import java.util.List;

/**
 * Created by 连嘉凡 on 2018/8/8.
 */

public class MyAddressListAdapter extends BaseAdapter {
    private Context context;
    private List<GetAddressListByCustomerIdResponse.DataBean.ReceiverAddressdtoBean>
            dtoListBeanList;
    private DataListener dataListener;
    private int removeState;//0 隐藏 1 显示

    public MyAddressListAdapter(Context context) {
        this.context = context;
    }

    public void setRemoveState(int removeState) {
        this.removeState = removeState;
    }

    public void setdtoListBeanList(List<GetAddressListByCustomerIdResponse.DataBean.ReceiverAddressdtoBean
            > dtoListBeanList) {
        this.dtoListBeanList = dtoListBeanList;
        notifyDataSetChanged();
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }

    @Override
    public int getCount() {
        return dtoListBeanList != null ? dtoListBeanList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return dtoListBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyAddressListViewHolder myAddressListViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout
                    .adapter_my_address_list_item, null);
            myAddressListViewHolder = new MyAddressListViewHolder(convertView);
            convertView.setTag(myAddressListViewHolder);
        } else {
            myAddressListViewHolder = (MyAddressListViewHolder) convertView.getTag();
        }
        GetAddressListByCustomerIdResponse.DataBean.ReceiverAddressdtoBean dtoListBean =
                dtoListBeanList.get(position);
        myAddressListViewHolder.tvName.setText(dtoListBean.getReceiverName());
        myAddressListViewHolder.tvTel.setText(dtoListBean.getReceiverPhone());
        myAddressListViewHolder.tvAddressInfo.setText(dtoListBean.getReceiverVillage());
        myAddressListViewHolder.tvAddressInfo.append("   ");
        myAddressListViewHolder.tvAddressInfo.append(dtoListBean.getReceiverAddressDetail());

        myAddressListViewHolder.chbDefault.setOnClickListener(v -> {
            dataListener.setDefault(position, dtoListBean.getAddressId());
        });

        if (dtoListBean.getDefaultFlag() == 1) {
            myAddressListViewHolder.chbDefault.setChecked(true);
            myAddressListViewHolder.chbDefault.setClickable(false);
        } else {
            myAddressListViewHolder.chbDefault.setChecked(false);
            myAddressListViewHolder.chbDefault.setClickable(true);
        }


        myAddressListViewHolder.tvText.setOnClickListener(v -> {
            dataListener.textAddress(position);
        });

        myAddressListViewHolder.tvRemove.setOnClickListener(v -> {
            dataListener.removeAddress(position, dtoListBean.getAddressId());
        });
        return convertView;
    }

    public interface DataListener {
        void textAddress(int pos);

        void removeAddress(int pos, int addressId);

        void setDefault(int pos, int addressId);

    }

    class MyAddressListViewHolder {
        private TextView tvName, tvTel, tvAddressInfo, tvText, tvRemove;
        private CheckBox chbDefault;

        public MyAddressListViewHolder(View view) {
            tvName = view.findViewById(R.id.tv_name);
            tvTel = view.findViewById(R.id.tv_tel);
            tvAddressInfo = view.findViewById(R.id.tv_address_info);
            tvText = view.findViewById(R.id.tv_text);
            tvRemove = view.findViewById(R.id.tv_remove);
            chbDefault = view.findViewById(R.id.chb_default);
            if (removeState == 0) {
                tvRemove.setVisibility(View.GONE);
            }

        }
    }
}
