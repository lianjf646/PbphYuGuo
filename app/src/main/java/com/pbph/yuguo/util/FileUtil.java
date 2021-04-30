package com.pbph.yuguo.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.update.UpdateManager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;


public class FileUtil {
    private FileUtil() {

    }

    public static FileUtil getInstance() {
        return FileUtilHolder.mInstance;
    }

    private static class FileUtilHolder {
        static FileUtil mInstance = new FileUtil();
    }

    public void writeFile(UpdateManager.DownInfo downInfo, UpdateManager.OnDownLoadUpdateListener updateListener) {
        String filePath = ConstantData.DOWN_LOAD_PATH + "update.apk";
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            if (file.exists()) {
                file.delete();
            }
//            file.createNewFile();
            updateListener.onShowProgress();
            InputStream inputStream = downInfo.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024 * 1024];
            int length;
            long count = 0;
            while ((length = inputStream.read(buffer)) != -1) {
                count += length;
                outputStream.write(buffer, 0, length);
                int pr = (int) (count * 100 / downInfo.getContentLenght());
                updateListener.onUpdateProgress(pr);
            }
            inputStream.close();
            outputStream.close();
            updateListener.onComplete(filePath);
        } catch (IOException exp) {
            exp.printStackTrace();
            updateListener.onError(exp);
        }
    }

    public File getCacheFileFromGlide(Context context, String url) {
        FutureTarget<File> future = Glide.with(context)
                .load(url)
                .downloadOnly(480, 800);
        try {
            File cacheFile = future.get();
            return cacheFile;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeFile(Bitmap bitmap, File file) {
        writeFile(BitmapUtil.getImageByte(bitmap), file);
    }

    public void writeFile(byte[] bytes, File file) {
        FileOutputStream outputStream = null;
        ByteArrayInputStream inputStream = null;
        try {
            inputStream = new ByteArrayInputStream(bytes);
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024 * 1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != outputStream) {
                    outputStream.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
