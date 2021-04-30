package com.pbph.yuguo.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Properties;
import java.util.TreeSet;

public class CrashHandler implements UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    private static CrashHandler INSTANCE;
    private Context mContext;
    private UncaughtExceptionHandler mDefaultHandler;
    private SendReports sendReports = null;
    private Properties mDeviceCrashInfo = new Properties();
    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_CODE = "versionCode";
    private static final String STACK_TRACE = "STACK_TRACE";
    private static final String CRASH_REPORTER_EXTENSION = ".cr";

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CrashHandler();
        return INSTANCE;
    }

    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Error : ", e);
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return true;
        }
        final String msg = ex.getLocalizedMessage();
        Log.e("handleException", msg);
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
//                Toast.makeText(mContext, "程序出错啦,我们会尽快修改:" + msg, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        // 收集设备信息
        collectCrashDeviceInfo(mContext);
        String crashFileName = saveCrashInfoToFile(ex);
        //sendCrashReportsToServer(mContext);

        return true;
    }

    /**
     * 收集程序崩溃的设备信息
     *
     * @param ctx
     */
    public void collectCrashDeviceInfo(Context ctx) {
        try {
            // Class for retrieving various kinds of information related to the
            // application packages that are currently installed on the device.
            // You can find this class through getPackageManager().
            PackageManager pm = ctx.getPackageManager();
            // getPackageInfo(String packageName, int flags)
            // Retrieve overall information about an application package that is installed on the system.
            // public static final int GET_ACTIVITIES
            // Since: API Level 1 PackageInfo flag: return information about activities in the package in activities.
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                // public String versionName The version name of this package,
                // as specified by the <manifest> tag's versionName attribute.
                mDeviceCrashInfo.put(VERSION_NAME, pi.versionName == null ? "not set" : pi.versionName);
                // public int versionCode The version number of this package,
                // as specified by the <manifest> tag's versionCode attribute.
                mDeviceCrashInfo.put(VERSION_CODE, String.valueOf(pi.versionCode));
            }
            mDeviceCrashInfo.put("ctype", "");

        } catch (NameNotFoundException e) {
            Log.e(TAG, "Error while collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mDeviceCrashInfo.put(field.getName(), String.valueOf(field.get(null)));
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "Error while collect crash info", e);
            }
        }
    }

    /**
     * @param ex
     * @return
     */
    private String saveCrashInfoToFile(Throwable ex) {
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        // printStackTrace(PrintWriter s)
        ex.printStackTrace(printWriter);

        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }

        String result = info.toString();
        printWriter.close();
        mDeviceCrashInfo.put(STACK_TRACE, result);
        Log.e(TAG, result);
        String log = mDeviceCrashInfo.toString();
//        MobclickAgent.reportError(mContext,log);
        return null;
    }

    /**
     * @param ctx
     */
    public void sendCrashReportsToServer(Context ctx) {
        String[] crFiles = getCrashReportFiles(ctx);
        if (crFiles != null && crFiles.length > 0) {
            TreeSet<String> sortedFiles = new TreeSet<String>();
            sortedFiles.addAll(Arrays.asList(crFiles));

            for (String fileName : sortedFiles) {
                File cr = new File(ctx.getFilesDir(), fileName);
                postReport(ctx, cr);
                cr.delete();
            }
        }
    }

    /**
     * @param ctx
     * @return
     */
    private String[] getCrashReportFiles(Context ctx) {
        File filesDir = ctx.getFilesDir();
        FilenameFilter filter = new FilenameFilter() {
            // accept(File dir, String name)
            // 测试指定文件是否应该包含在某一文件列表中。
            public boolean accept(File dir, String name) {
                return name.endsWith(CRASH_REPORTER_EXTENSION);
            }
        };
        // list(FilenameFilter filter) 
        return filesDir.list(filter);
    }

    private void postReport(Context ctx, File file) {
        try {
            FileInputStream fin = new FileInputStream(file);
            StringBuffer localStringBuffer = new StringBuffer();
            byte[] buffer = new byte[1024];
            int i;
            while ((i = fin.read(buffer)) != -1) {
                localStringBuffer.append(new String(buffer, 0, i));
            }
            fin.close();
//            MobclickAgent.reportError(ctx,localStringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     */
    public void sendPreviousReportsToServer() {
        if (null != sendReports && !sendReports.isAlive()) {
            sendReports.start();
        }
    }

    private final class SendReports extends Thread {

        private Context mContext;
        private final Object lock = new Object();

        @SuppressWarnings("unused")
        public SendReports(Context mContext) {
            this.mContext = mContext;
        }

        public void run() {
            try {
                synchronized (this.lock) {
                    sendCrashReportsToServer(mContext);
                }
            } catch (Exception e) {
                Log.e(TAG, "SendReports error.", e);
            }
        }

    }
}
