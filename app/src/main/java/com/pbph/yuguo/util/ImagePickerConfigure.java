package com.pbph.yuguo.util;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/24.
 */

public class ImagePickerConfigure {
    private static ImagePicker imagePicker;

    public static ImagePickerConfigure getInstance() {
        return ImagePickerConfigureHolder.mInstance;
    }

    private ImagePickerConfigure() {
        initImagePicker();
    }

    private static class ImagePickerConfigureHolder {
        private static ImagePickerConfigure mInstance = new ImagePickerConfigure();
    }

    public void initImagePicker() {
        Log.e("initImagePicker", "initImagePicker");
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoaderUntil());
        imagePicker.setShowCamera(false);
        imagePicker.setMultiMode(true);
        imagePicker.setCrop(false);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
//        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics());
//        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 280, getResources().getDisplayMetrics());
//        imagePicker.setFocusWidth(width);
//        imagePicker.setFocusHeight(height);
        imagePicker.setSelectLimit(2);
    }

    public void setSelectLimit(int count) {
        imagePicker.setSelectLimit(count);
    }

    public void toImageGridView(Activity context) {
        Intent intent = new Intent(context, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, new ArrayList<ImageItem>());
        context.startActivityForResult(intent, 100);

    }

    public void toImageGridView(Activity context, ArrayList<ImageItem> imageItems) {
        Intent intent = new Intent(context, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, imageItems);
        context.startActivityForResult(intent, 100);
    }
}
