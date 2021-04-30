package com.pbph.yuguo.util;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2018/1/11.
 */

public class EditTextCheckUtil implements TextWatcher {
    OnCheckResult mOnCheckResult;
    String mPatternString;
    //    EditText text;
    ImageView imageView;

    public EditTextCheckUtil(@NonNull ImageView imageView, String patternString, OnCheckResult onCheckResult) {
//        text.addTextChangedListener(this);
        this.mOnCheckResult = onCheckResult;
        this.mPatternString = patternString;
//        this.text = text;
        this.imageView = imageView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean isCorrect = AMUtils.isCorrect(mPatternString, s.toString());
        if (null != mOnCheckResult) {
            mOnCheckResult.onResult(isCorrect);
        }
        if (null != imageView) {
            imageView.setVisibility(View.VISIBLE);
//            imageView.setImageResource(isCorrect ? R.drawable.ico_mission_release_input_check : R.drawable.ico_mission_release_input_fault);

        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface OnCheckResult {
        void onResult(boolean is);
//        void onResult(EditText text, boolean is);
    }
}
