package com.pbph.yuguo.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetCustomerInfoByIdRequest;
import com.pbph.yuguo.response.GetCustomerInfoByIdResponse;
import com.pbph.yuguo.util.SpHelper;
import com.pbph.yuguo.util.YesNoDialog;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class SplashActivity extends Activity {

    long lastTime;

    protected YuGuoApplication application = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        application = (YuGuoApplication) getApplication();

        setContentView(R.layout.activity_splash);
//        String time = System.currentTimeMillis() + "";
//        Log.e("test", time.length() + "   " + time);


        lastTime = System.currentTimeMillis();
        requestPermissions();

    }

    private boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }


    //根据用户id查询用户详情
    private void getCustomerInfoById() {

        if (!isNetworkConnected(this)) {
            onNetFail();
            return;
        }

        if (YuGuoApplication.userInfo.getCustomerId() == null || YuGuoApplication.userInfo.getCustomerId() == -1) {
            YuGuoApplication.userInfo.setToken("");
            YuGuoApplication.userInfo.setCustomerId(-1);

            ongetCustomerInfoById();
            return;
        }

        HttpAction.getInstance().getCustomerInfoById(new GetCustomerInfoByIdRequest(YuGuoApplication.userInfo.getCustomerId())).subscribe(new BaseObserver<>(SplashActivity.this, response -> {

            if (response.getCode() != 200) {
                YuGuoApplication.userInfo.setToken("");
                YuGuoApplication.userInfo.setCustomerId(-1);
            } else {
                GetCustomerInfoByIdResponse.DataBean.CustomerBean customer = response.getData().getCustomer();
                YuGuoApplication.userInfo.setCustomerPhone(customer.getCustomerPhone());
                YuGuoApplication.userInfo.setCustomerLevelType(customer.getCustomerLevelType());
            }

            ongetCustomerInfoById();

        }, true));

    }

    private void ongetCustomerInfoById() {
        long now = 3000 - System.currentTimeMillis() - lastTime;

        if (now > 0) {
            new Handler().postDelayed(() -> {
                if (TextUtils.isEmpty(YuGuoApplication.userInfo.getToken())) {
                    goToMain();
                } else {
                    YuGuoApplication.userInfo.setCustomerId(SpHelper.getInstance().getIntUserId());
                    YuGuoApplication.userInfo.setToken(SpHelper.getInstance().getToken());

                    goToMain();
                }
            }, now);
        } else {
            goToMain();
        }
    }

    private void onNetFail() {
        Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> {
            finish();
        }, 1800);
    }


    private void goToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void goToMain() {
        startActivity(new Intent(this, MainTabActivity.class));
        finish();
    }


    @Override
    protected void onResume() {
        JPushInterface.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        JPushInterface.onPause(this);
        super.onPause();
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissions() {

        List<String> permissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isNeedRequestPermissions(permissions)) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
        } else {
            getCustomerInfoById();
        }
    }

    private boolean isNeedRequestPermissions(List<String> permissions) {
        // 定位精确位置
//        addPermission(permissions, permission.ACCESS_FINE_LOCATION);
        // 存储权限
        addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // 读取手机状态
        addPermission(permissions, Manifest.permission.READ_PHONE_STATE);
        // 定位精确位置
        addPermission(permissions, Manifest.permission.ACCESS_COARSE_LOCATION);
        addPermission(permissions, Manifest.permission.ACCESS_FINE_LOCATION);
//        addPermission(permissions, Manifest.permission.REQUEST_INSTALL_PACKAGES);

        return permissions.size() > 0;
    }

    private void addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(permission) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < permissions.length; i++) {

            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) continue;

            boolean b = shouldShowRequestPermissionRationale(permissions[i]);
            if (!b) {//拒绝且不再展示
                applyLocationJurisdiction(permissions[i]);
                return;
            }

            Toast.makeText(this, "请给与app相应权限，否则将无法正常使用", Toast.LENGTH_SHORT).show();
            requestPermissions();

            return;
        }
        getCustomerInfoById();
    }

    public boolean checkPermission(@NonNull String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * 判断是否有定位权限
     */
    private void applyLocationJurisdiction(String str) {

        if (PackageManager.PERMISSION_GRANTED != getPackageManager().checkPermission(str, getPackageName())) {
            YesNoDialog.show(this, "需要权限请前往设置", new YesNoDialog.OnClickRateDialog() {
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
                    finish();
                }
            }, false);
        }
    }

}
