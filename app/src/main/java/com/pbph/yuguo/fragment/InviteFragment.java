package com.pbph.yuguo.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.activity.LoginActivity;
import com.pbph.yuguo.activity.MyBrowsersActivity;
import com.pbph.yuguo.adapter.InviteFragmentPageAdapter;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseFragment;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.dialog.MySharePopRedBagWindow;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetInviteGallopListResquest;
import com.pbph.yuguo.request.GetInviteICouponListResquest;
import com.pbph.yuguo.response.GetInviteGallopListResponse;
import com.pbph.yuguo.response.GetInviteICouponListResponse;
import com.pbph.yuguo.util.MoneyHelper;
import com.pbph.yuguo.wxutil.WechatUtils;

import java.util.ArrayList;

public class InviteFragment extends BaseFragment {


//    TextSwitchView tv_friend_require;

    private TextView tvCouponRequire;
    private TextView tvCouponName;

    private TextView tvCouponNxtPernum;
    private TextView tvCouponNxt;

//    private TextView tvTime;


    private View point1;
    private TextView tvFriendShareNum1;
    private RelativeLayout rl_coupon_bg1;
    private TextView viewFriendShareCouponMoney1;
    private TextView view_friend_share_coupon_num_pre1;
    private TextView viewFriendShareCouponNum1;

    private View include_coupon_line;

    private View point2;
    private TextView tvFriendShareNum2;
    private RelativeLayout rl_coupon_bg2;
    private TextView viewFriendShareCouponMoney2;
    private TextView view_friend_share_coupon_num_pre2;
    private TextView viewFriendShareCouponNum2;


    //////////////
    private TabLayout mTabLayout;


    TextView tv_list_head1;
    TextView tv_list_head2;
    TextView tv_list_head3;

    private ViewPager mViewPager;
    private InviteFragmentPageAdapter mAdapter;
    ArrayList<Fragment> fragments = new ArrayList<>();

    MySharePopRedBagWindow pop;

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_invite;
    }

    @Override
    public void initView() {

//        tv_friend_require = mContentView.findViewById(R.id.tv_friend_require);

        tvCouponRequire = (TextView) mContentView.findViewById(R.id.tv_coupon_require);
        tvCouponName = (TextView) mContentView.findViewById(R.id.tv_coupon_name);

        tvCouponNxtPernum = (TextView) mContentView.findViewById(R.id.tv_coupon_nxt_pernum);
        tvCouponNxt = (TextView) mContentView.findViewById(R.id.tv_coupon_nxt);

//        tvTime = (TextView) mContentView.findViewById(R.id.tv_time);


        View temp = mContentView.findViewById(R.id.include_coupon_num1);
        point1 = temp.findViewById(R.id.view_friend_share_num);
        tvFriendShareNum1 = (TextView) temp.findViewById(R.id.tv_friend_share_num);
        rl_coupon_bg1 = temp.findViewById(R.id.rl_coupon_bg);
        viewFriendShareCouponMoney1 = (TextView) temp.findViewById(R.id.view_friend_share_coupon_money);
        view_friend_share_coupon_num_pre1 = temp.findViewById(R.id.view_friend_share_coupon_num_pre);
        viewFriendShareCouponNum1 = (TextView) temp.findViewById(R.id.view_friend_share_coupon_num);


        include_coupon_line = mContentView.findViewById(R.id.include_coupon_line);

        temp = mContentView.findViewById(R.id.include_coupon_num2);
        point2 = temp.findViewById(R.id.view_friend_share_num);
        tvFriendShareNum2 = (TextView) temp.findViewById(R.id.tv_friend_share_num);
        rl_coupon_bg2 = temp.findViewById(R.id.rl_coupon_bg);
        viewFriendShareCouponMoney2 = (TextView) temp.findViewById(R.id.view_friend_share_coupon_money);
        view_friend_share_coupon_num_pre2 = temp.findViewById(R.id.view_friend_share_coupon_num_pre);
        viewFriendShareCouponNum2 = (TextView) temp.findViewById(R.id.view_friend_share_coupon_num);


        tv_list_head1 = mContentView.findViewById(R.id.tv_list_head1);
        tv_list_head1.setText("排行");
        tv_list_head2 = mContentView.findViewById(R.id.tv_list_head2);
        tv_list_head2.setText("昵称");
        tv_list_head3 = mContentView.findViewById(R.id.tv_list_head3);
        tv_list_head3.setText("邀请人数");

        fragments.add(new ShareRankingListFragment());
        fragments.add(new MyInviteListFragment());

        mAdapter = new InviteFragmentPageAdapter(getChildFragmentManager(), fragments);
        mViewPager = mContentView.findViewById(R.id.vp_main_list);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mAdapter);

        mTabLayout = mContentView.findViewById(R.id.tl_main_tab);

        mTabLayout.addTab(mTabLayout.newTab());
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
                        tv_list_head1.setText("排行");
                        tv_list_head2.setText("昵称");
                        tv_list_head3.setText("邀请人数");
                        break;
                    case 1:
                        tv_list_head1.setText("昵称");
                        tv_list_head2.setText("手机号码");
                        tv_list_head3.setText("当前收益");
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


        mContentView.findViewById(R.id.tv_guizeshuoming).setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                startActivity(new Intent(getContext(), MyBrowsersActivity.class)
                        .putExtra("title", "规则说明")
                        .putExtra("url", "file:///android_asset/rules.html")
                );
            }
        });

        mContentView.findViewById(R.id.button).setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                if (!YuGuoApplication.isLogin()) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    return;
                }
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
                        .sendWebPage(ConstantData.SHARE_URL + YuGuoApplication.userInfo.getCustomerId(), "一款只卖全球时令好果子的APP", "");
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
    public void onStart() {
        super.onStart();

        getInviteICouponList();
        getInviteGallopList();
    }

    private void getInviteICouponList() {

        HttpAction.getInstance().getInviteICouponList(new GetInviteICouponListResquest(YuGuoApplication.userInfo.getCustomerId())).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }
            GetInviteICouponListResponse.DataBean data = response.getData();
            GetInviteICouponListResponse.DataBean.InviteICouponListBean frt = data.getInviteICouponList().get(0);
            GetInviteICouponListResponse.DataBean.InviteICouponListBean.CouponListBean frtItem0 = frt.getCouponList().get(0);
            GetInviteICouponListResponse.DataBean.InviteICouponListBean scd = data.getInviteICouponList().get(1);
            GetInviteICouponListResponse.DataBean.InviteICouponListBean.CouponListBean secItem0 = scd.getCouponList().get(0);

            tvCouponRequire.setText("每成功邀请");
            tvCouponRequire.append(String.valueOf(frt.getInviteNum()));
            tvCouponRequire.append("人");

            String frtMoney = MoneyHelper.getInstance4Fen(frtItem0.getCouponPrice()).change2Yuan().getInt() + "";
            tvCouponName.setText("可获得"+frtMoney);
            tvCouponName.append("元无门槛优惠券");

            tvCouponNxtPernum.setText("邀请\n");
            tvCouponNxtPernum.append(String.valueOf(scd.getInviteNum()));
            tvCouponNxtPernum.append("人");

            String secMoney = MoneyHelper.getInstance4Fen(secItem0.getCouponPrice()).change2Yuan().getInt() + "";
            tvCouponNxt.setText(secMoney);


//            DateUtils dateUtils1 = new DateUtils(data.getTimeStart());
//            DateUtils dateUtils2 = new DateUtils(data.getTimeEnd());
           /* tvTime.setText("活动时间：");
            tvTime.append(dateUtils1.getString(DateUtils.PATTERN_3));
            tvTime.append("-");
            tvTime.append(dateUtils2.getString(DateUtils.PATTERN_3));*/


            ///////////////
            // point1;
            tvFriendShareNum1.setText("已邀请");
            tvFriendShareNum1.append(String.valueOf(data.getCustomerNum()));
            tvFriendShareNum1.append("人获得");

            viewFriendShareCouponMoney1.setText(frtMoney);

            viewFriendShareCouponNum1.setText(String.valueOf(frtItem0.getCouponNum()));


            // point2;
            tvFriendShareNum2.setText("每邀请");
            tvFriendShareNum2.append(String.valueOf(scd.getInviteNum()));
            tvFriendShareNum2.append("人 额外获得");

            viewFriendShareCouponMoney2.setText(secMoney);

            viewFriendShareCouponNum2.setText(String.valueOf(secItem0.getCouponNum()));


            if (frtItem0.getCouponNum() <= 0) {
                point1.setBackgroundResource(R.drawable.round_point_bg);
                tvFriendShareNum1.setTextColor(getResources().getColor(R.color.black_gray));
                rl_coupon_bg1.setBackgroundResource(R.drawable.quan2);
                view_friend_share_coupon_num_pre1.setTextColor(getResources().getColor(R.color.black_gray));
                viewFriendShareCouponNum1.setTextColor(getResources().getColor(R.color.black_gray));
            } else {
                point1.setBackgroundResource(R.drawable.round_point_bg1);
                tvFriendShareNum1.setTextColor(getResources().getColor(R.color.main_color));
                rl_coupon_bg1.setBackgroundResource(R.drawable.quan1);
                view_friend_share_coupon_num_pre1.setTextColor(getResources().getColor(R.color.main_color));
                viewFriendShareCouponNum1.setTextColor(getResources().getColor(R.color.main_color));
            }

            if (secItem0.getCouponNum() <= 0) {
                point2.setBackgroundResource(R.drawable.round_point_bg);
                tvFriendShareNum2.setTextColor(getResources().getColor(R.color.black_gray));
                rl_coupon_bg2.setBackgroundResource(R.drawable.quan2);
                view_friend_share_coupon_num_pre2.setTextColor(getResources().getColor(R.color.black_gray));
                viewFriendShareCouponNum2.setTextColor(getResources().getColor(R.color.black_gray));

                include_coupon_line.setBackgroundResource(R.color.dark_gray);
            } else {
                point2.setBackgroundResource(R.drawable.round_point_bg1);
                tvFriendShareNum2.setTextColor(getResources().getColor(R.color.main_color));
                rl_coupon_bg2.setBackgroundResource(R.drawable.quan1);
                view_friend_share_coupon_num_pre2.setTextColor(getResources().getColor(R.color.main_color));
                viewFriendShareCouponNum2.setTextColor(getResources().getColor(R.color.main_color));

                include_coupon_line.setBackgroundResource(R.color.main_color);
            }

        })));
    }


    private void getInviteGallopList() {

        HttpAction.getInstance().getInviteGallopList(new GetInviteGallopListResquest()).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }
            ArrayList<String> list = new ArrayList<>();
            for (GetInviteGallopListResponse.DataBean.InviteGallopListBean vo : response.getData().getInviteGallopList()) {
                StringBuilder sb = new StringBuilder();

                String phone = vo.getCustomerPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                sb.append(phone);
                sb.append("成功邀请");
                sb.append(vo.getCustomerNum());
                sb.append("人 获得");
                sb.append(MoneyHelper.getInstance4Fen(vo.getSumMaidProfit()).change2Yuan().getString());
                sb.append("元");

                list.add(sb.toString());

            }

         /*   tv_friend_require.setResources(list);
            tv_friend_require.setTextStillTime(2500);*/
        })));
    }

}
