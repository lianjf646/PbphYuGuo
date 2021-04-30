package com.pbph.yuguo.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.GoodsCouponListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/6.
 */


public class MyCouponPopWindow implements View.OnClickListener {
    Context context;
    private PopupWindow popupWindow;
    private View contentView;
    private GoodsCouponListAdapter adapter;
    private List<String> list = new ArrayList<>();
    private ImageView mIvDialogGoodsInfoClose;
    private ListView mLvGoodsInfoConponList;
    private Button mBtnDialogGoodsInfoSubmit;


    public MyCouponPopWindow(Context context) {
        this.context = context;

    }


//    public MyReportPopWindow(Context context, int type) {
//        this.type = type;
//        this.context = context;
//
//    }

    private void windowTransparent(float alpha) {
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
//        wl.flags=WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        wl.alpha = alpha;   //这句就是设置窗口里崆件的透明度的．０.０全透明．１.０不透明．
        window.setAttributes(wl);
    }

    public void showDialog(View button) {
        if (null != popupWindow && contentView != null) {
            popupWindow.showAtLocation(button, Gravity.BOTTOM, 0, 0);
            windowTransparent(0.6f);
            return;
        }
        contentView = LayoutInflater.from(context).inflate(R.layout.popwindow_goods_info_coupon, null);
        popupWindow = new PopupWindow(context);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        adapter = new GoodsCouponListAdapter(context, list);

        mIvDialogGoodsInfoClose = (ImageView) contentView.findViewById(R.id.iv_dialog_goods_info_close);
        mIvDialogGoodsInfoClose.setOnClickListener((view) -> {
            popupWindow.dismiss();
        });
        list.add("1");
        list.add("2");
        list.add("3");
        mLvGoodsInfoConponList = (ListView) contentView.findViewById(R.id.lv_goods_info_conpon_list);
        mLvGoodsInfoConponList.setAdapter(adapter);
        mBtnDialogGoodsInfoSubmit = (Button) contentView.findViewById(R.id.btn_dialog_goods_info_submit);
        popupWindow.setContentView(contentView);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        popupWindow.showAtLocation(button, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
        windowTransparent(0.6f);
        popupWindow.setOnDismissListener(() -> {
            windowTransparent(1);
        });
    }

    @Override
    public void onClick(View v) {

    }

    public void closeDialog() {
        if (null != popupWindow) {
            popupWindow.dismiss();
        }
    }

    public interface OnPopWindowShowListener {
        void onShowListener(boolean isShow);
    }

}
