package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.pbph.yuguo.R;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.fragment.WebViewFragment;

public class MyBrowsersActivity extends BaseActivity {
    WebViewFragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_browsers);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");

        initTitle(TITLE_STYLE_WHITE, title, true, false);
        fragment = WebViewFragment.newInstance(url, title);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content_frame, fragment).commit();
    }

    @Override
    public void onLeftClick() {
        fragment.webViewGoBack();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            fragment.onKeyDown(keyCode, event);
        }
        return false;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
