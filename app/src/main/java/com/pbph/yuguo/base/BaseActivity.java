package com.pbph.yuguo.base;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.myview.MyToast;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.update.UpdateManager;
import com.pbph.yuguo.util.AMUtils;
import com.sobot.chat.SobotApi;
import com.sobot.chat.api.model.Information;


public abstract class BaseActivity extends AppCompatActivity {
    protected AppCompatActivity mContext;
    /**
     * 使用CompositeSubscription来持有所有的Subscriptions
     */
//    protected CompositeSubscription mCompositeSubscription;
    /**
     * 加载对话框
     */
//    protected DialogLoading loading;
    /**
     * 来自哪个 页面
     */
    protected String fromWhere;
    /**
     * 页面布局的 根view
     */
    protected View mContentView;
    public static final int TITLE_STYLE_WHITE = 0x11;
    public static final int TITLE_STYLE_MAIN_COLOR = 0x10;
    LinearLayout mRootLayout;
    RelativeLayout mTitleLayout;
    public TextView mTitle;
    protected ImageView mLeftBtn, mRightBtn;
    public TextView btn_right1;
    protected YuGuoApplication application = null;
    private DisplayMetrics displayMetrics = null;
    private PopupWindow popupWindow;
    private UpdateManager updateManager = null;
    private int titleStyle = TITLE_STYLE_MAIN_COLOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
        super.setContentView(R.layout.base_activity_layout);
        application = (YuGuoApplication) getApplication();
        mRootLayout = findViewById(R.id.ll_base_root_layout);
        mTitleLayout = findViewById(R.id.include_title);
        mTitle = findViewById(R.id.tv_title);
        mRightBtn = findViewById(R.id.btn_right);
        mLeftBtn = findViewById(R.id.btn_left);
        mRightBtn = findViewById(R.id.btn_right);
        btn_right1 = findViewById(R.id.btn_right1);
        mLeftBtn.setOnClickListener((view) -> onLeftClick());
//        mRightBtn.setOnClickListener((view) -> {
//            mRightBtn.setImageResource(R.drawable.lqdl_btlw_icon2);
//            int drop = DensityUtils.dip2px(this, 14);
//            popupWindow.showAsDropDown(mRightBtn, drop, drop);
//        });
        mContext = this;
        //Activity管理
//        ActivityPageManager.getInstance().addActivity(this);
//        initPopWindow();
    }

    public void setTitleStyle(int titleStyle) {
        this.titleStyle = titleStyle;
    }

    protected void hideStatusBar(int resid) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(getResources().getColor(resid));
        }
        ((LinearLayout.LayoutParams) mTitleLayout.getLayoutParams()).setMargins(0, getStatusBarHeight(), 0, 0);
    }

    protected void setmTitleLayoutBackground(int resid) {
        mTitleLayout.setBackgroundResource(resid);
    }

    protected int getStatusBarHeight() {
        int statusBarHeight = -1;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
//        Log.e("statusBar", "statusBar=" + statusBarHeight);
        return statusBarHeight;
    }

    protected void checkUpdate() {
        if (null == updateManager) {
            updateManager = new UpdateManager(this);
        }
        updateManager.checkVersion();
    }

    public boolean checkPermission(@NonNull String permission) {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }


    protected void toCustomService() {
        Information information = new Information();
        information.setAppkey(ConstantData.SOBOT_APP_KEY);
//        if (!TextUtils.isEmpty(application.getUserRecommendCode())) {
//            information.setRealname(application.getUserRecommendCode());
//        }
        information.setUseVoice(false);
        SobotApi.startSobotChat(this, information);
//        Log.e("toCustomService","toCustomService");
//        RongIM.getInstance().startCustomerServiceChat(this, tokenDataBean.getCustomerServiceID(), "在线客服", null);
    }


    //    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }
    protected void hideTitleView() {
        mTitleLayout.setVisibility(View.GONE);
    }

    protected void viewTitleView() {
        mTitleLayout.setVisibility(View.VISIBLE);
    }

    protected void initTitle(int titleStyle, @NonNull String title, @NonNull boolean isViewLeftBtn, @NonNull boolean isViewRightBtn) {
        setTitle(title);
        mLeftBtn.setVisibility(isViewLeftBtn ? View.VISIBLE : View.INVISIBLE);
        mRightBtn.setVisibility(isViewRightBtn ? View.VISIBLE : View.INVISIBLE);
        switch (titleStyle) {
            case TITLE_STYLE_WHITE:
//                hideStatusBar(R.color.white);
                setLeftButtonImage(R.drawable.back);
                mTitleLayout.setBackgroundResource(R.color.white);
                mTitle.setTextColor(getResources().getColor(R.color.black));
                break;
            case TITLE_STYLE_MAIN_COLOR:
//                hideStatusBar(R.color.main_color);
                setLeftButtonImage(R.drawable.ico_left_arrow);
                mTitleLayout.setBackgroundResource(R.color.main_color);
                mTitle.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }

    protected void setLeftButtonImage(int resId) {
        mLeftBtn.setImageResource(resId);
    }

    protected void setRightButtonImage(int resId) {
        mRightBtn.setImageResource(resId);
    }

    protected void setRightButtonClick(View.OnClickListener onClickListener) {
        mRightBtn.setOnClickListener(onClickListener);
    }

//    private void initPopWindow() {
//        View contentView = getLayoutInflater().inflate(R.layout.popwindow_layout, null);
//        popupWindow = new PopupWindow(this);
//        popupWindow.setFocusable(true);
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setContentView(contentView);
//        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
////        popupWindow.setWidth(getScreenWidth() / 3);  //如果不设置，就是 AnchorView 的宽度
//        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
//        contentView.findViewById(R.id.tv_popwindow_message).setOnClickListener((view) -> {
//        });
//        contentView.findViewById(R.id.tv_popwindow_custom_service).setOnClickListener((view) -> {
//            popupWindow.dismiss();
//            toCustomService();
//        });
//        contentView.findViewById(R.id.tv_popwindow_help).setOnClickListener((view) -> {
//            popupWindow.dismiss();
//            startActivity(new Intent(this, MyBrowsersActivity.class)
//                    .putExtra("url", ConstantData.HELP_URL)
//                    .putExtra("title", "帮助"));
//        });
//        contentView.findViewById(R.id.tv_popwindow_feed_back).setOnClickListener((view) -> {
//        });
//    }

    public void copy(String text) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(text.trim());
    }

    protected void setTitle(String title) {
        mTitle.setText(title.equals("首页") ? getResources().getString(R.string.app_name) : title);
    }

    @Override
    public void setContentView(int layoutResID) {
        mContentView = LayoutInflater.from(this).inflate(layoutResID, null);
        mRootLayout.addView(mContentView);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
        params.weight = 1;
        mContentView.setLayoutParams(params);

//        setContentView(baseview);
    }

    @Override
    public void setContentView(@NonNull View view) {
        mRootLayout.addView(view);
//        super.setContentView(view);
        mContentView = view;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
        params.weight = 1;
        mContentView.setLayoutParams(params);
        //初始化页面
        init();
    }

    /**
     * 初始化页面
     */
    public void init() {
//        initView();
//        bindEvent();
    }


    public abstract void onLeftClick();


    /**
     * 初始化view
     */
//    public abstract void initView();


    /**
     * 将 Fragment添加到Acitvtiy
     *
     * @param fragment
     * @param frameId
     */
    protected void addFragmentToActivity(@NonNull Fragment fragment, int frameId) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    private DisplayMetrics getDisplayMetrics() {
        if (null != displayMetrics) {
            return displayMetrics;
        }
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    @Override
    protected void onPause() {
        hintKbTwo();
        super.onPause();
    }

    @Override
    protected void onStop() {
        AMUtils.onInactive(this);
        super.onStop();
    }

    public int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    public int getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }

    public void showMyToast(int resid, String content) {
        MyToast.getInstance().showToast(this, resid, content);
    }

    /**
     * 显示一个Toast信息
     */
    public void showToast(String content) {
        if (!TextUtils.isEmpty(content)) {
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
        }
    }

    public void showLoadingDialog() {
        WaitUI.Show(this);
    }

    public void hideLoadingDialog() {
        WaitUI.Cancel();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContentView = null;
    }

    //此方法只是关闭软键盘 可以在finish之前调用一下
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&getCurrentFocus()!=null){
            if (getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
