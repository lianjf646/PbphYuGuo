package com.pbph.yuguo.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.LookBigPictureAdapter;

import java.util.ArrayList;

public class LookBigPictureActivity extends AppCompatActivity {
    public static final String POSITION = "position";
    public static final String URL = "pictureUrls";
    private ViewPager viewPager;
    private String[] pictureUrls;
    private ArrayList<ImageView> imageViews = new ArrayList<>();


    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookbigpicture);
        viewPager = findViewById(R.id.vp_big_picture);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (pictureUrls.length > 1) radioGroup.check(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initData();
    }

    private void initData() {

        LayoutInflater inflater = LayoutInflater.from(this);

        pictureUrls = getIntent().getStringArrayExtra(URL);
        int pos = getIntent().getIntExtra(POSITION, 0);

        radioGroup = findViewById(R.id.radioGroup);

        LookBigPictureAdapter lookGoodsBigPriAdapter = new LookBigPictureAdapter(this);
        for (int i = 0; i < pictureUrls.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.color.transparent);
            imageView.setOnClickListener(v -> finish());
            Glide.with(this).load(pictureUrls[i]).into(imageView);
            imageViews.add(imageView);

            if (pictureUrls.length <= 1) continue;

            RadioButton rb = (RadioButton) inflater.inflate(R.layout.radiobutton_round, null);
            rb.setId(i);
//            rb.setText(" ");
            rb.setEnabled(false);
            radioGroup.addView(rb);

            RadioGroup.LayoutParams params = (RadioGroup.LayoutParams) rb.getLayoutParams();
            params.width = params.height = 15;
            params.rightMargin = params.leftMargin = 6;
            rb.setLayoutParams(params);

        }
        lookGoodsBigPriAdapter.setImageViews(imageViews);
        viewPager.setAdapter(lookGoodsBigPriAdapter);

        if (pictureUrls.length > 1) radioGroup.check(pos);
        viewPager.setCurrentItem(pos);
    }
}
