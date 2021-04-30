package com.pbph.yuguo.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.dialog.BothBtnBottomPopWin;
import com.pbph.yuguo.interfaces.OnPopWinBothBtnClickListener;
import com.pbph.yuguo.update.UpdateManager;
import com.pbph.yuguo.util.DataCleanManager;
import com.sobot.chat.utils.ToastUtil;

public class SettingActivity extends BaseActivity {
    private final Context context = SettingActivity.this;

    private TextView tvSubmit;
    private TextView tvVersion;
    private TextView tvCheckUpdate;
    private TextView tvCacheSize;

    private UpdateManager update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initTitle(TITLE_STYLE_WHITE, "设置", true, false);
        initView();
        getCacheSize();
        initClick();
    }

    private void initView() {
        tvSubmit = findViewById(R.id.tv_submit);
        tvVersion = findViewById(R.id.tv_version);
        tvCheckUpdate = findViewById(R.id.tv_check_update);
        tvCacheSize = findViewById(R.id.tv_cache_size);

        update = new UpdateManager(context);
        tvVersion.setText("版本号: " + update.getVersionName());

        /*if (update.isUpdate()) {
            tvCheckUpdate.setText("发现新版本");
        } else {
            tvCheckUpdate.setText("已是最新版本");
        }*/
    }

    private void getCacheSize() {
        String cacheSize = "0M";
        try {
            cacheSize = DataCleanManager.getTotalCacheSize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvCacheSize.setText(cacheSize);
    }

    private void initClick() {
        tvCheckUpdate.setOnClickListener(v -> {
//            update.checkVersion();
            ToastUtil.showToast(context, "已是最新版本");
        });
        tvCacheSize.setOnClickListener(v -> {
            BothBtnBottomPopWin popWin = new BothBtnBottomPopWin(context, v, "确定清除缓存?", "确定", "取消");
            popWin.show(v);
            popWin.setOnPopWinBothBtnClickListener(new OnPopWinBothBtnClickListener() {
                @Override
                public void onFirstBtnClick() {
                    DataCleanManager.clearAllCache(context);
                    showToast("清除成功");
                    tvCacheSize.setText("0M");
                    popWin.dismiss();
                }

                @Override
                public void onSecondBtnClick() {
                    popWin.dismiss();
                }
            });
        });
        tvSubmit.setOnClickListener(v -> {
            YuGuoApplication.userInfo.setCustomerId(-1);
            YuGuoApplication.userInfo.setToken(null);
            YuGuoApplication.userInfo.setRecAddId(null);
            YuGuoApplication.userInfo.setJpushId(null);
//            YuGuoApplication.userInfo = null;
            finish();
        });
    }

    @Override
    public void onLeftClick() {
        finish();
    }
}
