package com.pbph.yuguo.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetAppSmsCodeResquest;
import com.pbph.yuguo.request.GetJpushIdResquest;
import com.pbph.yuguo.request.LoginResquest;
import com.pbph.yuguo.util.AMUtils;
import com.pbph.yuguo.util.DownTimer;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity implements DownTimer.DownTimerListener, View.OnClickListener {

    private EditText mUsername, mCode, mImageCodeEdit;
    private TextView getCodeTextView = null;
    private DownTimer downTimer = new DownTimer();
    private ImageView mImageCode;
    private RelativeLayout mImageCodeLayout;
    private CheckBox checkLoginAgree;
    private TextView loginPlatformAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//这句话 很重要 不要轻易删除
        BaseObserver.has1010Error = false;


        setContentView(R.layout.activity_login);

        initTitle(TITLE_STYLE_WHITE, "登录", true, false);

        downTimer.setListener(this);
        checkLoginAgree = findViewById(R.id.cb_login_agree);
        loginPlatformAgree = findViewById(R.id.tv_login_platform_agree);
        mImageCode = findViewById(R.id.iv_login_image_code);
        mImageCodeLayout = findViewById(R.id.rl_login_image_code_layout);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mImageCodeLayout.getLayoutParams().width = displayMetrics.widthPixels / 6;
        mImageCodeLayout.getLayoutParams().height = displayMetrics.widthPixels / 12;
        mImageCode.getLayoutParams().width = displayMetrics.widthPixels / 6;
        mImageCode.getLayoutParams().height = displayMetrics.widthPixels / 12;
        mUsername = findViewById(R.id.edt_login_phone);
        mCode = findViewById(R.id.edt_login_sms_code);
        mImageCodeEdit = findViewById(R.id.edt_login_image_code);
        getCodeTextView = findViewById(R.id.tv_login_get_code);
        findViewById(R.id.btn_login_submit).setOnClickListener(this);
        getCodeTextView.setOnClickListener(this);
        loginPlatformAgree.setOnClickListener((view) -> {

            startActivity(new Intent(mContext, MyBrowsersActivity.class)
                    .putExtra("title", "用户使用协议")
                    .putExtra("url", "file:///android_asset/userAgreement.html")
            );
        });
        mImageCode.setOnClickListener(this);
        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11 && AMUtils.isMobile(String.valueOf(s))) {
//                    AMUtils.onInactive(LoginActivity.this, mUsername);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        requestPermissions();
    }

    private void login(String mobile, String smsCode) {
        WaitUI.Show(this);
        HttpAction.getInstance().login(new LoginResquest(mobile, smsCode)).subscribe(new BaseObserver<>(this, (response -> {
            WaitUI.Cancel();
            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }
            YuGuoApplication.userInfo.setCustomerId(response.getData().getCustomerId());
            YuGuoApplication.userInfo.setToken(response.getData().getToken());

            getJpushId();
//            startActivity(new Intent(LoginActivity.this, MainTabActivity.class));
            finish();
        })));
    }

    private void getAppSmsCode(String mobile) {
        WaitUI.Show(this);
        HttpAction.getInstance().getAppSmsCode(new GetAppSmsCodeResquest(mobile)).subscribe(new BaseObserver<>(this, (response -> {
            WaitUI.Cancel();
            WaitUI.Cancel();
            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }
            downTimer.startDown(60 * 1000);
        })));
    }

    private void requestPermissions() {

        List<String> permissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isNeedRequestPermissions(permissions)) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
        } else {
            checkUpdate();
            application.createFolder();
        }
    }


    private boolean isNeedRequestPermissions(List<String> permissions) {
        // 定位精确位置
//        addPermission(permissions, permission.ACCESS_FINE_LOCATION);
        // 存储权限
        addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        addPermission(permissions, Manifest.permission.REQUEST_INSTALL_PACKAGES);
        // 读取手机状态
        addPermission(permissions, Manifest.permission.READ_PHONE_STATE);
        // 定位精确位置
        addPermission(permissions, Manifest.permission.ACCESS_COARSE_LOCATION);
        addPermission(permissions, Manifest.permission.ACCESS_FINE_LOCATION);
        addPermission(permissions, Manifest.permission.REQUEST_INSTALL_PACKAGES);

        return permissions.size() > 0;
    }

    private void addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            application.createFolder();
            checkUpdate();
        } else {
            boolean b = shouldShowRequestPermissionRationale(permissions[0]);
            if (!b) {
//                showToast("请给与app相应权限，否则将无法正常使用");
//                finish();
//                System.exit(0);
            } else {
                showToast("请给与app相应权限，否则将无法正常使用");
                requestPermissions();
            }
        }
    }

    @Override
    public void onTick(long millisUntilFinished) {
//        getCodeTextView.setTextColor(getResources().getColor(R.color.main_color));
        getCodeTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s后重发");
        getCodeTextView.setClickable(false);
    }

    @Override
    public void onFinish() {
//        getCodeTextView.setTextColor(getResources().getColor(R.color.dark_gray));
        getCodeTextView.setText(R.string.btn_login_getcode_text);
        getCodeTextView.setClickable(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        boolean edge_out_id = getIntent().getBooleanExtra("kickedByOtherClient", false);
//        if (edge_out_id == true) {
//            showDialog();
////            mHintDialog.show();
//        }
    }

    private void showDialog() {
        AlertDialog.Builder mHintDialog = null;
        if (mHintDialog == null) {
            mHintDialog = new AlertDialog.Builder(LoginActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            mHintDialog.setTitle("提示:");
            mHintDialog.setMessage("您的账号在别的设备上登入，被迫下线");
            mHintDialog.setPositiveButton("知道了", null);
        }
        mHintDialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        downTimer.stopDown();
        downTimer = null;


    }

    @Override
    public void onClick(View v) {
//        if (true) {
//            startActivity(new Intent(this, MainTabActivity.class));
//            finish();
//            return;
//        }
        String phone = mUsername.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, R.string.phone_number_is_null, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!AMUtils.isMobile(String.valueOf(phone))) {
            Toast.makeText(this, R.string.Illegal_phone_number, Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()) {
            case R.id.btn_login_submit:
                String smsCode = mCode.getText().toString();
                String userName = mUsername.getText().toString();
                if (!checkLoginAgree.isChecked()) {
                    AlertDialog.Builder cancelBuilder = new AlertDialog.Builder(this);
                    cancelBuilder.setTitle("提示");
                    cancelBuilder.setMessage("您还未同意平台协议，请在确认同意协议前，谨慎阅读并充分理解内容。");
                    cancelBuilder.setPositiveButton("确定", (dialog, which) -> {
                    });
                    cancelBuilder.show();
//                    showToast("您还为同意平台协议，请在确认同意协议前，谨慎阅读并充分理解内容。");
                    return;
                }
                if (TextUtils.isEmpty(smsCode)) {
                    Toast.makeText(this, R.string.sms_number_is_null, Toast.LENGTH_SHORT).show();
                    return;
                }
                login(phone, smsCode);
                break;
            case R.id.tv_login_get_code:
                getAppSmsCode(phone);
                break;
            case R.id.iv_login_image_code:
                break;
        }
    }

    @Override
    public void onLeftClick() {
        finish();
    }


    private void getJpushId() {

        if (!YuGuoApplication.isLogin()) return;

        if (TextUtils.isEmpty(YuGuoApplication.userInfo.getJpushId())) return;

        HttpAction.getInstance().getJpushId(new GetJpushIdResquest(String.valueOf(YuGuoApplication.userInfo.getCustomerId()), YuGuoApplication.userInfo.getJpushId())).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                return;
            }
        })));
    }

}
