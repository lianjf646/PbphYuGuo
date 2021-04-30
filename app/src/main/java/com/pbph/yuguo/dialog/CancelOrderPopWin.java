package com.pbph.yuguo.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.activity.OrderDetailActivity;
import com.pbph.yuguo.adapter.CancelReasonAdapter;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetCancelOrderRequest;
import com.pbph.yuguo.util.PublicViewUtil;
import com.sobot.chat.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 取消订单弹出框
 * Created by zyp on 2018/8/16 0016.
 */

public class CancelOrderPopWin extends PopupWindow {
    private Context mContext;

    private ImageView ivCancel;
    private RelativeLayout rlContainer;
    private TextView tvConfirm;
    private GridView gvReasonList;
    private CancelReasonAdapter adapter;

    private int orderId;
    private int type;

    public CancelOrderPopWin(Context context, int orderId, int type) {
        this.mContext = context;
        this.orderId = orderId;
        this.type = type;
        init();
    }

    private void init() {
        View view = View.inflate(mContext, R.layout.pop_win_cancel_order, null);
        setContentView(view);

        setOnDismissListener(() -> PublicViewUtil.backgroundAlpha((Activity) mContext, 1f));
        initView(view);
        initClick();
    }

    public void show(View view) {
        setAnimationStyle(R.style.mypopwindow_anim_style);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setFocusable(true);
        setOutsideTouchable(true);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
        PublicViewUtil.backgroundAlpha((Activity) mContext, 0.6f);
        update();
        setReasonItem();
    }

    private void initView(View view) {
        TextView tiTitle = view.findViewById(R.id.tv_title);
        ivCancel = view.findViewById(R.id.iv_cancel);
        rlContainer = view.findViewById(R.id.rl_container);
        gvReasonList = view.findViewById(R.id.gv_reason_list);
        tvConfirm = view.findViewById(R.id.tv_confirm);

        if (type == 2) {
            tiTitle.setText("申请原因");
        }
    }

    private void setReasonItem() {
        List<String> list = new ArrayList<>();
        list.add("不想买了");
        list.add("无法签收");
        list.add("重复购买");
        list.add("其他原因");

        gvReasonList.setVerticalSpacing(mContext.getResources().getDimensionPixelOffset(R.dimen.dp_20));
        gvReasonList.setHorizontalSpacing(mContext.getResources().getDimensionPixelOffset(R.dimen.dp_20));
        adapter = new CancelReasonAdapter(mContext, list);
        gvReasonList.setAdapter(adapter);
    }

    private void initClick() {
        ivCancel.setOnClickListener(v -> dismiss());
        rlContainer.setOnClickListener(v -> dismiss());

        gvReasonList.setOnItemClickListener((parent, view, position, id) -> {
            adapter.setSelectItem(position);
        });

        tvConfirm.setOnClickListener(v -> {
            String item = adapter.getReasonItem();
            if (TextUtils.isEmpty(item)) {
                if (type == 1) {
                    ToastUtil.showToast(mContext, "请选择取消原因");
                } else {
                    ToastUtil.showToast(mContext, "请选择退款原因");
                }
                return;
            }
            WaitUI.Show(mContext);
            GetCancelOrderRequest request = new GetCancelOrderRequest(orderId, item);
            HttpAction.getInstance().cancelOrder(request).subscribe(new BaseObserver<>(mContext, response -> {
                WaitUI.Cancel();
                int code = response.getCode();
                String msg = response.getMsg();
                if (code == 200) {
                    if (mContext instanceof OrderDetailActivity) {
                        ((OrderDetailActivity) mContext).cancelOrder();
                    }
                    if (type == 1) {
                        ToastUtil.showToast(mContext, "订单取消成功");
                    } else {
                        ToastUtil.showToast(mContext, "申请已提交，等待系统退款");
                    }

                } else {
                    if (type == 1) {
                        ToastUtil.showToast(mContext, TextUtils.isEmpty(msg) ? "订单取消失败" : msg);
                    } else {
                        ToastUtil.showToast(mContext, TextUtils.isEmpty(msg) ? "申请退款失败" : msg);
                    }
                }
                dismiss();
            }));
        });
    }
}
