package com.pbph.yuguo.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pbph.yuguo.base.Province9MBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/25.
 */

public class ProvincePicker {
    Province9MBean province9MBean;
    ArrayList<Province9MBean.DataBean.ProvinceVoListBean> provinceBeans;
    OptionsPickerView areaPtions;

    private static class ProvincePickerHolder {
        private static ProvincePicker instance = new ProvincePicker();
    }

    public static ProvincePicker getInstance() {
        return ProvincePickerHolder.instance;
    }

    public void showAreaPtions() {
        areaPtions.show();
    }

    public void initAreaPicker(Context context, OnSelectAddressListener onSelectAddressListener) {// 弹出选择器

        areaPtions = new OptionsPickerView.Builder(context, (options1, options2, options3, v) -> {
            if (provinceBeans == null || provinceBeans.size() <= 0) return;
            if (options1 >= provinceBeans.size()) return;
            Province9MBean.DataBean.ProvinceVoListBean pvo = provinceBeans.get(options1);
            if (pvo == null) return;
            //设置市
            List<Province9MBean.DataBean.ProvinceVoListBean.CityVoListBean> cityList = pvo.getCityVoList();
            if (cityList == null || cityList.size() <= 0) return;
            if (options2 >= cityList.size()) return;
            Province9MBean.DataBean.ProvinceVoListBean.CityVoListBean cvo = cityList.get(options2);
            if (cvo == null) return;
            List<Province9MBean.DataBean.ProvinceVoListBean.CityVoListBean.AreaListBean> areaList = cvo.getAreaList();
            if (areaList == null || areaList.size() <= 0) return;
            if (options3 >= areaList.size()) return;
            String area = areaList.get(options3).getAreaName();
            if (TextUtils.isEmpty(area)) return;
            StringBuilder areaBuilder = new StringBuilder();
            areaBuilder.append(pvo.getProvinceName());
            areaBuilder.append("-");
            areaBuilder.append(cvo.getCityName());
            areaBuilder.append("-");
            areaBuilder.append(area);
            Log.e("province9MBean", areaBuilder.toString());
            onSelectAddressListener.onSelectAddress(pvo.getProvinceName(), cvo.getCityName(), area);
//            updateUserData("", "", pvo.getName(), cvo.getName());
        }).setTitleText("城市选择").build();
        setDatas4OptionsPickerView(context);
    }

    void setDatas4OptionsPickerView(Context context) {
        String JsonData = AMUtils.readFileFromAsset2String(context, "9mprovince.json");
        province9MBean = new Gson().fromJson(JsonData, new TypeToken<Province9MBean>() {

        }.getType());
//        provinceBeans = new Gson().fromJson(JsonData, new TypeToken<List<ProvinceBean>>() {
//
//        }.getType());
        provinceBeans = (ArrayList<Province9MBean.DataBean.ProvinceVoListBean>) province9MBean.getData().getProvinceVoList();
        List<String> options1Items = new ArrayList<>(provinceBeans.size());
        List<List<String>> options2Items = new ArrayList<>(provinceBeans.size());
        List<List<List<String>>> options3Items = new ArrayList<>(provinceBeans.size());

        for (Province9MBean.DataBean.ProvinceVoListBean pvo : provinceBeans) {
            List<Province9MBean.DataBean.ProvinceVoListBean.CityVoListBean> cityList = pvo.getCityVoList();
            List<String> cityStrs = new ArrayList<>(cityList.size());
            List<List<String>> areaLists = new ArrayList<>(cityList.size());
            for (Province9MBean.DataBean.ProvinceVoListBean.CityVoListBean cvo : cityList) {
                cityStrs.add(cvo.getCityName());
                List<String> areaStrs = new ArrayList<>(cityList.size());
                List<Province9MBean.DataBean.ProvinceVoListBean.CityVoListBean.AreaListBean> temps = cvo.getAreaList();
                for (Province9MBean.DataBean.ProvinceVoListBean.CityVoListBean.AreaListBean areaBean : temps) {
                    areaStrs.add(areaBean.getAreaName());
                }
                areaLists.add(areaStrs);
//                if (temps == null) {
//                    areaLists.add(new ArrayList<>());
//                }
//                else {
//                    areaLists.add(cvo.getCityName());
//                }
            }
            options1Items.add(pvo.getProvinceName());
            options2Items.add(cityStrs);
            options3Items.add(areaLists);
        }
        areaPtions.setPicker(options1Items, options2Items, options3Items);
    }

    public interface OnSelectAddressListener {
        void onSelectAddress(String province, String city, String area);
    }
}
