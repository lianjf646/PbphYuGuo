package com.pbph.yuguo.myview;

import android.content.Context;

import com.pbph.yuguo.R;


/**
 * Created by Administrator on 2018/8/7 0007.
 */

public class LoadingDialog extends CustomerDialogNoTitle{
    public LoadingDialog(Context context, String title) {
        super(context, R.style.myDialog, R.layout.loading_dialog, title);
    }

    public LoadingDialog(Context context, String title, float margin) {
        super(context, R.style.myDialog, R.layout.loading_dialog, margin, title);
    }
}
