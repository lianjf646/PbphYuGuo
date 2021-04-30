package com.pbph.yuguo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.dialog.BothBtnBottomPopWin;
import com.pbph.yuguo.dialog.BothBtnCenterPopWin;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.interfaces.OnPopWinBothBtnClickListener;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetEvaluateOrderRequest;
import com.pbph.yuguo.request.GetGoodsEvaluateOrderDetailsRequest;
import com.pbph.yuguo.request.GetProjectTokenResquest;
import com.pbph.yuguo.response.EvaluateOrderResponse;
import com.pbph.yuguo.response.GoodsEvaluateOrderDetailsResponse;
import com.pbph.yuguo.response.WaitEvaluateListResponse;
import com.pbph.yuguo.util.AMUtils;
import com.pbph.yuguo.util.AliOss;
import com.pbph.yuguo.util.GlideImageLoaderUtils;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.MoneyHelper;
import com.sobot.chat.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaluateCenterActivity extends BaseActivity {
    private final Context context = EvaluateCenterActivity.this;
    private final int REQUEST_CODE_PHOTO = 100;

    private View layoutSingleGoods;
    private ImageView ivGoodsImage;
    private TextView tvGoodsName;
    private TextView tvGoodsIntro;
    private TextView tvGoodsPrice;
    private TextView tvGoodsNum;

    private LinearLayout llImageParent;

    private EditText etEvaluate;
    private ImageView ivAddPhoto;
    private LinearLayout llCheck;
    private CheckBox cbCheck;
    private TextView tvPublish;

    private ImagePicker imagePicker;

    private Map<Integer, String> imgUrlMap = new HashMap<>();
    private int position = 0;
    private int itemId = 0;

    private WaitEvaluateListResponse.DataBean.WaitEvaluateListBean waitEvaluate;
    private GoodsEvaluateOrderDetailsResponse.DataBean data;

    private boolean isAnonymous = false;
    private BothBtnBottomPopWin popWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_center);
        initImagePager();
        initTitle(TITLE_STYLE_WHITE, "评价中心", true, false);
        initIntent();
        initView();
        initData();
        initClick();
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            waitEvaluate = (WaitEvaluateListResponse.DataBean.WaitEvaluateListBean) intent.getSerializableExtra("waitEvaluate");
        }
    }

    private void initView() {
        layoutSingleGoods = findViewById(R.id.layout_single_goods);
        ivGoodsImage = layoutSingleGoods.findViewById(R.id.iv_goods_image);
        tvGoodsName = layoutSingleGoods.findViewById(R.id.tv_goods_name);
        tvGoodsIntro = layoutSingleGoods.findViewById(R.id.tv_goods_intro);
        tvGoodsPrice = layoutSingleGoods.findViewById(R.id.tv_goods_price);
        tvGoodsNum = layoutSingleGoods.findViewById(R.id.tv_goods_num);
        llImageParent = findViewById(R.id.ll_image_parent);

        etEvaluate = findViewById(R.id.et_evaluate);
        AMUtils.setEtFilter(etEvaluate, 120);
//        etEvaluate.setFilters(new InputFilter[]{new InputFilter.LengthFilter(120)});
        ivAddPhoto = findViewById(R.id.iv_add_photo);
        llCheck = findViewById(R.id.ll_check);
        cbCheck = findViewById(R.id.cb_check);
        tvPublish = findViewById(R.id.tv_publish);
        if (waitEvaluate != null) {
            String goodsImage = waitEvaluate.getGoodsImg();
            String goodsName = waitEvaluate.getGoodsName();
            String goodsIntro = waitEvaluate.getGoodsSpec();
            double goodsPrice = waitEvaluate.getMemberPrice();
            int goodsNum = waitEvaluate.getGoodsNum();

            GlideUtil.displayImage(context, goodsImage, ivGoodsImage);
            tvGoodsName.setText(goodsName);
            tvGoodsIntro.setText(goodsIntro);

            tvGoodsPrice.setText(String.format("￥%s", MoneyHelper.getInstance4Fen(goodsPrice).change2Yuan().getString()));
            tvGoodsNum.setText(String.format("x%s", goodsNum));
        }
    }

    private void initData() {
        getGoodsEvaluate();
    }

    private void getGoodsEvaluate() {
        int customerId = YuGuoApplication.userInfo.getCustomerId();
        if (customerId == -1 || customerId == 0) {
            showToast("用户Id获取失败");
            return;
        }
        int orderId = waitEvaluate.getOrderId();
        int goodsId = waitEvaluate.getGoodsId();
        WaitUI.Show(context);
        GetGoodsEvaluateOrderDetailsRequest request = new GetGoodsEvaluateOrderDetailsRequest(orderId, customerId, goodsId);
        HttpAction.getInstance().getGoodsEvaluateOrderDetails(request).subscribe(new BaseObserver<>(context, response -> {
            WaitUI.Cancel();
            int code = response.getCode();
            if (code == 200) {
                data = response.getData();
                setEvaluate();
            }
        }));
    }

    private void setEvaluate() {
        int evaluateFlag = waitEvaluate.getEvaluateFlag();
        int anonymousFlag = data.getAnonymousFlag();
        if (evaluateFlag == 1) {
            //代表已评价  评价内容及匿名状态都不可编辑
            etEvaluate.setEnabled(false);
            llCheck.setEnabled(false);
            cbCheck.setEnabled(false);
            etEvaluate.setText(data.getEvaluateContent());
        }

        if (anonymousFlag == 0) {
            //非匿名
            cbCheck.setChecked(false);
        } else {
            cbCheck.setChecked(true);
        }
    }

    private void initClick() {
        ivAddPhoto.setOnClickListener(v -> {

            if (popWin == null) {
                popWin = new BothBtnBottomPopWin(context, v, "选择图片", "从相册选择图片", "拍照");
            }
            popWin.show(v);

            popWin.setOnPopWinBothBtnClickListener(new OnPopWinBothBtnClickListener() {
                @Override
                public void onFirstBtnClick() {
                    imagePicker.setSelectLimit(3 - imgUrlMap.size());
                    Intent intent = new Intent(context, ImageGridActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_PHOTO);
                    popWin.dismiss();
                }

                @Override
                public void onSecondBtnClick() {
                    Intent intent = new Intent(context, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true);
                    startActivityForResult(intent, REQUEST_CODE_PHOTO);
                    popWin.dismiss();
                }
            });
        });

        llCheck.setOnClickListener(v -> {
            isAnonymous = !isAnonymous;

            cbCheck.setChecked(isAnonymous);
        });

        cbCheck.setOnClickListener(v -> {
            isAnonymous = !isAnonymous;

            cbCheck.setChecked(isAnonymous);
        });

        tvPublish.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                int evaluateFlag = 0;
                if (waitEvaluate != null) {
                    evaluateFlag = waitEvaluate.getEvaluateFlag();
                }
                if (evaluateFlag == 0) {
                    //评价
                    evaluateOrder();
                } else {
                    //晒单
                    evaluateBaskOrder();
                }
            }
        });
    }

    private void evaluateOrder() {
        //评价
        /*"orderId":1,
                "customerId":1,
                "anonymousFlag":0,
                "storeGoodsId":1,
                "evaluateContent":"五星好评",
                "shareImgUrls":"123.jgp,123.jgp,123.jgp"*/

        int customerId = YuGuoApplication.userInfo.getCustomerId();
        if (customerId == -1 || customerId == 0) {
            showToast("用户Id获取失败");
            return;
        }

        if (TextUtils.isEmpty(etEvaluate.getText().toString())) {
            ToastUtil.showToast(context, "请填写评价内容");
            return;
        }
        if (TextUtils.isEmpty(etEvaluate.getText().toString())) {
            ToastUtil.showToast(context, "评价字数不足");
            return;
        }
        /*if (etEvaluate.getText().toString().length() > 120) {
            ToastUtil.showToast(context, "评价字数大于120个字");
            return;
        }*/

        int anonymousFlag;
        if (isAnonymous) {
            anonymousFlag = 1;
        } else {
            anonymousFlag = 0;
        }

        String imgUrls = getImgUrl(imgUrlMap);

        GetEvaluateOrderRequest request = new GetEvaluateOrderRequest(waitEvaluate.getOrderId(), -1, customerId, anonymousFlag,
                waitEvaluate.getGoodsId(), waitEvaluate.getStoreGoodsId(), etEvaluate.getText().toString(), imgUrls);
        HttpAction.getInstance().evaluateOrder(request).subscribe(new BaseObserver<>(context, response -> {
            int code = response.getCode();
            if (code == 200) {
                ToastUtil.showToast(context, "评价成功");
                EvaluateOrderResponse evaluateOrderResponse = new EvaluateOrderResponse();
                EventBus.getDefault().post(evaluateOrderResponse);
                finish();
            }
        }));
    }

    private void evaluateBaskOrder() {
        //晒单
        //不传 customerId orderId
        if (imgUrlMap.size() <= 0) {
            ToastUtil.showToast(context, "请上传评价图片");
            return;
        }

//        if (etEvaluate.getText().toString().length() < 12) {
//            ToastUtil.showToast(context, "评价字数不足");
//            return;
//        }

        String imgUrls = getImgUrl(imgUrlMap);

        GetEvaluateOrderRequest request = new GetEvaluateOrderRequest(-1, data.getEvaluateId(), -1, data.getAnonymousFlag(),
                -1, -1, etEvaluate.getText().toString(), imgUrls);
        HttpAction.getInstance().evaluateBaskOrder(request).subscribe(new BaseObserver<>(context, response -> {
            int code = response.getCode();
            if (code == 200) {
                ToastUtil.showToast(context, "晒单成功");
                EvaluateOrderResponse evaluateOrderResponse = new EvaluateOrderResponse();
                EventBus.getDefault().post(evaluateOrderResponse);
                finish();
            }
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        result2SetImg(requestCode, resultCode, data);
    }

    private void result2SetImg(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        if (requestCode != REQUEST_CODE_PHOTO || resultCode != ImagePicker.RESULT_CODE_ITEMS) {
            return;
        }
        ArrayList<ImageItem> imageList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);

        if (imageList == null || imageList.size() <= 0) {
            return;
        }

        upDataAliImages(imageList);
    }

    private void upDataAliImages(ArrayList<ImageItem> imageList) {
        WaitUI.Show(context);
        ArrayList<ImageItem> imageItemList = new ArrayList<>();
        for (int i = 0; i < imageList.size(); i++) {
            File file = new File(imageList.get(i).path);
            File small_file = new File(getAlbumDir(), "small_" + file.getName());
            Bitmap bm = getSmallBitmap(imageList.get(i).path);
            if (bm != null) {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(small_file);
                    bm.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            ImageItem imageItem = new ImageItem();
            imageItem.path = small_file.getPath();
            imageItemList.add(imageItem);
        }

        HttpAction.getInstance().getProjectToken(new GetProjectTokenResquest()).subscribe(new BaseObserver<>(this, response -> {
            int code = response.getCode();
            if (code == 200) {
                uploadImageList(response.getData().getToken(), response.getData().getProjectCode(), imageItemList);
            } else {
                showToast(TextUtils.isEmpty(response.getMsg()) ? "获取token失败" : response.getMsg());
            }
        }));
    }

    private void uploadImageList(String token, String projectCode, ArrayList<ImageItem> imageItemList) {
        AliOss.getInstance().uploadImageList(getApplication(), token, projectCode, imageItemList, path -> {

            Log.e("AliImage", "onSuccess: " + path);
            runOnUiThread(() -> {
                WaitUI.Cancel();
                addImage(path);
            });
        });
    }

    public File getAlbumDir() {
        File file = new File(Environment.getExternalStorageDirectory(), getAlbumName());
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public String getAlbumName() {
        return "/Android/data/" + getApplicationContext().getPackageName() + "/cache/imgCache";
    }

    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    private void initImagePager() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoaderUtils());
        imagePicker.setShowCamera(true);
        imagePicker.setMultiMode(true);
        imagePicker.setCrop(true);
        imagePicker.setSaveRectangle(true);
        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200,
                getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200,
                getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(width);
        imagePicker.setFocusHeight(height);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        imagePicker.setOutPutX(width);
        imagePicker.setOutPutY(height);
    }

    public void addImage(String imageItem) {

        //控件和索引的集合
        final HashMap<Integer, View> viewMap = new HashMap<>();
        //初始化控件
        View view = LayoutInflater.from(context).inflate(R.layout.layout_evaluate_image, null);
        FrameLayout flContainer = view.findViewById(R.id.fl_container);
        final ImageView ivEvaluateImage = view.findViewById(R.id.iv_evaluate_image);
        final ImageView ivDeleteImage = view.findViewById(R.id.iv_delete);
        ivEvaluateImage.setScaleType(ImageView.ScaleType.FIT_XY);

        //设置图片尺寸
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width = getResources().getDimensionPixelOffset(R.dimen.dp_80dp);
        layoutParams.height = getResources().getDimensionPixelOffset(R.dimen.dp_80dp);
        layoutParams.rightMargin = getResources().getDimensionPixelOffset(R.dimen.dp_10dp);
        //设置上传的按钮图片和上传的图片尺寸一样
        ivAddPhoto.setLayoutParams(layoutParams);
        flContainer.setLayoutParams(layoutParams);
        imgUrlMap.put(itemId, imageItem);

        viewMap.put(itemId, view);
        ivDeleteImage.setTag(itemId);

        GlideUtil.displayImage(context, imageItem, ivEvaluateImage);
        llImageParent.addView(view, position);


        ivDeleteImage.setOnClickListener(v -> {
            BothBtnCenterPopWin pop = new BothBtnCenterPopWin(context, ivDeleteImage, "确定删除吗?", "提示", "取消", "确定", true);
            pop.setOnPopWinBothBtnClickListener(new OnPopWinBothBtnClickListener() {

                @Override
                public void onFirstBtnClick() {
                    pop.dismiss();
                }

                @Override
                public void onSecondBtnClick() {
                    int itemId = (int) v.getTag();
                    llImageParent.removeView(viewMap.get(itemId));
                    ivAddPhoto.setVisibility(View.VISIBLE);
                    imgUrlMap.remove(itemId);
                    pop.dismiss();
                    position--;
                }
            });
        });
        position++;
        itemId++;
        if (imgUrlMap.size() >= 3) {
            ivAddPhoto.setVisibility(View.GONE);
        }
    }

    public String getImgUrl(Map<Integer, String> imgUrlMap) {
        if (imgUrlMap.size() == 0) {
            return "";
        }
        StringBuilder shareImageUrl = new StringBuilder();

        List<Map.Entry<Integer, String>> imgUrlList = new ArrayList<>(imgUrlMap.entrySet());

        Collections.sort(imgUrlList, (o1, o2) -> (o1.getKey()).toString().compareTo(o2.getKey() + ""));

        for (int i = 0; i < imgUrlList.size(); i++) {
            String value = imgUrlList.get(i).getValue();
            try {
                value = URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (i != imgUrlList.size() - 1) {
                shareImageUrl.append(value).append(",");
            } else {
                shareImageUrl.append(value);
            }
        }

        return shareImageUrl.toString();
    }

    @Override
    public void onLeftClick() {
        finish();
    }
}
