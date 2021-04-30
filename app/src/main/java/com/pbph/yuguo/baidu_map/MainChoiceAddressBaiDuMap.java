package com.pbph.yuguo.baidu_map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.pbph.yuguo.R;
import com.pbph.yuguo.activity.AddAddressInfoActvity;
import com.pbph.yuguo.activity.ChoiceAddressListSearchActivity;
import com.pbph.yuguo.activity.MainTabActivity;
import com.pbph.yuguo.application.UserInfo;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.singlepointlistener.OnItemSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetStoreListRequest;
import com.pbph.yuguo.response.GetStoreListResponse;
import com.pbph.yuguo.util.MoreSpatialRelationUntil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 挡风的纱窗 on 2019/2/1.
 */
public class MainChoiceAddressBaiDuMap extends BaseActivity {
    public final static String LONGITUDE = "longitude";
    public final static String ADDRESS = "address";
    public final static String NAME = "name";
    public final static String CITY_NAME = "city_name";
    public final static String RECEIVER_VILLAGE = "receiverVillage";
    public final static String DISTRICT = "district";
    public final static String RECEIVER_PROVINCE = "receiverProvince";
    public final static String RECEIVER_CITY = "receiverCity";
    public final static String RECEIVER_AREA = "receiverRigion";
    public final static String LATITUDE = "latitude";

    private MapView mMapView = null;
    BaiduMap mBaiduMap;
    GeoCoder mCoder;

    private ListView lv;
    private BaiDuAddressAdapter baiDuAddressAdapter;
    private TextView tvSearchAddAddress;
    private ImageView ivGoneHeard;
    private ImageView bmapLocalMyself;
    private LinearLayout linearView;

    List<String> stringList;
    private String province = "";
    private String city = "";
    private String area = "";

    MyMapStatusChangeListener myMapStatusChangeListener = new MyMapStatusChangeListener();
    List<List<LatLng>> regionLalngList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_test);
        initTitle(TITLE_STYLE_WHITE, "选择收货地址", true, false);
        initClient();
        startLocation();

    }

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();

    }

    @Override
    protected void onDestroy() {
        mBaiduMap.setMyLocationEnabled(false);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mCoder.destroy();
        super.onDestroy();

    }

    private void initClient() {
        stringList = Arrays.asList(this.getResources().getStringArray(R.array.areaList));
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        baiDuAddressAdapter = new BaiDuAddressAdapter(this);
        linearView = findViewById(R.id.linear_view);
        lv = findViewById(R.id.lv);
        lv.setAdapter(baiDuAddressAdapter);
        lv.setOnItemClickListener(new OnItemSPClickListener() {
            @Override
            public void onItemClickSucc(AdapterView<?> parent, View view, int position, long id) {

                if (regionLalngList.isEmpty()){
                    Toast.makeText(mContext, "数据获取中", Toast.LENGTH_SHORT).show();
                    return;
                }
                PoiInfo poiInfo = (PoiInfo) baiDuAddressAdapter.getItem(position);
                if (!MoreSpatialRelationUntil.isPolygonContainsPoints(regionLalngList, poiInfo.getLocation())) {
                    Toast.makeText(mContext, "不在配送范围内", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (!poiInfo.city.equals("哈尔滨市")) {
//                    Toast.makeText(MainChoiceAddressBaiDuMap.this, "只能在哈尔滨市范围内", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (!stringList.contains(area)) {
//                    ToastUtil.showToast(MainChoiceAddressBaiDuMap.this, String.valueOf(getString(R.string.no_choice_address)));
//                    return;
//                }
                UserInfo.getInstance().setCity(city);
                UserInfo.getInstance().setDistrict(area);
                UserInfo.getInstance().setStreet(poiInfo.name);
                UserInfo.getInstance().setLatitude(poiInfo.location.latitude);
                UserInfo.getInstance().setLongitude(poiInfo.location.longitude);


                UserInfo.getInstance().setRecAddId(null);
                UserInfo.getInstance().setRecLongitude(null);
                UserInfo.getInstance().setRecLatitude(null);
                UserInfo.getInstance().setRecCity(null);
                UserInfo.getInstance().setRecStreet(null);
                UserInfo.getInstance().setReDistrict(null);

                startActivity(new Intent(MainChoiceAddressBaiDuMap.this, MainTabActivity.class));
            }
        });

        mMapView = findViewById(R.id.bmapView);
        tvSearchAddAddress = findViewById(R.id.tv_search_add_address);
        tvSearchAddAddress.setOnClickListener(v -> {
            startActivity(new Intent(this, ChoiceAddressListSearchActivity.class).putExtra(ChoiceAddressListSearchActivity
                    .ACT_TYPE, 1));

        });
        ivGoneHeard = findViewById(R.id.iv_gone_heard);
        ivGoneHeard.setOnClickListener(v -> {
            linearView.setVisibility(View.GONE);
        });
        bmapLocalMyself = findViewById(R.id.bmap_local_myself);
        bmapLocalMyself.setOnClickListener(v -> {
            startLocation();
        });
        mBaiduMap = mMapView.getMap();
        //开启地图的定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //监听位置发生变化
        mBaiduMap.setOnMapStatusChangeListener(myMapStatusChangeListener);
        mMapView.showScaleControl(false);

        mCoder = GeoCoder.newInstance();
        mCoder.setOnGetGeoCodeResultListener(listener);
        mMapView.showScaleControl(false);
        mMapView.showZoomControls(false);
        getStoreList();
    }

    /**
     * 获取门店圈起的经纬度
     */
    private void getStoreList() {
        HttpAction.getInstance().getStoreList(new GetStoreListRequest()).subscribe(new BaseObserver<>(this, response -> {

            if (response.getCode() != 200) {
                Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }
            List<GetStoreListResponse.DataBean.StoreListBean> storeListBeanList = response.getData().getStoreList();
            drawRegion(storeListBeanList);

        }, (code, message) -> {
        }));

    }

    /**
     * 绘制配送范围区域
     */
    private void drawRegion(List<GetStoreListResponse.DataBean.StoreListBean> storeListBeanList) {
        for (int i = 0; i < storeListBeanList.size(); i++) {
            //多边形顶点位置
            List<LatLng> points = new ArrayList<>();
            List<GetStoreListResponse.DataBean.StoreListBean.DeliveryAreaListBean> deliveryAreaListBeanList = storeListBeanList
                    .get(i).getDeliveryAreaList();
            if (deliveryAreaListBeanList.size() < 3) continue;
            regionLalngList.add(points);
            for (int j = 0; j < deliveryAreaListBeanList.size(); j++) {
                double la = Double.parseDouble(deliveryAreaListBeanList.get(j).getReceiverLat());
                double lo = Double.parseDouble(deliveryAreaListBeanList.get(j).getReceiverLng());
                points.add(new LatLng(la, lo));
            }
            //构造PolygonOptions
            PolygonOptions mPolygonOptions = new PolygonOptions().points(points).fillColor(0xAAFFB2B2) //填充颜色
                    .stroke(new Stroke(1, 0xAAFFB2B2)); //边框宽度和颜色
            //在地图上显示多边形
            mBaiduMap.addOverlay(mPolygonOptions);

        }
    }


    /**
     * 开始定位并且显示位置
     */
    private void startLocation() {
        BaiduLocationUntil.getInstance().onCreate(getApplicationContext());
        BaiduLocationUntil.getInstance().setLocationAdressInfoListener(location -> {
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude()).longitude(location.getLongitude())
                    .build();
            mBaiduMap.setMyLocationData(locData);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(latLng, 15.0f);
            mBaiduMap.animateMapStatus(u);
            mCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng)
                    // POI召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。默认值为1000
                    .radius(500));
        });
    }

    /**
     * 监听百度地图状态改变
     */
    private class MyMapStatusChangeListener implements BaiduMap.OnMapStatusChangeListener {

        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

        }

        @Override
        public void onMapStatusChange(MapStatus mapStatus) {

        }

        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {
            mCoder.reverseGeoCode(new ReverseGeoCodeOption().location(mapStatus.target)
                    // POI召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。默认值为1000
                    .radius(500));

        }
    }

    /**
     * 逆地理编码
     */
    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
            if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有找到检索结果
                Toast.makeText(MainChoiceAddressBaiDuMap.this, "由于网络原因，请移动地图", Toast.LENGTH_SHORT).show();
                return;
            } else {
                List<PoiInfo> poiInfoList = reverseGeoCodeResult.getPoiList();
                province = reverseGeoCodeResult.getAddressDetail().province;
                city = reverseGeoCodeResult.getAddressDetail().city;
                area = reverseGeoCodeResult.getAddressDetail().district;
                //判断点pt是否在位置点列表mPoints构成的多边形内。
                if (poiInfoList == null || poiInfoList.size() == 0) return;
                baiDuAddressAdapter.setPoiInfoList(poiInfoList, regionLalngList);
//                LatLng pt = reverseGeoCodeResult.getPoiList().get(0).getLocation();
//                if (MoreSpatialRelationUntil.isPolygonContainsPoints(regionLalngList, pt)) {
//                    Toast.makeText(mContext, "在配送范围内", Toast.LENGTH_SHORT).show();
//                }
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddAddressInfoActvity.ADDRESS_INFO && resultCode == RESULT_OK) {
            double la = data.getDoubleExtra(MainChoiceAddressBaiDuMap.LATITUDE, 0.0);
            double lo = data.getDoubleExtra(MainChoiceAddressBaiDuMap.LONGITUDE, 0.0);
            String name = data.getStringExtra(MainChoiceAddressBaiDuMap.NAME);
            String address = data.getStringExtra(MainChoiceAddressBaiDuMap.ADDRESS);
            String city_name = data.getStringExtra(MainChoiceAddressBaiDuMap.CITY_NAME);
            String province = data.getStringExtra(MainChoiceAddressBaiDuMap.RECEIVER_PROVINCE);
            String city = data.getStringExtra(RECEIVER_CITY);
            String area = data.getStringExtra(RECEIVER_AREA);
            Intent intent1 = MainChoiceAddressBaiDuMap.this.getIntent();

            intent1.putExtra(LATITUDE, la);
            intent1.putExtra(LONGITUDE, lo);
            intent1.putExtra(RECEIVER_VILLAGE, name);
            intent1.putExtra(RECEIVER_PROVINCE, province);// 省
            intent1.putExtra(RECEIVER_CITY, city);        // 市
            intent1.putExtra(RECEIVER_AREA, area);        // 区
            MainChoiceAddressBaiDuMap.this.setResult(RESULT_OK, intent1);
            finish();
        }
    }
}
