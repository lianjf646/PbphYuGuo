package com.pbph.yuguo.adapter.shoppingcaradapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.adapter.recyclerview.ViewHolder;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.vo.ShoppingCarFootVo;

/**
 * Created by Administrator on 2018/8/2.
 */

public class FootViewHolder extends ViewHolder<ShoppingCarFootVo> {

    public View ll_zk;
    public TextView tv_zk;
    public ImageView iv_zk;

    @Override
    protected int getLayout() {
        return R.layout.adapterfoot_shoppingcar;
    }

    @Override
    protected void getView(View itemView) {

        this.ll_zk = itemView.findViewById(R.id.ll_zk);

        this.tv_zk = (TextView) itemView.findViewById(R.id.tv_zk);
        tv_zk.setOnClickListener(listener);
        this.iv_zk = (ImageView) itemView.findViewById(R.id.iv_zk);
        iv_zk.setOnClickListener(listener);
    }

    @Override
    protected void showView() {
        if (item.exp) {
            tv_zk.setText("收缩");
            iv_zk.setImageResource(R.drawable.grayup);
        } else {
            tv_zk.setText("展开");
            iv_zk.setImageResource(R.drawable.graydown);
        }
    }

    OnSPClickListener listener = new OnSPClickListener() {
        @Override
        public void onClickSucc(View v) {
            adapter.onItemViewClickListener.onItemViewClick(v.getId(), FootViewHolder.this);
        }
    };


}
