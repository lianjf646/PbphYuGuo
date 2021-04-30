package com.pbph.yuguo.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pbph.yuguo.R;
import com.pbph.yuguo.activity.LookBigPictureActivity;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.response.GetGoodsDetailResponse;

import java.util.List;


public class EvaluateListImageAdapter extends BaseAdapter {
    private Context context;
    private List<GetGoodsDetailResponse.DataBean.EvaluateListBean.ShareImgListBean> imagelist;
    private com.pbph.yuguo.adapter.MyAddressListAdapter.DataListener dataListener;
    private int widith;

    public EvaluateListImageAdapter(Context context, List<GetGoodsDetailResponse.DataBean.EvaluateListBean.ShareImgListBean> imagelist) {
        this.context = context;
        this.imagelist = imagelist;
        BaseActivity activity = (BaseActivity) context;
        widith = activity.getScreenWidth();
    }


    @Override
    public int getCount() {
        return imagelist != null ? imagelist.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return imagelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout
                    .adapter_evaluate_list_image_grid, null);
        }
        GetGoodsDetailResponse.DataBean.EvaluateListBean.ShareImgListBean bean = imagelist.get(position);
        ImageView goodsImage = (ImageView) ViewHolder.get(convertView, R.id.iv_adapter_evaluate_list_image);
        goodsImage.getLayoutParams().width = widith / 4;
        goodsImage.getLayoutParams().height = widith / 4;

        Glide.with(context)//配置上下文
                .load(bean.getImageUrl())      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
//                .load(ImageUrlUtil.getImageUrl(url))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
//                .error(R.mipmap.default_image)           //设置错误图片
//                .placeholder(R.mipmap.default_image)     //设置占位图片
                .into(goodsImage);
        String[] strs = new String[imagelist.size()];
        goodsImage.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                for (int i = 0, c = imagelist.size(); i < c; i++) {
                    strs[i] = imagelist.get(i).getImageUrl();
                }
                context.startActivity(new Intent(context, LookBigPictureActivity.class)
                        .putExtra(LookBigPictureActivity.POSITION, position)
                        .putExtra(LookBigPictureActivity.URL, strs)
                );
            }
        });

        return convertView;
    }

    private static class ViewHolder {

        public static View get(View view, int id) {

            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<View>();
                view.setTag(viewHolder);
            }
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }
            return childView;
        }
    }
}


