package com.pbph.yuguo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.pbph.yuguo.R;

import java.util.List;

/**
 * Created by 连嘉凡 on 2018/8/8.
 */

public class ChoiceAddressListSearchAdapter extends BaseAdapter {
    private Context context;
    private List<PoiInfo> poiInfos;

    public ChoiceAddressListSearchAdapter(Context context) {
        this.context = context;
    }


    public void setPoiInfos(List<PoiInfo> poiInfos) {
        this.poiInfos = poiInfos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return poiInfos != null ? poiInfos.size() : 0;
    }

    @Override
    public PoiInfo getItem(int position) {
        return poiInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChoiceAddressListSearchViewHolder choiceAddressListSearchViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout
                    .adapter_choice_address_search_item, null);
            choiceAddressListSearchViewHolder = new ChoiceAddressListSearchViewHolder(convertView);
            convertView.setTag(choiceAddressListSearchViewHolder);
        } else {
            choiceAddressListSearchViewHolder = (ChoiceAddressListSearchViewHolder) convertView
                    .getTag();
        }

        PoiInfo poiInfo = poiInfos.get(position);
        choiceAddressListSearchViewHolder.mTvAddress.setText(poiInfo.name);
        choiceAddressListSearchViewHolder.mTvAddressInfo.setText(poiInfo.address);
        return convertView;
    }

    class ChoiceAddressListSearchViewHolder {
        private TextView mTvAddress, mTvAddressInfo;

        public ChoiceAddressListSearchViewHolder(View view) {
            mTvAddress = view.findViewById(R.id.tv_address);
            mTvAddressInfo = view.findViewById(R.id.tv_address_info);
        }
    }
}
