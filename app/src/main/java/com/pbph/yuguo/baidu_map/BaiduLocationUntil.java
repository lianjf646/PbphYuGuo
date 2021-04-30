package com.pbph.yuguo.baidu_map;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by 连嘉凡 on 2018/8/10.
 */

public final class BaiduLocationUntil {

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private LocationAdressInfoListener locationAdressInfoListener;
    private Context context;

    public static BaiduLocationUntil getInstance() {
        return BaiduLocationUntilHolder.baiduLocationUntil;

    }

    private static class BaiduLocationUntilHolder {
        private static BaiduLocationUntil baiduLocationUntil = new BaiduLocationUntil();

    }
    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
    //原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明

    public void onCreate(Context context) {
        this.context = context;
        mLocationClient = new LocationClient(context);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(3000);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.start();

    }

    public void setLocationAdressInfoListener(LocationAdressInfoListener locationAdressInfoListener) {
        this.locationAdressInfoListener = locationAdressInfoListener;
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            /**  locType
             *   61 	GPS定位结果，GPS定位成功
             *   62 	无法获取有效定位依据，定位失败，请检查运营商网络或者WiFi网络是否正常开启，尝试重新请求定位
             *   63 	网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位
             *   66 	离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果
             *   67 	离线定位失败
             *   161 	网络定位结果，网络定位成功
             *   162 	请求串密文解析失败，一般是由于客户端SO文件加载失败造成，请严格参照开发指南或demo开发，放入对应SO文件
             *   167 	服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位
             *   505 	AK不存在或者非法，请按照说明文档重新申请AK
             */
            int locType = location.getLocType();
            double la = location.getLatitude();//经度
            double lo = location.getLongitude();//维度
            String locationDescribe = location.getLocationDescribe();    //获取位置描述信息
            Log.e("LocationInfoSSSS", "onReceiveLocation: " +
                    "" + addr + "\n" + country + "\n" + province + "\n" + city + "\n" + district
                    + "\n" + street + "\n" + la + "\n" + lo + "\n" + location.getAddress()
                    + "\n" + locationDescribe + "code\n" + location.getLocType());

            if (locType != 161) {
                locationAdressInfoListener.onGetAddressInfo(location);
                return;
            }

            locationAdressInfoListener.onGetAddressInfo(location);
            onStop();
        }
    }

    public void onStop() {
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
    }

    public interface LocationAdressInfoListener {
        void onGetAddressInfo(BDLocation location);


    }
}
