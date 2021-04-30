package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.MyAddressListAdapter;
import com.pbph.yuguo.application.UserInfo;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.DelReceiverAddressResquest;
import com.pbph.yuguo.request.GetAddressListByCustomerIdResquest;
import com.pbph.yuguo.request.UpdateReceiverAddressResquest;
import com.pbph.yuguo.response.GetAddressListByCustomerIdResponse;
import com.pbph.yuguo.util.YesNoDialog;

import java.util.List;

/**
 * Created by 连嘉凡 on 2018/8/8.
 */

public class MyAddressListActivity extends BaseActivity implements MyAddressListAdapter.DataListener {

    private ListView lvMyAddress;
    private TextView tvNotHaveAddress;
    private Button btnAddAddress;
    private MyAddressListAdapter myAddressListAdapter;
    private List<GetAddressListByCustomerIdResponse.DataBean.ReceiverAddressdtoBean> dtoListBeanList;
    private GetAddressListByCustomerIdResponse.DataBean.ReceiverAddressdtoBean dtoListBean;
    private Integer customerId;
    private int removeState;
    public final static String REMOVE_STATE = "removeState";
    private String orderAddressId = "";


    @Override
    public void onLeftClick() {
        setAddressInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address_list);
        initTitle(TITLE_STYLE_WHITE, "我的地址", true, false);
        orderAddressId = getIntent().getStringExtra("order_address_id");
        lvMyAddress = findViewById(R.id.lv_my_address);
        tvNotHaveAddress = findViewById(R.id.tv_not_have_address);
        btnAddAddress = findViewById(R.id.btn_add_address);
        btnAddAddress.setOnClickListener(v -> {
            if (dtoListBeanList.size() >= 10) {
                Toast.makeText(mContext, "收货地址只可新建10条", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(MyAddressListActivity.this, AddAddressInfoActvity.class));
        });

        lvMyAddress.setOnItemClickListener((parent, view, position, id) -> {
            if (removeState == 0) {
                dtoListBean = dtoListBeanList.get(position);
                Intent intent1 = new Intent();
                intent1.putExtra("name", dtoListBean.getReceiverName());
                intent1.putExtra("phone", dtoListBean.getReceiverPhone());
                intent1.putExtra("address", dtoListBean.getReceiverVillage() + dtoListBean.getReceiverAddressDetail());
                intent1.putExtra("addressId", String.valueOf(dtoListBean.getAddressId()));
                intent1.putExtra("receiverLat", dtoListBean.getReceiverLat());
                intent1.putExtra("receiverLng", dtoListBean.getReceiverLng());
                setResult(RESULT_OK, intent1);
                finish();
            }
        });

        initData();
    }

    /**
     * 返回键设置地址信息
     */
    private void setAddressInfo() {

        if (TextUtils.isEmpty(orderAddressId)) {
            Intent intent1 = new Intent();
            setResult(RESULT_OK, intent1);
            finish();
            return;
        }
        for (int i = 0; i < dtoListBeanList.size(); i++) {
            dtoListBean = dtoListBeanList.get(i);
            if (orderAddressId.equals(String.valueOf(dtoListBean.getAddressId()))) {
                Intent intent1 = new Intent();
                intent1.putExtra("name", dtoListBean.getReceiverName());
                intent1.putExtra("phone", dtoListBean.getReceiverPhone());
                intent1.putExtra("address", dtoListBean.getReceiverVillage() + dtoListBean.getReceiverAddressDetail());
                intent1.putExtra("addressId", String.valueOf(dtoListBean.getAddressId()));
                intent1.putExtra("receiverLat", dtoListBean.getReceiverLat());
                intent1.putExtra("receiverLng", dtoListBean.getReceiverLng());
                setResult(RESULT_OK, intent1);
                finish();
            }
        }


    }


    private void initData() {
        customerId = YuGuoApplication.userInfo.getCustomerId();
        removeState = getIntent().getIntExtra(REMOVE_STATE, 0);
        myAddressListAdapter = new MyAddressListAdapter(this);
        lvMyAddress.setAdapter(myAddressListAdapter);
        myAddressListAdapter.setDataListener(this);
        myAddressListAdapter.setRemoveState(removeState);


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setAddressInfo();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 获取地址列表
     */
    private void getReceiverAddressList() {
        WaitUI.Show(this);
        HttpAction.getInstance().getAddressListByCustomerId(new GetAddressListByCustomerIdResquest(String.valueOf
                (YuGuoApplication.userInfo.getCustomerId()), "1")).subscribe(new BaseObserver<>(mContext, (response -> {
            WaitUI.Cancel();
            if (200 != response.getCode()) {

                return;
            }
            dtoListBeanList = response.getData().getReceiverAddressdto();
            myAddressListAdapter.setdtoListBeanList(dtoListBeanList);
            if (!dtoListBeanList.isEmpty()) {
                tvNotHaveAddress.setVisibility(View.GONE);
            } else {
                tvNotHaveAddress.setVisibility(View.VISIBLE);

            }

        }), (code, message) -> {
            WaitUI.Cancel();
        }));
    }


    /**
     * 删除地址
     */
    private void delReceiverAddress(Integer addressId) {
        HttpAction.getInstance().delReceiverAddress(new DelReceiverAddressResquest(addressId)).subscribe(new BaseObserver<>
                (this, response -> {
            Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
            if (UserInfo.getInstance().getRecAddId() == addressId) {
                UserInfo.getInstance().setRecAddId(null);
                UserInfo.getInstance().setRecLongitude(null);
                UserInfo.getInstance().setRecLatitude(null);
                UserInfo.getInstance().setRecCity(null);
                UserInfo.getInstance().setRecAddId(null);
                UserInfo.getInstance().setReDistrict(null);
            }
            getReceiverAddressList();
        }, ((code, message) -> {

        })));
    }

    /**
     * 编辑地址
     */
    @Override
    public void textAddress(int pos) {
        Intent intent = new Intent(MyAddressListActivity.this, AddAddressInfoActvity.class);
        intent.putExtra(AddAddressInfoActvity.DTO_LIST_BEAN, dtoListBeanList.get(pos));
        startActivity(intent);
    }

    /**
     * 删除地址
     */
    @Override
    public void removeAddress(int pos, int addressId) {
        YesNoDialog.show(this, "您确定删除地址?", new YesNoDialog.OnClickRateDialog() {
            @Override
            public void onClickLeft() {

            }

            @Override
            public void onClickRight() {
                delReceiverAddress(addressId);
            }
        }, false);
    }


    /**
     * 地址设置默认值
     */
    @Override
    public void setDefault(int pos, int addressId) {
        updateReceiverAddress(pos, addressId);
    }

    /**
     * 修改地址
     */
    private void updateReceiverAddress(int pos, int addressId) {
        dtoListBean = dtoListBeanList.get(pos);
        HttpAction.getInstance().updateReceiverAddress(new UpdateReceiverAddressResquest(addressId, String.valueOf(customerId),
                dtoListBean.getReceiverName(), dtoListBean.getReceiverPhone(), "60", dtoListBean.getReceiverAddressDetail(),
                "1", dtoListBean.getReceiverLat(), dtoListBean.getReceiverLng(), dtoListBean.getReceiverVillage())).subscribe
                (new BaseObserver<>(this, response -> {
            if (response.getCode() == 200) {
                getReceiverAddressList();

            }
        }));

    }

    @Override
    protected void onResume() {
        super.onResume();
        getReceiverAddressList();
    }
}
