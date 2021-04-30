package com.pbph.yuguo.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.VipEquityAdapter;
import com.pbph.yuguo.adapter.VipGoodsAdapter;
import com.pbph.yuguo.application.UserInfo;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseFragment;
import com.pbph.yuguo.callback.MyErrorCallBack;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.MyGridView;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetCustomerInfoByIdRequest;
import com.pbph.yuguo.request.GetMemberBenefitsListRequest;
import com.pbph.yuguo.request.GetVipGoodsListRequest;
import com.pbph.yuguo.response.GetCustomerInfoByIdResponse;
import com.pbph.yuguo.response.MemberBenefitsListResponse;
import com.pbph.yuguo.response.VipGoodsListResponse;
import com.pbph.yuguo.util.GlideUtil;

import java.util.List;

public class VipFragment extends BaseFragment implements View.OnClickListener {
    private View shop_logout;
    private View layout_vip_head;
    private ImageView iv_heard_icon;
    private TextView tv_name;
    private TextView tv_vip;
    private TextView tv_time;
    private TextView tv_vip_explain;

    private View layout_vip_equity;
    private TextView tv_info;
    private MyGridView gv_equity;

    private View layout_vip_goods;
    private MyGridView gv_vip_goods;
    private TextView tv_no_goods;
    private TextView tv_more;

    private ImageView iv_vip_special;
    private Button btn_confirm;

    GetCustomerInfoByIdResponse.DataBean.CustomerBean customerBean;

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_vip;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!YuGuoApplication.isLogin()) {
            shop_logout.setVisibility(View.VISIBLE);
        } else {
            shop_logout.setVisibility(View.GONE);
        }
    }

    @Override
    public void initView() {
        shop_logout = mContentView.findViewById(R.id.shop_logout);
        mContentView.findViewById(R.id.btn_shop_login).setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        layout_vip_head = mContentView.findViewById(R.id.layout_vip_head);
        iv_heard_icon = layout_vip_head.findViewById(R.id.iv_heard_icon);
        tv_name = layout_vip_head.findViewById(R.id.tv_name);
        tv_time = layout_vip_head.findViewById(R.id.tv_time);
        tv_vip = layout_vip_head.findViewById(R.id.tv_vip);
        tv_vip_explain = layout_vip_head.findViewById(R.id.tv_vip_explain);
        tv_vip_explain.setOnClickListener(this);
        tv_vip_explain.setVisibility(View.VISIBLE);

        layout_vip_equity = mContentView.findViewById(R.id.layout_vip_equity);
        tv_info = layout_vip_equity.findViewById(R.id.tv_info);
        tv_info.setOnClickListener(this);
        gv_equity = layout_vip_equity.findViewById(R.id.gv_equity);
        iv_vip_special = layout_vip_equity.findViewById(R.id.iv_vip_special);
        iv_vip_special.setOnClickListener(this);

        layout_vip_goods = mContentView.findViewById(R.id.layout_vip_goods);
        gv_vip_goods = layout_vip_goods.findViewById(R.id.gv_vip_goods);
        tv_more = layout_vip_goods.findViewById(R.id.tv_more);
        tv_no_goods = mContentView.findViewById(R.id.tv_no_goods);
        tv_more.setOnClickListener(this);

        btn_confirm = mContentView.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);

        tv_info.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_info.getPaint().setAntiAlias(true);//抗锯齿
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                startActivity(new Intent(getActivity(), OpenVipActivity.class));
                break;
            case R.id.tv_vip_explain:
                Intent intent = new Intent(getActivity(), MyBrowsersActivity.class);
                intent.putExtra("title", "会员说明");
                intent.putExtra("url", "http://bshop.pbphkj.com/ygweb/vip_desc.html");
                startActivity(intent);
                break;
            case R.id.tv_info:
                Intent vipWebIntent = new Intent(getActivity(), VipEquityWebActivity.class);
                int levelType = customerBean.getCustomerLevelType();
                vipWebIntent.putExtra("levelType", levelType);
                startActivity(vipWebIntent);
                break;
            case R.id.iv_vip_special:
                startActivity(new Intent(getActivity(), VipSpecialFieldActivity.class));
                break;
            case R.id.tv_more:
                startActivity(new Intent(getActivity(), VipSpecialFieldActivity.class));
                break;
        }
    }

    private void getMemberBenefitsList() {
        GetMemberBenefitsListRequest request = new GetMemberBenefitsListRequest();
        HttpAction.getInstance().getMemberBenefitsList(request).subscribe(new BaseObserver<>(getActivity(), response -> {
            int code = response.getCode();
            if (200 == code) {
                MemberBenefitsListResponse.DataBean data = response.getData();
                List<MemberBenefitsListResponse.DataBean.MemberBenefitsListBean> listBean = data.getMemberBenefitsList();
                setVipEquityView(listBean);
            }
        }));
    }

    private void getVipGoodsList() {
        UserInfo userInfo = YuGuoApplication.userInfo;
        GetVipGoodsListRequest request;
        if (userInfo == null || userInfo.getRecAddId() == null) {
            request = new GetVipGoodsListRequest(userInfo.getLongitude() + "", userInfo.getLatitude() + "");
        } else {
            request = new GetVipGoodsListRequest(userInfo.getRecLongitude() + "", userInfo.getRecLatitude() + "");
        }

        HttpAction.getInstance().getVipGoodsList(request).subscribe(new BaseObserver<>(getActivity(), response -> {
            int code = response.getCode();
            if (200 == code) {
                VipGoodsListResponse.DataBean data = response.getData();
                List<VipGoodsListResponse.DataBean.GoodsListBean> listBean = data.getGoodsList();
                setVipGoodsView(listBean);
            } else {
                tv_no_goods.setVisibility(View.VISIBLE);
                layout_vip_goods.setVisibility(View.GONE);
            }
        }, (code, message) -> {
            tv_no_goods.setVisibility(View.VISIBLE);
            layout_vip_goods.setVisibility(View.GONE);
        }));
    }

    private void setVipEquityView(List<MemberBenefitsListResponse.DataBean.MemberBenefitsListBean> listBean) {
        VipEquityAdapter adapter = new VipEquityAdapter(getActivity(), listBean);
        gv_equity.setAdapter(adapter);
        gv_equity.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getActivity(), VipEquityWebActivity.class);
            intent.putExtra("tab", position);
            intent.putExtra("levelType", customerBean.getCustomerLevelType());
            startActivity(intent);
        });
    }

    private void setVipGoodsView(List<VipGoodsListResponse.DataBean.GoodsListBean> listBean) {
        if (listBean == null || listBean.size() == 0) {
            tv_no_goods.setVisibility(View.VISIBLE);
            layout_vip_goods.setVisibility(View.GONE);
            return;
        }
        VipGoodsAdapter adapter = new VipGoodsAdapter(getActivity(), listBean);
        gv_vip_goods.setAdapter(adapter);
        gv_vip_goods.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getActivity(), GoodsInfoActivity.class);
            intent.putExtra("storeGoodsId", listBean.get(position).getStoreGoodsId());
            intent.putExtra("storeGoodsInfoId", listBean.get(position).getStoreGoodsInfoId());
            startActivity(intent);
        });
    }

    //根据用户id查询用户详情
    private void getCustomerInfoById() {
        WaitUI.Show(mContext);
        GetCustomerInfoByIdRequest request = new GetCustomerInfoByIdRequest(YuGuoApplication.userInfo.getCustomerId());
        HttpAction.getInstance().getCustomerInfoById(request).subscribe(new BaseObserver<>(mContext, response -> {
            customerBean = response.getData().getCustomer();
            String customerImgUrl = customerBean.getCustomerImgUrl();
            if (TextUtils.isEmpty(customerImgUrl)){
                iv_heard_icon.setImageResource(R.drawable.wodeyidenglu_touxiang);
            } else {
                GlideUtil.displayCircleBitmap(mContext, customerBean.getCustomerImgUrl(), iv_heard_icon);
            }
            tv_name.setText(customerBean.getCustomerName());
            if (customerBean.getCustomerLevelType() == 0) { //会员类型（0普通，1试用，2正式）
                tv_time.setVisibility(View.GONE);
                tv_vip.setBackgroundResource(R.drawable.bg_vip_logo_solid_corner);
                tv_vip.setText("未开通");
                btn_confirm.setText("立即开通VIP会员");
            } else if (customerBean.getCustomerLevelType() == 1) {
                tv_time.setVisibility(View.VISIBLE);
                tv_time.setText(TextUtils.isEmpty(customerBean.getCustomerVipExpireTime()) ? "" : customerBean.getCustomerVipExpireTimeString() + "会员到期");
                tv_vip.setBackgroundResource(R.drawable.bg_vip_logo_corner);
                tv_vip.setText("体验会员");
                btn_confirm.setText("立即开通VIP会员");
            } else {
                tv_time.setVisibility(View.VISIBLE);
                tv_time.setText(TextUtils.isEmpty(customerBean.getCustomerVipExpireTime()) ? "" : customerBean.getCustomerVipExpireTimeString() + "会员到期");
                tv_vip.setBackgroundResource(R.drawable.bg_vip_logo_corner);
                tv_vip.setText("VIP会员");
                btn_confirm.setText("立即续费");
            }
            WaitUI.Cancel();
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (YuGuoApplication.isLogin()) {
            getCustomerInfoById();
        }
        getMemberBenefitsList();
        getVipGoodsList();
    }
}
