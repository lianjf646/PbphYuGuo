package com.pbph.yuguo.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.VoucherAdapter;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.interfaces.PopwinClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetCouponListForSubmitOrderRequest;
import com.pbph.yuguo.request.GetVoucherListForSubmitOrderRequest;
import com.pbph.yuguo.response.CouponResponse;
import com.pbph.yuguo.response.GoodsInfoBean;
import com.pbph.yuguo.util.PublicViewUtil;
import com.sobot.chat.utils.ToastUtil;

import java.util.List;

/**
 * 选择抵用券
 * Created by Administrator on 2018/8/6 0006.
 */

public class VoucherPopwin extends PopupWindow {
    private Context mContext;
    private RecyclerView rvVoucher;
    private RelativeLayout rlContainer;
    private ImageView ivDismiss;
    private TextView tvOk, tvTitle;
    public VoucherAdapter adapter;

    private int voucherType;
    private List<GoodsInfoBean> storeGoodsListBeans;
    private long goodsSumPrice;
    private View layoutNoList;

    //点击事件监听
    private PopwinClickListener mListener;

    private int choose_id = -1;

    /**
     * @param context       上下文
     * @param voucherType   类型：1抵扣券  2优惠券
     * @param goodsSumPrice 商品总价钱 优惠券是需要
     */
    public VoucherPopwin(Context context, int voucherType, List<GoodsInfoBean> storeGoodsListBeans, long goodsSumPrice) {
        this.mContext = context;
        this.voucherType = voucherType;
        this.storeGoodsListBeans = storeGoodsListBeans;
        this.goodsSumPrice = goodsSumPrice;
        init();
    }

    public void setDatas(List<GoodsInfoBean> storeGoodsListBeans, long goodsSumPrice) {
        this.storeGoodsListBeans = storeGoodsListBeans;
        this.goodsSumPrice = goodsSumPrice;
        layoutNoList.setVisibility(View.VISIBLE);
        rvVoucher.setVisibility(View.GONE);
        getCouponListForSubmitOrder();
    }

    private void init() {
        //inflate布局
        View view = View.inflate(mContext, R.layout.popwin_voucher, null);
        setContentView(view);
        initView(view);
        initData();
        initClick();
    }

    public void show(View parent) {
        setAnimationStyle(R.style.mypopwindow_anim_style);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setFocusable(true);
        setOutsideTouchable(true);

        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        PublicViewUtil.backgroundAlpha((Activity) mContext, 0.6f);
        setOnDismissListener(() -> PublicViewUtil.backgroundAlpha((Activity) mContext, 1f));
        initData();
        if (voucherType == 1) {
            tvTitle.setText("抵扣券");
        } else {
            tvTitle.setText("优惠券");
        }
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();
    }

    private void initView(View view) {
        layoutNoList = view.findViewById(R.id.layout_no_list);
        TextView tips = layoutNoList.findViewById(R.id.tv_tips);
        rvVoucher = view.findViewById(R.id.rv_voucher);
        ivDismiss = view.findViewById(R.id.iv_dismiss);
        rlContainer = view.findViewById(R.id.rl_container);
        tvOk = view.findViewById(R.id.tv_ok);
        tvTitle = view.findViewById(R.id.tv_title);

        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.custom_divider));
        rvVoucher.addItemDecoration(divider);

        adapter = new VoucherAdapter(mContext);

        rvVoucher.setLayoutManager(new LinearLayoutManager(mContext));
        rvVoucher.setAdapter(adapter);
        adapter.recyclerViewItemClickListener((v, id) -> {
            if (id == adapter.pos_id) {
                adapter.pos_id = -1;
            } else {
                adapter.pos_id = id;
            }
            adapter.notifyDataSetChanged();
        });

        if (voucherType == 1) {
            tvTitle.setText("抵扣券");
            tips.setText("暂无抵扣券");
        } else {
            tvTitle.setText("优惠券");
            tips.setText("暂无优惠券");
        }
        layoutNoList.setVisibility(View.VISIBLE);
        rvVoucher.setVisibility(View.GONE);
    }

    private void initData() {
        if (voucherType == 1) {
            //抵扣券
            getVoucherListForSubmitOrder();
        } else {
            //优惠券
            getCouponListForSubmitOrder();
        }
    }

    private void getVoucherListForSubmitOrder() {
        int customerId = YuGuoApplication.userInfo.getCustomerId();
        if (customerId == -1) {
            ToastUtil.showToast(mContext, "请重新登录");
            return;
        }
        GetVoucherListForSubmitOrderRequest request = new GetVoucherListForSubmitOrderRequest(customerId, 1);
        HttpAction.getInstance().getVoucherListForSubmitOrder(request).subscribe(new BaseObserver<>(mContext, response -> {
            int code = response.getCode();
            if (code == 200) {
                CouponResponse.DataBean data = response.getData();
                List<CouponResponse.DataBean.ListBean> couponList = data.getList();
                setAdapter(couponList);
            } else {
                layoutNoList.setVisibility(View.VISIBLE);
                rvVoucher.setVisibility(View.GONE);
            }
        }, (code, message) -> {
            layoutNoList.setVisibility(View.VISIBLE);
            rvVoucher.setVisibility(View.GONE);
        }));
    }

    private void getCouponListForSubmitOrder() {
        int customerId = YuGuoApplication.userInfo.getCustomerId();
        if (customerId == -1) {
            ToastUtil.showToast(mContext, "请重新登录");
            return;
        }
        GetCouponListForSubmitOrderRequest request = new GetCouponListForSubmitOrderRequest(customerId, storeGoodsListBeans, goodsSumPrice, 1);
        HttpAction.getInstance().getCouponListForSubmitOrder(request).subscribe(new BaseObserver<>(mContext, response -> {
            int code = response.getCode();
            if (code == 200) {
                CouponResponse.DataBean data = response.getData();
                List<CouponResponse.DataBean.ListBean> couponList = data.getList();
                setAdapter(couponList);
            } else {
                layoutNoList.setVisibility(View.VISIBLE);
                rvVoucher.setVisibility(View.GONE);
            }
        }, (code, message) -> {
            layoutNoList.setVisibility(View.VISIBLE);
            rvVoucher.setVisibility(View.GONE);
        }));
    }

    private void initClick() {
        ivDismiss.setOnClickListener(v -> {
            dismiss();
            adapter.pos_id = choose_id;
            adapter.notifyDataSetChanged();
        });
        rlContainer.setOnClickListener(v -> {
            dismiss();
            adapter.pos_id = choose_id;
            adapter.notifyDataSetChanged();
        });

        tvOk.setOnClickListener(v -> {
            dismiss();
            choose_id = adapter.pos_id;
            if (mListener != null) {
                mListener.onClickListener(choose_id);
            }
        });
    }

    private void setAdapter(List<CouponResponse.DataBean.ListBean> couponList) {

        adapter.mVoucherList.clear();

        if (couponList != null) {
            boolean b = false;
            for (CouponResponse.DataBean.ListBean vo : couponList) {
                if (vo.getCouponId() == adapter.pos_id) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                adapter.pos_id = -1;
            }
            adapter.mVoucherList.addAll(couponList);
        } else {
            adapter.pos_id = -1;
        }

        adapter.notifyDataSetChanged();

        if (couponList == null || couponList.size() == 0) {
            layoutNoList.setVisibility(View.VISIBLE);
            rvVoucher.setVisibility(View.GONE);
        } else {
            layoutNoList.setVisibility(View.GONE);
            rvVoucher.setVisibility(View.VISIBLE);
        }
    }

    //设置外部调用的点击监听
    public void setOnPopWinClickListener(PopwinClickListener listener) {
        this.mListener = listener;
    }
}
