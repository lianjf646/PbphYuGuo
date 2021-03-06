package com.sobot.chat.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sobot.chat.activity.base.SobotBaseActivity;
import com.sobot.chat.adapter.base.SobotPicListAdapter;
import com.sobot.chat.api.ResultCallBack;
import com.sobot.chat.api.model.CommonModelBase;
import com.sobot.chat.api.model.PostParamModel;
import com.sobot.chat.api.model.SobotFieldModel;
import com.sobot.chat.api.model.SobotLeaveMsgParamModel;
import com.sobot.chat.api.model.SobotTypeModel;
import com.sobot.chat.api.model.ZhiChiMessage;
import com.sobot.chat.api.model.ZhiChiUploadAppFileModelResult;
import com.sobot.chat.application.MyApplication;
import com.sobot.chat.core.http.callback.StringResultCallBack;
import com.sobot.chat.listener.ISobotCusField;
import com.sobot.chat.presenter.StCusFieldPresenter;
import com.sobot.chat.utils.ChatUtils;
import com.sobot.chat.utils.HtmlTools;
import com.sobot.chat.utils.LogUtils;
import com.sobot.chat.utils.ResourceUtils;
import com.sobot.chat.utils.ScreenUtils;
import com.sobot.chat.utils.SharedPreferencesUtil;
import com.sobot.chat.utils.ZhiChiConstant;
import com.sobot.chat.widget.ThankDialog;
import com.sobot.chat.widget.dialog.SobotDialogUtils;
import com.sobot.chat.widget.dialog.SobotSelectPicDialog;
import com.sobot.chat.widget.kpswitch.util.KeyboardUtil;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("HandlerLeak")
public class SobotPostMsgActivity extends SobotBaseActivity implements OnClickListener,ISobotCusField {

    private EditText sobot_post_email, sobot_et_content, sobot_post_phone;
    private TextView sobot_tv_post_msg, sobot_post_email_lable, sobot_post_phone_lable, sobot_post_lable, sobot_post_question_type;
    private ImageView sobot_img_clear_email, sobot_img_clear_phone;
    private View sobot_frist_line;
    private GridView sobot_post_msg_pic;
    private LinearLayout sobot_enclosure_container, sobot_post_customer_field;
    private RelativeLayout sobot_post_email_rl, sobot_post_phone_rl, sobot_post_question_rl;
    private List<ZhiChiUploadAppFileModelResult> pic_list = new ArrayList<>();
    private SobotPicListAdapter adapter;
    private SobotSelectPicDialog menuWindow;
    private ArrayList<SobotFieldModel> field;
    private ArrayList<SobotTypeModel> types;

    private LinearLayout sobot_post_msg_layout;
    private String uid = "";
    private String companyId = "";
    private String groupId = "";
    private String msgTmp = "";
    private String msgTxt = "";
    private boolean flag_exit_sdk;
    private boolean telFlag;
    private boolean telShowTvFlag;
    private boolean emailFlag;
    private boolean emailShowTvFlag;
    private boolean enclosureShowFlag;
    private boolean enclosureFlag;

    private int flag_exit_type = -1;
    private ThankDialog d;

    public Handler handler = new Handler() {
        public void handleMessage(final android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (flag_exit_type == 1) {
                        finishPageOrSDK(true);
                    } else if (flag_exit_type == 2) {
                        setResult(200);
                        finishPageOrSDK(false);
                    } else {
                        finishPageOrSDK(flag_exit_sdk);
                    }
                    break;
            }
        }
    };

    @Override
    protected int getContentViewResId() {
        return getResLayoutId("sobot_activity_post_msg");
    }

    protected void initBundleData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            if (getIntent() != null) {
                uid = getIntent().getStringExtra("uid");
                companyId = getIntent().getStringExtra("companyId");
                groupId = getIntent().getStringExtra("groupId");
                flag_exit_type = getIntent().getIntExtra(ZhiChiConstant.FLAG_EXIT_TYPE, -1);
                flag_exit_sdk = getIntent().getBooleanExtra(ZhiChiConstant.FLAG_EXIT_SDK, false);
                msgTmp = getIntent().getStringExtra("msgTmp").replaceAll("\n", "<br/>");
                msgTxt = getIntent().getStringExtra("msgTxt").replaceAll("\n", "<br/>");
            }
        } else {
            uid = savedInstanceState.getString("uid");
            companyId = savedInstanceState.getString("companyId");
            groupId = savedInstanceState.getString("groupId");
            flag_exit_type = savedInstanceState.getInt(ZhiChiConstant.FLAG_EXIT_TYPE, -1);
            flag_exit_sdk = savedInstanceState.getBoolean(ZhiChiConstant.FLAG_EXIT_SDK, false);

            msgTmp = savedInstanceState.getString("msgTmp");
            msgTxt = savedInstanceState.getString("msgTxt");
            if (!TextUtils.isEmpty(msgTmp)) {
                msgTmp = msgTmp.replaceAll("\n", "<br/>");
            }

            if (!TextUtils.isEmpty(msgTxt)) {
                msgTxt = msgTxt.replaceAll("\n", "<br/>");
            }
        }
    }

    @Override
    protected void initView() {
        sobot_post_phone = findViewById(getResId("sobot_post_phone"));
        sobot_post_email = findViewById(getResId("sobot_post_email"));
        sobot_frist_line = findViewById(getResId("sobot_frist_line"));
        sobot_et_content = findViewById(getResId("sobot_post_et_content"));
        sobot_tv_post_msg = findViewById(getResId("sobot_tv_post_msg"));
        sobot_post_email_lable = findViewById(getResId("sobot_post_email_lable"));
        sobot_post_phone_lable = findViewById(getResId("sobot_post_phone_lable"));
        sobot_post_lable = findViewById(getResId("sobot_post_question_lable"));
        String test = "<font color='#8B98AD'>" + getResString("sobot_problem_types") + "</font>" + "<font color='#f9676f'>&#8201*</font>";
        sobot_post_lable.setText(Html.fromHtml(test));
        sobot_post_question_type = findViewById(getResId("sobot_post_question_type"));
        sobot_post_question_type.setOnClickListener(this);
        sobot_post_msg_layout = findViewById(getResId("sobot_post_msg_layout"));
        sobot_img_clear_email = findViewById(getResId("sobot_img_clear_email"));
        sobot_img_clear_phone = findViewById(getResId("sobot_img_clear_phone"));
        sobot_img_clear_phone.setOnClickListener(this);
        sobot_img_clear_email.setOnClickListener(this);
        sobot_enclosure_container = findViewById(getResId("sobot_enclosure_container"));
        sobot_post_customer_field = findViewById(getResId("sobot_post_customer_field"));


        sobot_post_email_rl = findViewById(getResId("sobot_post_email_rl"));
        sobot_post_phone_rl = findViewById(getResId("sobot_post_phone_rl"));
        sobot_post_question_rl = findViewById(getResId("sobot_post_question_rl"));

        sobot_post_customer_field.setVisibility(View.GONE);

        showRightMenu(0, getResString("sobot_submit"), true);
        showLeftMenu(getResDrawableId("sobot_btn_back_selector"),getResString("sobot_back"),true);

        setTitle(getResString("sobot_str_bottom_message"));

        //???????????????????????????
        telShowTvFlag = SharedPreferencesUtil.getBooleanData(this, ZhiChiConstant.SOBOT_POSTMSG_TELSHOWFLAG, false);//??????textView????????????
        telFlag = SharedPreferencesUtil.getBooleanData(this, ZhiChiConstant.SOBOT_POSTMSG_TELFLAG, false);//??????????????????

        emailShowTvFlag = SharedPreferencesUtil.getBooleanData(this, ZhiChiConstant.SOBOT_POSTMSG_EMAILSHOWFLAG, false);//??????textView????????????
        emailFlag = SharedPreferencesUtil.getBooleanData(this, ZhiChiConstant.SOBOT_POSTMSG_EMAILFLAG, false);//??????????????????

        enclosureShowFlag = SharedPreferencesUtil.getBooleanData(this, ZhiChiConstant.SOBOT_POSTMSG_ENCLOSURESHOWFLAG, false);//????????????????????????
        enclosureFlag = SharedPreferencesUtil.getBooleanData(this, ZhiChiConstant.SOBOT_POSTMSG_ENCLOSUREFLAG, false);//????????????????????????


        if (emailShowTvFlag) {
            sobot_post_email_rl.setVisibility(View.VISIBLE);
        } else {
            sobot_post_email_rl.setVisibility(View.GONE);
        }

        if (telShowTvFlag) {
            sobot_post_phone_rl.setVisibility(View.VISIBLE);
        } else {
            sobot_post_phone_rl.setVisibility(View.GONE);
        }

        if (emailShowTvFlag && telShowTvFlag) {
            sobot_frist_line.setVisibility(View.VISIBLE);
        } else {
            sobot_frist_line.setVisibility(View.GONE);
        }

        if (telFlag) {
            sobot_post_phone.setText(SharedPreferencesUtil.getStringData(this, "sobot_user_phone", ""));
        }

        if (emailFlag) {
            sobot_post_email.setText(SharedPreferencesUtil.getStringData(this, "sobot_user_email", ""));
        }

        if (enclosureShowFlag) {
            sobot_enclosure_container.setVisibility(View.VISIBLE);
            initPicListView();
        } else {
            sobot_enclosure_container.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initData() {
        zhiChiApi.getLeaveMsgParam(SobotPostMsgActivity.this,uid, new ResultCallBack<SobotLeaveMsgParamModel>() {

            @Override
            public void onFailure(Exception e, String des) {
                try {
                    showHint(getString(ResourceUtils.getIdByName(getApplicationContext(), "string", "sobot_try_again")), false);
                } catch (Exception e1) {

                }
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {

            }

            @Override
            public void onSuccess(SobotLeaveMsgParamModel result) {
                if (result != null) {
                    if (result.isTicketTypeFlag()) {
                        sobot_post_question_rl.setVisibility(View.VISIBLE);
                    } else {
                        sobot_post_question_rl.setVisibility(View.GONE);
                        sobot_post_question_type.setTag(result.getTicketTypeId());
                    }

                    if (result.getField() != null && result.getField().size() != 0) {
                        field = result.getField();
                        StCusFieldPresenter.addWorkOrderCusFields(SobotPostMsgActivity.this, field, sobot_post_customer_field,SobotPostMsgActivity.this);
                    }

                    if (result.getType() != null && result.getType().size() != 0) {
                        types = result.getType();
                    }
                }
            }
        });

        msgFilter();
        editTextSetHint();
    }

    private void setCusFieldValue() {
        StCusFieldPresenter.formatCusFieldVal(SobotPostMsgActivity.this,sobot_post_customer_field,field);
        checkSubmit();
    }


    private void checkSubmit() {
        String userPhone = "", userEamil = "";

        if (sobot_post_question_rl.getVisibility() == View.VISIBLE) {
            if (TextUtils.isEmpty(sobot_post_question_type.getText().toString())) {
                showHint(getResString("sobot_problem_types")  + "  "+ getResString("sobot__is_null"), false);
                return;
            }
        }

        if (field != null && field.size() != 0) {
            for (int i = 0; i < field.size(); i++) {
                if (1 == field.get(i).getCusFieldConfig().getFillFlag()) {
                    if (TextUtils.isEmpty(field.get(i).getCusFieldConfig().getValue())) {
                        showHint(field.get(i).getCusFieldConfig().getFieldName()  + "  "+ getResString("sobot__is_null"), false);
                        return;
                    }
                }
            }
        }

        if (TextUtils.isEmpty(sobot_et_content.getText().toString().trim())) {
            showHint(getResString("sobot_problem_description") + "  "+ getResString("sobot__is_null"), false);
            return;
        }

        if (enclosureShowFlag && enclosureFlag) {
            if (TextUtils.isEmpty(getFileStr())) {
                showHint(getResString("sobot_please_load"), false);
                return;
            }
        }

        if (emailShowTvFlag) {
            if (emailFlag) {
                if (!TextUtils.isEmpty(sobot_post_email.getText().toString().trim())
                        && ScreenUtils.isEmail(sobot_post_email.getText().toString().trim())) {
                    userEamil = sobot_post_email.getText().toString().trim();
                } else {
                    showHint(getResString("sobot_email_dialog_hint"), false);
                    return;
                }
            } else {
                if (!TextUtils.isEmpty(sobot_post_email.getText().toString().trim())) {
                    String emailStr = sobot_post_email.getText().toString().trim();
                    if (ScreenUtils.isEmail(emailStr)) {
                        userEamil = sobot_post_email.getText().toString().trim();
                    } else {
                        showHint(getResString("sobot_email_dialog_hint"), false);
                        return;
                    }
                }
            }
        }

        if (telShowTvFlag) {
            if (telFlag) {
                if (!TextUtils.isEmpty(sobot_post_phone.getText().toString().trim())
                        && ScreenUtils.isMobileNO(sobot_post_phone.getText().toString().trim())) {
                    userPhone = sobot_post_phone.getText().toString();
                } else {
                    showHint(getResString("sobot_phone_dialog_hint"), false);
                    return;
                }
            } else {
                if (!TextUtils.isEmpty(sobot_post_phone.getText().toString().trim())) {
                    String phoneStr = sobot_post_phone.getText().toString().trim();
                    if (ScreenUtils.isMobileNO(phoneStr)) {
                        userPhone = phoneStr;
                    } else {
                        showHint(getResString("sobot_phone_dialog_hint"), false);
                        return;
                    }
                }
            }
        }

        postMsg(userPhone, userEamil);
    }

    @Override
    protected void onRightMenuClick(View view) {
        setCusFieldValue();
    }

    @SuppressWarnings("deprecation")
    public void showHint(String content, final boolean flag) {
        if (!isFinishing()) {
            KeyboardUtil.hideKeyboard(SobotPostMsgActivity.this.getCurrentFocus());
            if (d != null) {
                d.dismiss();
            }
            ThankDialog.Builder customBuilder = new ThankDialog.Builder(
                    SobotPostMsgActivity.this);
            customBuilder.setMessage(content);
            d = customBuilder.create();
            d.show();

            Window window = d.getWindow();
            if (window != null) {
                WindowManager.LayoutParams lp = window.getAttributes();

                float dpToPixel = ScreenUtils.dpToPixel(
                        getApplicationContext(), 1);
                lp.width = (int) (dpToPixel * 200); // ????????????
                d.getWindow().setAttributes(lp);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!isFinishing()) {
                            if (d != null) {
                                d.dismiss();
                            }
                            if (flag) {
                                handler.sendEmptyMessage(1);
                            }
                        }
                    }
                }, 2000);
            }
        }
    }

    @Override
    public void onClick(View view) {

        if (view == sobot_img_clear_email) {
            sobot_post_email.setText("");
            sobot_img_clear_email.setVisibility(View.GONE);
        }

        if (view == sobot_img_clear_phone) {
            sobot_post_phone.setText("");
            sobot_img_clear_phone.setVisibility(View.GONE);
        }

        if (view == sobot_post_question_type) {
            if (types != null && types.size() != 0) {
                Intent intent = new Intent(SobotPostMsgActivity.this, SobotPostCategoryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("types", types);
                if (sobot_post_question_type != null &&
                        !TextUtils.isEmpty(sobot_post_question_type.getText().toString()) &&
                        sobot_post_question_type.getTag() != null &&
                        !TextUtils.isEmpty(sobot_post_question_type.getTag().toString())) {
                    bundle.putString("typeName", sobot_post_question_type.getText().toString());
                    bundle.putString("typeId", sobot_post_question_type.getTag().toString());
                }
                intent.putExtra("bundle", bundle);
                startActivityForResult(intent, ZhiChiConstant.work_order_list_display_type_category);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (flag_exit_type == 1 || flag_exit_type == 2) {
            finishPageOrSDK(false);
        } else {
            finishPageOrSDK(flag_exit_sdk);
        }
    }

    private void postMsg(String userPhone, String userEamil) {

        PostParamModel postParam = new PostParamModel();
        postParam.setUid(uid);
        postParam.setTicketContent(sobot_et_content.getText().toString());
        postParam.setCustomerEmail(userEamil);
        postParam.setCustomerPhone(userPhone);
        postParam.setCompanyId(companyId);
        postParam.setFileStr(getFileStr());
        postParam.setGroupId(groupId);
        if (sobot_post_question_type.getTag() != null && !TextUtils.isEmpty(sobot_post_question_type.getTag().toString())) {
            postParam.setTicketTypeId(sobot_post_question_type.getTag().toString());
        }

        postParam.setExtendFields(StCusFieldPresenter.getSaveFieldVal(field));

        zhiChiApi.postMsg(SobotPostMsgActivity.this,postParam, new StringResultCallBack<CommonModelBase>() {
            @Override
            public void onSuccess(CommonModelBase base) {
                if (Integer.parseInt(base.getStatus()) == 0) {
                    showHint(base.getMsg(), false);
                } else if (Integer.parseInt(base.getStatus()) == 1) {
                    showHint(getResString("sobot_leavemsg_success_hint"), true);
                }
            }

            @Override
            public void onFailure(Exception e, String des) {
                try {
                    showHint(getString(ResourceUtils.getIdByName(getApplicationContext(), "string", "sobot_try_again")), false);
                } catch (Exception e1) {

                }
            }
        });
    }

    private void finishPageOrSDK(boolean flag) {
        if (!flag) {
            finish();
            overridePendingTransition(ResourceUtils.getIdByName(
                    getApplicationContext(), "anim", "push_right_in"),
                    ResourceUtils.getIdByName(getApplicationContext(), "anim",
                            "push_right_out"));
        } else {
            MyApplication.getInstance().exit();
        }
    }

    @Override
    protected void onDestroy() {
        SobotDialogUtils.stopProgressDialog(this);
        if (d != null) {
            d.dismiss();
        }
        super.onDestroy();
    }

    protected void onSaveInstanceState(Bundle outState) {
        //??????????????????????????????
        outState.putString("uid", uid);
        outState.putString("companyId", companyId);
        outState.putString("groupId", groupId);
        outState.putInt("flag_exit_type", flag_exit_type);
        outState.putBoolean("flag_exit_sdk", flag_exit_sdk);
        outState.putString("msgTmp", msgTmp);
        outState.putString("msgTxt", msgTxt);
        super.onSaveInstanceState(outState);
    }

    /**
     * ??????????????????????????????
     */
    private void initPicListView() {
        sobot_post_msg_pic = findViewById(getResId("sobot_post_msg_pic"));
        adapter = new SobotPicListAdapter(SobotPostMsgActivity.this, pic_list);
        sobot_post_msg_pic.setAdapter(adapter);
        sobot_post_msg_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KeyboardUtil.hideKeyboard(view);
                if (pic_list.get(position).getViewState() == 0) {
                    menuWindow = new SobotSelectPicDialog(SobotPostMsgActivity.this, itemsOnClick);
                    menuWindow.show();
                } else {
                    LogUtils.i("???????????????????????????" + position);
                    Intent intent = new Intent(SobotPostMsgActivity.this, SobotPhotoListActivity.class);
                    intent.putExtra(ZhiChiConstant.SOBOT_KEYTYPE_PIC_LIST, adapter.getPicList());
                    intent.putExtra(ZhiChiConstant.SOBOT_KEYTYPE_PIC_LIST_CURRENT_ITEM, position);
                    startActivityForResult(intent, ZhiChiConstant.SOBOT_KEYTYPE_DELETE_FILE_SUCCESS);
                }
            }
        });
        adapter.restDataView();
    }

    //???msg??????
    private void msgFilter() {
        if (!TextUtils.isEmpty(msgTmp)) {
            msgTmp = msgTmp.replace("<br/>", "");
        }

        if (!TextUtils.isEmpty(msgTxt)) {
            msgTxt = msgTxt.replace("<br/>", "");
        }

        sobot_et_content.setHint(Html.fromHtml(msgTmp));
        HtmlTools.getInstance(getApplicationContext()).setRichText(sobot_tv_post_msg, msgTxt,
                ResourceUtils.getIdByName(this, "color", "sobot_postMsg_url_color"));
        sobot_post_msg_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.hideKeyboard(sobot_post_msg_layout);
            }
        });
    }

    //??????editText???hint????????????
    private void editTextSetHint() {
        String mustFill = "<font color='red'>&#8201*</font>";

        if (emailFlag) {
            sobot_post_email_lable.setText(Html.fromHtml("<font color='#8B98AD'>" + getResString("sobot_email") + "</font>" + mustFill));
        } else {
            sobot_post_email_lable.setText(Html.fromHtml("<font color='#8B98AD'>" + getResString("sobot_email") + "</font>"));
        }

        if (telFlag) {
            sobot_post_phone_lable.setText(Html.fromHtml("<font color='#8B98AD'>" + getResString("sobot_phone") + "</font>" + mustFill));
        } else {
            sobot_post_phone_lable.setText(Html.fromHtml("<font color='#8B98AD'>" + getResString("sobot_phone") + "</font>"));
        }
    }

    private ChatUtils.SobotSendFileListener sendFileListener = new ChatUtils.SobotSendFileListener() {
        @Override
        public void onSuccess(final String filePath) {
            zhiChiApi.fileUploadForPostMsg(companyId, filePath, new ResultCallBack<ZhiChiMessage>() {
                @Override
                public void onSuccess(ZhiChiMessage zhiChiMessage) {
                    SobotDialogUtils.stopProgressDialog(SobotPostMsgActivity.this);
                    if (zhiChiMessage.getData() != null) {
                        ZhiChiUploadAppFileModelResult item = new ZhiChiUploadAppFileModelResult();
                        item.setFileUrl(zhiChiMessage.getData().getUrl());
                        item.setFileLocalPath(filePath);
                        item.setViewState(1);
                        adapter.addData(item);
                    }
                }

                @Override
                public void onFailure(Exception e, String des) {
                    SobotDialogUtils.stopProgressDialog(SobotPostMsgActivity.this);
                    showHint(des, false);
                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {

                }
            });
        }

        @Override
        public void onError() {
            SobotDialogUtils.stopProgressDialog(SobotPostMsgActivity.this);
        }
    };

    public String getFileStr() {
        String tmpStr = "";
        if (!enclosureShowFlag) {
            return tmpStr;
        }

        ArrayList<ZhiChiUploadAppFileModelResult> tmpList = adapter.getPicList();
        for (int i = 0; i < tmpList.size(); i++) {
            tmpStr += tmpList.get(i).getFileUrl() + ";";
        }
        return tmpStr;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == ZhiChiConstant.REQUEST_CODE_picture) { // ??????????????????
                if (data != null && data.getData() != null) {
                    Uri selectedImage = data.getData();
                    SobotDialogUtils.startProgressDialog(SobotPostMsgActivity.this);
                    ChatUtils.sendPicByUriPost(this, selectedImage, sendFileListener);
                } else {
                    showHint(getResString("sobot_did_not_get_picture_path"), false);
                }
            } else if (requestCode == ZhiChiConstant.REQUEST_CODE_makePictureFromCamera) {
                if (cameraFile != null && cameraFile.exists()) {
                    SobotDialogUtils.startProgressDialog(SobotPostMsgActivity.this);
                    ChatUtils.sendPicByFilePath(this, cameraFile.getAbsolutePath(), sendFileListener);
                } else {
                    showHint(getResString("sobot_pic_select_again"), false);
                }
            }
        }

        if (data != null) {

            StCusFieldPresenter.onStCusFieldActivityResult(SobotPostMsgActivity.this,data,field,sobot_post_customer_field);

            switch (requestCode) {
                case ZhiChiConstant.SOBOT_KEYTYPE_DELETE_FILE_SUCCESS://????????????
                    List<ZhiChiUploadAppFileModelResult> tmpList = (List<ZhiChiUploadAppFileModelResult>) data.getExtras().getSerializable(ZhiChiConstant.SOBOT_KEYTYPE_PIC_LIST);
                    adapter.addDatas(tmpList);
                    break;
                case ZhiChiConstant.work_order_list_display_type_category:
                    if (!TextUtils.isEmpty(data.getStringExtra("category_typeId"))) {
                        String typeName = data.getStringExtra("category_typeName");
                        String typeId = data.getStringExtra("category_typeId");
                        sobot_post_question_type.setText(typeName);
                        sobot_post_question_type.setTag(typeId);
                    }
                    break;
                default:
                    break;
            }
        }
    }


    // ???????????????popupwindow???????????????
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            menuWindow.dismiss();
            if (v.getId() == getResId("btn_take_photo")) {
                LogUtils.i("??????");
                selectPicFromCamera();
            }
            if (v.getId() == getResId("btn_pick_photo")) {
                LogUtils.i("????????????");
                selectPicFromLocal();
            }
        }
    };

    @Override
    public void onClickCusField(View view,int fieldType, SobotFieldModel cusField) {
        switch (fieldType) {
            case ZhiChiConstant.WORK_ORDER_CUSTOMER_FIELD_DATE_TYPE:
            case ZhiChiConstant.WORK_ORDER_CUSTOMER_FIELD_TIME_TYPE:
                StCusFieldPresenter.openTimePicker(SobotPostMsgActivity.this,view,fieldType);
                break;
            case ZhiChiConstant.WORK_ORDER_CUSTOMER_FIELD_SPINNER_TYPE:
            case ZhiChiConstant.WORK_ORDER_CUSTOMER_FIELD_RADIO_TYPE:
            case ZhiChiConstant.WORK_ORDER_CUSTOMER_FIELD_CHECKBOX_TYPE:
                StCusFieldPresenter.startSobotCusFieldActivity(SobotPostMsgActivity.this,cusField);
                break;
            default:
                break;
        }
    }
}