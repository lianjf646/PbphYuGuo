package com.pbph.yuguo.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.GlideCircleTransform;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class GlideUtil {


    public static void displayImage(Context activity, String url, ImageView imageView) {
        Glide.with(activity)//配置上下文
                .load(url)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
//                .load(ImageUrlUtil.getImageUrl(url))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
//                .error(R.mipmap.default_image)           //设置错误图片
//                .placeholder(R.mipmap.default_image)     //设置占位图片
                .into(imageView);
    }

    public static void displayImage(Context activity, String url, ImageView imageView,int errorMipmap) {
        Glide.with(activity)//配置上下文
                .load(url)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(errorMipmap)           //设置错误图片
                .placeholder(errorMipmap)     //设置占位图片
                .into(imageView);
    }

    public static void displayImage(Context activity, String url, int errorResourceId, int errorPlaceholder, ImageView imageView) {
        Glide.with(activity)//配置上下文
                .load(ImageUrlUtil.getImageUrl(url))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(errorResourceId)           //设置错误图片
                .placeholder(errorPlaceholder)     //设置占位图片
                .into(imageView);
    }

    /**
     * 毛玻璃效果
     *
     * @param activity
     * @param url
     * @param imageView
     */
    public static void displayImagebitmapTransform(Context activity, String url, ImageView imageView) {
        Glide.with(activity)//配置上下文
                .load(ImageUrlUtil.getImageUrl(url))
                .bitmapTransform(new BlurTransformation(activity, 50), new CenterCrop(activity))
                .into(imageView);
    }

    /**
     * 圆角效果
     *
     * @param activity
     * @param url
     * @param imageView
     */
    public static void displayRoundBitmapTransform(Context activity, String url, ImageView imageView) {
        Glide.with(activity)//配置上下文
                .load(url)
                .error(R.mipmap.ic_launcher)           //设置错误图片
                .placeholder(R.mipmap.ic_launcher)
                .transform(new CornersTransform(activity, 30))
                .into(imageView);
    }

    /**
     * 圆形图片
     *
     * @param activity
     * @param url
     * @param imageView
     */
    public static void displayCircleBitmap(Context activity, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        Glide.with(activity).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(activity))
                .crossFade()
                .into(imageView);
    }

    /**
     * 圆形图片
     *
     * @param activity
     * @param url
     * @param imageView
     */
    public static void displayCircleBitmaps(Context activity, String url, ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        Glide.with(activity).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(activity))
                .placeholder(R.drawable.wodeyidenglu_touxiang)
                .crossFade()
                .into(imageView);
    }

    /**
     * 圆形图片
     *
     * @param activity
     * @param imageView
     */
    public static void displayCircleBitmapLocal(Context activity, int path, ImageView imageView) {

        Glide.with(activity).load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(activity))
                .crossFade()
                .into(imageView);
    }
}
