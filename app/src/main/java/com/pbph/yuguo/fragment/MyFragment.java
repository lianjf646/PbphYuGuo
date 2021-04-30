package com.pbph.yuguo.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.activity.AccountManagementActivity;
import com.pbph.yuguo.activity.AccountSecurityActivity;
import com.pbph.yuguo.activity.BalanceRechargeActivity;
import com.pbph.yuguo.activity.ChoiceAddressListActivity;
import com.pbph.yuguo.activity.CouponActivity;
import com.pbph.yuguo.activity.CustomerServiceActivity;
import com.pbph.yuguo.activity.IntegralActivity;
import com.pbph.yuguo.activity.InviteActivity;
import com.pbph.yuguo.activity.LoginActivity;
import com.pbph.yuguo.activity.MessageActivity;
import com.pbph.yuguo.activity.MyAddressListActivity;
import com.pbph.yuguo.activity.MyOrderActivity;
import com.pbph.yuguo.activity.MyRedBagActivity;
import com.pbph.yuguo.activity.OpinionFeedbackActivity;
import com.pbph.yuguo.activity.SalesServiceActivity;
import com.pbph.yuguo.activity.SettingActivity;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseFragment;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.msg.SwitchFragmentMsg;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetCustomerInfoByIdRequest;
import com.pbph.yuguo.request.GetNoReadMessageCountRequest;
import com.pbph.yuguo.request.GetOrderNumRequest;
import com.pbph.yuguo.response.GetCustomerInfoByIdResponse;
import com.pbph.yuguo.response.GetOrderNumResponse;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.MoneyHelper;
import com.pbph.yuguo.util.RxBusF;

/**
 * Created by 连嘉凡 on 2018/8/7.
 */

public class MyFragment extends BaseFragment implements View.OnClickListener {
    private Button btn_address;

    private TextView tvMyAddress;//我的地址
    private LinearLayout linearIntegral;

    //账户相关
    private View viewMyHeardLayout;
    private View viewNotLogin;
    private Button btnLogin;
    private ImageView ivHeardIcon;
    private LinearLayout llCoupon;
    private TextView tvName;          // 用户名
    private TextView tvVipOffDate;   //  会员时间
    private TextView tvWithdrawcash; // 储值 元
    private TextView tvRedBag;       //红包个
    private TextView tvCoupon;       //优惠券张
    private TextView tvIntegral;     //积分 分
    private ImageView ivVipHuangguan; //皇冠

    private TextView tv_mian_msg_count;


    //订单相关
    private View viewMyBottomView;
    private TextView tvOrderAll;
    private TextView tvObligation;
    private TextView tvSend;
    private TextView tvReceive;
    private TextView tvEvaluation;
    private TextView tvAfterSales;
    private TextView tvObligationNum; //待付款 数量
    private TextView tvSendNum;        //代发货数量
    private TextView tvReceiveNum;     // 待收货数量
    private TextView tvEvaluationNum;  //待评价数量


    //设置相关
    private TextView tvAccountSecurity;
    private TextView tvSetting;

    private TextView tvContactCustomerService;// 联系客服

    private LinearLayout linearMyRedBag;
    private Button btnVip;
    private TextView tvOpinionFeedback;
    private ImageView ivInvite;// 邀请有礼

    GetCustomerInfoByIdResponse.DataBean.CustomerBean customerBean;

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initView() {
        btn_address = mContentView.findViewById(R.id.btn_address);
        btn_address.setOnClickListener(v -> {
            startActivity(new Intent(mContext, ChoiceAddressListActivity.class));
        });
        tvMyAddress = mContentView.findViewById(R.id.tv_my_address);
        tvMyAddress.setOnClickListener(this);
        linearIntegral = mContentView.findViewById(R.id.linear_integral);
        linearIntegral.setOnClickListener(this);

        mContentView.findViewById(R.id.linear_my_withdrawcash).setOnClickListener(this);
        linearMyRedBag = mContentView.findViewById(R.id.linear_my_red_bag);
        linearMyRedBag.setOnClickListener(this);
        btnVip = mContentView.findViewById(R.id.btn_vip);
        btnVip.setOnClickListener(this);
        tvOpinionFeedback = mContentView.findViewById(R.id.tv_opinion_feedback);
        tvOpinionFeedback.setOnClickListener(this);

        viewMyHeardLayout = mContentView.findViewById(R.id.view_my_heard_layout);
        viewNotLogin = mContentView.findViewById(R.id.view_not_login);
        btnLogin = viewNotLogin.findViewById(R.id.btn_login);
        ivHeardIcon = viewMyHeardLayout.findViewById(R.id.iv_heard_icon);
        tvName = viewMyHeardLayout.findViewById(R.id.tv_name);
        tvVipOffDate = viewMyHeardLayout.findViewById(R.id.tv_vip_off_date);
        ivVipHuangguan = viewMyHeardLayout.findViewById(R.id.iv_vip_huangguan);

        btnLogin.setOnClickListener(v -> {
            if (!YuGuoApplication.isLogin()) {
                startActivity(new Intent(getContext(), LoginActivity.class));
                return;
            }
        });

        viewMyBottomView = mContentView.findViewById(R.id.view_my_bottom_view);
        tvOrderAll = viewMyBottomView.findViewById(R.id.tv_order_all);
        tvObligation = viewMyBottomView.findViewById(R.id.tv_obligation);
        tvSend = viewMyBottomView.findViewById(R.id.tv_send);
        tvObligationNum = viewMyBottomView.findViewById(R.id.tv_obligation_num);
        tvSendNum = viewMyBottomView.findViewById(R.id.tv_send_num);
        tvReceiveNum = viewMyBottomView.findViewById(R.id.tv_receive_num);
        tvEvaluationNum = viewMyBottomView.findViewById(R.id.tv_evaluation_num);

        tvWithdrawcash = viewMyBottomView.findViewById(R.id.tv_withdrawcash);
        tvRedBag = viewMyBottomView.findViewById(R.id.tv_red_bag);
        tvCoupon = viewMyBottomView.findViewById(R.id.tv_coupon);
        tvIntegral = viewMyBottomView.findViewById(R.id.tv_integral);
        llCoupon = viewMyBottomView.findViewById(R.id.ll_coupon);
        ivInvite = viewMyBottomView.findViewById(R.id.iv_invite);

        ivInvite.setOnClickListener(this);
        ivHeardIcon.setOnClickListener(this);
        llCoupon.setOnClickListener(this);


        tvReceive = viewMyBottomView.findViewById(R.id.tv_receive);
        tvEvaluation = viewMyBottomView.findViewById(R.id.tv_evaluation);
        tvAfterSales = viewMyBottomView.findViewById(R.id.tv_after_sales);
        tvAccountSecurity = viewMyBottomView.findViewById(R.id.tv_account_security);
        tvSetting = viewMyBottomView.findViewById(R.id.tv_setting);
        tvContactCustomerService = viewMyBottomView.findViewById(R.id.tv_contact_customer_service);
        tvOrderAll.setOnClickListener(this);
        tvObligation.setOnClickListener(this);
        tvSend.setOnClickListener(this);
        tvReceive.setOnClickListener(this);
        tvEvaluation.setOnClickListener(this);
        tvAfterSales.setOnClickListener(this);
        tvAccountSecurity.setOnClickListener(this);
        tvSetting.setOnClickListener(this);
        tvContactCustomerService.setOnClickListener(this);


        mContentView.findViewById(R.id.iv_mian_msg).setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                if (!YuGuoApplication.isLogin()) {
                    return;
                }
                startActivity(new Intent(getContext(), MessageActivity.class));
            }
        });

        tv_mian_msg_count = mContentView.findViewById(R.id.tv_mian_msg_count);
        tv_mian_msg_count.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();


        if (!YuGuoApplication.isLogin()) {
            viewNotLogin.setVisibility(View.VISIBLE);
            viewMyHeardLayout.setVisibility(View.GONE);
            tvObligationNum.setVisibility(View.GONE);
            tvSendNum.setVisibility(View.GONE);
            tvReceiveNum.setVisibility(View.GONE);
            tvEvaluationNum.setVisibility(View.GONE);

            tvWithdrawcash.setText("0元");
            tvCoupon.setText("0张");
            tvIntegral.setText("0分");

            tv_mian_msg_count.setVisibility(View.INVISIBLE);
        } else {

            ivHeardIcon.setImageResource(R.drawable.wodeyidenglu_touxiang);

            viewMyHeardLayout.setVisibility(View.VISIBLE);
            viewNotLogin.setVisibility(View.GONE);
            getCustomerInfoById();
            getOrderStatusNum();

            getNoReadMessageCount();
        }
    }

    /**
     * 获取订单状态数量
     */
    private void getOrderStatusNum() {
        HttpAction.getInstance().getOrderStatusNum(new GetOrderNumRequest(YuGuoApplication.userInfo.getCustomerId())).subscribe(new BaseObserver<>(mContext, response -> {
            GetOrderNumResponse.DataBean.CountBean countBean = response.getData().getCount();
            if (response.getData().getCount() != null) {
                tvNumGoneOrVisible(tvObligationNum, countBean.getPendingPaymentCount());//	待付款数量
                tvNumGoneOrVisible(tvSendNum, countBean.getPendingDeliveryCount());//	  待发货数量
                tvNumGoneOrVisible(tvReceiveNum, countBean.getAlreadyShippedCount());//	已发货数量
                tvNumGoneOrVisible(tvEvaluationNum, countBean.getWaitEvaluateCount());// 	待评价数量
            }
        }));
    }

    /**
     * @param view TextView
     * @param num  数量
     */
    private void tvNumGoneOrVisible(TextView view, int num) {
        if (num == 0) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            if (num > 99) {
                view.setText("99+");
            } else {
                view.setText(String.valueOf(num));
            }
        }
    }

    //根据用户id查询用户详情
    private void getCustomerInfoById() {
        HttpAction.getInstance().getCustomerInfoById(new GetCustomerInfoByIdRequest(YuGuoApplication.userInfo.getCustomerId())).subscribe(new BaseObserver<>(mContext, response -> {
            customerBean = response.getData().getCustomer();

            YuGuoApplication.userInfo.setCustomerPhone(customerBean.getCustomerPhone());

            YuGuoApplication.userInfo.setCustomerLevelType(customerBean.getCustomerLevelType());

            GlideUtil.displayCircleBitmap(mContext, customerBean.getCustomerImgUrl(), ivHeardIcon);
            tvName.setText(customerBean.getCustomerName());
            if (customerBean.getCustomerLevelType() == 0) { //会员类型（0普通，1试用，2正式）
                btnVip.setText("普通用户");
                ivVipHuangguan.setImageResource(R.drawable.mineyidenglu_huangguan_hui);
                tvVipOffDate.setText("");

            } else if (customerBean.getCustomerLevelType() == 1) {
                btnVip.setText("体验会员");
                ivVipHuangguan.setImageResource(R.drawable.wodeyidenglu_huangguan);
                tvVipOffDate.setText(customerBean.getCustomerVipExpireTimeString());
                tvVipOffDate.append("会员到期");
            } else {
                btnVip.setText("VIP会员");
                ivVipHuangguan.setImageResource(R.drawable.wodeyidenglu_huangguan);
                tvVipOffDate.setText(customerBean.getCustomerVipExpireTimeString());
                tvVipOffDate.append("会员到期");
            }

            tvWithdrawcash.setText(MoneyHelper.getInstance4Fen(customerBean.getStoredMoney()).change2Yuan().getString());
            tvWithdrawcash.append("元");
//                    tvRedBag.setText(MoneyHelper.getInstance4Fen(customerBean
// .getRedPackageMoney())
//                            .change2Yuan().getString());
//                    tvRedBag.append("元");
            tvCoupon.setText(String.valueOf(response.getData().getCouponNo()));
            tvCoupon.append("张");
            tvIntegral.setText(String.valueOf(customerBean.getCustomerTotalPoint()));
            tvIntegral.append("分");

        }));

    }

    private void getAfterSaleRule() {
//        HttpAction.getInstance().getSysConfig(new GetSysConfigRequest()).subscribe(new
// BaseObserver<>(mContext, response -> {
//            int code = response.getCode();
//            if (code == 200) {
//                GetSysConfigResponse.DataBean data = response.getData();
//                String phone = data.getSysConfig().getServicePhone();
//                ToastDialog.show(mContext, "请联系客服", "客服电话：" + phone, () -> {
//                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +
// phone));//跳转到拨号界面，同时传递电话号码
//                    startActivity(dialIntent);
//                });
//            }
//        }));
        startActivity(new Intent(getContext(), CustomerServiceActivity.class));
    }

    @Override
    public void onClick(View v) {
        if (!YuGuoApplication.isLogin()) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            return;
        }

        switch (v.getId()) {
            case R.id.iv_heard_icon:
                startActivity(new Intent(mContext, AccountManagementActivity.class));
                break;
            case R.id.ll_coupon:
                startActivity(new Intent(mContext, CouponActivity.class));
                break;
            case R.id.tv_my_address: {
                startActivity(new Intent(mContext, MyAddressListActivity.class).putExtra(MyAddressListActivity.REMOVE_STATE, 1));
            }
            break;
            case R.id.linear_integral: {
                startActivity(new Intent(mContext, IntegralActivity.class));
            }
            break;

            case R.id.linear_my_withdrawcash: {
                startActivity(new Intent(mContext, BalanceRechargeActivity.class));
            }
            break;
            case R.id.linear_my_red_bag: {
                startActivity(new Intent(mContext, MyRedBagActivity.class));
            }
            break;
            case R.id.tv_order_all: {
                //全部订单
                Intent intentAllOrder = new Intent(mContext, MyOrderActivity.class);
                intentAllOrder.putExtra("orderStatus", 0);
                startActivity(intentAllOrder);
            }

            break;
            case R.id.tv_obligation: {
                //待支付
                Intent intentObligation = new Intent(mContext, MyOrderActivity.class);
                intentObligation.putExtra("orderStatus", 1);
                startActivity(intentObligation);
            }

            break;
            case R.id.tv_send: {
                //待发货
                Intent intentSend = new Intent(mContext, MyOrderActivity.class);
                intentSend.putExtra("orderStatus", 2);
                startActivity(intentSend);
            }

            break;
            case R.id.tv_receive: {
                //待收货
                Intent intentReceive = new Intent(mContext, MyOrderActivity.class);
                intentReceive.putExtra("orderStatus", 3);
                startActivity(intentReceive);
            }

            break;
            case R.id.tv_evaluation: {
                //待评价
                Intent intentEvaluation = new Intent(mContext, MyOrderActivity.class);
                intentEvaluation.putExtra("orderStatus", 4);
                startActivity(intentEvaluation);
            }

            break;
            case R.id.tv_after_sales:
                //售后
                Intent intentAfterSales = new Intent(mContext, SalesServiceActivity.class);
                startActivity(intentAfterSales);
                break;
            case R.id.tv_account_security:
                //账户与安全
                startActivity(new Intent(mContext, AccountSecurityActivity.class));
                break;
            case R.id.tv_setting:
                //设置
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.tv_vip: {
//                startActivity(new Intent(mContext, VipFragment.class));
            }
            break;
            case R.id.tv_opinion_feedback: {
                startActivity(new Intent(mContext, OpinionFeedbackActivity.class));
            }

            break;

            case R.id.tv_contact_customer_service: {
                getAfterSaleRule();

            }
            break;
            case R.id.btn_vip: {
                RxBusF.post0(new SwitchFragmentMsg(true));
            }
            break;
            case R.id.iv_invite: {
                startActivity(new Intent(mContext, InviteActivity.class));
            }
            break;
            default: {


            }
            break;

        }
    }

    private void getNoReadMessageCount() {

        if (!YuGuoApplication.isLogin()) {
            tv_mian_msg_count.setVisibility(View.INVISIBLE);
            return;
        }

        if (TextUtils.isEmpty(tv_mian_msg_count.getText().toString())) {
            tv_mian_msg_count.setVisibility(View.INVISIBLE);
        } else {
            tv_mian_msg_count.setVisibility(View.VISIBLE);
        }

        HttpAction.getInstance().getNoReadMessageCount(new GetNoReadMessageCountRequest(YuGuoApplication.userInfo.getCustomerId())).subscribe(new BaseObserver<>(mContext,
                (response -> {

            if (200 != response.getCode()) {
//                Toast.makeText(getContext(), response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }
            int c = response.getData().getCount();
            if (c <= 0) {
                tv_mian_msg_count.setVisibility(View.INVISIBLE);
            } else {
                tv_mian_msg_count.setVisibility(View.VISIBLE);
            }
            tv_mian_msg_count.setText(String.valueOf(c));

        })));
    }
}
