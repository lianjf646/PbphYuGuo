package com.pbph.yuguo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetSysConfigRequest;
import com.pbph.yuguo.response.GetSysConfigResponse;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.ToastDialog;
import com.sobot.chat.api.model.Information;

/**
 * Created by v on 2019/5/21.
 */
public class CustomerServiceActivity extends BaseActivity {

    private ImageView ivCode;
    private Button btnCustomer;
    private Button btnCustomerPhone;
    GetSysConfigResponse.DataBean data;
    Information information;

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerservice);
        initTitle(TITLE_STYLE_WHITE, "客服服务", true, false);
        initView();
        information = new Information();
        information.setAppkey(ConstantData.SOBOT_APP_KEY);
        information.setUseVoice(false);
    }

    private void initView() {
        ivCode = findViewById(R.id.iv_code);
        btnCustomer = findViewById(R.id.btn_customer);
        btnCustomer.setOnClickListener(onSPClickListener);
        btnCustomerPhone = findViewById(R.id.btn_customer_phone);
        btnCustomerPhone.setOnClickListener(onSPClickListener);
        getSysConfig();
    }

    private void getSysConfig() {
        HttpAction.getInstance().getSysConfig(new GetSysConfigRequest()).subscribe(new BaseObserver<>(mContext, response -> {
            int code = response.getCode();
            if (code == 200) {
                data = response.getData();
                GlideUtil.displayImage(this,data.getSysConfig().getWxCodeService(),ivCode);

            }
        }));
    }


    OnSPClickListener onSPClickListener = new OnSPClickListener() {
        @Override
        public void onClickSucc(View v) {
            switch (v.getId()) {
                case R.id.btn_customer: {
                    toCustomService();
                    break;
                }
                case R.id.btn_customer_phone: {
                    if (data == null) return;
                    if (data.getSysConfig() == null) return;
                    if (data.getSysConfig().getServicePhone() == null) return;
                    String phone = data.getSysConfig().getServicePhone();
                    ToastDialog.show(mContext, "请联系客服", "客服电话：" + phone, () -> {
                        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));//跳转到拨号界面，同时传递电话号码
                        startActivity(dialIntent);
                    });
                    break;
                }
            }
        }
    };
}
