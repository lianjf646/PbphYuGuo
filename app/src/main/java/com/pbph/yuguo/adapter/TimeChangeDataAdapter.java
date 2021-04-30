package com.pbph.yuguo.adapter;

import android.content.Context;
import android.widget.AbsListView;

import com.pbph.yuguo.myview.adapter.abslistview.DataAdapter;
import com.pbph.yuguo.myview.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/9.
 */

public class TimeChangeDataAdapter extends DataAdapter {


    public List<OnTimeChangeListener> interfaceList = new ArrayList<>();

    public TimeChangeDataAdapter(Context context, AbsListView view, Class viewholder) {
        super(context, view, viewholder);
    }

    @Override
    public void onGetView(ViewHolder holder) {
        super.onGetView(holder);
        interfaceList.add((OnTimeChangeListener) holder);
    }

    public interface OnTimeChangeListener {
        void onTimeChange(long passTime);
    }
}
