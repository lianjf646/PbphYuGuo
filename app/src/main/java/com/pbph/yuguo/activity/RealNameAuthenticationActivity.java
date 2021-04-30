package com.pbph.yuguo.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.pbph.yuguo.dialog.BothBtnBottomPopWin;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.interfaces.OnPopWinBothBtnClickListener;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetAppChangeCustomerInfoRequest;
import com.pbph.yuguo.request.GetProjectTokenResquest;
import com.pbph.yuguo.request.ToAuthenticationResquest;
import com.pbph.yuguo.util.AliOss;
import com.pbph.yuguo.util.GlideImageLoaderUtils;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.RegexUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Administrator on 2018/12/20.
 */

public class RealNameAuthenticationActivity extends BaseActivity {
    private final Context context = RealNameAuthenticationActivity.this;
    private RelativeLayout rl_shenfenzheng;
    private ImageView id_crad;
    private final int REQUEST_CODE_PHOTO = 100;
    private String imageUrl = "";
    private RelativeLayout rl_yaoqiu;
    private ImageView iv_paizhao;
    private EditText et_name;
    private EditText et_card;
    private TextView botton;
    private String imagesUrl = "";
    private Boolean isPath = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_realname_authentication);
        initTitle(TITLE_STYLE_WHITE, "上传证件", true, false);

        initImagePager();
        initView();
        initData();







    }

    private void initData() {


    }


    private void initView() {
        id_crad = findViewById(R.id.id_crad);
        iv_paizhao = findViewById(R.id.iv_paizhao);
        et_name = findViewById(R.id.et_name);
        et_card = findViewById(R.id.et_card);
        botton = findViewById(R.id.botton);
        botton.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {

                toAuthentication();

            }
        });
        //上传  重新上传
        rl_shenfenzheng = findViewById(R.id.rl_shenfenzheng);
        rl_shenfenzheng.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                BothBtnBottomPopWin popWin = new BothBtnBottomPopWin(context, rl_shenfenzheng, "实名认证", "从相册选择照片", "拍照");
                popWin.show(v);
                popWin.setOnPopWinBothBtnClickListener(new OnPopWinBothBtnClickListener() {
                    @Override
                    public void onFirstBtnClick() {
//                        ImagePicker.getInstance().setCrop(false);
                        Intent intent = new Intent(RealNameAuthenticationActivity.this, ImageGridActivity.class);
                        startActivityForResult(intent, REQUEST_CODE_PHOTO);
                        popWin.dismiss();
                    }

                    @Override
                    public void onSecondBtnClick() {
//                        ImagePicker.getInstance().setCrop(false);
                        Intent intent = new Intent(context, ImageGridActivity.class);
                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true);
                        startActivityForResult(intent, REQUEST_CODE_PHOTO);
                        popWin.dismiss();
                    }
                });
            }
        });
        rl_yaoqiu = findViewById(R.id.rl_yaoqiu);
        rl_yaoqiu.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                showDialog();
            }
        });
    }

    private void toAuthentication() {

        if (imageUrl.equals("")){
            showToast("请上传身份证");
            return;
        }
        if (isPath){
            showToast("请重新上传身份证");
        }
        try {
            imagesUrl = URLEncoder.encode(imageUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        String realName = et_name.getText().toString();
        String card = et_card.getText().toString();
        if (!RegexUtil.isLegalName(realName)){
            showToast("姓名不符合规范");
            return;
        }
        if (!RegexUtil.isRealIDCard(card)){
            showToast("身份证号码不符合规范");
            return;
        }

        int customerId = YuGuoApplication.userInfo.getCustomerId();
        if (customerId == -1 || customerId == 0) {
            showToast("用户Id获取失败");
            return;
        }

        ToAuthenticationResquest request = new ToAuthenticationResquest(customerId, imagesUrl, realName,card);
        HttpAction.getInstance().toAuthentication(request).subscribe(new BaseObserver<>(context, response -> {
            int code = response.getCode();
            String msg = response.getMsg();
            if (200 == code) {
                showToast("上传成功");
                finish();
            } else {
                showToast(TextUtils.isEmpty(msg) ? "上传失败" : msg);
            }
        }));
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        //填充对话框的布局
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_yaoqiu, null);
        TextView price = (TextView) view.findViewById(R.id.botton);
        price.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);

        dialog.setCanceledOnTouchOutside(true);

        //获取当前Activity所在的窗体
        final Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        if (dialogWindow != null) {
            WindowManager windowManager = dialogWindow.getWindowManager();
            Point outSize = new Point();
            windowManager.getDefaultDisplay().getSize(outSize);
            dialogWindow.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams params = dialogWindow.getAttributes();
            params.width = outSize.x;
            dialogWindow.setAttributes(params);
        }
        //将布局设置给Dialog
        dialog.show();//显示对话框

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
        ArrayList<ImageItem> imglist = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
        if (imglist == null || imglist.size() <= 0) {
            return;
        }

        upDataAliImages(imglist.get(0));
    }

    private void upDataAliImages(ImageItem item) {
        WaitUI.Show(context);
        File file = new File(item.path);
        Glide.with(context).load(file).into(id_crad);
        iv_paizhao.setVisibility(View.GONE);
        File small_file = new File(getAlbumDir(), "small_" + file.getName());
        Bitmap bm = getSmallBitmap(item.path);
        if (bm != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(small_file);
                bm.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        ArrayList<ImageItem> imageItemList = new ArrayList<>();
        ImageItem imageItem = new ImageItem();
        imageItem.path = file.getPath();
        imageItemList.add(imageItem);

        HttpAction.getInstance().getProjectToken(new GetProjectTokenResquest())
                .subscribe(new BaseObserver<>(this, response -> {
                    if (response.getCode() != 200) {
                        Toast.makeText(context, response.getMsg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    AliOss.getInstance().uploadImageList(getApplication(), response.getData().getToken(), response.getData().getProjectCode(), imageItemList, path -> {
                        runOnUiThread(WaitUI::Cancel);
                        Log.e("AliTuPian", "onSuccess: " + path);
                        imageUrl = path;
                        if (TextUtils.isEmpty(imageUrl)){
                            showToast("请重新上传身份证");
                            return;
                        }


                    });
                }, (code, message) -> {
                    WaitUI.Cancel();
                    showToast("身份证上传失败");
                }));
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
    @Override
    public void onLeftClick() {
        finish();
    }


    private void initImagePager() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoaderUtils());
        imagePicker.setShowCamera(false);
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(false);
        imagePicker.setSaveRectangle(false);
        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200,
                getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200,
                getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(width);
        imagePicker.setFocusHeight(height);
        imagePicker.setSelectLimit(1);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        imagePicker.setOutPutX(width);
        imagePicker.setOutPutY(height);
    }
}
