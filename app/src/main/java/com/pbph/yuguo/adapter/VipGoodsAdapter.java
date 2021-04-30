package com.pbph.yuguo.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.dialog.SpecChoicePop;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.msg.Add2ShoppingCarMsg;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.AddShoppingCartResquest;
import com.pbph.yuguo.response.VipGoodsListResponse;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.MoneyHelper;
import com.pbph.yuguo.util.RxBusF;
import com.sobot.chat.utils.ToastUtil;

import java.util.List;

/**
 * Created by zyp on 2018/12/20 0020.
 * class note:会员商品adapter
 */

public class VipGoodsAdapter extends BaseAdapter {

    private Context context;
    private List<VipGoodsListResponse.DataBean.GoodsListBean> listBean;
    private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    public VipGoodsAdapter(Context context, List<VipGoodsListResponse.DataBean.GoodsListBean> listBean) {
        this.context = context;
        this.listBean = listBean;
    }

    @Override
    public int getCount() {
        return listBean == null || listBean.size() == 0 ? 0 : listBean.size();
    }

    @Override
    public VipGoodsListResponse.DataBean.GoodsListBean getItem(int position) {
        return listBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_vip_goods_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VipGoodsListResponse.DataBean.GoodsListBean goodsListBean = getItem(position);

        holder.ll_tag_view.removeAllViews();

        List<String> tagList = goodsListBean.getLabelNameList();
        if (tagList != null && tagList.size() != 0) {
            int textPadding = context.getResources().getDimensionPixelOffset(R.dimen.dp_2dp);
            for (int i = 0; i < tagList.size(); i++) {
                if (i > 2) {
                    continue;
                }
                TextView textView = new TextView(context);
                params.rightMargin = context.getResources().getDimensionPixelOffset(R.dimen.dp_5dp);
                textView.setPadding(textPadding, textPadding, textPadding, textPadding);
                textView.setLayoutParams(params);
                textView.setMaxLines(4);
                textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                textView.setTextColor(context.getResources().getColor(R.color.black_gray));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                textView.setBackgroundResource(R.drawable.vip_goods_tag_corner_bg);
                textView.setText(tagList.get(i));
                holder.ll_tag_view.addView(textView);
            }
        } else {
            TextView textView = new TextView(context);
            int textPadding = context.getResources().getDimensionPixelOffset(R.dimen.dp_2dp);
            params.rightMargin = context.getResources().getDimensionPixelOffset(R.dimen.dp_5dp);
            textView.setPadding(textPadding, textPadding, textPadding, textPadding);
            textView.setLayoutParams(params);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            holder.ll_tag_view.addView(textView);
            holder.ll_tag_view.setVisibility(View.INVISIBLE);
        }

        GlideUtil.displayImage(context, goodsListBean.getGoodsPicUrl(), holder.iv_goods_image);
        holder.tv_goods_name.setText(goodsListBean.getGoodsName());
        holder.tv_goods_info.setText(goodsListBean.getGoodsNameSub());

        holder.tv_vip_price.setText("￥");
        holder.tv_vip_price.append(MoneyHelper.getInstance4Fen(goodsListBean.getMemberPrice()).change2Yuan().getString());
        holder.tv_original_price.setText("￥");
        holder.tv_original_price.append(MoneyHelper.getInstance4Fen(goodsListBean.getGoodsSalePrice()).change2Yuan().getString());

//        holder.tv_original_price.getPaint().setAntiAlias(true);//抗锯齿
//        holder.tv_original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        holder.iv_add_cart.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {

                int manyGoodsInfoFlag = goodsListBean.getManyGoodsInfoFlag();
                int storeGoodsId = goodsListBean.getStoreGoodsId();
                int storeId = goodsListBean.getStoreId();
                int storeGoodsInfoId = goodsListBean.getStoreGoodsInfoId();

                if (manyGoodsInfoFlag == 0) {
                    addShoppingCart(YuGuoApplication.userInfo.getCustomerId(), storeGoodsId, storeId,
                            1, storeGoodsInfoId, 0, -1);
                } else {
                    new SpecChoicePop(context, (specChoicePop) -> {
                        if (specChoicePop.type != SpecChoicePop.Type.ADDSHOP) {
                            return;
                        }
                        addShoppingCart(YuGuoApplication.userInfo.getCustomerId(), specChoicePop.storeGoodsId, specChoicePop.storeId,
                                specChoicePop.goodsNum, specChoicePop.storeGoodsInfoId, 0, -1);
                    }).showPop(storeGoodsInfoId, storeGoodsId, "", 0, v);
                }
            }
        });
        return convertView;
    }

    private void addShoppingCart(int customerId,
                                 int storeGoodsId,
                                 int storeId,
                                 int goodsNum,
                                 int storeGoodsInfoId,
                                 int activeId,
                                 int activeType) {
        if (goodsNum <= 0) {
            Toast.makeText(context, "暂无库存", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpAction.getInstance().addShoppingCart(new AddShoppingCartResquest(customerId,
                storeGoodsId, storeId, goodsNum, storeGoodsInfoId, activeId, activeType)).subscribe(new BaseObserver<>(context,
                (response -> {

                    if (200 != response.getCode()) {
                        ToastUtil.showToast(context, response.getMsg());
                        return;
                    }
                    Toast.makeText(context,  "加入购物车成功！", Toast.LENGTH_SHORT).show();

                    RxBusF.post0(new Add2ShoppingCarMsg());

                })));
    }

    class ViewHolder {
        View rootView;
        TextView tv_goods_name;
        TextView tv_goods_info;
        LinearLayout ll_tag_view;
        TextView tv_vip_price;
        ImageView iv_goods_image;
        ImageView iv_add_cart;
        TextView tv_original_price;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_goods_name = rootView.findViewById(R.id.tv_goods_name);
            this.tv_goods_info = rootView.findViewById(R.id.tv_goods_info);
            this.ll_tag_view = rootView.findViewById(R.id.ll_tag_view);
            this.tv_vip_price = rootView.findViewById(R.id.tv_vip_price);
            this.iv_goods_image = rootView.findViewById(R.id.iv_goods_image);
            this.iv_add_cart = rootView.findViewById(R.id.iv_add_cart);
            this.tv_original_price = rootView.findViewById(R.id.tv_original_price);
        }
    }
}
