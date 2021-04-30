package com.youth.banner.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pbph.yuguo.util.ImageUrlUtil;


/**
 * Created by Administrator on 2017/7/28.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        try {
            String url= (String) path;
            Glide.with(context).load(ImageUrlUtil.getImageUrl(url)).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
