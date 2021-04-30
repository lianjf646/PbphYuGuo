package com.pbph.yuguo.util;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.MainDialogViewHolder;
import com.pbph.yuguo.myview.adapter.abslistview.DataAdapter;
import com.pbph.yuguo.myview.singlepointlistener.OnItemSPClickListener;
import com.pbph.yuguo.response.GetIndexPopupCouponResponse;

import java.util.List;


/**
 * Created by Administrator on 2018/3/12.
 */

public class MainDialog extends Dialog {

    private ListView listView;
    private DataAdapter adapter;

    public MainDialog(Context context, List<GetIndexPopupCouponResponse.DataBean.ListBean> datas, OnClickRateDialogListener onClickRateListener) {
        this(context, R.style.Dialog, datas, onClickRateListener);
    }

    public MainDialog(Context context, int themeResId, List<GetIndexPopupCouponResponse.DataBean.ListBean> datas, OnClickRateDialogListener onClickRateListener) {
        super(context, themeResId);
        //        super(context, R.style.MyRateDialog);

        this.onClickRateListener = onClickRateListener;
        setCustomDialog();

        adapter.setDatas(datas);
    }


    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_main, null);

        listView = mView.findViewById(R.id.listView);
        adapter = new DataAdapter(getContext(), listView, MainDialogViewHolder.class);

        listView.setOnItemClickListener(new OnItemSPClickListener() {
            @Override
            public void onItemClickSucc(AdapterView<?> parent, View view, int position, long id) {
//                adapter.choiceHelper.putChoiceNotify(position);
            }
        });

        Button positiveButton = mView.findViewById(R.id.button2);

        if (positiveButton != null) positiveButton.setOnClickListener(v -> {
            if (onClickRateListener != null)
                onClickRateListener.onClickRight();
            dismiss();
        });
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(mView);
    }

    OnClickRateDialogListener onClickRateListener;

    public interface OnClickRateDialogListener {
        //        void onClickLeft();
        void onClickRight();
    }

    public MainDialog showAndReturn() {
        show();
        return this;
    }

    @Override
    public void show() {
        super.show();
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        /////////获取屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        /////////设置高宽
        lp.width = (int) (screenWidth * 0.75); // 宽度
        lp.height = (int) (lp.width * 1);     // 高度
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
    }

    public static MainDialog show(Context context, List<GetIndexPopupCouponResponse.DataBean.ListBean> datas, OnClickRateDialogListener onClickRateListener) {
        return new MainDialog(context, datas, onClickRateListener).showAndReturn();
    }
}
