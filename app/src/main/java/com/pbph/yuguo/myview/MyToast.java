package com.pbph.yuguo.myview;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/1/28.
 */

public class MyToast {
    private View view = null;
    private ImageView image;
    private TextView content;
    private Toast toast = null;

    private MyToast() {

    }

    public static MyToast getInstance() {
        return MyToastHolder.myToast;
    }

    private static class MyToastHolder {
        private static MyToast myToast = new MyToast();
    }

    public void showToast(Activity context, int resID, int textID) {
        showToast(context, resID, context.getResources().getString(textID));
    }

    public void showToast(Activity context, int resID, String text) {
        getToast(context, resID, text).show();
    }

    private void initView(Context context) {
//        LayoutInflater inflater = LayoutInflater.from(context);//调用Activity的getLayoutInflater()
//        view = inflater.inflate(R.layout.my_toast_layout, null); //加載layout下的布局
//        BaseActivity baseActivity = (BaseActivity) context;
//        int width = baseActivity.getScreenWidth();
//        int height = baseActivity.getScreenHeight();
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(context.getResources(), R.drawable.wrong, options);
//        Log.e("initView", options.outWidth + "   " + options.outHeight);
//        RelativeLayout linearLayout = view.findViewById(R.id.ll_my_toast_layout);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
//        params.setMargins(0, 0, 0, 0);
//        linearLayout.setLayoutParams(params);
//        image = view.findViewById(R.id.iv_my_toast_image);
//        content = view.findViewById(R.id.tv_my_toast_desc);
//        RelativeLayout.LayoutParams contentLinearLayout = (RelativeLayout.LayoutParams) content.getLayoutParams();
//        int contentWidth = width / 3 * 2;
//        contentLinearLayout.width = contentWidth;
//        contentLinearLayout.height = contentWidth / 2;
//        contentLinearLayout.setMargins(0, options.outHeight / 5 * 4, 0, 0);
    }

    private Toast getToast(Activity context, int resID, String text) {
        if (null == view) {
            initView(context);
        }
        if (null == toast) {
            toast = new Toast(context);
            toast.setGravity(Gravity.FILL, 0, 0);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
            toast.setDuration(Toast.LENGTH_SHORT);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
            toast.setView(view); //添加视图文件
        }
        image.setImageResource(resID);
        content.setText(text);

        return toast;
    }
}
