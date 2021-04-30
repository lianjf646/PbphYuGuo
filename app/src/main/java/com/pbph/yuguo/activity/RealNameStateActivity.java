package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetAuthenticationResquest;
import com.pbph.yuguo.request.GetInviteVipCouponListResquest;
import com.pbph.yuguo.response.GetAuthenticationResponse;
import com.pbph.yuguo.response.GetInviteVipCouponListResponse;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.MoneyHelper;

/**
 * Created by Administrator on 2018/12/27.
 */

public class RealNameStateActivity extends BaseActivity {
    private RelativeLayout rl_realname;
    private ImageView iv_touxiang;
    private LinearLayout ll_1;
    private TextView tv_yuanyin;
    private ImageView iv_1;
    private TextView tv_1;
    private TextView tv_2;
    private ImageView iv_jiantou;
    private TextView tv_name;
    private TextView tv_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname_state);
        initTitle(TITLE_STYLE_WHITE, "实名认证", true, false);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        ll_1 = findViewById(R.id.ll_1);
        tv_yuanyin = findViewById(R.id.tv_yuanyin);
        iv_1 = findViewById(R.id.iv_1);
        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        iv_jiantou = findViewById(R.id.iv_jiantou);
        tv_name = findViewById(R.id.tv_name);
        tv_phone = findViewById(R.id.tv_phone);


        //上传  重新上传
        rl_realname = findViewById(R.id.rl_realname);

        //头像
        iv_touxiang = findViewById(R.id.iv_touxiang);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getAuthentication();
    }

    private void getAuthentication() {
        HttpAction.getInstance().getAuthentication(new GetAuthenticationResquest(YuGuoApplication.userInfo.getCustomerId())).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }
            GetAuthenticationResponse.DataBean data = response.getData();
            GlideUtil.displayCircleBitmaps(mContext, data.getAuthentication().getIdCardUrl(), iv_touxiang);
            if (data.getAuthentication().getAuthStatus() ==2){
                //上传证件
                shangchuan();
            }else if (data.getAuthentication().getAuthStatus() ==0){
                //审核中
                shenhezhong();
            }else if (data.getAuthentication().getAuthStatus()==1){
                if (data.getAuthentication().getAuthReuslt() == 0){
                    //重新上传
                    shenheno();
                    tv_yuanyin.setText("驳回原因："+data.getAuthentication().getRejectReason());

                }else if (data.getAuthentication().getAuthReuslt() == 1){
                    //审核通过
                    shenheyes();
                    String name = data.getAuthentication().getCustomerRealName().substring(1,data.getAuthentication().getCustomerRealName().length());
                    tv_name.setText("*"+name);
                    String qian = data.getAuthentication().getIdCardNo().substring(0,1);
                    String hou = data.getAuthentication().getIdCardNo().substring(data.getAuthentication().getIdCardNo().length()-1,data.getAuthentication().getIdCardNo().length());

                    tv_phone.setText(qian + "****************" + hou);





                }
            }



        })));

    }

    private void shenheno() {
        iv_1.setImageResource(R.drawable.uploadagain_icon);
        tv_1.setText("重新上传");
        ll_1.setVisibility(View.GONE);
        tv_yuanyin.setVisibility(View.VISIBLE);
        iv_jiantou.setVisibility(View.VISIBLE);
        rl_realname.setClickable(true);
        rl_realname.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                startActivity(new Intent(mContext,RealNameAuthenticationActivity.class));
            }
        });
    }

    private void shenheyes() {
        iv_1.setImageResource(R.drawable.approved_icon);
        tv_1.setText("审核通过");
        ll_1.setVisibility(View.VISIBLE);
        tv_yuanyin.setVisibility(View.GONE);
        tv_2.setVisibility(View.GONE);
        iv_jiantou.setVisibility(View.GONE);
        rl_realname.setClickable(false);

    }

    private void shenhezhong() {
        iv_1.setImageResource(R.drawable.inthereview_icon);
        tv_1.setText("审核中");
        ll_1.setVisibility(View.GONE);
        tv_yuanyin.setVisibility(View.GONE);
        tv_2.setVisibility(View.VISIBLE);
        iv_jiantou.setVisibility(View.GONE);
        rl_realname.setClickable(false);
    }

    private void shangchuan() {
        iv_1.setImageResource(R.drawable.uploaddocuments);
        tv_1.setText("上传证件");
        ll_1.setVisibility(View.GONE);
        tv_yuanyin.setVisibility(View.GONE);
        tv_2.setVisibility(View.VISIBLE);
        rl_realname.setClickable(true);
        rl_realname.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                startActivity(new Intent(mContext,RealNameAuthenticationActivity.class));
            }
        });
    }

    @Override
    public void onLeftClick() {
        finish();
    }
}
