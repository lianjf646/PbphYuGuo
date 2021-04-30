package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.pbph.yuguo.R;
import com.pbph.yuguo.base.BaseActivity;

public class ViewFlipperActivity extends BaseActivity implements View.OnClickListener{

    //设置ViewFlipper和view视图
    private ViewFlipper viewFlipper;
    private int[] imageId = {R.drawable.welcome, R.drawable.welcome, R.drawable.welcome};
    //设置小圆点
    private ImageView[] points;//存放小圆圈数组
    private int currentIndex = 0;//当前页面,默认首页

    private float startX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitleView();
        setContentView(R.layout.activity_view_flipper);
        initViewFlipper();
        initPoints();
    }

    @Override
    public void onLeftClick() {

    }

    //初始化ViewFlipper
    private void initViewFlipper() {
        viewFlipper = findViewById(R.id.vf_slogen);
        //添加对应的view进入集合（数据源）
        for (int anImageId : imageId) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);//设置缩放样式
            imageView.setImageResource(anImageId);
            viewFlipper.addView(imageView);
        }
        //另外增加最后一个能点击进入应用的视图
        View lastView = View.inflate(this, R.layout.activity_view_pager4, null);
        Button button = lastView.findViewById(R.id.btn_start);
        viewFlipper.addView(lastView);
        //设置最后一个视图上的点击事件
        button.setOnClickListener(v -> {
            startActivity(new Intent(this, MainTabActivity.class));
            finish();
        });
    }

    //初始化指示器
    private void initPoints() {
        LinearLayout linearLayout = findViewById(R.id.ll);
        points = new ImageView[4];
        //初始化布局中的小圆点ImageView控件
        for (int i = 0; i < points.length; i++) {
            points[i] = (ImageView) linearLayout.getChildAt(i);
            points[i].setTag(i);
        }
        currentIndex = 0;
        points[currentIndex].setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        viewFlipper.setDisplayedChild((int) v.getTag());
        currentIndex = (int) v.getTag();
    }

    /**
     * 处理手势事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 获取起始点的x轴的坐标
                startX = event.getX();
                break;

            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                // 向右滑动
                if (event.getX() > startX + 100) {
                    if (viewFlipper.getDisplayedChild() != 0) {
                        viewFlipper.setInAnimation(this, R.anim.left_in);
                        viewFlipper.setOutAnimation(this, R.anim.right_out);
                        // 显示前一页
                        viewFlipper.showPrevious();
                        setIndicator();
                        return true;
                    }
                }
                // 向左边滑动
                if (event.getX() + 100 < startX) {
                    if (viewFlipper.getDisplayedChild() != viewFlipper.getChildCount() - 1) {
                        viewFlipper.setInAnimation(this, R.anim.right_in);
                        viewFlipper.setOutAnimation(this, R.anim.left_out);
                        // 显示前一页
                        viewFlipper.showNext();
                        setIndicator();
                        return true;
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }

    private void setIndicator(){
        points[viewFlipper.getDisplayedChild()].setEnabled(false);//不可点击
        points[currentIndex].setEnabled(true);//恢复之前页面状态
        currentIndex = viewFlipper.getDisplayedChild();
    }
}
