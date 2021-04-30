package com.pbph.yuguo.util;

import android.Manifest;
import android.app.Activity;

import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * Created by Administrator on 2018/5/14.
 */

public class RxPermissionsManager {
    public final static int ALLOW = 0x211;
    public final static int REFUSE_NO_ASK = 0x213;
    public final static int REFUSE = 0x212;

    public static void checkPerMission(Activity activity,OnCheckPermissionListener permissionListener) {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(permissions).subscribe(permission -> {
            if (permission.granted) {
                permissionListener.checkPermission(ALLOW);
//                getBaseView().toastShort("已获取权限" +[图片] permission.name);
            } else if (permission.shouldShowRequestPermissionRationale) { //拒绝权限请求
                permissionListener.checkPermission(REFUSE);
//                getBaseView().toastShort("已拒绝权限" +[图片] permission.name);
            } else {  // 拒绝权限请求,并不再询问 需要进入设置界面去设置权限
                permissionListener.checkPermission(REFUSE_NO_ASK);
//                getBaseView().toastShort("已拒绝权限并不再询问" +[图片] permission.name);
            }
        });
    }

    public interface OnCheckPermissionListener {
        void checkPermission(int state);
    }
}
