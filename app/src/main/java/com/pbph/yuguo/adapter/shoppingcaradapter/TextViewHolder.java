package com.pbph.yuguo.adapter.shoppingcaradapter;


import android.view.View;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.adapter.recyclerview.ViewHolder;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;

/**
 * Created by Administrator on 2018/8/2.
 */

public class TextViewHolder extends ViewHolder {

    TextView tv_clear;

    @Override
    protected int getLayout() {
        return R.layout.adapter_shoppingcar_text;
    }

    @Override
    protected void getView(View itemView) {
        tv_clear = itemView.findViewById(R.id.tv_clear);
        tv_clear.setOnClickListener(listener);
    }

    @Override
    protected void showView() {

    }

    OnSPClickListener listener = new OnSPClickListener() {
        @Override
        public void onClickSucc(View v) {
            adapter.onItemViewClickListener.onItemViewClick(v.getId(), TextViewHolder.this);
        }
    };
}
