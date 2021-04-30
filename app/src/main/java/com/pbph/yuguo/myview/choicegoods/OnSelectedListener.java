package com.pbph.yuguo.myview.choicegoods;

import android.widget.CompoundButton;

/**
 * 选择成功回调
 */
public interface OnSelectedListener {
    void onSelected(CompoundButton view, String title, int titleId, String smallTitle, int id);
}
