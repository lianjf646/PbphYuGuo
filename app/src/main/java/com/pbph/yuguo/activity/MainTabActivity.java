package com.pbph.yuguo.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pbph.yuguo.R;
import com.pbph.yuguo.application.UserInfo;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.baidu_map.BaiduLocationUntil;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.fragment.MainViewPagerFragment;
import com.pbph.yuguo.fragment.MyFragment;
import com.pbph.yuguo.fragment.ShoppingCarFragment;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.msg.Add2ShoppingCarMsg;
import com.pbph.yuguo.msg.LocationMsg;
import com.pbph.yuguo.msg.SwitchFragmentMsg;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetAddressListByCustomerIdResquest;
import com.pbph.yuguo.request.GetJpushIdResquest;
import com.pbph.yuguo.request.GetShoppingCartCountResquest;
import com.pbph.yuguo.request.GetSysConfigRequest;
import com.pbph.yuguo.response.GetAddressListByCustomerIdResponse;
import com.pbph.yuguo.util.RxBusF;
import com.pbph.yuguo.util.SpHelper;
import com.sobot.chat.SobotApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class MainTabActivity extends BaseActivity {
    private FragmentTabHost mTabHost = null;
    private Class fragmentArray[] = {MainViewPagerFragment.class, VipFragment.class, ShoppingCarFragment.class, MyFragment.class};
    private int mImageViewArray[] = {R.drawable.tab_1_bg_selecter, R.drawable.tab_2_bg_selecter, R.drawable.tab_3_bg_selecter,
            R.drawable.tab_5_bg_selecter};
    private String mTextviewArray[] = {"花果山", "会员", "购物车", "我的"};
    //*******************************************
    //如果上述数组进行了修改。请记得  public void setTab(int position)  方法在其他页面调用position位置是否正确。如果不正确会出问题的。
    //    RankingListViewPagerFragment.class,
//    R.drawable.tab_4_bg_selecter,
//    "排行",
//*******************************************

    private LayoutInflater layoutInflater = null;


    //    TextView tvPoint;//第二标签页的红点
    public TextView tvPointNum;//第三标签页的未读文字


    List<String> stringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintab_layout);
        stringList = Arrays.asList(this.getResources().getStringArray(R.array.areaList));
        hideTitleView();
        initTitle(TITLE_STYLE_WHITE, getResources().getString(R.string.app_name), false, true);

        getReceiverAddressDefault();

        initView();

        SobotApi.initSobotSDK(getApplication(), ConstantData.SOBOT_APP_KEY, SpHelper.getInstance().getToken());

        String jpushId = JPushInterface.getRegistrationID(this);
        YuGuoApplication.userInfo.setJpushId(jpushId);

        getJpushId();

        requestPermissions();
//        ToastDialog.show(this,"aaaaa","bbbbb",null);
//        YesNoDialog.show(this, "sdfsdf", null);


        RxBusF.register0(MainTabActivity.this, Add2ShoppingCarMsg.class, locationMsg -> {
            getShoppingCartCount();
        });

        getAfterSaleRule();
        RxBusF.register0(MainTabActivity.this, SwitchFragmentMsg.class, switchFragmentMsg -> {
            setTab(1);
        });
    }


    @Override
    public void onLeftClick() {
        finish();
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissions() {

        if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            application.createFolder();
            checkUpdate();
            return;
        }
        List<String> permissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isNeedRequestPermissions(permissions)) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
        } else {
            application.createFolder();
            checkUpdate();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        YuGuoApplication application = (YuGuoApplication) getApplication();
        application.rootActivity = this;
//        requestPermissions();

        if (YuGuoApplication.isLogin()) {
//            tvPointNum.setVisibility(View.VISIBLE);
        } else {
            tvPointNum.setVisibility(View.INVISIBLE);
        }
        getShoppingCartCount();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(permission) != PackageManager
                .PERMISSION_GRANTED) {
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
            if (null != permissions && permissions.length > 0) {
                boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                if (!b) {
//                    Log.e("permissions","请给与app相应权限，否则将无法正常使用");
//                    showToast("请给与app相应权限，否则将无法正常使用");
//                finish();
//                System.exit(0);
                } else {
                    showToast("请给与app相应权限，否则将无法正常使用");
                    requestPermissions();
                }
            }
        }
    }


    public void setTab(int position) {
        mTabHost.setCurrentTab(position);
    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        RxBusF.removeDisposable0(MainTabActivity.this, Add2ShoppingCarMsg.class);
        RxBusF.removeDisposable0(MainTabActivity.this, SwitchFragmentMsg.class);
        Glide.get(application).clearMemory();
        new Thread() {
            @Override
            public void run() {
                Glide.get(application).clearDiskCache();
            }
        }.start();
        super.onDestroy();
    }

    private void initView() {
        //实例化布局对象
        layoutInflater = LayoutInflater.from(this);
        //实例化TabHost对象，得到TabHost
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        //getSupportFragmentManager().beginTransaction().
        //得到fragment的个数
        int count = fragmentArray.length;
        for (int i = 0; i < count; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            Bundle bundle = new Bundle();
//            if (i == 3) {
//                bundle.putString(WebViewFragment.ARG_PARAM1, ConstantData.SHARE_URL);
//            }
            mTabHost.addTab(tabSpec, fragmentArray[i], bundle);
            //设置Tab按钮的背景
//            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color
// .rc_notice_warning);
        }
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.getTabWidget().setBackgroundResource(R.color.transparent);
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener((tabId) -> {


            if ("邀请".equals(tabId)) {
                setTitle("邀请有礼");
            }

            if ("花果山".equals(tabId)) {
                hideTitleView();
            } else if ("我的".equals(tabId)) {
                hideTitleView();
            } else {
                viewTitleView();
                setTitle(tabId);
            }

//            if (mTextviewArray[1].equals(tabId)) {
//                tvPoint.setVisibility(View.INVISIBLE);
//            }

            if (mTextviewArray[2].equals(tabId)) {
                tvPointNum.setVisibility(View.INVISIBLE);
            } else {
//                tvPointNum.setVisibility(View.VISIBLE);
                getShoppingCartCount();
            }

        });
    }

    @TargetApi(17)
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);
        TextView textView = view.findViewById(R.id.textview);
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, mImageViewArray[index], 0, 0);
        textView.setText(mTextviewArray[index]);

//        if (index == 1) {
//            tvPoint = (TextView) view.findViewById(R.id.tv_point);
//        } else
        if (index == 2) {
            tvPointNum = (TextView) view.findViewById(R.id.tv_point_num);
        }


        return view;
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getShoppingCartCount() {
        if (!YuGuoApplication.isLogin()) {
            return;
        }
        HttpAction.getInstance().getShoppingCartCount(new GetShoppingCartCountResquest(YuGuoApplication.userInfo.getCustomerId
                ())).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                return;
            }
            int num = response.getData().getShoppingCartCount();
            if (num <= 0) tvPointNum.setVisibility(View.INVISIBLE);
            else {
                tvPointNum.setVisibility(View.VISIBLE);
                tvPointNum.setText(String.valueOf(num));
            }
        })));
    }


    private void getAfterSaleRule() {

        HttpAction.getInstance().getSysConfig(new GetSysConfigRequest()).subscribe(new BaseObserver<>(mContext, response -> {
            int code = response.getCode();
            if (code == 200) {
                if (response.getData().getSysConfig().getNewDateRangeFlag() == 0)
                    Toast.makeText(mContext, "超过营业时间，现在下单将明日送达", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void getReceiverAddressDefault() {

        if (!YuGuoApplication.isLogin()) {
            getLocation(true);
            return;
        }
        HttpAction.getInstance().getAddressListByCustomerId(new GetAddressListByCustomerIdResquest(String.valueOf
                (YuGuoApplication.userInfo.getCustomerId()), "2")).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                getLocation(true);
                return;
            }
            List<GetAddressListByCustomerIdResponse.DataBean.ReceiverAddressdtoBean> dto = response.getData()
                    .getReceiverAddressdto();

            if (dto == null || dto.isEmpty()) {
                getLocation(true);
                return;
            }
            GetAddressListByCustomerIdResponse.DataBean.ReceiverAddressdtoBean data = dto.get(0);
            YuGuoApplication.userInfo.setRecAddId(data.getAddressId());
            YuGuoApplication.userInfo.setRecLongitude(Double.parseDouble(data.getReceiverLng()));
            YuGuoApplication.userInfo.setRecLatitude(Double.parseDouble(data.getReceiverLat()));
            YuGuoApplication.userInfo.setRecCity(data.getReceiverCityName());
            YuGuoApplication.userInfo.setRecStreet(data.getReceiverVillage());
            YuGuoApplication.userInfo.setReDistrict(data.getReceiverVillage() + data.getReceiverAddressDetail());
            YuGuoApplication.userInfo.setRecUserName(data.getReceiverName());
            YuGuoApplication.userInfo.setRecPhone(data.getReceiverPhone());
            LocationMsg msg = new LocationMsg(true);
            msg.loc = false;
            RxBusF.post0(msg);

            getLocation(false);
        })));
    }

    private void getLocation(boolean b) {
        BaiduLocationUntil.getInstance().onCreate(getApplicationContext());
        BaiduLocationUntil.getInstance().setLocationAdressInfoListener(location -> {
            if (location.getLocType() != 161) {
                BaiduLocationUntil.getInstance().onStop();
                if (b) RxBusF.post0(new LocationMsg(false));
                return;
            }

            if (!stringList.contains(location.getDistrict())) {
                LocationMsg msg = new LocationMsg(false);
                msg.street = location.getStreet();
                if (b) RxBusF.post0(msg);
                return;
            }

            UserInfo.getInstance().setCity(location.getCity());
            UserInfo.getInstance().setStreet(location.getStreet());
            UserInfo.getInstance().setLongitude(location.getLongitude());
            UserInfo.getInstance().setLatitude(location.getLatitude());
            UserInfo.getInstance().setDistrict(location.getDistrict());
//            UserInfo.getInstance().setLongitude(126.709489);
//            UserInfo.getInstance().setLatitude(45.763583);
            if (b) {
                LocationMsg msg = new LocationMsg(true);
                msg.loc = true;
                RxBusF.post0(msg);
            }
        });
    }


    private void getJpushId() {

        if (!YuGuoApplication.isLogin()) return;

        if (TextUtils.isEmpty(YuGuoApplication.userInfo.getJpushId())) return;

        HttpAction.getInstance().getJpushId(new GetJpushIdResquest(String.valueOf(YuGuoApplication.userInfo.getCustomerId()),
                YuGuoApplication.userInfo.getJpushId())).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                return;
            }
        })));
    }
}
