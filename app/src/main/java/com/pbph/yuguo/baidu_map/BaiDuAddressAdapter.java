package com.pbph.yuguo.baidu_map;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.pbph.yuguo.R;
import com.pbph.yuguo.util.MoreSpatialRelationUntil;

import java.util.List;

/**
 * Created by 挡风的纱窗 on 2019/2/11.
 */
public class BaiDuAddressAdapter extends BaseAdapter {
    private Context context;
    private List<PoiInfo> poiInfoList;
    private List<List<LatLng>> regionLalngList;

    public BaiDuAddressAdapter(Context context) {
        this.context = context;
    }

    public void setPoiInfoList(List<PoiInfo> poiInfoList, List<List<LatLng>> regionLalngList) {
        this.poiInfoList = poiInfoList;
        this.regionLalngList = regionLalngList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return poiInfoList != null ? poiInfoList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return poiInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        PoiInfo poiInfo = poiInfoList.get(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_baidu_address_list, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();

        }
        viewHolder.tvTitle.setText(poiInfo.getName());
        viewHolder.tvContent.setText(poiInfo.getAddress());
//        poiInfo.province+poiInfo.city+poiInfo.getArea()
        if (MoreSpatialRelationUntil.isPolygonContainsPoints(regionLalngList, poiInfo.getLocation())) {
            viewHolder.tvTitle.setTextColor(Color.parseColor("#3f3f3f"));
            viewHolder.tvContent.setTextColor(Color.parseColor("#4b2929"));
        } else {
            viewHolder.tvTitle.setTextColor(Color.parseColor("#b4b3b3"));
            viewHolder.tvContent.setTextColor(Color.parseColor("#b4b3b3"));
        }
        return view;
    }

    class ViewHolder {
        private TextView tvTitle;
        private TextView tvContent;

        public ViewHolder(View itemView) {
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);

        }
    }
}
