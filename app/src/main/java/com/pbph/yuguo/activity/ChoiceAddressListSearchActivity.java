package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.ChoiceAddressListSearchAdapter;
import com.pbph.yuguo.application.UserInfo;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.baidu_map.AddAddressBaiDuMap;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetStoreListRequest;
import com.pbph.yuguo.response.GetStoreListResponse;
import com.pbph.yuguo.util.AddressSearchHelper;
import com.pbph.yuguo.util.MoreSpatialRelationUntil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 连嘉凡 on 2018/8/7.
 */

public class ChoiceAddressListSearchActivity extends BaseActivity {
    private ListView mLvChoiceAddressSearch;
    private ChoiceAddressListSearchAdapter choiceAddressListSearchAdapter;
    private ImageView mBtnLife;
    private EditText mEtAddressSearch;
    private TextView tvAddAddress;
    private AddressSearchHelper addressSearchHelper;
    private List<PoiInfo> poiInfos;
    public final static String ACT_TYPE = "ActType";
    private int ActType;
    private TextView tvCancel;
    List<String> stringList;
    List<List<LatLng>> regionLalngList = new ArrayList<>();

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_address_list_search);
        hideTitleView();
        ActType = getIntent().getIntExtra(ACT_TYPE, 0);
        stringList = Arrays.asList(getResources().getStringArray(R.array.areaList));
        initView();
        initData();
        getStoreList();
    }

    private void initView() {

        mBtnLife = findViewById(R.id.btn_lefts);
        mBtnLife.setOnClickListener(v -> {
            finish();
        });
        tvAddAddress = findViewById(R.id.tv_add_address);
        tvAddAddress.setOnClickListener(v -> {
            if (!YuGuoApplication.isLogin()) {
                startActivity(new Intent(this, LoginActivity.class));
                return;
            }
            startActivity(new Intent(this, AddAddressInfoActvity.class));
        });
        tvCancel = findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(v -> {
            mEtAddressSearch.setText("");
        });

        mEtAddressSearch = findViewById(R.id.et_address_search);
        mEtAddressSearch.setOnEditorActionListener((v, actionId, event) -> {
            addressSearchHelper.setSearchInfo(UserInfo.getInstance().getCity(), v.getText().toString().trim());
            return false;
        });
        mLvChoiceAddressSearch = findViewById(R.id.lv_choice_address_search);
        choiceAddressListSearchAdapter = new ChoiceAddressListSearchAdapter(this);
        mLvChoiceAddressSearch.setAdapter(choiceAddressListSearchAdapter);
        mLvChoiceAddressSearch.setOnItemClickListener((parent, view, position, id) -> {
            if (poiInfos.get(position) == null) return;
//            LogUtils.e("Loge", "initView:区 " + poiInfos.get(position).area);
//            String area = poiInfos.get(position).area;
//            if (!stringList.contains(area)) {
//                ToastUtil.showToast(this, String.valueOf(getString(R.string.no_choice_address)));
//                return;
//            }
            if (regionLalngList.isEmpty()) {
                Toast.makeText(mContext, "数据获取中", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!MoreSpatialRelationUntil.isPolygonContainsPoints(regionLalngList, poiInfos.get(position).getLocation())) {
                Toast.makeText(mContext, "不在配送范围内", Toast.LENGTH_SHORT).show();
                return;
            }
            if (ActType == 1) {
                UserInfo.getInstance().setCity(poiInfos.get(position).getCity());
                UserInfo.getInstance().setStreet(poiInfos.get(position).name);
                UserInfo.getInstance().setLatitude(poiInfos.get(position).location.latitude);
                UserInfo.getInstance().setLongitude(poiInfos.get(position).location.longitude);
                UserInfo.getInstance().setDistrict(poiInfos.get(position).getArea());

                UserInfo.getInstance().setRecAddId(null);
                UserInfo.getInstance().setRecLongitude(null);
                UserInfo.getInstance().setRecLatitude(null);
                UserInfo.getInstance().setRecCity(null);
                UserInfo.getInstance().setRecStreet(null);
                UserInfo.getInstance().setReDistrict(null);
                startActivity(new Intent(this, MainTabActivity.class));
            } else if (ActType == 0) {


                Intent intent1 = ChoiceAddressListSearchActivity.this.getIntent();
                intent1.putExtra(AddAddressBaiDuMap.LATITUDE, poiInfos.get(position).location.latitude);
                intent1.putExtra(AddAddressBaiDuMap.LONGITUDE, poiInfos.get(position).location.longitude);
                intent1.putExtra(AddAddressBaiDuMap.NAME, poiInfos.get(position).name);
//                intent1.putExtra(BaiduMapActivity.ADDRESS, poiInfos.get(position).address);
//                intent1.putExtra(BaiduMapActivity.CITY_NAME, poiInfos.get(position).city);
//                intent1.putExtra(BaiduMapActivity.CITY, poiInfos.get(position).city);
                intent1.putExtra(AddAddressBaiDuMap.RECEIVER_PROVINCE, poiInfos.get(position).province);
                intent1.putExtra(AddAddressBaiDuMap.RECEIVER_CITY, poiInfos.get(position).getCity());
                intent1.putExtra(AddAddressBaiDuMap.RECEIVER_CITY, poiInfos.get(position).getArea());
                setResult(RESULT_OK, intent1);
                finish();
            }
        });
    }

    private void initData() {
        addressSearchHelper = new AddressSearchHelper(this, poiInfos -> {
            choiceAddressListSearchAdapter.setPoiInfos(poiInfos);
            this.poiInfos = poiInfos;

        });

        if (ActType == 0) {
            tvAddAddress.setVisibility(View.GONE);
        } else {
            tvAddAddress.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 获取门店圈起的经纬度
     */
    private void getStoreList() {
        WaitUI.Show(this);
        HttpAction.getInstance().getStoreList(new GetStoreListRequest()).subscribe(new BaseObserver<>(this, response -> {

            WaitUI.Cancel();
            if (response.getCode() != 200) {
                Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }
            List<GetStoreListResponse.DataBean.StoreListBean> storeListBeanList = response.getData().getStoreList();
            drawRegion(storeListBeanList);

        }, (code, message) -> {
            WaitUI.Cancel();
        }));

    }


    /**
     * 绘制配送范围区域
     */
    private void drawRegion(List<GetStoreListResponse.DataBean.StoreListBean> storeListBeanList) {
        for (int i = 0; i < storeListBeanList.size(); i++) {
            //多边形顶点位置
            List<LatLng> points = new ArrayList<>();
            List<GetStoreListResponse.DataBean.StoreListBean.DeliveryAreaListBean> deliveryAreaListBeanList = storeListBeanList.get(i).getDeliveryAreaList();
            if (deliveryAreaListBeanList.size() < 3) continue;
            regionLalngList.add(points);
            for (int j = 0; j < deliveryAreaListBeanList.size(); j++) {
                double la = Double.parseDouble(deliveryAreaListBeanList.get(j).getReceiverLat());
                double lo = Double.parseDouble(deliveryAreaListBeanList.get(j).getReceiverLng());
                points.add(new LatLng(la, lo));
            }
        }
    }

    @Override
    protected void onDestroy() {
        addressSearchHelper.destroy();
        super.onDestroy();

    }


}
