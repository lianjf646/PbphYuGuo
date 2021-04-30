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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetAppChangeCustomerInfoRequest;
import com.pbph.yuguo.request.GetCustomerInfoByIdRequest;
import com.pbph.yuguo.request.GetProjectTokenResquest;
import com.pbph.yuguo.response.GetCustomerInfoByIdResponse;
import com.pbph.yuguo.util.AliOss;
import com.pbph.yuguo.util.GlideImageLoaderUtils;
import com.pbph.yuguo.util.GlideUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class AccountManagementActivity extends BaseActivity {

    private final Context context = AccountManagementActivity.this;
    private final int REQUEST_CODE_PHOTO = 100;

    private RelativeLayout rlUserFace;
    private ImageView ivUserFace;
    private EditText etNickName;
    private RelativeLayout rlSex;
    private TextView tvSex;
    private TextView etRealName;
    private TextView tvBirth;
    private TextView tvSave;

    private GetCustomerInfoByIdResponse.DataBean.CustomerBean customerBean;

    private String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);
        initTitle(TITLE_STYLE_WHITE, "账户管理", true, false);
        initView();
        initData();
        initClick();
        initImagePager();
    }

    private void initView() {
        rlUserFace = findViewById(R.id.rl_user_face);
        ivUserFace = findViewById(R.id.iv_user_face);
        etNickName = findViewById(R.id.et_nick_name);
        rlSex = findViewById(R.id.rl_sex);
        tvSex = findViewById(R.id.tv_sex);
        etRealName = findViewById(R.id.et_real_name);
        tvBirth = findViewById(R.id.tv_birth);
        tvSave = findViewById(R.id.tv_save);
    }

    private void initData() {
        int customerId = YuGuoApplication.userInfo.getCustomerId();
        if (customerId == -1 || customerId == 0) {
            showToast("用户Id获取失败");
            return;
        }
        WaitUI.Show(context);
        HttpAction.getInstance().getCustomerInfoById(new GetCustomerInfoByIdRequest(customerId)).subscribe(new BaseObserver<>(mContext, response -> {
            WaitUI.Cancel();
            int code = response.getCode();
            if (200 == code) {
                customerBean = response.getData().getCustomer();
                YuGuoApplication.userInfo.setCustomerLevelType(customerBean.getCustomerLevelType());
                setUserInfo();
            }
        }));
    }

    private void setUserInfo() {
        String userImageUrl = customerBean.getCustomerImgUrl();
        String nickName = customerBean.getCustomerName();
        String realName = customerBean.getCustomerRealName();
        int sex = customerBean.getCustomerSex();
        String birth = customerBean.getCustomerBirthday();
        if (!TextUtils.isEmpty(userImageUrl)) {
            imageUrl = userImageUrl;
            GlideUtil.displayCircleBitmap(context, imageUrl, ivUserFace);
        }
        etNickName.setText(nickName);

        String sexStr;
        if (sex == 2) {
            sexStr = "男";
        } else if (sex == 3) {
            sexStr = "女";
        } else {
            sexStr = "请选择";
        }
        tvSex.setText(sexStr);

        if (TextUtils.isEmpty(realName)) {
            etRealName.setText("请实名认证");
        } else {
            etRealName.setText(realName);
        }

        if (TextUtils.isEmpty(birth)) {
            tvBirth.setText("请实名认证");
        } else {
            tvBirth.setText(birth);
        }
    }

    private void initClick() {
        rlUserFace.setOnClickListener(v -> {
            BothBtnBottomPopWin popWin = new BothBtnBottomPopWin(context, rlUserFace, "更换头像", "从相册选择照片", "拍照");
            popWin.show(v);
            popWin.setOnPopWinBothBtnClickListener(new OnPopWinBothBtnClickListener() {
                @Override
                public void onFirstBtnClick() {
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

        rlSex.setOnClickListener(v -> {
            BothBtnBottomPopWin popWin = new BothBtnBottomPopWin(context, rlUserFace, "选择性别", "男", "女");
            popWin.show(v);
            popWin.setOnPopWinBothBtnClickListener(new OnPopWinBothBtnClickListener() {
                @Override
                public void onFirstBtnClick() {
                    tvSex.setText("男");
                    popWin.dismiss();
                }

                @Override
                public void onSecondBtnClick() {
                    tvSex.setText("女");
                    popWin.dismiss();
                }
            });
        });

        etRealName.setOnClickListener(v -> startActivity(new Intent(context, RealNameStateActivity.class)));

        tvBirth.setOnClickListener(v -> startActivity(new Intent(context, RealNameStateActivity.class)));

        tvSave.setOnClickListener(v -> {
            String sexStr = tvSex.getText().toString();
            int sex;
            String realName = etRealName.getText().toString();
            String birth = tvBirth.getText().toString();
            if (TextUtils.isEmpty(imageUrl) && TextUtils.isEmpty(sexStr) && TextUtils.isEmpty(realName) && TextUtils.isEmpty(birth)) {
                showToast("未修改任何内容");
                return;
            }
            if ("男".equals(sexStr)) {
                sex = 2;
            } else if ("女".equals(sexStr)) {
                sex = 3;
            } else {
                sex = -1;
            }
            try {
                imageUrl = URLEncoder.encode(imageUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            int customerId = YuGuoApplication.userInfo.getCustomerId();
            if (customerId == -1 || customerId == 0) {
                showToast("用户Id获取失败");
                return;
            }

            GetAppChangeCustomerInfoRequest request = new GetAppChangeCustomerInfoRequest(customerId, imageUrl, etNickName.getText().toString(), realName, birth, null, null,
                    null, sex);
            HttpAction.getInstance().appChangeCustomerInfo(request).subscribe(new BaseObserver<>(context, response -> {
                int code = response.getCode();
                String msg = response.getMsg();
                if (200 == code) {
                    showToast("修改成功");
                    finish();
                } else {
                    showToast(TextUtils.isEmpty(msg) ? "修改失败" : msg);
                }
            }));
        });
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
        imageItem.path = small_file.getPath();
        imageItemList.add(imageItem);
        HttpAction.getInstance().getProjectToken(new GetProjectTokenResquest()).subscribe(new BaseObserver<>(this, response -> {
            if (response.getCode() != 200) {
                Toast.makeText(context, response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }

            AliOss.getInstance().uploadImageList(getApplication(), response.getData().getToken(), response.getData().getProjectCode(),
                    imageItemList,onOosUploadListener);
        }, (code, message) -> showToast("头像上传失败")));
    }

    AliOss.OnOosUploadListener onOosUploadListener = new AliOss.OnOosUploadListener() {
        @Override
        public void onSuccess(String path) {
//
            Log.e("AliTuPian", "onSuccess: " + path);
            imageUrl = path;
            runOnUiThread(() -> {
                WaitUI.Cancel();
                GlideUtil.displayCircleBitmap(context, path, ivUserFace);
            });

        }
    };

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
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    private void initImagePager() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoaderUtils());
        imagePicker.setShowCamera(true);
        imagePicker.setMultiMode(false);
        imagePicker.setCrop(true);
        imagePicker.setSaveRectangle(true);
        imagePicker.setStyle(CropImageView.Style.CIRCLE);
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(width);
        imagePicker.setFocusHeight(height);
        imagePicker.setSelectLimit(1);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        imagePicker.setOutPutX(width);
        imagePicker.setOutPutY(height);
    }

    @Override
    public void onLeftClick() {
        finish();
    }
}
