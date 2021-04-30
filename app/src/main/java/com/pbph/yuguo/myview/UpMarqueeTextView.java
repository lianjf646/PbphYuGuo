package com.pbph.yuguo.myview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * 跑马灯效果<向上方向>的TextView
 */
public class UpMarqueeTextView extends TextView implements
        Animator.AnimatorListener {
//    List<GetWithdrawalsNoticeResponse> withdrawalsNoticeResponses;
    List<String> textArray = new ArrayList<>();
    private static final String TAG = "UpMarqueeTextView";
    private static final int ANIMATION_DURATION = 200;
    private float height;
    private AnimatorSet mAnimatorStartSet;
    private AnimatorSet mAnimatorEndSet;
    private String mText;
    private int index = 0;

    public UpMarqueeTextView(Context context) {
        super(context);
    }

    public UpMarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.w(TAG, "--- onDraw ---");
        height = getHeight();// 确保view的高度
    }

//    public void setWithdrawalsNoticeResponses(List<GetWithdrawalsNoticeResponse> withdrawalsNoticeResponses) {
//        this.withdrawalsNoticeResponses = withdrawalsNoticeResponses;
//        StringBuffer stringBuffer=new StringBuffer();
//        for (GetWithdrawalsNoticeResponse response : withdrawalsNoticeResponses) {
//            String data=response.getSmallName() + ": " + response.getMoney();
//            stringBuffer.append(stringBuffer).append("\n");
//            textArray.add(data);
//        }
//        super.setText(textArray.get(index));
//        if (null == mAnimatorStartSet) {
//            initStartAnimation();
//        }
//        mAnimatorStartSet.start();
//    }

    /**
     * --- 初始化向上脱离屏幕的动画效果 ---
     */
    private void initStartAnimation() {
        ObjectAnimator translate = ObjectAnimator.ofFloat(this, "translationY",
                0, -height);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f);
        mAnimatorStartSet = new AnimatorSet();
        mAnimatorStartSet.play(translate).with(alpha);
        mAnimatorStartSet.setDuration(ANIMATION_DURATION);
        mAnimatorStartSet.addListener(this);
    }

    /**
     * --- 初始化从屏幕下面向上的动画效果 ---
     */
    private void initEndAnimation() {
        ObjectAnimator translate = ObjectAnimator.ofFloat(this, "translationY",
                height, 0);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f);
        mAnimatorEndSet = new AnimatorSet();
        mAnimatorEndSet.play(translate).with(alpha);
        mAnimatorEndSet.setDuration(ANIMATION_DURATION);
    }

    /**
     * --- 设置内容 ---
     **/
    public void setText(String text) {
        if (TextUtils.isEmpty(text)) {
            Log.e(TAG, "--- 请确保text不为空 ---");
            return;
        }
        mText = text;
//        setText(textArray.get(0));
        if (null == mAnimatorStartSet) {
            initStartAnimation();
        }
        mAnimatorStartSet.start();
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        if (null == mAnimatorEndSet) {
            initEndAnimation();
        }
        Log.e("动画借宿","123123123");
        index++;
        setText(textArray.get(index));
//        mAnimatorEndSet.start();
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
