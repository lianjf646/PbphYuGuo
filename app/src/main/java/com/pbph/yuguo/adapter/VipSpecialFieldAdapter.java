package com.pbph.yuguo.adapter;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.response.VipGoodsListResponse;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.MoneyHelper;

import java.util.List;

/**
 * Created by 挡风的纱窗 on 2018/12/19.
 */
public class VipSpecialFieldAdapter extends BaseAdapter {

    private List<VipGoodsListResponse.DataBean.GoodsListBean> listBean;
    private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
            .LayoutParams.WRAP_CONTENT);

    private OnItemViewClickListener onItemViewClickListener;

    public VipSpecialFieldAdapter(OnItemViewClickListener onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }

    public void setStringList(List<VipGoodsListResponse.DataBean.GoodsListBean> listBean) {
        this.listBean = listBean;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listBean != null ? listBean.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return listBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vip_goods_list_more, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.llTagView.removeAllViews();
        VipGoodsListResponse.DataBean.GoodsListBean goodsListBean = listBean.get(position);
        List<String> tagList = goodsListBean.getLabelNameList();
        if (tagList != null && tagList.size() != 0) {
            int textPadding = parent.getContext().getResources().getDimensionPixelOffset(R.dimen.dp_2dp);
            for (int i = 0; i < tagList.size(); i++) {
                if (i > 2) continue;
                TextView textView = new TextView(parent.getContext());
                params.rightMargin = parent.getContext().getResources().getDimensionPixelOffset(R.dimen.dp_5dp);
                textView.setPadding(textPadding, textPadding, textPadding, textPadding);
                textView.setLayoutParams(params);
                textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                textView.setTextColor(parent.getContext().getResources().getColor(R.color.black_gray));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                textView.setBackgroundResource(R.drawable.vip_goods_tag_corner_bg);
                textView.setMaxLines(4);
                textView.setText(tagList.get(i));
                viewHolder.llTagView.addView(textView);
            }
        } else {
            TextView textView = new TextView(parent.getContext());
            int textPadding = parent.getContext().getResources().getDimensionPixelOffset(R.dimen.dp_2dp);
            params.rightMargin = parent.getContext().getResources().getDimensionPixelOffset(R.dimen.dp_5dp);
            textView.setPadding(textPadding, textPadding, textPadding, textPadding);
            textView.setLayoutParams(params);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            viewHolder.llTagView.addView(textView);
            viewHolder.llTagView.setVisibility(View.INVISIBLE);
        }
        GlideUtil.displayImage(parent.getContext(), goodsListBean.getGoodsPicUrl(), viewHolder.ivGoodsImage);
        viewHolder.tvGoodsName.setText(goodsListBean.getGoodsName());
        viewHolder.tvGoodsInfo.setText(goodsListBean.getGoodsNameSub());

        viewHolder.tvVipPrice.setText("￥");
        viewHolder.tvVipPrice.append(MoneyHelper.getInstance4Fen(goodsListBean.getMemberPrice()).change2Yuan().getString());
        viewHolder.tvOriginalPrice.setText("￥");
        viewHolder.tvOriginalPrice.append(MoneyHelper.getInstance4Fen(goodsListBean.getGoodsSalePrice()).change2Yuan().getString());

//        viewHolder.tvOriginalPrice.getPaint().setAntiAlias(true);//抗锯齿
//        viewHolder.tvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        viewHolder.ivAddCart.setOnClickListener(v -> {
            onItemViewClickListener.onItemViewClick(goodsListBean, 1);
        });
        return convertView;
    }

    public interface OnItemViewClickListener {
        void onItemViewClick(VipGoodsListResponse.DataBean.GoodsListBean goodsListBean, int pos);
    }


    public class ViewHolder {
        public View rootView;
        public ImageView ivGoodsImage;
        public TextView tvGoodsName;
        public TextView tvGoodsInfo;
        public LinearLayout llTagView;
        public TextView tvVipPrice;
        public ImageView ivAddCart;
        public TextView tvOriginalPrice;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ivGoodsImage = (ImageView) rootView.findViewById(R.id.iv_goods_image);
            this.tvGoodsName = (TextView) rootView.findViewById(R.id.tv_goods_name);
            this.tvGoodsInfo = (TextView) rootView.findViewById(R.id.tv_goods_info);
            this.llTagView = (LinearLayout) rootView.findViewById(R.id.ll_tag_view);
            this.tvVipPrice = (TextView) rootView.findViewById(R.id.tv_vip_price);
            this.ivAddCart = (ImageView) rootView.findViewById(R.id.iv_add_cart);
            this.tvOriginalPrice = (TextView) rootView.findViewById(R.id.tv_original_price);
        }

    }
}
