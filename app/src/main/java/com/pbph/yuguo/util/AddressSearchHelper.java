package com.pbph.yuguo.util;

import android.content.Context;
import android.widget.Toast;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.List;

/**
 * Created by 连嘉凡 on 2018/8/8.
 */

public class AddressSearchHelper implements OnGetPoiSearchResultListener {
    List<PoiInfo> poiInfos;
    private Context context;
    private PoiSearch mPoiSearch;
    private OnGetAddressInfoListener onGetAddressInfoListener;

    public AddressSearchHelper(Context context, OnGetAddressInfoListener onGetAddressInfoListener) {
        this.context = context;
        this.onGetAddressInfoListener = onGetAddressInfoListener;
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
    }

    public void setSearchInfo(String location, String searchInfo) {
        final PoiCitySearchOption poiCitySearchOption = new PoiCitySearchOption()
                .city(location)
                .keyword(searchInfo);

        mPoiSearch.searchInCity(poiCitySearchOption);

    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        //获取POI检索结果
        if (poiResult == null) {
            Toast.makeText(context, "没有搜索到指定地里位置", Toast.LENGTH_SHORT).show();
            return;
        }
        if (poiResult.getAllPoi() == null) {
            Toast.makeText(context, "没有搜索到指定地里位置", Toast.LENGTH_SHORT).show();
            return;
        }
        poiInfos = poiResult.getAllPoi();
        onGetAddressInfoListener.onGetListPoiInfo(poiResult.getAllPoi());
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    public void destroy() {
        mPoiSearch.destroy();
    }

    public interface OnGetAddressInfoListener {
        void onGetListPoiInfo(List<PoiInfo> poiInfos);

    }
}
