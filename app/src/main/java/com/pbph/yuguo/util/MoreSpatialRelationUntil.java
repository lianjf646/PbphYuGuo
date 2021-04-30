package com.pbph.yuguo.util;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.SpatialRelationUtil;

import java.util.List;

/**
 * Created by v on 2019/5/8.
 */
public class MoreSpatialRelationUntil {

    public MoreSpatialRelationUntil() {

    }

    public static boolean isPolygonContainsPoints(List<List<LatLng>> regionLalngList, LatLng latLng) {
        for (int j = 0; j < regionLalngList.size(); j++) {
            if (SpatialRelationUtil.isPolygonContainsPoint(regionLalngList.get(j), latLng)) return true;

        }
        return false;
    }
}
