package com.pbph.yuguo.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import com.pbph.yuguo.R;
import com.sobot.chat.utils.ToastUtil;

import org.jsoup.helper.StringUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义密码输入框
 * Created by zyp on 2018/8/9 0009.
 */

public class PasswordInputEdt extends android.support.v7.widget.AppCompatEditText {
    private Paint rectPaint;
    private Paint textPaint;
    private Rect textRect;
    private String text = "";
    private boolean isFocus = false;
    /**
     * 是否密文显示
     */
    private boolean isPwd;
    /**
     * 是否只能输入数字
     */
    private boolean isNumber;
    /**
     * 横向间距
     */
    private int widthSpace;
    /**
     * 纵向间距
     */
    private int heightSpace;
    // 背景边框圆角大小
    private int bgCorner = 0;
    /**
     * 密码框的宽度
     */
    private int rectStroke;
    /**
     * 字体大小
     */
    private int txtSize;
    /**
     * 边框或者实体框
     */
    private boolean isBgFill;
    /**
     * 密码长度
     */
    private int numLength;

    /**
     * 字体颜色
     *
     * @param context
     */
    private int textColor;

    /**
     * 默认框框颜色
     *
     * @param context
     */
    private int rectNormalColor;
    /**
     * 选中框框颜色
     *
     * @param context
     */
    private int rectChooseColor;

    /**
     * 密码显示方式
     */
    private PwdType pwdType;

    /**
     * 是否需要在输入完成后关闭键盘
     */
    private boolean isAutoCloseKeyBoard = true;

    private int width;

    public enum PwdType {
        CIRCLE,
        ASTERISK
    }

    public void setPwdType(PwdType pwdType) {
        this.pwdType = pwdType;
    }

    private int pwdType_CircleRadius;

    private onInputOverListener onInputOverListener;

    public void setOnInputOverListener(PasswordInputEdt.onInputOverListener onInputOverListener) {
        this.onInputOverListener = onInputOverListener;
    }

    public PasswordInputEdt(Context context) {
        super(context);

        setAttr(null, 0);

        init();
    }

    public PasswordInputEdt(Context context, AttributeSet attrs) {
        super(context, attrs);

        setAttr(attrs, 0);

        init();
    }

    public PasswordInputEdt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setAttr(attrs, defStyleAttr);

        init();

    }

    public boolean isFocus() {
        return isFocus;
    }

    public void setFocus(boolean focus) {
        isFocus = focus;
    }

    public boolean isPwd() {
        return isPwd;
    }

    public void setIsPwd(boolean pwd) {
        isPwd = pwd;
    }


    public void setIsNumber(boolean number) {
        isNumber = number;
    }

    public void setWidthSpace(int widthSpace) {
        this.widthSpace = widthSpace;
    }

    public void setHeightSpace(int heightSpace) {
        this.heightSpace = heightSpace;
    }

    public void setRectStroke(int rectStroke) {
        this.rectStroke = rectStroke;
    }

    public void setTxtSize(int txtSize) {
        this.txtSize = txtSize;
    }

    public boolean isBgFill() {
        return isBgFill;
    }

    public void setIsBgFill(boolean bgFill) {
        this.isBgFill = bgFill;
    }

    public void setNumLength(int numLength) {
        this.numLength = numLength;
    }

    public void setRectChooseColor(int rectChooseColor) {
        this.rectChooseColor = rectChooseColor;
    }

    public void setRectNormalColor(int rectNormalColor) {
        this.rectNormalColor = rectNormalColor;
    }

    @Override
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                heightSize = MeasureSpec.getSize(heightMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
                heightSize = widthSize / numLength;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        setMeasuredDimension(widthSize, heightSize);

    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        isFocus = focused;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        String text = s.toString().trim();
        if (this.text == null) {
            return;
        }

        if (getText().length() == numLength) {
//            this.text = this.text + text.toString();
            if (isAutoCloseKeyBoard) {
                closeKeyboard();
            }
        }
        if (!StringUtil.isBlank(text)) {
            this.text = text;
        } else {
            this.text = "";
        }

        if (onInputOverListener != null) {
            onInputOverListener.onInputOver(this.text);
        }
    }

    private void init() {
        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textRect = new Rect();

        setBackgroundDrawable(null);
        setLongClickable(false);
        setTextIsSelectable(false);
        setCursorVisible(false);

        textPaint.setStyle(Paint.Style.FILL);
    }

    private void setAttr(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordInputEdt, defStyleAttr, 0);
        isPwd = a.getBoolean(R.styleable.PasswordInputEdt_isPwd, true);
        isAutoCloseKeyBoard = a.getBoolean(R.styleable.PasswordInputEdt_autoCloseKeyBoard, true);
        isNumber = a.getBoolean(R.styleable.PasswordInputEdt_isNumber, true);
        widthSpace = a.getDimensionPixelSize(R.styleable.PasswordInputEdt_widthSpace, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        pwdType_CircleRadius = a.getDimensionPixelSize(R.styleable.PasswordInputEdt_circleRadius, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        heightSpace = a.getDimensionPixelSize(R.styleable.PasswordInputEdt_heightSpace, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        rectStroke = a.getDimensionPixelSize(R.styleable.PasswordInputEdt_rectStroke, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        txtSize = a.getDimensionPixelSize(R.styleable.PasswordInputEdt_txtSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics()));
        isBgFill = a.getBoolean(R.styleable.PasswordInputEdt_bgFill, false);
        numLength = a.getInt(R.styleable.PasswordInputEdt_numLength, 6);
        textColor = a.getColor(R.styleable.PasswordInputEdt_textColor, 0xff666666);
        bgCorner = a.getDimensionPixelOffset(R.styleable.PasswordInputEdt_bgCorner, 0);
        rectNormalColor = a.getColor(R.styleable.PasswordInputEdt_rectNormalColor, 0xff808080);
        rectChooseColor = a.getColor(R.styleable.PasswordInputEdt_rectChooseColor, 0xff44ce61);
        pwdType = a.getInt(R.styleable.PasswordInputEdt_pwdType, 0) == 0 ? PwdType.CIRCLE : PwdType.ASTERISK;
        a.recycle();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 67 && text.length() != 0) {
            text = text.substring(0, text.length() - 1);
            invalidate();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isNumber) {
            setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        width = Math.min(getMeasuredHeight(), getMeasuredWidth() / numLength);

        // 绘制背景
        drawBg(canvas);
        // 绘制分割线
        drawDivisionLine(canvas);
        // 绘制密码
        drawHidePassword(canvas);
    }

    private void drawBg(Canvas canvas) {
        rectPaint.setColor(rectNormalColor);
        // 设置画笔为空心
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(rectStroke);
        RectF rectF = new RectF(rectStroke, rectStroke, getWidth() - rectStroke, getHeight() - rectStroke);

        // 如果没有设置圆角，就画矩形
        if (bgCorner == 0) {
            canvas.drawRect(rectF, rectPaint);
        } else {
            // 如果有设置圆角就画圆矩形
            canvas.drawRoundRect(rectF, bgCorner, bgCorner, rectPaint);
        }
    }

    private void drawHidePassword(Canvas canvas) {
        // 设置画笔为实心
        rectPaint.setStyle(Paint.Style.FILL);
        rectPaint.setColor(textColor);
        textPaint.setTextSize(txtSize);
        rectPaint.getFlags();
        for (int i = 0; i < text.length(); i++) {

            if (isPwd) {
                int cx = i * rectStroke + i * width + width / 2 + rectStroke;
                switch (pwdType) {
                    case CIRCLE:
                        canvas.drawCircle(cx, getHeight() / 2, bgCorner, rectPaint);
                        break;
                    case ASTERISK:
                        textPaint.getTextBounds("*", 0, 1, textRect);
                        canvas.drawText("*", cx - txtSize / 4, (getHeight() + txtSize) / 2, textPaint);
                        break;
                }

            } else {
                int cx = i * rectStroke + i * width + width / 2 + rectStroke;
                textPaint.getTextBounds(text.substring(i, i + 1), 0, 1, textRect);
                canvas.drawText(text.substring(i, i + 1), cx - txtSize / 4, (getHeight() + txtSize) / 2, textPaint);
            }

        }
    }

    private void drawDivisionLine(Canvas canvas) {
        rectPaint.setStrokeWidth(rectStroke);
        rectPaint.setColor(rectNormalColor);
        for (int i = 0; i < numLength - 1; i++) {
            int startX = (i + 1) * rectStroke + (i + 1) * width + rectStroke;
            canvas.drawLine(startX, rectStroke, startX, getHeight() - rectStroke, rectPaint);
        }
    }

    public interface onInputOverListener {
        void onInputOver(String text);
    }

    public void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindowToken(), 0);
        }
    }
}
