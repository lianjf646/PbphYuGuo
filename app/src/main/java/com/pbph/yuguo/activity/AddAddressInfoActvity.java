package com.pbph.yuguo.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.application.UserInfo;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.baidu_map.AddAddressBaiDuMap;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.SaveReceiverAddressResquest;
import com.pbph.yuguo.request.UpdateReceiverAddressResquest;
import com.pbph.yuguo.response.GetAddressListByCustomerIdResponse;
import com.pbph.yuguo.util.AMUtils;
import com.pbph.yuguo.util.YesNoDialog;

/**
 * Created by 连嘉凡 on 2018/8/10.
 */

public class AddAddressInfoActvity extends BaseActivity implements View.OnClickListener {

    public final static int ADDRESS_INFO = 263;

    private String name = "";
    private String customerName;
    private String customerPhone;
    private String buildingCard;
    private String customerId;
    private String defaultFlag; //	是否默认（0:非默认;1:默认）
    private String la;
    private String lo;
    private String receiverVillage;// 小区名称

    private String province;
    private String city;
    private String district;


    private GetAddressListByCustomerIdResponse.DataBean.ReceiverAddressdtoBean dtoListBean;
    public final static String DTO_LIST_BEAN = "dtoListBean";
    private Integer addressId;

    private EditText etCustomerName;
    private EditText etCustomerPhone;
    private TextView tvCity;
    private TextView tvAddressInfo;
    private EditText etBuildingCard;
    private CheckBox chbDefault;
    private Button btnConfirm;

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_info);
        initTitle(TITLE_STYLE_WHITE, "添加收货的地址", true, false);
        initView();
        initData();

    }

    private void initView() {
        etCustomerName = (EditText) findViewById(R.id.et_customer_name);
        etCustomerPhone = (EditText) findViewById(R.id.et_customer_phone);
        tvCity = (TextView) findViewById(R.id.tv_city);
        tvCity.setOnClickListener(this);
        tvAddressInfo = (TextView) findViewById(R.id.tv_address_info);
        tvAddressInfo.setOnClickListener(this);
        etBuildingCard = (EditText) findViewById(R.id.et_building_card);
        chbDefault = (CheckBox) findViewById(R.id.chb_default);
        chbDefault.setOnClickListener(this);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(this);
    }

    private void initData() {
        this.customerId = String.valueOf(YuGuoApplication.userInfo.getCustomerId());
        dtoListBean = (GetAddressListByCustomerIdResponse.DataBean.ReceiverAddressdtoBean) getIntent().getSerializableExtra(DTO_LIST_BEAN);
        if (dtoListBean != null) {
            etCustomerName.setText(dtoListBean.getReceiverName());
            etCustomerPhone.setText(dtoListBean.getReceiverPhone());
            tvAddressInfo.setText(dtoListBean.getReceiverVillage());
            etBuildingCard.setText(dtoListBean.getReceiverAddressDetail());
            addressId = dtoListBean.getAddressId();
            if (dtoListBean.getDefaultFlag() == 1) {
                chbDefault.setChecked(true);
            } else {
                chbDefault.setChecked(false);
            }
            la = dtoListBean.getReceiverLat();
            lo = dtoListBean.getReceiverLng();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS_INFO && resultCode == RESULT_OK) {
            la = String.valueOf(data.getDoubleExtra(AddAddressBaiDuMap.LATITUDE, 0.0));
            lo = String.valueOf(data.getDoubleExtra(AddAddressBaiDuMap.LONGITUDE, 0.0));
            receiverVillage = data.getStringExtra(AddAddressBaiDuMap.RECEIVER_VILLAGE);
            province = data.getStringExtra(AddAddressBaiDuMap.RECEIVER_PROVINCE);
            city = data.getStringExtra(AddAddressBaiDuMap.RECEIVER_CITY);
            district = data.getStringExtra(AddAddressBaiDuMap.RECEIVER_AREA);
            tvAddressInfo.setText(receiverVillage);
            return;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_address_info: {
                Intent intent = new Intent();
//                intent.setClass(this, BaiduMapActivity.class);
                intent.setClass(this, AddAddressBaiDuMap.class);
                intent.putExtra(AddAddressBaiDuMap.LATITUDE, la == null ? 0 : Double.valueOf(la));
                intent.putExtra(AddAddressBaiDuMap.LONGITUDE, lo == null ? 0 : Double.valueOf(lo));
                intent.putExtra(AddAddressBaiDuMap.NAME, tvAddressInfo.getText().toString().trim());
                startActivityForResult(intent, ADDRESS_INFO);


            }
            break;
            case R.id.btn_confirm: {
                submit();
            }
            break;
        }
    }

    /**
     * 判断是否有定位权限
     */
    private void applyLocationJurisdiction() {
        PackageManager pm = getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission("android.permission.ACCESS_COARSE_LOCATION", getPackageName()));
        if (permission) {
//            Toast.makeText(MainActivity.this,"有权限",Toast.LENGTH_SHORT).show();
        } else {
            YesNoDialog.show(this, "需要定位权限请前往设置", new YesNoDialog.OnClickRateDialog() {
                @Override
                public void onClickLeft() {
                    finish();
                }

                @Override
                public void onClickRight() {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivity(intent);
                }
            }, false);
        }
    }

    private void submit() {
        customerName = etCustomerName.getText().toString().trim();
        if (TextUtils.isEmpty(customerName)) {
            Toast.makeText(this, "请收货人输入姓名", Toast.LENGTH_SHORT).show();
            return;
        }

        customerPhone = etCustomerPhone.getText().toString().trim();
        if (TextUtils.isEmpty(customerPhone)) {
            Toast.makeText(this, "请输入收货人手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!AMUtils.isMobile(String.valueOf(customerPhone))) {
            Toast.makeText(this, R.string.Illegal_phone_number, Toast.LENGTH_SHORT).show();
            return;
        }

        buildingCard = etBuildingCard.getText().toString().trim();
        if (TextUtils.isEmpty(buildingCard)) {
            Toast.makeText(this, "请输入楼牌号码例如：A座101室", Toast.LENGTH_SHORT).show();
            return;
        }
        if (la == null) {
            Toast.makeText(this, "请选择小区", Toast.LENGTH_SHORT).show();
            return;
        }
        receiverVillage = tvAddressInfo.getText().toString().trim();

        if (chbDefault.isChecked()) {
            defaultFlag = "1";
        } else {
            defaultFlag = "0";
        }


        if (dtoListBean == null) {
            saveReceiverAddress();
        } else {
            updateReceiverAddress();
        }

    }


    /**
     * 保存地址
     */
    private void saveReceiverAddress() {
        WaitUI.Show(this);
        HttpAction.getInstance().saveReceiverAddress(new SaveReceiverAddressResquest(customerId, customerName, customerPhone, "60", buildingCard,
                defaultFlag, la, lo, receiverVillage)).subscribe(new BaseObserver<>(this, response -> {
            WaitUI.Cancel();
            Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
            if (response.getCode() != 200) {
                return;
            }
            finish();
        }));

    }

    /**
     * 修改地址
     */
    private void updateReceiverAddress() {
        WaitUI.Show(this);
        HttpAction.getInstance().updateReceiverAddress(new UpdateReceiverAddressResquest(addressId, customerId, customerName, customerPhone, "60",
                buildingCard, defaultFlag, la, lo, receiverVillage)).subscribe(new BaseObserver<>(this, response -> {
            WaitUI.Cancel();
            Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
            if (UserInfo.getInstance().getRecAddId() == null) {
                finish();
                return;
            } else if (UserInfo.getInstance().getRecAddId() == addressId) {
                UserInfo.getInstance().setRecAddId(addressId);
                UserInfo.getInstance().setRecLongitude(Double.valueOf(lo));
                UserInfo.getInstance().setRecLatitude(Double.valueOf(la));
                UserInfo.getInstance().setRecCity("哈尔滨市");
                UserInfo.getInstance().setRecStreet(receiverVillage);
                UserInfo.getInstance().setReDistrict(district);
                finish();
                return;
            } else {
//                showToast("修改失败");
                finish();
            }


        }));

    }

    @Override
    protected void onResume() {
        super.onResume();
        applyLocationJurisdiction();
    }
}
