package com.pbph.yuguo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.activity.LookBigPictureActivity;
import com.pbph.yuguo.dialog.ImagePopwin;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.response.GoodsEvaluateResponse;
import com.pbph.yuguo.util.AMUtils;
import com.pbph.yuguo.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品评价列表adapter
 * Created by zyp on 2018/8/13 0013.
 */

public class EvaluateListAdapter extends RecyclerView.Adapter<EvaluateListAdapter.EvaluateListViewHolder> {

    private Context mContext;
    private List<GoodsEvaluateResponse.DataBean.EvaluateListBean> mEvaluateList;

    public EvaluateListAdapter(Context context, List<GoodsEvaluateResponse.DataBean.EvaluateListBean> evaluateList) {
        this.mContext = context;
        this.mEvaluateList = evaluateList;
    }

    @Override
    public EvaluateListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_evaluate_list, parent, false);
        return new EvaluateListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EvaluateListViewHolder holder, int position) {
        GoodsEvaluateResponse.DataBean.EvaluateListBean evaluate = getItem(position);

        int anonymousFlag = evaluate.getAnonymousFlag();

        String anonymousStr = "";
        switch (anonymousFlag) {
            case 0:
                anonymousStr = evaluate.getCustomerName();
                if (AMUtils.isMobile(anonymousStr)) {
                    anonymousStr = anonymousStr.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                }
                GlideUtil.displayCircleBitmap(mContext, evaluate.getCustomerImgUrl(), holder.ivUserFace);
                break;
            case 1:
                anonymousStr = "匿名用户";
                holder.ivUserFace.setImageResource(R.drawable.wodeyidenglu_touxiang);
                break;
        }

        holder.tvUserName.setText(anonymousStr);

        holder.tvTime.setText(evaluate.getCreateTime());
        holder.tvContent.setText(evaluate.getEvaluateContent());

        List<GoodsEvaluateResponse.DataBean.EvaluateListBean.ShareImgListBean> imgList = evaluate.getShareImgList();
        if (imgList != null && imgList.size() != 0) {
//            setEvaImageList(holder, imgList);
            holder.llImage.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mContext.getResources().getDimensionPixelOffset(R.dimen.dp_80dp),
                    mContext.getResources().getDimensionPixelOffset(R.dimen.dp_80dp));
            holder.llImage.removeAllViews();
            for (int i = 0; i < imgList.size(); i++) {
                GoodsEvaluateResponse.DataBean.EvaluateListBean.ShareImgListBean img = imgList.get(i);
                ImageView imageView = new ImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setLayoutParams(params);
                params.rightMargin = mContext.getResources().getDimensionPixelOffset(R.dimen.dp_5dp);
                GlideUtil.displayImage(mContext, img.getImageUrl(), imageView);
                holder.llImage.addView(imageView);

                int finalI = i;
                imageView.setOnClickListener(new OnSPClickListener() {
                    @Override
                    public void onClickSucc(View v) {
                        String[] strs = new String[imgList.size()];

                        for (int i = 0, c = imgList.size(); i < c; i++) {
                            strs[i] = imgList.get(i).getImageUrl();
                        }
                        mContext.startActivity(new Intent(mContext, LookBigPictureActivity.class)
                                .putExtra(LookBigPictureActivity.POSITION, finalI)
                                .putExtra(LookBigPictureActivity.URL, strs)
                        );
                    }
                });
            }
        } else {
            holder.llImage.setVisibility(View.GONE);
        }
    }

    private GoodsEvaluateResponse.DataBean.EvaluateListBean getItem(int position) {
        return mEvaluateList.get(position);
    }

    public void refresh(List<GoodsEvaluateResponse.DataBean.EvaluateListBean> orderList) {
        //下拉刷新
        mEvaluateList.clear();
        mEvaluateList.addAll(orderList);
        notifyDataSetChanged();
    }

    public void add(List<GoodsEvaluateResponse.DataBean.EvaluateListBean> orderList) {
        //上拉加载
        int position = this.mEvaluateList.size();
        mEvaluateList.addAll(position, orderList);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return mEvaluateList == null ? 0 : mEvaluateList.size();
    }

    class EvaluateListViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivUserFace;
        private TextView tvUserName;
        private TextView tvTime;
        private TextView tvContent;
        private LinearLayout llImage;

        private EvaluateListViewHolder(View itemView) {
            super(itemView);
            ivUserFace = itemView.findViewById(R.id.iv_user_face);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvContent = itemView.findViewById(R.id.tv_content);
            llImage = itemView.findViewById(R.id.ll_image);
        }
    }
}
