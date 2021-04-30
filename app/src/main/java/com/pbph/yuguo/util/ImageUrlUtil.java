package com.pbph.yuguo.util;

import android.text.TextUtils;

import java.util.HashMap;

public class ImageUrlUtil {

    public static String getImageUrl(String url) {

        if (TextUtils.isEmpty(url)) {
            return url;
        }
        String urlStr = url;
        if (url.startsWith("http")) {
            return url;
        } else {
            HashMap<String, String> map = getUrlMap(url);
            if (!map.isEmpty()) {
//                urlStr = String.format(ConstantData.IMAGE_VIEW_URL, map.get("bucket"), map.get("key"));
                urlStr = url;
            }
        }
        return urlStr;
    }

    private static HashMap<String, String> getUrlMap(String url) {
        HashMap map = new HashMap();
//        Log.e("url", url);
        String datas[] = url.split("&");
        for (String data : datas) {
            int index = data.indexOf("=");
            String key = data.substring(0, index);
            String value = data.substring(index + 1, data.length());
//            Log.e("getUrlMap", data + "  " + value);
            map.put(key, value);
        }
        return map;
    }


}
