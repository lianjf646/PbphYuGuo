package com.pbph.yuguo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.CommitFeedbackResquest;
import com.pbph.yuguo.request.GetProjectTokenResquest;
import com.pbph.yuguo.util.AMUtils;
import com.pbph.yuguo.util.AliOss;
import com.pbph.yuguo.util.GlideImageLoaderUntil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class OpinionFeedbackActivity extends BaseActivity implements View.OnClickListener {

    public Context context;
    LayoutInflater inflater;
    LinearLayout ll_imgs;
    ArrayList<ImgView> imgViewlist = new ArrayList<>();
    ArrayList<ImageItem> imglist = new ArrayList<>();
    ImagePicker imagePicker;

    private EditText etProblemDescribe;//问题描述
    private EditText etUserPhone;//联系方式
    private TextView tvEtNum;
    private RadioButton rbnFunction;
    private RadioButton ranOptimization;
    private Button btnConfirm;

    /********************************************************************************/
    private ArrayList<String> imageList;
    private List<File> fileList;
    private String feedbackUrl = "";//图片
    private String feedbackContent = ""; // 内容
    private String feedbackContact = ""; //联系方式
    String type = "1";//1 :功能 2： 优化建议
    boolean isChoice = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle(TITLE_STYLE_WHITE, "意见反馈", true, false);
        setContentView(R.layout.activity_opinion_feedback);
        imageList = new ArrayList<>();
        context = this;
        inflater = LayoutInflater.from(context);
        fileList = new ArrayList<>();
        ll_imgs = findViewById(R.id.ll_imgs);
        etProblemDescribe = findViewById(R.id.et_problem_describe);
        etUserPhone = findViewById(R.id.et_user_phone);
        AMUtils.setEtFilter(etProblemDescribe, 500); //禁止输入表情和特殊符号
        tvEtNum = findViewById(R.id.tv_et_num);
        rbnFunction = findViewById(R.id.rbn_function);
        ranOptimization = findViewById(R.id.rbn_optimization);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(this);
        rbnFunction.setOnClickListener(this);
        ranOptimization.setOnClickListener(this);
        etProblemDescribe.addTextChangedListener(textWatcher);
        initImagePager();
        addImgView(null);

        etProblemDescribe.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        });
//        etProblemDescribe.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});

    }

    @Override
    public void onLeftClick() {
        finish();
    }

    private void initImagePager() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoaderUntil());
        imagePicker.setShowCamera(true);
        imagePicker.setMultiMode(true);
        imagePicker.setCrop(false);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, 
                getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, 
                getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(width);
        imagePicker.setFocusHeight(height);
        imagePicker.setSelectLimit(1);

    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
//            int editStart = etProblemDescribe.getSelectionStart();
            int editEnd = etProblemDescribe.getSelectionEnd();
            tvEtNum.setText(String.valueOf(editEnd));
            tvEtNum.append("/500");
            if (editEnd == 500) {
                Toast.makeText(context, "最多只能输入500字符", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbn_function: //功能
                type = "1";
                break;
            case R.id.rbn_optimization://建议
                type = "2";
                break;
            case R.id.btn_confirm:
                submission();
                break;
        }

    }

    /**
     * 提交信息
     */
    private void submission() {
        feedbackContent = etProblemDescribe.getText().toString().trim();
        feedbackContact = etUserPhone.getText().toString().trim();

        if (feedbackContent.length() < 10) {
            Toast.makeText(OpinionFeedbackActivity.this, "请描述您的问题或建议，最少10个字符", Toast
                    .LENGTH_SHORT).show();
            return;
        }
        if (feedbackContact.length() != 0 && !AMUtils.isMobile(feedbackContact)) {
            Toast.makeText(this, "请输入正确手机号码", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (imglist.size() == 0) {
//            commitFeedback();
//        } else {
//            upDataAliImages();
//
//        }
        WaitUI.Show(this);
        if (isChoice == true) {
            upDataAliImages();
        } else {
            commitFeedback();
        }
    }

    private void upDataAliImages() {
        for (int i = 0; i < imglist.size(); i++) {
            File file = new File(imglist.get(i).path);
            File small_file = new File(getAlbumDir(), "small_" + file.getName());
            Bitmap bm = getSmallBitmap(imglist.get(i).path);
            if (bm != null) {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(small_file);
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            fileList.add(small_file);
            imglist.get(i).path = small_file.getPath();
        }


        HttpAction.getInstance().getProjectToken(new GetProjectTokenResquest()).subscribe(new 
                BaseObserver<>(this, response -> {
            if (response.getCode() != 200) {
                WaitUI.Cancel();
                Toast.makeText(context, response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }
            AliOss.getInstance().uploadImageList(getApplication(), response.getData().getToken(),
                    response.getData().getProjectCode(), imglist, path -> {
                if (path.equals("")) {
                    WaitUI.Cancel();
                    Toast.makeText(context, "图片上传失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("AliTuPian", "onSuccess: " + path);
                imageList.add(path);
                if (imageList.size() == imglist.size()) {
                    feedbackUrl = path;
                    commitFeedback();
                }
            });

        }, (code, message) -> {
            WaitUI.Cancel();
        }));
    }

    private void commitFeedback() {
//        feedbackUrl = feedbackUrl.replaceAll("/",";");
        try {
            feedbackUrl = URLEncoder.encode(feedbackUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        HttpAction.getInstance().commitFeedback(new CommitFeedbackResquest(YuGuoApplication
                .userInfo.getCustomerId(), type, feedbackUrl, feedbackContent, feedbackContact))
                .subscribe(new BaseObserver<>(this, response -> {
            WaitUI.Cancel();
            if (response.getCode() != 200) {

                return;
            }
            Toast.makeText(context, response.getMsg(), Toast.LENGTH_SHORT).show();
            finish();

        }, (code, message) -> {
            WaitUI.Cancel();
        }));


    }

    class ImgView {

        public View view;
        public ImageView iv;
        public ImageView ivx;

        public ImageItem vo;

        public ImgView(View view) {
            this.view = view;

            iv = view.findViewById(R.id.iv_workimg);
            ivx = view.findViewById(R.id.iv_workimgx);

            view.setTag(this);
//            iv.setTag(this);
            ivx.setTag(this);

            ivx.setVisibility(View.INVISIBLE);
        }

        public void setVo(ImageItem vo) {

            this.vo = vo;
            if (vo != null) {
                Glide.with(context).load(vo.path).error(R.mipmap.default_image)           //设置错误图片
                        .placeholder(R.mipmap.default_image).into(iv);
                ivx.setVisibility(View.VISIBLE);

            } else {
                ivx.setVisibility(View.INVISIBLE);
            }

        }
    }

    void addImgView(ImageItem vo) {
        if (ll_imgs.getChildCount() >= 2) return;

        ImgView imgView = new ImgView(inflater.inflate(R.layout.layout_publish_img, null));
        imgView.view.setOnClickListener(onImgsClick);
        imgView.ivx.setOnClickListener(onImgsxClick);
        ll_imgs.addView(imgView.view);
        imgViewlist.add(imgView);
        if (vo == null) return;
        imgView.setVo(vo);

    }

    void removeImgView(ImgView imgView) {

        imgView.view.setOnClickListener(null);
        imgView.ivx.setOnClickListener(null);

        ll_imgs.removeView(imgView.view);
        imgViewlist.remove(imgView);
        imglist.remove(imgView.vo);
        imageList.remove(imgView.vo);


    }

    OnSPClickListener onImgsClick = new OnSPClickListener() {
        @Override
        public void onClickSucc(View v) {
            ImgView imgView = (ImgView) v.getTag();

            if (imgView.vo != null) return;

            getImageInfo();
        }
    };
    OnSPClickListener onImgsxClick = new OnSPClickListener() {
        @Override
        public void onClickSucc(View v) {
            ImgView imgView = (ImgView) v.getTag();
            removeImgView(imgView);
            isChoice = false;
            if (ll_imgs.getChildCount() == imglist.size() && imglist.size() <= 3) {
                addImgView(null);
            }

        }
    };

    private void getImageInfo() {
        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, imglist);
        startActivityForResult(intent, 100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS && requestCode == 100) {
            if (data != null) {
                ArrayList<ImageItem> items = (ArrayList<ImageItem>) data.getSerializableExtra
                        (ImagePicker.EXTRA_RESULT_ITEMS);


                imglist.clear();
                imglist.addAll(items);
                isChoice = true;
                for (int i = imglist.size(); i < ll_imgs.getChildCount(); i++) {
                    ll_imgs.removeViewAt(i);
                }
                int i = 0;
                for (ImageItem item : imglist) {
                    View view = ll_imgs.getChildAt(i);
                    if (view != null) {
                        ImgView imgView = (ImgView) view.getTag();
                        imgView.setVo(item);
                    } else {
                        addImgView(item);
                    }
                    i++;
                }
                if (ll_imgs.getChildCount() == imglist.size() && imglist.size() != 1) {
                    addImgView(null);
                }
            }
        }
    }


    public File getAlbumDir() {
        File file = new File(Environment.getExternalStorageDirectory(), getAlbumName());
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }


    /**
     * 获取保存 隐患检查的图片文件夹名称
     *
     * @return
     */
    public String getAlbumName() {
        return "/Android/data/" + OpinionFeedbackActivity.this.getApplicationContext()
                .getPackageName() + "/cache/imgCache";
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int 
            reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }
}