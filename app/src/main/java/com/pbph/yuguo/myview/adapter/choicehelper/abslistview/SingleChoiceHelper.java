package com.pbph.yuguo.myview.adapter.choicehelper.abslistview;


import com.pbph.yuguo.myview.adapter.abslistview.DataAdapter;
import com.pbph.yuguo.myview.adapter.choicehelper.ChoiceHelper;

/**
 * Created by Administrator on 2018/7/10.
 */

public class SingleChoiceHelper<T extends DataAdapter> extends ChoiceHelper<T> {


    public Object choice_data;

    public SingleChoiceHelper(T adapter) {
        super(adapter);
    }


    @Override
    public Object putChoice(int pos) {
        if (pos < 0 || pos >= adapter.getCount()) return null;
        return choice_data = adapter.getItem(pos);
    }

    @Override
    public int putChoice(Object data) {
        int pos = adapter.getPosition(data);
        if (pos != -1) choice_data = data;
        return pos;
    }


    @Override
    public Object removeChoice(int pos) {
        if (pos < 0 || pos >= adapter.getCount()) return null;
        clearChoices();
        return adapter.getItem(pos);
    }

    @Override
    public int removeChoice(Object data) {
        int pos = adapter.getPosition(data);
        if (pos != -1) clearChoices();
        return pos;
    }

    @Override
    public void choiceAll() {
    }

    @Override
    public void clearChoices() {
        removeChoiceNotify(choice_data);
    }

    @Override
    public boolean isChoiced(int pos) {
        if (pos < 0 || pos >= adapter.getCount()) return false;
        return isChoiced(adapter.getItem(pos));
    }

    @Override
    public boolean isChoiced(Object data) {
        return this.choice_data == data;
    }

    @Override
    public void notifyDataSetChanged(int position) {
        adapter.notifyDataSetChanged();
    }
}
