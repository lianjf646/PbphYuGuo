package com.pbph.yuguo.update;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;

import com.pbph.yuguo.BuildConfig;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.MyProgressDialog;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.AppversionResquest;
import com.pbph.yuguo.request.GetProjectTokenResquest;
import com.pbph.yuguo.response.UpdateBeanResponse;
import com.pbph.yuguo.util.ErrorUtils;
import com.pbph.yuguo.util.LogUtils;

import java.io.File;
import java.io.InputStream;
import java.lang.ref.WeakReference;


public class UpdateManager {
    private static final int PROGRESS_DISMISS = 0x11;
    private static final int PROGRESS_SHOW = 0x12;
    private static final int PROGRESS_UPDATE = 0x13;
    private static final int PROGRESS_ERROR = 0x14;
    UpdateBeanResponse updateBeanResponse;
    //    HashMap<String, String> mHashMap;
    private Context mContext;
    private MyProgressDialog mDownloadDialog;
    //    private ParseXmlService service = new ParseXmlService();
    MyHandler myHandler = null;

    public UpdateManager(Context context) {
        this.mContext = context;
        mDownloadDialog = new MyProgressDialog(context);
        myHandler = new MyHandler(this);
    }

    public void checkVersion() {
        getAliToken();
    }

    private void getAliToken() {
        HttpAction.getInstance().getProjectToken(new GetProjectTokenResquest()).subscribe(new BaseObserver<>(mContext, response -> {
            if (response.getCode() == 200) {
                String token = response.getData().getToken();
                String projectCode = response.getData().getProjectCode();
                getAliSign(token, projectCode);
            }
        }));
    }

    private void getAliSign(String token, String projectCode) {
        AppversionResquest resquest = new AppversionResquest();
        resquest.token = token;
        resquest.projectCode = projectCode;

        HttpAction.getInstance().checkVersion(resquest)
                .subscribe(new BaseObserver<>(mContext, (response -> {
                    try {
                        updateBeanResponse = response;
                        LogUtils.e("isUpdate getIsTip=" + response.getIsTip());
//                        InputStream inputStream = response.byteStream();
//                        mHashMap = service.parseXml(inputStream);
//                        inputStream.close();
                        if (isUpdate()) {
                            showNoticeDialog();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })));
    }

    public int getVersionCode() {
        try {
            PackageInfo pi = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public String getVersionName() {
        try {
            PackageInfo pi = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public boolean isUpdate() {
        try {
//            int versionCode = getVersionCode();

            String versionName = getVersionName().trim();
            if (null != updateBeanResponse) {
                String serviceVersionName = updateBeanResponse.getData().getVersionNum().trim();
//                int serviceCode = Integer.valueOf(mHashMap.get("version"));
//                LogUtils.e("isUpdate versionCode=" + versionCode + "     serviceCode=" + serviceCode);
                LogUtils.e("isUpdate versionName=" + versionName + "     serviceName=" + serviceVersionName);
                return !versionName.equals(serviceVersionName);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void requestDownFile(String downURL) {
        WaitUI.Show(mContext);
        HttpAction.getInstance().downloadFile(downURL, onDownLoadUpdateListener).
                subscribe((downInfo) -> {
                    WaitUI.Cancel();
                }, (throwable) -> {
                    WaitUI.Cancel();
                });
    }

    private static class MyHandler extends Handler {
        WeakReference<UpdateManager> weakReference = null;

        public MyHandler(UpdateManager updateManager) {
            weakReference = new WeakReference(updateManager);
        }

        @Override
        public void handleMessage(Message msg) {
            UpdateManager updateManager = weakReference.get();
            if (null == updateManager) {
                return;
            }
            switch (msg.what) {
                case PROGRESS_DISMISS:
                    String filePath = (String) msg.obj;
                    updateManager.mDownloadDialog.cancel();

                    updateManager.installApk(filePath);
                    break;
                case PROGRESS_ERROR:
                    updateManager.mDownloadDialog.cancel();
                    ErrorUtils.paserError(updateManager.mContext, (Throwable) msg.obj, null);
                    break;
                case PROGRESS_UPDATE:
                    updateManager.mDownloadDialog.setProgress(msg.arg1);
                    break;
                case PROGRESS_SHOW:
                    WaitUI.Cancel();
                    updateManager.mDownloadDialog.showDialog();
                    break;
            }
        }
    }

    OnDownLoadUpdateListener onDownLoadUpdateListener = new OnDownLoadUpdateListener() {
        @Override
        public void onUpdateProgress(int progress) {
            Message message = Message.obtain();
            message.what = PROGRESS_UPDATE;
            message.arg1 = progress;
            myHandler.sendMessage(message);
        }

        @Override
        public void onComplete(String filePath) {
            Log.e("onComplete", filePath + "");
            Message message = Message.obtain();
            message.what = PROGRESS_DISMISS;
            message.obj = filePath;
            myHandler.sendMessage(message);
        }

        @Override
        public void onError(Throwable throwable) {
            Message message = Message.obtain();
            message.what = PROGRESS_ERROR;
            message.obj = throwable;
            myHandler.sendMessage(message);

        }

        @Override
        public void onShowProgress() {
            myHandler.sendEmptyMessage(PROGRESS_SHOW);
        }
    };

    public void showNoticeDialog() {
        Builder builder = new Builder(mContext);
        builder.setTitle("软件更新");
        String message = "";
//        if (mHashMap.get("kb") != null) {
//            message = "文件大小：" + mHashMap.get("kb") + "\n";
//        }
//        if (mHashMap.get("note") != null) {
//            message = message + mHashMap.get("note").replaceAll("br", "\n");
//        }
//        if (message.equals("")) {
        message = "发现新版本,是否更新?";
//        }
        builder.setMessage(message);
        // 更新
        builder.setPositiveButton("马上更新", (DialogInterface dialog, int which) ->
                requestDownFile(updateBeanResponse.getData().getDownloadUrl()));
//                requestDownFile("http://lqd.szxrj.net:9001/laiQianDao/android111.apk"));
        // 如果不是必须更新 显示稍后更新
//        if (!mHashMap.get("must").equals("true")) {
//        builder.setNegativeButton("稍后更新", (DialogInterface dialog, int which) -> dialog.dismiss());
//        }
        builder.setOnKeyListener((DialogInterface dialog, int keyCode, KeyEvent event) -> {
            return true;
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void installApk(String filePath) {
        File apkfile = new File(filePath);
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileProvider", apkfile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
//		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),"application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }


    public interface OnDownLoadUpdateListener {
        void onUpdateProgress(int progress);

        void onComplete(String filePath);

        void onError(Throwable throwable);

        void onShowProgress();
    }

    public static class DownInfo {
        private long contentLenght = 0;
        private InputStream inputStream;

        public DownInfo(long contentLenght, InputStream inputStream) {
            this.contentLenght = contentLenght;
            this.inputStream = inputStream;
        }

        public long getContentLenght() {
            return contentLenght;
        }

        public void setContentLenght(long contentLenght) {
            this.contentLenght = contentLenght;
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }
    }
}
