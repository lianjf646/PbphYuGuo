package com.pbph.yuguo.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.ChoiceAddressAdapter;
import com.pbph.yuguo.application.UserInfo;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.baidu_map.BaiduLocationUntil;
import com.pbph.yuguo.baidu_map.MainChoiceAddressBaiDuMap;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetAddressListByCustomerIdResquest;
import com.pbph.yuguo.response.GetAddressListByCustomerIdResponse;
import com.pbph.yuguo.util.YesNoDialog;

import java.util.List;

/**
 * Created by 连嘉凡 on 2018/8/7.
 */

public class ChoiceAddressListActivity extends BaseActivity implements View.OnClickListener {
    private ListView mLvChoiceAddress;
    private ChoiceAddressAdapter choiceAddressAdapter;
    private TextView tvSearchAddress;
    private TextView mTvRefreshLocation;
    private ImageView mBtnLife;
    private TextView mTvMoreAddress;
    private TextView tvAddAddress;
    private TextView tvLocationAddress;
    private ImageView ivRefresh;

    private BDLocation bdLocation;
    private List<GetAddressListByCustomerIdResponse.DataBean.ReceiverAddressdtoBean> dtoListBeanList;
    GetAddressListByCustomerIdResponse.DataBean.ReceiverAddressdtoBean dtoListBean;
//    boolean b = false; // true 有权限 false 没有权限
    /**
     * 读写权限  自己可以添加需要判断的权限
     */
    public final static String[] permissionsREAD = {Manifest.permission.LOCATION_HARDWARE, Manifest.permission
            .ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    public void onLeftClick() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_address_list);
        hideTitleView();
        initView();
        View view = LayoutInflater.from(this).inflate(R.layout.include_choice_address_heard_view, null);
        initHeardView(view);
    }

    /**
     * 判断是否有定位权限
     */
    private boolean applyLocationJurisdiction() {
        PackageManager pm = getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED == pm.checkPermission(Manifest.permission
                .ACCESS_COARSE_LOCATION, getPackageName()));
        if (!permission) {
            YesNoDialog.show(this, "需要定位权限请前往设置", new YesNoDialog.OnClickRateDialog() {
                @Override
                public void onClickLeft() {

                }

                @Override
                public void onClickRight() {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivity(intent);
                }
            }, false);
        }
        return permission;
    }

    private void initView() {
        mBtnLife = findViewById(R.id.btn_lefts);
        mBtnLife.setOnClickListener(this);
        tvSearchAddress = findViewById(R.id.tv_search_add_address);
        tvSearchAddress.setOnClickListener(this);
        tvAddAddress = findViewById(R.id.tv_add_address);
        tvAddAddress.setOnClickListener(this);
        mLvChoiceAddress = findViewById(R.id.lv_choice_address);
        choiceAddressAdapter = new ChoiceAddressAdapter(this);
        mLvChoiceAddress.setAdapter(choiceAddressAdapter);

//        choiceAddressAdapter.setDataListener(pos -> {
//            updateReceiverAddress(pos);
//        });

    }

    private void initHeardView(View view) {
        mTvRefreshLocation = view.findViewById(R.id.tv_refresh_location);
        mTvRefreshLocation.setOnClickListener(this);
        tvLocationAddress = view.findViewById(R.id.tv_location_address);
        tvLocationAddress.setOnClickListener(this);
        mTvMoreAddress = view.findViewById(R.id.tv_more_address);
        mTvMoreAddress.setOnClickListener(this);
        mLvChoiceAddress.addHeaderView(view);
        mLvChoiceAddress.setOnItemClickListener((parent, view1, position, id) -> {
            if (position == 0) return;
            dtoListBean = dtoListBeanList.get(position - 1);
            UserInfo.getInstance().setRecAddId(dtoListBean.getAddressId());
            UserInfo.getInstance().setRecLongitude(Double.valueOf(dtoListBean.getReceiverLng()));
            UserInfo.getInstance().setRecLatitude(Double.valueOf(dtoListBean.getReceiverLat()));
            UserInfo.getInstance().setRecCity(dtoListBean.getReceiverCityName());
            UserInfo.getInstance().setRecStreet(dtoListBean.getReceiverVillage());
            UserInfo.getInstance().setReDistrict(dtoListBean.getReceiverVillage() + dtoListBean.getReceiverAddressDetail());

            UserInfo.getInstance().setRecUserName(dtoListBean.getReceiverName());
            UserInfo.getInstance().setRecPhone(dtoListBean.getReceiverPhone());
            finish();
        });
        ivRefresh = view.findViewById(R.id.iv_refresh);
        ivRefresh.setOnClickListener(this);
        tvLocationAddress.setText("定位地址:");
        tvLocationAddress.append(UserInfo.getInstance().getStreet() == null ? "重新定位" : UserInfo.getInstance().getStreet());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lefts: {
                finish();
            }
            break;

            case R.id.tv_search_add_address: {//搜索地址
                Intent intent = new Intent(this, ChoiceAddressListSearchActivity.class);
                intent.putExtra(ChoiceAddressListSearchActivity.ACT_TYPE, 1);
                startActivity(intent);
            }
            break;

            case R.id.tv_add_address: {//添加地址
                if (!YuGuoApplication.isLogin()) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    startActivity(new Intent(this, AddAddressInfoActvity.class));
                }

            }
            break;

            case R.id.tv_refresh_location: {//刷新地址;
                if (!applyLocationJurisdiction()) return;
                BaiduLocationUntil.getInstance().onCreate(getApplicationContext());
                startAnimation();
                BaiduLocationUntil.getInstance().setLocationAdressInfoListener(location -> {

                    if (location.getLocType() != 161) {
                        return;
                    }

                    bdLocation = location;
                    tvLocationAddress.setText("定位地址:");
                    tvLocationAddress.append(location.getStreet());
                    stopAnimation();
                });

            }
            break;

            case R.id.tv_more_address: {//更多地址
                ;
                if (!applyLocationJurisdiction()) return;
                startActivity(new Intent(this, MainChoiceAddressBaiDuMap.class));
            }
            break;

            case R.id.tv_location_address: {
                if (bdLocation == null) {
                    UserInfo.getInstance().setRecAddId(null);
                    UserInfo.getInstance().setRecLongitude(null);
                    UserInfo.getInstance().setRecLatitude(null);
                    UserInfo.getInstance().setRecCity(null);
                    UserInfo.getInstance().setRecStreet(null);
                    UserInfo.getInstance().setReDistrict(null);
                    UserInfo.getInstance().setRecUserName(null);
                    UserInfo.getInstance().setRecPhone(null);
                    finish();
                    return;
                }
                if (bdLocation.getLocType() != 161) {
                    Toast.makeText(mContext, "请刷新定位信息", Toast.LENGTH_SHORT).show();

                } else {
                    UserInfo.getInstance().setCity(bdLocation.getCity());
                    UserInfo.getInstance().setStreet(bdLocation.getStreet());
                    UserInfo.getInstance().setLatitude(bdLocation.getLatitude());
                    UserInfo.getInstance().setLongitude(bdLocation.getLongitude());
                    UserInfo.getInstance().setDistrict(bdLocation.getDistrict());

                    UserInfo.getInstance().setRecAddId(null);
                    UserInfo.getInstance().setRecLongitude(null);
                    UserInfo.getInstance().setRecLatitude(null);
                    UserInfo.getInstance().setRecCity(null);
                    UserInfo.getInstance().setRecStreet(null);
                    UserInfo.getInstance().setReDistrict(null);
                    UserInfo.getInstance().setRecUserName(null);
                    UserInfo.getInstance().setRecPhone(null);
                    finish();
                }
            }
            break;

        }
    }

    /**
     * 开始旋转
     **/
    RotateAnimation animation;

    private void startAnimation() {
        animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        /**设置旋转一圈所需时间**/
        animation.setDuration(1000);
        /**设置旋转次数，近乎无限次**/
        animation.setRepeatCount(Integer.MAX_VALUE);
        /**设置旋转无停顿**/
        animation.setInterpolator(new LinearInterpolator());
        animation.setFillAfter(true);
        ivRefresh.startAnimation(animation);
    }

    /**
     * 结束旋转
     **/
    private void stopAnimation() {
        if (animation != null) {
            animation.cancel();
            animation = null;
        }
    }

    /**
     * 判断权限集合
     * permissions 权限数组
     * return true-表示没有改权限  false-表示权限已开启
     */
    public static boolean lacksPermissions(Context mContexts, String[] permissionsREAD) {
        for (String permission : permissionsREAD) {
            if (lacksPermission(mContexts, permission)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断是否缺少权限
     */
    private static boolean lacksPermission(Context mContexts, String permission) {
        return ContextCompat.checkSelfPermission(mContexts, permission) == PackageManager.PERMISSION_DENIED;
    }

    /**
     * 获取地址列表
     */
    private void getReceiverAddressList() {

        WaitUI.Show(this);
        HttpAction.getInstance().getAddressListByCustomerId(new GetAddressListByCustomerIdResquest(String.valueOf
                (YuGuoApplication.userInfo.getCustomerId()), "1")).subscribe(new BaseObserver<>(mContext, (response -> {
            WaitUI.Cancel();
            if (200 != response.getCode()) {
                return;
            }
            dtoListBeanList = response.getData().getReceiverAddressdto();
            choiceAddressAdapter.setDtoListBeanList(dtoListBeanList);

        }), (code, message) -> {
            WaitUI.Cancel();
        }));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (YuGuoApplication.isLogin()) {
            getReceiverAddressList();
        }
        applyLocationJurisdiction();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
