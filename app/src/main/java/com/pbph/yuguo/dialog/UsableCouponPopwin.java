package com.pbph.yuguo.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.UsableCouponAdapter;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetReceiveCouponRequest;
import com.pbph.yuguo.request.GetUsableCouponListForGoodsRequest;
import com.pbph.yuguo.response.CouponResponse;
import com.pbph.yuguo.util.PublicViewUtil;
import com.sobot.chat.utils.ToastUtil;

import java.util.List;

/**
 * Created by zyp on 2018/9/5 0005.
 * class note:
 */

public class UsableCouponPopwin extends PopupWindow {
    private Context mContext;
    private RecyclerView rvVoucher;
    private RelativeLayout rlContainer;
    private ImageView ivDismiss;
    private TextView tvOk;
    private TextView tvTitle;
    private List<CouponResponse.DataBean.ListBean> couponList;
    private UsableCouponAdapter adapter;

    private View layoutNoList;

    private int goodsId;

    public UsableCouponPopwin(Context context, int goodsId) {
        this.mContext = context;
        this.goodsId = goodsId;
        init();
    }

    private void init() {
        //inflate布局
        View view = View.inflate(mContext, R.layout.popwin_voucher, null);
        setContentView(view);
        setOnDismissListener(() -> PublicViewUtil.backgroundAlpha((Activity) mContext, 1f));
        initView(view);
        initData();
    }

    private void initView(View view) {
        layoutNoList = view.findViewById(R.id.layout_no_list);
        TextView tips = layoutNoList.findViewById(R.id.tv_tips);
        rvVoucher = view.findViewById(R.id.rv_voucher);
        ivDismiss = view.findViewById(R.id.iv_dismiss);
        rlContainer = view.findViewById(R.id.rl_container);
        tvTitle = view.findViewById(R.id.tv_title);
        tvOk = view.findViewById(R.id.tv_ok);

        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.custom_divider));
        rvVoucher.addItemDecoration(divider);

        tvTitle.setText("优惠券");
        tips.setText("暂无可领取的优惠券");
    }

    public void show(View parent) {
        setAnimationStyle(R.style.mypopwindow_anim_style);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setFocusable(true);
        setOutsideTouchable(true);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        PublicViewUtil.backgroundAlpha((Activity) mContext, 0.6f);
        update();
        initClick();
    }

    private void initData() {
        int customerId = YuGuoApplication.userInfo.getCustomerId();
        if (customerId == -1) {
            ToastUtil.showToast(mContext, "请重新登录");
            return;
        }
        GetUsableCouponListForGoodsRequest request = new GetUsableCouponListForGoodsRequest(goodsId, customerId, 1);
        HttpAction.getInstance().getUsableCouponListForGoods(request).subscribe(new BaseObserver<>(mContext, response -> {
            int code = response.getCode();
            if (code == 200) {
                CouponResponse.DataBean data = response.getData();
                couponList = data.getList();
                setAdapter();
            } else {
                layoutNoList.setVisibility(View.VISIBLE);
                rvVoucher.setVisibility(View.GONE);
            }
        }, (code, msg) -> {
            layoutNoList.setVisibility(View.VISIBLE);
            rvVoucher.setVisibility(View.GONE);
        }));
    }

    private void initClick() {

        tvOk.setOnClickListener(v -> {
            dismiss();
        });
        rlContainer.setOnClickListener(v -> dismiss());
        ivDismiss.setOnClickListener(v -> dismiss());
    }

    private void refreshList() {
        int customerId = YuGuoApplication.userInfo.getCustomerId();
        if (customerId == -1) {
            ToastUtil.showToast(mContext, "请重新登录");
            return;
        }
        GetUsableCouponListForGoodsRequest request = new GetUsableCouponListForGoodsRequest(goodsId, customerId, 1);
        HttpAction.getInstance().getUsableCouponListForGoods(request).subscribe(new BaseObserver<>(mContext, response -> {
            int code = response.getCode();
            if (code == 200) {
                CouponResponse.DataBean data = response.getData();
                couponList = data.getList();
                adapter.refresh(couponList);
            }
        }));
    }

    private void setAdapter() {
        adapter = new UsableCouponAdapter(mContext, couponList);

        rvVoucher.setLayoutManager(new LinearLayoutManager(mContext));
        rvVoucher.setAdapter(adapter);
        if (couponList == null || couponList.size() == 0) {
            layoutNoList.setVisibility(View.VISIBLE);
            rvVoucher.setVisibility(View.GONE);
        } else {
            layoutNoList.setVisibility(View.GONE);
            rvVoucher.setVisibility(View.VISIBLE);
        }
        adapter.recyclerViewItemClickListener((view, position) -> {
            //领取优惠券
            int customerId = YuGuoApplication.userInfo.getCustomerId();
            if (customerId == -1) {
                ToastUtil.showToast(mContext, "登陆后才能领取");
            }
            GetReceiveCouponRequest request = new GetReceiveCouponRequest(customerId, couponList.get(position).getCouponActivityId(), goodsId);
            HttpAction.getInstance().receiveCoupon(request).subscribe(new BaseObserver<>(mContext, response -> {
                int code = response.getCode();
                String msg = response.getMsg();
                if (200 == code) {
                    refreshList();
                    ToastUtil.showToast(mContext, "领取成功");
                } else {
                    ToastUtil.showToast(mContext, TextUtils.isEmpty(msg) ? "领取失败" : msg);
                }
            }));
        });
    }
}
