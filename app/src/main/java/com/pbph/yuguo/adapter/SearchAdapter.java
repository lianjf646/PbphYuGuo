package com.pbph.yuguo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.response.GetGoodsListResponse;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.MoneyHelper;

import java.util.List;

/**
 * Created by 挡风的纱窗 on 2018/12/20.
 */
public class SearchAdapter extends BaseAdapter {
    private List<GetGoodsListResponse.DataBean.GoodsCategoryListBean.CategoryGoodsListBean> stringList;
    private OnClickAddShopListener onClickAddShopListener;

    public SearchAdapter(OnClickAddShopListener onClickAddShopListener) {
        this.onClickAddShopListener = onClickAddShopListener;
    }


    public void setStringList(List<GetGoodsListResponse.DataBean.GoodsCategoryListBean.CategoryGoodsListBean> stringList) {
        this.stringList = stringList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return stringList != null ? stringList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.ivAdapterGoodsListShoppingCart.setOnClickListener(v -> {
            if (onClickAddShopListener == null) return;
            onClickAddShopListener.onClickAddShop(1);

        });
        GetGoodsListResponse.DataBean.GoodsCategoryListBean.CategoryGoodsListBean categoryGoodsListBean = stringList.get
                (position);
        GlideUtil.displayImage(parent.getContext(), categoryGoodsListBean.getGoodsPicUrl(), viewHolder.ivAdapterGoodsListImage);
        viewHolder.tvAdapterGoodsListTitle.setText(categoryGoodsListBean.getGoodsName());
        viewHolder.tvAdapterGoodsListDesc.setText(categoryGoodsListBean.getGoodsNameSub());
        viewHolder.tvAdapterGoodsListPrice.setText(MoneyHelper.getInstance4Fen(categoryGoodsListBean.getMemberPrice())
                .change2Yuan().getString());
        viewHolder.tvAdapterGoodsListDiscountPrice.setText("￥");
        viewHolder.tvAdapterGoodsListDiscountPrice.append(MoneyHelper.getInstance4Fen(categoryGoodsListBean.getGoodsSalePrice())
                .change2Yuan().getString());
        switch (categoryGoodsListBean.getLabelNameList().size()) {
            case 3:
                viewHolder.tvTag1.setVisibility(View.VISIBLE);
                viewHolder.tvTag2.setVisibility(View.VISIBLE);
                viewHolder.tvTag3.setVisibility(View.VISIBLE);
                viewHolder.tvTag1.setText(categoryGoodsListBean.getLabelNameList().get(0));
                viewHolder.tvTag2.setText(categoryGoodsListBean.getLabelNameList().get(1));
                viewHolder.tvTag3.setText(categoryGoodsListBean.getLabelNameList().get(2));
                break;
            case 2:
                viewHolder.tvTag1.setVisibility(View.VISIBLE);
                viewHolder.tvTag2.setVisibility(View.VISIBLE);
                viewHolder.tvTag3.setVisibility(View.INVISIBLE);
                viewHolder.tvTag1.setText(categoryGoodsListBean.getLabelNameList().get(0));
                viewHolder.tvTag2.setText(categoryGoodsListBean.getLabelNameList().get(1));
                break;
            case 1:
                viewHolder.tvTag1.setVisibility(View.VISIBLE);
                viewHolder.tvTag2.setVisibility(View.INVISIBLE);
                viewHolder.tvTag3.setVisibility(View.INVISIBLE);
                viewHolder.tvTag1.setText(categoryGoodsListBean.getLabelNameList().get(0));
                break;
            default:
                viewHolder.tvTag1.setVisibility(View.INVISIBLE);
                viewHolder.tvTag2.setVisibility(View.INVISIBLE);
                viewHolder.tvTag3.setVisibility(View.INVISIBLE);
                break;
        }

        return convertView;
    }

    public interface OnClickAddShopListener {
        void onClickAddShop(int pos);
    }

    class ViewHolder {
        public View rootView;
        public ImageView ivAdapterGoodsListImage;
        public TextView tvAdapterGoodsListTitle;
        public TextView tvAdapterGoodsListDesc;
        public TextView tvTag1;
        public TextView tvTag2;
        public TextView tvTag3;
        public LinearLayout linear;
        public TextView tvAdapterGoodsListPricePre;
        public TextView tvAdapterGoodsListPrice;
        public TextView tvAdapterGoodsListDiscountPrice;
        public ImageButton ivAdapterGoodsListShoppingCart;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ivAdapterGoodsListImage = (ImageView) rootView.findViewById(R.id.iv_adapter_goods_list_image);
            this.tvAdapterGoodsListTitle = (TextView) rootView.findViewById(R.id.tv_adapter_goods_list_title);
            this.tvAdapterGoodsListDesc = (TextView) rootView.findViewById(R.id.tv_adapter_goods_list_desc);
            this.tvTag1 = (TextView) rootView.findViewById(R.id.tv_tag1);
            this.tvTag2 = (TextView) rootView.findViewById(R.id.tv_tag2);
            this.tvTag3 = (TextView) rootView.findViewById(R.id.tv_tag3);
            this.linear = (LinearLayout) rootView.findViewById(R.id.linear);
            this.tvAdapterGoodsListPricePre = (TextView) rootView.findViewById(R.id.tv_adapter_goods_list_price_pre);
            this.tvAdapterGoodsListPrice = (TextView) rootView.findViewById(R.id.tv_adapter_goods_list_price);
            this.tvAdapterGoodsListDiscountPrice = (TextView) rootView.findViewById(R.id.tv_adapter_goods_list_discount_price);
            this.ivAdapterGoodsListShoppingCart = rootView.findViewById(R.id.iv_adapter_goods_list_shopping_cart);
//            tvAdapterGoodsListDiscountPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        }

    }
}
