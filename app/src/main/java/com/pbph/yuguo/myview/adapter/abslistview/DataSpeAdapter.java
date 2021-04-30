package com.pbph.yuguo.myview.adapter.abslistview;

import android.content.Context;
import android.widget.AbsListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSpeAdapter extends DataAdapter {


    Map<Integer, KV> adapter_pos_2_true_pos_map = new HashMap<>();
    int dataCount = 0;

    OnGetSon onGetSon;


    public DataSpeAdapter(Context context, AbsListView view, Class farViewHolder, Class sonViewHolder, OnGetSon onGetSon) {
        this(context, view, farViewHolder, sonViewHolder, onGetSon, null);
    }

    public DataSpeAdapter(Context context, AbsListView view, Class farViewHolder, Class sonViewHolder, OnGetSon onGetSon, Class choiceHelperClz) {
        super(context, view, 2, choiceHelperClz);

        viewTypeCount.add(0, farViewHolder);
        viewTypeCount.add(1, sonViewHolder);

        this.onGetSon = onGetSon;
    }

    public KV getKV(int adapter_pos) {
        return adapter_pos_2_true_pos_map.get(adapter_pos);
    }


    public Object getItemByKV(KV kv) {
        Object obj = viewDataList.get(kv.fpos);
        if (kv.spos == -1)
            return obj;

        return onGetSon.getSonList(obj).get(kv.spos);

    }

    public Object getItemByFS(int fpos, int spos) {
        Object obj = viewDataList.get(fpos);
        if (spos == -1)
            return obj;

        return onGetSon.getSonList(obj).get(spos);

    }

    @Override
    public int getCount() {
        return dataCount;
    }

    @Override
    public Object getItem(int adapter_pos) {
        return getItemByKV(getKV(adapter_pos));
    }


    ////////////

    @Override
    public int getItemViewType(int adapter_pos) {
        KV vo = getKV(adapter_pos);
        if (vo.spos == -1) {
            return 0;
        } else {
            return 1;
        }
    }

    public Class getItemViewTypeClass(int adapter_pos) {
        return viewTypeCount.get(getItemViewType(adapter_pos));
    }

    private void flushDatas() {

        adapter_pos_2_true_pos_map.clear();
        dataCount = 0;

        int adapter_pos = -1;

        for (int i = 0, counti = viewDataList.size(); i < counti; i++) {

            adapter_pos += 1;

            putPosMap(adapter_pos, i, -1);

            List<Object> sonList = onGetSon.getSonList(viewDataList.get(i));
            if (sonList == null || sonList.size() <= 0) continue;

            for (int j = 0, countj = sonList.size(); j < countj; j++) {
                adapter_pos += 1;
                putPosMap(adapter_pos, i, j);
            }
        }
        dataCount += adapter_pos + 1;
    }


    private void putPosMap(int adapter_pos, int i, int j) {
        KV vo = new KV();
        vo.fpos = i;
        vo.spos = j;
        adapter_pos_2_true_pos_map.put(adapter_pos, vo);
    }

    public <T extends Object> void setDatas(List<T> datas) {

        clearDatas();

        addDatas(datas);
    }

    //
    public <T extends Object> void addDatas(List<T> datas) {
        super.addDatas(datas);

        flushDatas();
        notifyDataSetChanged();
    }

    //
    public <T extends Object> void addData(T data) {
        viewDataList.add(data);

        flushDatas();
        notifyDataSetChanged();
    }

    public <T extends Object> void addData(int true_fpos, T data) {
        if (true_fpos >= viewDataList.size()) {
            true_fpos = viewDataList.size();
        }

        viewDataList.add(true_fpos, data);
        flushDatas();
        notifyDataSetChanged();
    }

    public <T extends Object> void addSonData(int true_fpos, T data) {
        Object obj = viewDataList.get(true_fpos);
        List<Object> list = onGetSon.getSonList(obj);
        list.add(data);

        flushDatas();
        notifyDataSetChanged();
    }

    public <T extends Object> void addSonData(int true_fpos, int true_spos, T data) {

        Object obj = viewDataList.get(true_fpos);

        List<Object> list = onGetSon.getSonList(obj);

        if (true_spos >= list.size()) {
            true_spos = list.size();
        }
        list.add(true_spos, data);

        flushDatas();
        notifyDataSetChanged();
    }

    //
    public void removeData(int adapter_pos) {

        KV vo = getKV(adapter_pos);
        if (vo.spos == -1) {
            Object fobj = viewDataList.get(vo.fpos);

            viewDataList.remove(fobj);

            if (choiceHelper != null) {
                List<Object> list = onGetSon.getSonList(fobj);
                if (list != null) {
                    for (Object obj : list) {
                        choiceHelper.removeChoice(obj);
                    }
                }
                choiceHelper.removeChoice(fobj);
            }
        } else {
            List<Object> list = onGetSon.getSonList(viewDataList.get(vo.fpos));

            Object sObj = list.get(vo.spos);
            list.remove(sObj);

            if (choiceHelper != null) {
                choiceHelper.removeChoice(sObj);
            }
        }

        flushDatas();
        notifyDataSetChanged();
    }

    //
    public void clearDatas() {

        adapter_pos_2_true_pos_map.clear();
        dataCount = 0;

        viewDataList.clear();
        if (viewTypeList != null) {
            viewTypeList.clear();
        }
        if (choiceHelper != null) {
            choiceHelper.clearChoices();
        }
        notifyDataSetChanged();
    }


    public final class KV {
        public int fpos = -1;
        public int spos = -1;
    }

    public interface OnGetSon {
        List getSonList(Object obj);
    }

}