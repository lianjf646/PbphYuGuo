package com.pbph.yuguo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {
    public BaseActivity mContext;
    public View mContentView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mContentView == null) {
            mContentView = inflater.inflate(onSetLayoutId(), container, false);
            initView();
//            bindEvent();
        }
        return mContentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getBaseActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public BaseActivity getBaseActivity() {
        return (BaseActivity) this.getActivity();
    }

    public abstract int onSetLayoutId();

    public abstract void initView();

    //    public abstract void bindEvent();
    public void showToast(String content) {
        mContext.showToast(content);
    }

    public void showLoadingDialog() {
        mContext.showLoadingDialog();
    }

    public void hideLoadingDialog() {
        mContext.hideLoadingDialog();
    }


}
