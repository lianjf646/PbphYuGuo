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
 * Created by 连嘉凡 on 2018/8/7.
 */

public class ChoiceAddressAdapter extends BaseAdapter {
    private Context context;
    private List<GetAddressListByCustomerIdResponse.DataBean.ReceiverAddressdtoBean> dtoListBeanList;
    private DataListener dataListener;

    public ChoiceAddressAdapter(Context context) {
        this.context = context;
    }

    public void setDtoListBeanList(List<GetAddressListByCustomerIdResponse.DataBean.ReceiverAddressdtoBean> dtoListBeanList) {
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
        ChoiceAddressViewHolderTwo choiceAddressViewHolderTwo;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout
                    .adapter_choice_address_two_item, null);
            choiceAddressViewHolderTwo = new ChoiceAddressViewHolderTwo(convertView);
            convertView.setTag(choiceAddressViewHolderTwo);
        } else {
            choiceAddressViewHolderTwo = (ChoiceAddressViewHolderTwo) convertView.getTag();
        }

        GetAddressListByCustomerIdResponse.DataBean.ReceiverAddressdtoBean dtoListBean =
                dtoListBeanList.get(position);
        choiceAddressViewHolderTwo.tvAddressInfo.setText(dtoListBean.getReceiverVillage());
        choiceAddressViewHolderTwo.tvAddressInfo.append("   ");
        choiceAddressViewHolderTwo.tvAddressInfo.append(dtoListBean.getReceiverAddressDetail());
        choiceAddressViewHolderTwo.tvName.setText(dtoListBean.getReceiverName());
        choiceAddressViewHolderTwo.tvTel.setText(dtoListBean.getReceiverPhone());
//        choiceAddressViewHolderTwo.chbChoiceAdress.setOnClickListener(v -> {
//            dataListener.setDataPos(position);
//        });
        if (dtoListBean.getDefaultFlag() == 1) {
            choiceAddressViewHolderTwo.chbChoiceAdress.setChecked(true);
        } else {
            choiceAddressViewHolderTwo.chbChoiceAdress.setChecked(false);
        }

        return convertView;
    }

    public interface DataListener {
        void setDataPos(int pos);

    }

    class ChoiceAddressViewHolderTwo {
        private TextView tvName, tvTel, tvAddressInfo;
        private CheckBox chbChoiceAdress;


        public ChoiceAddressViewHolderTwo(View view) {
            tvName = view.findViewById(R.id.tv_name);
            tvTel = view.findViewById(R.id.tv_tel);
            tvAddressInfo = view.findViewById(R.id.tv_address_info);
            chbChoiceAdress = view.findViewById(R.id.chb_choice_address);
            chbChoiceAdress.setEnabled(false);
        }
    }

}
