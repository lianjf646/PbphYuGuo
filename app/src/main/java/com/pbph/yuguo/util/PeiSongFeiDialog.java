package com.pbph.yuguo.util;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;


/**
 * Created by Administrator on 2018/3/12.
 */

public class PeiSongFeiDialog extends Dialog {

    public TextView tv_jichuyunfei;
    public LinearLayout ll_jichuyunfei;
    public TextView tv_chaozhongyunfei;
    public LinearLayout ll_chaozhongyunfei;
    public TextView tv_chaojuyunfei;
    public LinearLayout ll_chaojuyunfei;
    public TextView tv_priceall;
    public TextView tv_chaozhongshuoming;
    public TextView tv_chaojushuoming;
    public LinearLayout ll_shuoming;

    int basic;
    int overDistance;
    int overWeight;
    String overWeightExplain;
    String overDistanceExplain;

    int allPrice;

    public PeiSongFeiDialog(Context context, int basic, int overDistance, int overWeight, String overWeightExplain, String overDistanceExplain, int allPrice) {
        super(context, R.style.Dialog);

        this.basic = basic;
        this.overDistance = overDistance;
        this.overWeight = overWeight;
        this.overWeightExplain = overWeightExplain;
        this.overDistanceExplain = overDistanceExplain;
        this.allPrice = allPrice;

        setCancelable(true);

        setCustomDialog();
    }


    private void setCustomDialog() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_peisongfei, null);

        this.tv_jichuyunfei = (TextView) rootView.findViewById(R.id.tv_jichuyunfei);
        this.ll_jichuyunfei = (LinearLayout) rootView.findViewById(R.id.ll_jichuyunfei);

        this.tv_chaozhongyunfei = (TextView) rootView.findViewById(R.id.tv_chaozhongyunfei);
        this.ll_chaozhongyunfei = (LinearLayout) rootView.findViewById(R.id.ll_chaozhongyunfei);

        this.tv_chaojuyunfei = (TextView) rootView.findViewById(R.id.tv_chaojuyunfei);
        this.ll_chaojuyunfei = (LinearLayout) rootView.findViewById(R.id.ll_chaojuyunfei);

        this.tv_priceall = (TextView) rootView.findViewById(R.id.tv_priceall);

        this.tv_chaozhongshuoming = (TextView) rootView.findViewById(R.id.tv_chaozhongshuoming);
        this.tv_chaojushuoming = (TextView) rootView.findViewById(R.id.tv_chaojushuoming);

        this.ll_shuoming = (LinearLayout) rootView.findViewById(R.id.ll_shuoming);

        tv_jichuyunfei.setText("￥");
        tv_jichuyunfei.append(MoneyHelper.getInstance4Fen(basic).change2Yuan().getString());

        if (overWeight == 0) {
            ll_chaozhongyunfei.setVisibility(View.GONE);
            tv_chaozhongshuoming.setVisibility(View.GONE);
        } else {
            ll_chaozhongyunfei.setVisibility(View.VISIBLE);
            tv_chaozhongshuoming.setVisibility(View.VISIBLE);

            tv_chaozhongyunfei.setText("￥");
            tv_chaozhongyunfei.append(MoneyHelper.getInstance4Fen(overWeight).change2Yuan().getString());

            tv_chaozhongshuoming.setText(overWeightExplain);
        }

        if (overDistance == 0) {
            ll_chaojuyunfei.setVisibility(View.GONE);
            tv_chaojushuoming.setVisibility(View.GONE);
        } else {
            ll_chaojuyunfei.setVisibility(View.VISIBLE);
            tv_chaojushuoming.setVisibility(View.VISIBLE);

            tv_chaojuyunfei.setText("￥");
            tv_chaojuyunfei.append(MoneyHelper.getInstance4Fen(overDistance).change2Yuan().getString());

            tv_chaojushuoming.setText(overDistanceExplain);
        }

        if (overWeight == 0 && overDistance == 0) {
            ll_shuoming.setVisibility(View.GONE);
        } else {
            ll_shuoming.setVisibility(View.VISIBLE);
        }

        tv_priceall.setText(MoneyHelper.getInstance4Fen(allPrice).change2Yuan().getString());

        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(rootView);
    }


    public PeiSongFeiDialog showAndReturn() {
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
//        lp.height = (int) (lp.width * 0.70);     // 高度
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);
    }

    public static PeiSongFeiDialog show(Context context, int basic, int overDistance, int overWeight, String overWeightExplain, String overDistanceExplain, int allPrice) {
        return new PeiSongFeiDialog(context, basic, overDistance, overWeight, overWeightExplain, overDistanceExplain, allPrice).showAndReturn();
    }


}
