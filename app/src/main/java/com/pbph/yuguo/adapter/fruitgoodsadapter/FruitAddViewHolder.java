package com.pbph.yuguo.adapter.fruitgoodsadapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.adapter.recyclerview.ViewHolder;
import com.pbph.yuguo.response.GetGoodsListResponse;

/**
 * Created by Administrator on 2018/8/2.
 */

public class FruitAddViewHolder extends ViewHolder<GetGoodsListResponse.DataBean.GoodsCategoryListBean> {

    ImageView iv;

    @Override
    protected int getLayout() {
        return R.layout.adapter_fruit_list_add;
    }

    @Override
    protected void getView(View itemView) {
        int add_height = adapter.bundle.getInt("add_height");
        iv = itemView.findViewById(R.id.iv_fruit_add);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv.getLayoutParams();
        params.height = add_height;
        iv.setLayoutParams(params);
    }

    @Override
    protected void showView() {
        Glide.with(adapter.context).load(item.getCategoryImgUrl()).into(iv);
    }
}
