package com.pbph.yuguo.myview;

import android.content.Context;

import com.pbph.yuguo.R;


/**
 * Created by Administrator on 2018/8/7 0007.
 */

public class SmallDialog extends CustomerDialogNoTitle {
    public SmallDialog(Context context, String title) {
        super(context, R.style.myDialog, R.layout.small_dailog, title);
    }

    public SmallDialog(Context context, String title, float margin) {
        super(context, R.style.myDialog, R.layout.small_dailog, margin, title);
    }
}
