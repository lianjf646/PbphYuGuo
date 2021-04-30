package com.pbph.yuguo.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.util.RxBusF;
import com.pbph.yuguo.wxutil.WechatUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WXPayEntryActivity";
    IWXAPI api = null;

    private void handleIntent(Intent paramIntent) {
        api.handleIntent(paramIntent, this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WechatUtils.APP_ID, false);
//        api.registerApp(ConstantData.WX_APP_ID);
        handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //setIntent(intent);
        //  api.handleIntent(intent, this);

        setIntent(intent);
        handleIntent(intent);

    }

    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
//        Log.e(TAG, "onPayFinish, errCode = " + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            String respString = null;
            if (resp.errCode == 0) {
                respString = "支付成功";
//                ConfirmationOrderActivity.ConfirmationOrderSendData data = new
// ConfirmationOrderActivity.ConfirmationOrderSendData();
//                data.type = ConstantsAPI.COMMAND_PAY_BY_WX;
//                data.data =resp;
                RxBusF.getInstance().post(resp);
                ConstantData.PayTypeSuccessOrFail = 1;
            } else if (resp.errCode == -1) {
                respString = "支付错误";
                ConstantData.PayTypeSuccessOrFail = 0;
            } else if (resp.errCode == -2) {
                respString = "取消支付";
                ConstantData.PayTypeSuccessOrFail = 0;
            }
            Toast.makeText(this, respString, Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}