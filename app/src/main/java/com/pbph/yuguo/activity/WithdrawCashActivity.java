package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.WithdrawCashViewHolder;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.MyGridView;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.myview.adapter.abslistview.DataAdapter;
import com.pbph.yuguo.myview.adapter.choicehelper.abslistview.SingleChoiceHelper;
import com.pbph.yuguo.myview.singlepointlistener.OnItemSPClickListener;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetRedPacketWithdrawRuleResquest;
import com.pbph.yuguo.request.SendRedResquest;
import com.pbph.yuguo.response.GetRedPacketWithdrawRuleResponse;
import com.pbph.yuguo.response.SendRedResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WithdrawCashActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvMoneyAll;
    private RadioButton rbWx;
    private RadioButton rbZfb;
    private MyGridView gridView;
    private DataAdapter adapter;

    private Button button;

    private String dealPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawcash);

        initTitle(TITLE_STYLE_WHITE, "提现", true, false);
        btn_right1.setVisibility(View.VISIBLE);
        btn_right1.setText("提现记录");
        btn_right1.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                startActivity(new Intent(mContext, WithdrawCashRecordsActivity.class));
            }
        });
        initView();
    }


    private void initView() {
        tvMoneyAll = (TextView) findViewById(R.id.tv_money_all);
        rbWx = (RadioButton) findViewById(R.id.rb_wx);
        rbZfb = (RadioButton) findViewById(R.id.rb_zfb);

        gridView = (MyGridView) findViewById(R.id.gv_money);
        adapter = new DataAdapter(mContext, gridView, WithdrawCashViewHolder.class,
                SingleChoiceHelper.class);

        gridView.setOnItemClickListener(new OnItemSPClickListener() {
            @Override
            public void onItemClickSucc(AdapterView<?> parent, View view, int position, long id) {
                adapter.choiceHelper.putChoiceNotify(position);
                GetRedPacketWithdrawRuleResponse.DataBean.WithdrawBean bean =
                        (GetRedPacketWithdrawRuleResponse.DataBean.WithdrawBean) adapter
                                .choiceHelper.putChoice(position);
                dealPrice = bean.getMoney();
            }
        });


        button = findViewById(R.id.button);
        button.setOnClickListener(this);

    }


    @Override
    public void onLeftClick() {
        finish();
    }

    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                long currentTime = Calendar.getInstance().getTimeInMillis();
                if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                    lastClickTime = currentTime;

                    if (dealPrice == null) return;

                    if (Double.valueOf(dealPrice) > Double.valueOf(tvMoneyAll.getText().toString
                            ())) {
                        Toast.makeText(mContext, "余额不足", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    sendRed();
                }

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCustomerInfoById();
        getRedPacketWithdrawRule();
    }

    //根据用户id查询用户详情
    private void getCustomerInfoById() {
//        HttpAction.getInstance().getCustomerInfoById(new GetCustomerInfoByIdRequest
//                (YuGuoApplication.userInfo.getCustomerId())).subscribe(new BaseObserver<>
//                (mContext, response -> {
//
//            if (200 != response.getCode()) {
//                showToast(response.getMsg());
//                return;
//            }
//
//            tvMoneyAll.setText(MoneyHelper.getInstance4Fen(response.getData().getCustomer()
//                    .getRedPackageMoney()).change2Yuan().getString());
//        }));
    }


    private void getRedPacketWithdrawRule() {

        HttpAction.getInstance().getRedPacketWithdrawRule(new GetRedPacketWithdrawRuleResquest())
                .subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }
            GetRedPacketWithdrawRuleResponse.DataBean.WithdrawConfigDtoBean vo = response.getData
                    ().getWithdrawConfigDto();

            button.setEnabled(vo.getOpenFlag() == 1);


            if (vo.getDefaultWay() == 1) {//wx
                rbWx.setChecked(true);
            } else if (vo.getDefaultWay() == 2) {//zfb
                rbZfb.setChecked(true);
            }

            if (vo.getAlipayFlag() == 1) {
                rbZfb.setEnabled(true);
                rbZfb.setClickable(true);
            } else {
                rbZfb.setEnabled(false);
                rbZfb.setClickable(false);
            }

            if (vo.getWechatFlag() == 1) {
                rbWx.setEnabled(true);
                rbWx.setClickable(true);
            } else {
                rbWx.setEnabled(false);
                rbWx.setClickable(false);
            }


            int charge = vo.getServiceCharge();
            String[] strs = vo.getWithdrawType().split(",");

            List<GetRedPacketWithdrawRuleResponse.DataBean.WithdrawBean> list = new ArrayList<>
                    (strs.length);

            for (String str : strs) {

                if (TextUtils.isEmpty(str)) continue;

                GetRedPacketWithdrawRuleResponse.DataBean.WithdrawBean bean = new
                        GetRedPacketWithdrawRuleResponse.DataBean.WithdrawBean();
                bean.setServiceCharge(charge);
                bean.setMoney(str);

                list.add(bean);
            }
            adapter.setDatas(list);

        })));
    }

    /**
     * 提现
     */
    private void sendRed() {
        int dealWay;
        if (rbWx.isChecked() == true) {
            dealWay = 0;
        } else {
            dealWay = 1;
        }
        WaitUI.Show(this);
        button.setClickable(false);
        HttpAction.getInstance().sendRed(new SendRedResquest(YuGuoApplication.userInfo
                .getCustomerId(), String.valueOf(Integer.valueOf(dealPrice) * 100), dealWay))
                .subscribe(new BaseObserver<SendRedResponse>(this, response -> {
            WaitUI.Cancel();
            button.setClickable(true);
            Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
            if (response.getCode() == 200) {
                Intent intent = new Intent(WithdrawCashActivity.this, WithdrawCashSucActivity
                        .class);
                startActivity(intent);
            }

        }, (code, message) -> {
            WaitUI.Cancel();
            button.setClickable(true);
        }));

    }

}
