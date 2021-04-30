package com.pbph.yuguo.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.pbph.yuguo.R;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.PublicViewUtil;

/**
 * Created by zyp on 2019/2/12 0012.
 * class note:图片放大展示
 */

public class ImagePopwin extends PopupWindow {
    private Context context;
    private View parent;
    private String imageUrl;

    private ImageView ivBack;
    private ImageView ivImage;

    public ImagePopwin(Context context, View parent, String imageUrl) {
        this.context = context;
        this.parent = parent;
        this.imageUrl = imageUrl;
        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.popwin_image, null);
        setContentView(view);
        initView(view);
        show(parent);
    }

    private void initView(View view) {
        ivBack = view.findViewById(R.id.iv_back);
        ivImage = view.findViewById(R.id.iv_image);
        GlideUtil.displayImage(context, imageUrl, ivImage);
        ivBack.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                dismiss();
            }
        });
    }

    public void show(View parent) {
        setAnimationStyle(R.style.Popupwindow);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setFocusable(true);
        setOutsideTouchable(true);

        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        PublicViewUtil.backgroundAlpha((Activity) context, 0.6f);
        setOnDismissListener(() -> PublicViewUtil.backgroundAlpha((Activity) context, 1f));
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();
    }
}
