package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.InviteFragmentPageAdapter;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.dialog.MySharePopRedBagWindow;
import com.pbph.yuguo.fragment.MyInviteListFragment;
import com.pbph.yuguo.fragment.ShareRankingListFragment;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetInviteICouponListResquest;
import com.pbph.yuguo.request.GetInviteVipCouponListResquest;
import com.pbph.yuguo.response.GetCustomerInfoByIdResponse;
import com.pbph.yuguo.response.GetInviteICouponListResponse;
import com.pbph.yuguo.response.GetInviteVipCouponListResponse;
import com.pbph.yuguo.util.MoneyHelper;
import com.pbph.yuguo.wxutil.WechatUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/12/25.
 */

public class InviteActivity extends BaseActivity {


    private TextView tv_guizeshuoming;
    private TextView tv_vip_ren_num;
    private TextView tv_vip_ren_num2;
    private TextView tv_coupon_nxt_pernum;
    private TextView tv_coupon_nxt;
    private View view_friend_share_num;
    private TextView tv_friend_share_num;
    private RelativeLayout rl_coupon_bg;
    private TextView view_friend_share_coupon_money;
    private TextView view_friend_share_coupon_num_pre;
    private TextView view_friend_share_coupon_num;
    private View include_coupon_line;
    private View view_friend_share_num_1;
    private TextView tv_friend_share_num_1;
    private RelativeLayout rl_coupon_bg_1;
    private TextView view_friend_share_coupon_money_1;
    private TextView view_friend_share_coupon_num_pre_1;
    private TextView view_friend_share_coupon_num_1;
    private View view_friend_share_num_2;
    private TextView tv_friend_share_num_2;
    private RelativeLayout rl_coupon_bg_2;
    private TextView view_friend_share_coupon_money_2;
    private TextView view_friend_share_coupon_num_pre_2;
    private TextView view_friend_share_coupon_num_2;
    private TabLayout mTabLayout;
    private TextView tv_list_head1;
    private TextView tv_list_head2;
    private TextView tv_list_head3;

    ArrayList<Fragment> fragments = new ArrayList<>();
    private InviteFragmentPageAdapter mAdapter;
    private Button button;
    MySharePopRedBagWindow pop;
    private TextView tv_coupon_require;
    private TextView tv_coupon_name;
    private GetCustomerInfoByIdResponse.DataBean.CustomerBean customerBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        initTitle(TITLE_STYLE_WHITE, "邀请有礼", true, false);
        initView();
        initData();
    }

    private void initData() {

    }

    @Override
    public void onStart() {
        super.onStart();
        //邀请好友获得优惠券
        getInviteICouponList();
        //邀请会员获得优惠券
        getInviteVipCouponList();

//        getInviteGallopList();
    }

    private void getInviteVipCouponList() {
        HttpAction.getInstance().getInviteVipCouponList(new GetInviteVipCouponListResquest(YuGuoApplication.userInfo.getCustomerId())).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }
            GetInviteVipCouponListResponse.DataBean.InvitationVipCouponVOBean data = response.getData().getInvitationVipCouponVO();
            String frtMoney = MoneyHelper.getInstance4Fen(data.getCouponPrice()).change2Yuan().getDouble() + "";
            String ling = frtMoney.substring(frtMoney.length() - 2, frtMoney.length());
            if (ling.equals("00")) {
                String frtMoneys = MoneyHelper.getInstance4Fen(data.getCouponPrice()).change2Yuan().getInt() + "";
                tv_vip_ren_num2.setText(frtMoneys + "");
                view_friend_share_coupon_money.setText("" + frtMoneys);
            } else {
                double f = MoneyHelper.getInstance4Fen(data.getCouponPrice()).change2Yuan().getDouble();
                BigDecimal b = new BigDecimal(f);
                //保留1位小数
                double f1 = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                tv_vip_ren_num2.setText(f1 + "");
                view_friend_share_coupon_money.setText("" + f1);
            }

            //立返邀请人 *元无门槛优惠券

            tv_friend_share_num.setText("已邀请");
            tv_friend_share_num.append(String.valueOf(data.getInviteNum()));
            tv_friend_share_num.append("人获得");

            view_friend_share_coupon_num.setText("" + data.getCouponNum());
            if (data.getCouponNum() <= 0) {
                //灰色
                view_friend_share_num.setBackgroundResource(R.drawable.round_point_bg);
                tv_friend_share_num.setTextColor(getResources().getColor(R.color.black_gray));
                rl_coupon_bg.setBackgroundResource(R.drawable.quan2);
                view_friend_share_coupon_num_pre.setTextColor(getResources().getColor(R.color.black_gray));
                view_friend_share_coupon_num.setTextColor(getResources().getColor(R.color.black_gray));

            } else {
                //绿色
                view_friend_share_num.setBackgroundResource(R.drawable.round_point_bg1);
                tv_friend_share_num.setTextColor(getResources().getColor(R.color.main_color));
                rl_coupon_bg.setBackgroundResource(R.drawable.quan1);
                view_friend_share_coupon_num_pre.setTextColor(getResources().getColor(R.color.main_color));
                view_friend_share_coupon_num.setTextColor(getResources().getColor(R.color.main_color));

            }

        })));

    }

    private void getInviteICouponList() {

        HttpAction.getInstance().getInviteICouponList(new GetInviteICouponListResquest(YuGuoApplication.userInfo.getCustomerId())).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }
            GetInviteICouponListResponse.DataBean data = response.getData();
            if (data.getInviteICouponList() == null) return;
            if (data.getInviteICouponList().size() == 0) return;
            if (data.getInviteICouponList().get(0).getCouponList() == null) return;
            if (data.getInviteICouponList().get(0).getCouponList().size() == 0) return;
            GetInviteICouponListResponse.DataBean.InviteICouponListBean frt = data.getInviteICouponList().get(0);
            GetInviteICouponListResponse.DataBean.InviteICouponListBean.CouponListBean frtItem0 = frt.getCouponList().get(0);
            GetInviteICouponListResponse.DataBean.InviteICouponListBean scd = data.getInviteICouponList().get(1);
            GetInviteICouponListResponse.DataBean.InviteICouponListBean.CouponListBean secItem0 = scd.getCouponList().get(0);
            tv_coupon_require.setText("每成功邀请");
            tv_coupon_require.append(String.valueOf(frt.getInviteNum()));
            tv_coupon_require.append("人");

            String frtMoney = MoneyHelper.getInstance4Fen(frtItem0.getCouponPrice()).change2Yuan().getInt() + "";
            tv_coupon_name.setText("可获得" + frtMoney);
            tv_coupon_name.append("元无门槛优惠券");
            //邀请*人
            tv_coupon_nxt_pernum.setText("邀请\n");
            tv_coupon_nxt_pernum.append(String.valueOf(scd.getInviteNum()));
            tv_coupon_nxt_pernum.append("人");
            String secMoney = MoneyHelper.getInstance4Fen(secItem0.getCouponPrice()).change2Yuan().getInt() + "";
            //可额外获得*元优惠券
            tv_coupon_nxt.setText(secMoney);


            // point1;
            tv_friend_share_num_1.setText("已邀请");
            tv_friend_share_num_1.append(String.valueOf(data.getCustomerNum()));
            tv_friend_share_num_1.append("人获得");

            view_friend_share_coupon_money_1.setText(frtMoney);

            view_friend_share_coupon_num_1.setText(String.valueOf(frtItem0.getCouponNum()));

            // point2;
            tv_friend_share_num_2.setText("每邀请");
            tv_friend_share_num_2.append(String.valueOf(scd.getInviteNum()));
            tv_friend_share_num_2.append("人 额外获得");

            view_friend_share_coupon_money_2.setText(secMoney);

            view_friend_share_coupon_num_2.setText(String.valueOf(secItem0.getCouponNum()));

            if (frtItem0.getCouponNum() <= 0) {
                view_friend_share_num_1.setBackgroundResource(R.drawable.round_point_bg);
                tv_friend_share_num_1.setTextColor(getResources().getColor(R.color.black_gray));
                rl_coupon_bg_1.setBackgroundResource(R.drawable.quan2);
                view_friend_share_coupon_num_pre_1.setTextColor(getResources().getColor(R.color.black_gray));
                view_friend_share_coupon_num_1.setTextColor(getResources().getColor(R.color.black_gray));

            } else {
                view_friend_share_num_1.setBackgroundResource(R.drawable.round_point_bg1);
                tv_friend_share_num_1.setTextColor(getResources().getColor(R.color.main_color));
                rl_coupon_bg_1.setBackgroundResource(R.drawable.quan1);
                view_friend_share_coupon_num_pre_1.setTextColor(getResources().getColor(R.color.main_color));
                view_friend_share_coupon_num_1.setTextColor(getResources().getColor(R.color.main_color));

            }
            if (secItem0.getCouponNum() <= 0) {
                view_friend_share_num_2.setBackgroundResource(R.drawable.round_point_bg);
                tv_friend_share_num_2.setTextColor(getResources().getColor(R.color.black_gray));
                rl_coupon_bg_2.setBackgroundResource(R.drawable.quan2);
                view_friend_share_coupon_num_pre_2.setTextColor(getResources().getColor(R.color.black_gray));
                view_friend_share_coupon_num_2.setTextColor(getResources().getColor(R.color.black_gray));

                include_coupon_line.setBackgroundResource(R.color.dark_gray);
            } else {
                view_friend_share_num_2.setBackgroundResource(R.drawable.round_point_bg1);
                tv_friend_share_num_2.setTextColor(getResources().getColor(R.color.main_color));
                rl_coupon_bg_2.setBackgroundResource(R.drawable.quan1);
                view_friend_share_coupon_num_pre_2.setTextColor(getResources().getColor(R.color.main_color));
                view_friend_share_coupon_num_2.setTextColor(getResources().getColor(R.color.main_color));

                include_coupon_line.setBackgroundResource(R.color.main_color);
            }


        })));
    }

    private void initView() {
        //TODO 邀请会员
        tv_vip_ren_num = findViewById(R.id.tv_vip_ren_num);
        tv_vip_ren_num2 = findViewById(R.id.tv_vip_ren_num2);


        //TODO 邀请好友
        tv_coupon_require = findViewById(R.id.tv_coupon_require);
        tv_coupon_name = findViewById(R.id.tv_coupon_name);
        tv_coupon_nxt_pernum = findViewById(R.id.tv_coupon_nxt_pernum);
        tv_coupon_nxt = findViewById(R.id.tv_coupon_nxt);

        //TODO 邀请奖励
        //点
        view_friend_share_num = findViewById(R.id.view_friend_share_num);
        //卷bg
        rl_coupon_bg = findViewById(R.id.rl_coupon_bg);
        //*
        view_friend_share_coupon_num_pre = findViewById(R.id.view_friend_share_coupon_num_pre);
        //乘以 *
        view_friend_share_coupon_num = findViewById(R.id.view_friend_share_coupon_num);
        //已邀请0人获得
        tv_friend_share_num = findViewById(R.id.tv_friend_share_num);
        //卷    *元
        view_friend_share_coupon_money = findViewById(R.id.view_friend_share_coupon_money);

        //TODO 2
        include_coupon_line = findViewById(R.id.include_coupon_line);
        //点
        view_friend_share_num_1 = findViewById(R.id.view_friend_share_num_1);
        view_friend_share_num_2 = findViewById(R.id.view_friend_share_num_2);
        //已邀请0人获得
        tv_friend_share_num_1 = findViewById(R.id.tv_friend_share_num_1);
        tv_friend_share_num_2 = findViewById(R.id.tv_friend_share_num_2);
        //卷bg
        rl_coupon_bg_1 = findViewById(R.id.rl_coupon_bg_1);
        rl_coupon_bg_2 = findViewById(R.id.rl_coupon_bg_2);
        //卷    *元
        view_friend_share_coupon_money_1 = findViewById(R.id.view_friend_share_coupon_money_1);
        view_friend_share_coupon_money_2 = findViewById(R.id.view_friend_share_coupon_money_2);
        //乘以 *
        view_friend_share_coupon_num_pre_1 = findViewById(R.id.view_friend_share_coupon_num_pre_1);
        view_friend_share_coupon_num_pre_2 = findViewById(R.id.view_friend_share_coupon_num_pre_2);
        //  元
        view_friend_share_coupon_num_1 = findViewById(R.id.view_friend_share_coupon_num_1);
        view_friend_share_coupon_num_2 = findViewById(R.id.view_friend_share_coupon_num_2);


        //TODO
        LinearLayout ll_1 = findViewById(R.id.ll_1);
        tv_list_head1 = findViewById(R.id.tv_list_head1);
        tv_list_head2 = findViewById(R.id.tv_list_head2);
        tv_list_head3 = findViewById(R.id.tv_list_head3);
        fragments.add(new ShareRankingListFragment());
        fragments.add(new MyInviteListFragment());
        mAdapter = new InviteFragmentPageAdapter(getSupportFragmentManager(), fragments);
        ViewPager mViewPager = findViewById(R.id.vp_main_list);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mAdapter);


        mTabLayout = findViewById(R.id.tl_main_tab);
        // 使用 TabLayout 和 ViewPager 相关联
        mTabLayout.setupWithViewPager(mViewPager);
        // TabLayout指示器添加文本
        mTabLayout.getTabAt(0).setText("排行榜");
        mTabLayout.getTabAt(1).setText("我的邀请详情");
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        ll_1.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        ll_1.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tv_guizeshuoming = findViewById(R.id.tv_guizeshuoming);
        tv_guizeshuoming.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                startActivity(new Intent(InviteActivity.this, MyBrowsersActivity.class)
                        .putExtra("title", "规则说明")
                        .putExtra("url", ConstantData.RULES)
                );
            }
        });

        button = findViewById(R.id.button);
        button.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                if (!YuGuoApplication.isLogin()) {
                    startActivity(new Intent(InviteActivity.this, LoginActivity.class));
                    return;
                }

//                HttpAction.getInstance().getCustomerInfoById(new GetCustomerInfoByIdRequest(YuGuoApplication.userInfo.getCustomerId()))
//                        .subscribe(new BaseObserver<>(mContext, response -> {
//                            customerBean = response.getData().getCustomer();
//
//                            YuGuoApplication.userInfo.setCustomerPhone(customerBean.getCustomerPhone());
//
//                            if (customerBean.getCustomerLevelType() == 2) {
//                                if (pop == null)
//                                    pop = new MySharePopRedBagWindow(mContext, onShareClickListener);
//
//                                pop.showDialog(mContentView);
//                            } else {
//                                showToast("只有VIP会员可以参与活动哦");
//                            }
//                        }));

                if (pop == null)
                    pop = new MySharePopRedBagWindow(mContext, onShareClickListener);

                pop.showDialog(mContentView);

            }
        });
    }

    MySharePopRedBagWindow.ShareOnClickListener onShareClickListener = type -> {
        switch (type) {
            case SHARELIFEWX: {
                WechatUtils.getInstance().initWechatUtils(mContext)
                        .sendWebPage(ConstantData.SHARE_URL + YuGuoApplication.userInfo.getCustomerId(), "一款只卖全球时令好果子的APP", "哈市最受欢迎鲜果售卖终端");
            }
            break;
            case SHARETIMELINE: {
                WechatUtils.getInstance().initWechatUtils(mContext)
                        .sendWebPageTimeLine(ConstantData.SHARE_URL + YuGuoApplication.userInfo.getCustomerId(), "一款只卖全球时令好果子的APP", "");
            }
            break;
        }
    };

    @Override
    public void onLeftClick() {
        finish();
    }
}
