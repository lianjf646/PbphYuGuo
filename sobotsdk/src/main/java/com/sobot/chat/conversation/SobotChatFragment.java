package com.sobot.chat.conversation;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sobot.chat.activity.SobotPostMsgActivity;
import com.sobot.chat.activity.SobotSkillGroupActivity;
import com.sobot.chat.activity.WebViewActivity;
import com.sobot.chat.adapter.base.SobotMsgAdapter;
import com.sobot.chat.api.apiUtils.GsonUtil;
import com.sobot.chat.api.apiUtils.SobotVerControl;
import com.sobot.chat.api.apiUtils.ZhiChiConstants;
import com.sobot.chat.api.enumtype.CustomerState;
import com.sobot.chat.api.model.CommonModel;
import com.sobot.chat.api.model.CommonModelBase;
import com.sobot.chat.api.model.ConsultingContent;
import com.sobot.chat.api.model.Information;
import com.sobot.chat.api.model.SobotCommentParam;
import com.sobot.chat.api.model.SobotEvaluateModel;
import com.sobot.chat.api.model.ZhiChiCidsModel;
import com.sobot.chat.api.model.ZhiChiGroup;
import com.sobot.chat.api.model.ZhiChiGroupBase;
import com.sobot.chat.api.model.ZhiChiHistoryMessage;
import com.sobot.chat.api.model.ZhiChiHistoryMessageBase;
import com.sobot.chat.api.model.ZhiChiInitModeBase;
import com.sobot.chat.api.model.ZhiChiMessageBase;
import com.sobot.chat.api.model.ZhiChiPushMessage;
import com.sobot.chat.api.model.ZhiChiReplyAnswer;
import com.sobot.chat.core.channel.Const;
import com.sobot.chat.core.channel.SobotMsgManager;
import com.sobot.chat.core.http.callback.StringResultCallBack;
import com.sobot.chat.listener.NoDoubleClickListener;
import com.sobot.chat.server.SobotSessionServer;
import com.sobot.chat.utils.AnimationUtil;
import com.sobot.chat.utils.AudioTools;
import com.sobot.chat.utils.ChatUtils;
import com.sobot.chat.utils.CommonUtils;
import com.sobot.chat.utils.ExtAudioRecorder;
import com.sobot.chat.utils.IntenetUtil;
import com.sobot.chat.utils.LogUtils;
import com.sobot.chat.utils.ResourceUtils;
import com.sobot.chat.utils.SharedPreferencesUtil;
import com.sobot.chat.utils.SobotOption;
import com.sobot.chat.utils.TimeTools;
import com.sobot.chat.utils.ToastUtil;
import com.sobot.chat.utils.ZhiChiConfig;
import com.sobot.chat.utils.ZhiChiConstant;
import com.sobot.chat.viewHolder.CusEvaluateMessageHolder;
import com.sobot.chat.viewHolder.RichTextMessageHolder;
import com.sobot.chat.viewHolder.VoiceMessageHolder;
import com.sobot.chat.voice.AudioPlayCallBack;
import com.sobot.chat.voice.AudioPlayPresenter;
import com.sobot.chat.widget.ClearHistoryDialog;
import com.sobot.chat.widget.ContainsEmojiEditText;
import com.sobot.chat.widget.DropdownListView;
import com.sobot.chat.widget.dialog.SobotEvaluateDialog;
import com.sobot.chat.widget.emoji.DisplayRules;
import com.sobot.chat.widget.emoji.Emojicon;
import com.sobot.chat.widget.emoji.InputHelper;
import com.sobot.chat.widget.kpswitch.CustomeChattingPanel;
import com.sobot.chat.widget.kpswitch.util.KPSwitchConflictUtil;
import com.sobot.chat.widget.kpswitch.util.KeyboardUtil;
import com.sobot.chat.widget.kpswitch.view.ChattingPanelEmoticonView;
import com.sobot.chat.widget.kpswitch.view.ChattingPanelUploadView;
import com.sobot.chat.widget.kpswitch.view.CustomeViewFactory;
import com.sobot.chat.widget.kpswitch.widget.KPSwitchPanelLinearLayout;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * @author Created by jinxl on 2018/2/1.
 */
public class SobotChatFragment extends SobotChatBaseFragment implements View.OnClickListener
        , DropdownListView.OnRefreshListenerHeader,SobotMsgAdapter.SobotMsgCallBack,
        ContainsEmojiEditText.SobotAutoCompleteListener
        ,ChattingPanelEmoticonView.SobotEmoticonClickListener,ChattingPanelUploadView.SobotPlusClickListener {

    //---------------UI?????? START---------------
    public TextView mTitleTextView;
    public TextView sobot_title_conn_status;
    public LinearLayout sobot_container_conn_status;
    public ProgressBar sobot_conn_loading;
    public RelativeLayout net_status_remide;
    public RelativeLayout relative;
    private TextView sobot_tv_satisfaction, notReadInfo, sobot_tv_message,
            sobot_txt_restart_talk;
    private TextView textReConnect;
    private ProgressBar loading_anim_view;
    private TextView txt_loading;
    private ImageView icon_nonet;
    private Button btn_reconnect;
    private RelativeLayout chat_main; // ???????????????;
    private FrameLayout welcome; // ????????????;
    private DropdownListView lv_message;/* ????????????ListView */
    private ContainsEmojiEditText et_sendmessage;// ???????????????????????????
    private Button btn_send; // ??????????????????
    private ImageButton btn_set_mode_rengong; // ?????????button
    private TextView send_voice_robot_hint;
    private Button btn_upload_view; // ????????????
    private ImageButton btn_emoticon_view; // ????????????
    private TextView voice_time_long;/*??????????????????*/
    private LinearLayout voice_top_image;
    private ImageView image_endVoice;
    private ImageView mic_image;
    private ImageView mic_image_animate; // ???????????????
    private ImageView recording_timeshort;// ?????????????????????
    private ImageButton btn_model_edit; // ????????????
    private ImageButton btn_model_voice;// ????????????
    private TextView txt_speak_content; // ?????????????????????
    private AnimationDrawable animationDrawable;/* ??????????????? */
    public KPSwitchPanelLinearLayout mPanelRoot; // ?????????????????????
    private LinearLayout btn_press_to_speak; // ??????view ;
    private RelativeLayout edittext_layout; // ?????????view;
    private LinearLayout recording_container;// ?????????????????????
    private TextView recording_hint;// ????????????????????????
    private RelativeLayout sobot_ll_restart_talk; // ?????????????????????ID
    private ImageView image_reLoading;
    private LinearLayout sobot_ll_bottom;
    //??????
    private RelativeLayout sobot_announcement; // ??????view ;
    private TextView sobot_announcement_right_icon;
    private TextView sobot_announcement_title;

    private SobotEvaluateDialog mEvaluateDialog;
    //---------------UI?????? END---------------

    Information info;

    //-----------
    // ??????????????????
    private List<ZhiChiMessageBase> messageList = new ArrayList<ZhiChiMessageBase>();
    protected SobotMsgAdapter messageAdapter;

    //--------

    private int showTimeVisiableCustomBtn = 0;/*???????????????????????????????????????*/
    private List<ZhiChiGroupBase> list_group;

    protected int type = -1;//?????????????????????
    private boolean isSessionOver = true;//???????????????????????????

    private boolean isComment = false;/* ??????????????????????????? */
    private int queueNum = 0;//???????????????
    private int queueTimes = 0;//???????????????????????????????????????
    private int mUnreadNum = 0;//???????????????

    private int logCollectTime = 0;//??????????????????

    //????????????
    public static final String mVoicePath = ZhiChiConstant.voicePositionPath;
    protected Timer voiceTimer;
    protected TimerTask voiceTimerTask;
    protected int voiceTimerLong = 0;
    protected String voiceTimeLongStr = "00";// ????????????????????????
    private int minRecordTime = 60;// ??????????????????
    private int recordDownTime = minRecordTime - 10;// ?????????????????? ?????????
    boolean isCutVoice;
    private String voiceMsgId = "";//  ???????????????Id
    private int currentVoiceLong = 0;

    AudioPlayPresenter mAudioPlayPresenter = null;
    AudioPlayCallBack mAudioPlayCallBack = null;
    private String mFileName = null;
    private ExtAudioRecorder extAudioRecorder;

    //??????????????????????????????????????????
    private List<String> cids = new ArrayList<>();//cid?????????
    private int currentCidPosition = 0;//?????????????????????????????????cid??????
    //????????????cid????????? ?????????????????? 0???????????? 1???????????? 2???????????????  3???????????????
    private int queryCidsStatus = ZhiChiConstant.QUERY_CIDS_STATUS_INITIAL;
    private boolean isInGethistory = false;//????????????????????????????????????
    private boolean isConnCustomerService = false;//?????????????????? ??????????????????????????????
    private boolean isNoMoreHistoryMsg = false;

    //????????????
    public int currentPanelId = 0;//????????????????????? ?????????????????????id ???????????????????????????view???
    private int mBottomViewtype = 0;//?????????????????????

    //---------
    //????????????
    private ViewTreeObserver.OnGlobalLayoutListener mKPSwitchListener;

    private MyMessageReceiver receiver;
    //?????????????????????????????????
    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;

    public static SobotChatFragment newInstance(Bundle info) {
        Bundle arguments = new Bundle();
        arguments.putBundle("informationBundle", info);
        SobotChatFragment fragment = new SobotChatFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("onCreate");
        if (getArguments() != null) {
            Bundle informationBundle = getArguments().getBundle("informationBundle");
            if (informationBundle != null) {
                info = (Information) informationBundle.getSerializable("info");
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(getResLayoutId("sobot_chat_fragment"), container, false);
        initView(root);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (info == null) {
            ToastUtil.showToast(mAppContext, getResString("sobot_init_data_is_null"));
            finish();
            return;
        }

        if (TextUtils.isEmpty(info.getAppkey())) {
            Toast.makeText(mAppContext, getResString("sobot_appkey_is_null"), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        SharedPreferencesUtil.saveStringData(mAppContext, ZhiChiConstant.SOBOT_CURRENT_IM_APPID, info.getAppkey());

        //?????????????????????
        ChatUtils.saveOptionSet(mAppContext, info);

        initData();

    }

    @Override
    public void onDestroyView() {
        if (initModel != null){
            if (!isSessionOver){
                //??????????????????
                saveCache();
            }else{
                //??????????????????
                clearCache();
            }
            //??????????????????
            ChatUtils.saveLastMsgInfo(mAppContext,info,info.getAppkey(),initModel,messageList);
        }
        hideReLoading();
        // ?????????????????????
        if (getActivity() != null) {
            getActivity().unregisterReceiver(receiver);
            KeyboardUtil.detach(getActivity(),mKPSwitchListener);
        }
        if (localBroadcastManager != null) {
            localBroadcastManager.unregisterReceiver(localReceiver);
        }
        // ???????????????????????????
        stopUserInfoTimeTask();
        // ???????????????????????????
        stopCustomTimeTask();
        stopVoice();
        AudioTools.destory();
        if (mEvaluateDialog != null && mEvaluateDialog.isShowing()) {
            mEvaluateDialog.dismiss();
        }
        if(SobotOption.sobotViewListener != null){
            SobotOption.sobotViewListener.onChatActClose(customerState);
        }
        super.onDestroyView();
    }

    private void initView(View rootView) {
        if (rootView == null) {
            return;
        }

        //loading ???
        relative = rootView.findViewById(getResId("sobot_layout_titlebar"));
        mTitleTextView = rootView.findViewById(getResId("sobot_text_title"));
        sobot_title_conn_status = rootView.findViewById(getResId("sobot_title_conn_status"));
        sobot_container_conn_status = rootView.findViewById(getResId("sobot_container_conn_status"));
        sobot_conn_loading = rootView.findViewById(getResId("sobot_conn_loading"));
        net_status_remide = rootView.findViewById(getResId("sobot_net_status_remide"));

        relative.setVisibility(View.GONE);
        notReadInfo = rootView.findViewById(getResId("notReadInfo"));
        chat_main = rootView.findViewById(getResId("sobot_chat_main"));
        welcome = rootView.findViewById(getResId("sobot_welcome"));
        txt_loading = rootView.findViewById(getResId("sobot_txt_loading"));
        textReConnect = rootView.findViewById(getResId("sobot_textReConnect"));
        loading_anim_view = rootView.findViewById(getResId("sobot_image_view"));
        image_reLoading = rootView.findViewById(getResId("sobot_image_reloading"));
        icon_nonet = rootView.findViewById(getResId("sobot_icon_nonet"));
        btn_reconnect = rootView.findViewById(getResId("sobot_btn_reconnect"));
        btn_reconnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                textReConnect.setVisibility(View.GONE);
                icon_nonet.setVisibility(View.GONE);
                btn_reconnect.setVisibility(View.GONE);
                loading_anim_view.setVisibility(View.VISIBLE);
                txt_loading.setVisibility(View.VISIBLE);
                customerInit();
            }
        });

        lv_message = rootView.findViewById(getResId("sobot_lv_message"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            lv_message.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
        et_sendmessage = rootView.findViewById(getResId("sobot_et_sendmessage"));
        et_sendmessage.setVisibility(View.VISIBLE);
        et_sendmessage.setTextColor(Color.parseColor("#000000"));
        btn_send = rootView.findViewById(getResId("sobot_btn_send"));
        btn_set_mode_rengong = rootView.findViewById(getResId("sobot_btn_set_mode_rengong"));
        send_voice_robot_hint = rootView.findViewById(getResId("send_voice_robot_hint"));
        send_voice_robot_hint.setVisibility(View.GONE);
        btn_upload_view = rootView.findViewById(getResId("sobot_btn_upload_view"));
        btn_emoticon_view = rootView.findViewById(getResId("sobot_btn_emoticon_view"));
        btn_model_edit = rootView.findViewById(getResId("sobot_btn_model_edit"));
        btn_model_voice = rootView.findViewById(getResId("sobot_btn_model_voice"));
        mPanelRoot = rootView.findViewById(getResId("sobot_panel_root"));
        btn_press_to_speak = rootView.findViewById(getResId("sobot_btn_press_to_speak"));
        edittext_layout = rootView.findViewById(getResId("sobot_edittext_layout"));
        recording_hint = rootView.findViewById(getResId("sobot_recording_hint"));
        recording_container = rootView.findViewById(getResId("sobot_recording_container"));

        // ??????????????????????????????
        voice_top_image = rootView.findViewById(getResId("sobot_voice_top_image"));
        // ????????????
        image_endVoice = rootView.findViewById(getResId("sobot_image_endVoice"));
        // ???????????????
        mic_image_animate = rootView.findViewById(getResId("sobot_mic_image_animate"));
        // ???????????????
        voice_time_long = rootView.findViewById(getResId("sobot_voiceTimeLong"));
        txt_speak_content = rootView.findViewById(getResId("sobot_txt_speak_content"));
        txt_speak_content.setText(getResString("sobot_press_say"));
        recording_timeshort = rootView.findViewById(getResId("sobot_recording_timeshort"));
        mic_image = rootView.findViewById(getResId("sobot_mic_image"));

        sobot_ll_restart_talk = rootView.findViewById(getResId("sobot_ll_restart_talk"));
        sobot_txt_restart_talk = rootView.findViewById(getResId("sobot_txt_restart_talk"));
        sobot_tv_message = rootView.findViewById(getResId("sobot_tv_message"));
        sobot_tv_satisfaction = rootView.findViewById(getResId("sobot_tv_satisfaction"));
        sobot_ll_bottom = rootView.findViewById(getResId("sobot_ll_bottom"));

        sobot_announcement = rootView.findViewById(getResId("sobot_announcement"));
        sobot_announcement_right_icon = rootView.findViewById(getResId("sobot_announcement_right_icon"));
        sobot_announcement_title = rootView.findViewById(getResId("sobot_announcement_title"));
    }

    /* ???????????? */
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {

        @SuppressWarnings("unchecked")
        public void handleMessage(final android.os.Message msg) {
            if (!isActive()) {
                return;
            }
            switch (msg.what) {
                case ZhiChiConstant.hander_my_senderMessage:/* ?????????????????? */
                    updateUiMessage(messageAdapter, msg);
                    lv_message.setSelection(messageAdapter.getCount());
                    break;
                case ZhiChiConstant.hander_my_update_senderMessageStatus:
                    updateTextMessageStatus(messageAdapter, msg);
                    lv_message.setSelection(messageAdapter.getCount());
                    break;
                case ZhiChiConstant.update_send_data:
                    ZhiChiMessageBase myMessage = (ZhiChiMessageBase) msg.obj;
                    messageAdapter.updateDataById(myMessage.getId(), myMessage);
                    messageAdapter.notifyDataSetChanged();
                    lv_message.setSelection(messageAdapter.getCount());
                    break;
                case ZhiChiConstant.hander_robot_message:
                    ZhiChiMessageBase zhiChiMessageBasebase = (ZhiChiMessageBase) msg.obj;
                    if(type == ZhiChiConstant.type_robot_first || type == ZhiChiConstant.type_custom_first ){
                        //????????????????????????????????????????????????????????????????????????????????????????????????
                        if(initModel != null && ChatUtils.checkManualType(initModel.getManualType(),
                                zhiChiMessageBasebase.getAnswerType())){
                            //????????????????????????????????? ???????????????????????????
                            zhiChiMessageBasebase.setShowTransferBtn(true);
                        }
                    }

                    if(ZhiChiConstant.type_answer_direct.equals(zhiChiMessageBasebase.getAnswerType())
                            || ZhiChiConstant.type_answer_wizard.equals(zhiChiMessageBasebase.getAnswerType())
                            || "11".equals(zhiChiMessageBasebase.getAnswerType())
                            || "12".equals(zhiChiMessageBasebase.getAnswerType())
                            || "14".equals(zhiChiMessageBasebase.getAnswerType())){
                        if(initModel != null && initModel.isRealuateFlag()){
                            //?????????????????? ??????????????????
                            zhiChiMessageBasebase.setRevaluateState(1);
                        }
                    }

                    if (zhiChiMessageBasebase.getAnswer() != null && zhiChiMessageBasebase.getAnswer().getMultiDiaRespInfo() != null
                            && zhiChiMessageBasebase.getAnswer().getMultiDiaRespInfo().getEndFlag()) {
                        // ????????????????????????????????????????????????????????????
                        restMultiMsg();
                    }

                    updateUiMessage(messageAdapter,zhiChiMessageBasebase);
                    if (SobotMsgManager.getInstance(mAppContext).getConfig(info.getAppkey()).getInitModel() != null) {
                        //???????????????????????????????????? ???????????????view ????????????????????????????????????
                        SobotMsgManager.getInstance(mAppContext).getConfig(info.getAppkey()).addMessage(zhiChiMessageBasebase);
                    }
                    // ?????????????????????????????????????????????????????????????????????????????????????????????
                    if (type == ZhiChiConstant.type_robot_first && (ZhiChiConstant.type_answer_unknown.equals(zhiChiMessageBasebase
                            .getAnswerType()) || ZhiChiConstant.type_answer_guide.equals(zhiChiMessageBasebase
                            .getAnswerType()))) {
                        showTransferCustomer();
                    }
                    gotoLastItem();
                    break;
                case ZhiChiConstant.message_type_wo_sendImage: // ??????????????? ??????ui
                    // ????????????view??????
                    updateUiMessage(messageAdapter, msg);
                    break;
                case ZhiChiConstant.message_type_send_voice: // ????????????
                    updateUiMessage(messageAdapter, msg);
                    break;
                // ???????????????????????????
                case ZhiChiConstant.message_type_update_voice:
                    updateVoiceStatusMessage(messageAdapter, msg);
                    break;
                case ZhiChiConstant.message_type_cancel_voice://????????????????????????
                    cancelUiVoiceMessage(messageAdapter, msg);
                    break;
                case ZhiChiConstant.hander_sendPicStatus_success:
                    setTimeTaskMethod(handler);
                    String id = (String) msg.obj;
                    updateUiMessageStatus(messageAdapter, id, ZhiChiConstant.MSG_SEND_STATUS_SUCCESS, 0);
                    break;
                case ZhiChiConstant.hander_sendPicStatus_fail:
                    String resultId = (String) msg.obj;
                    updateUiMessageStatus(messageAdapter, resultId, ZhiChiConstant.MSG_SEND_STATUS_ERROR, 0);
                    break;
                case ZhiChiConstant.hander_sendPicIsLoading:
                    String loadId = (String) msg.obj;
                    int uploadProgress = msg.arg1;
                    updateUiMessageStatus(messageAdapter, loadId,ZhiChiConstant.MSG_SEND_STATUS_LOADING, uploadProgress);
                    break;
                case ZhiChiConstant.hander_timeTask_custom_isBusying: // ?????????????????????
                    // --????????????
                    updateUiMessage(messageAdapter, msg);
                    LogUtils.i("?????????????????????:" + noReplyTimeCustoms);
                    stopCustomTimeTask();
                    break;
                case ZhiChiConstant.hander_timeTask_userInfo:// ?????????????????????
                    updateUiMessage(messageAdapter, msg);
                    stopUserInfoTimeTask();
                    LogUtils.i("??????????????????????????????  ?????????????????????" + noReplyTimeUserInfo);
                    break;
                case ZhiChiConstant.voiceIsRecoding:
                    // ???????????????????????????????????????????????????????????????
                    if (voiceTimerLong >= minRecordTime * 1000) {
                        isCutVoice = true;
                        voiceCuttingMethod();
                        voiceTimerLong = 0;
                        recording_hint.setText(getResString("sobot_voiceTooLong"));
                        recording_hint.setBackgroundResource(getResDrawableId("sobot_recording_text_hint_bg"));
                        recording_timeshort.setVisibility(View.VISIBLE);
                        mic_image.setVisibility(View.GONE);
                        mic_image_animate.setVisibility(View.GONE);
                        closeVoiceWindows(2);
                        btn_press_to_speak.setPressed(false);
                        currentVoiceLong = 0;
                    } else {
                        final int time = Integer.parseInt(msg.obj.toString());
//					LogUtils.i("??????????????????????????????" + time);
                        currentVoiceLong = time;
                        if (time < recordDownTime * 1000) {
                            if (time % 1000 == 0) {
                                voiceTimeLongStr = TimeTools.instance.calculatTime(time);
                                voice_time_long.setText(voiceTimeLongStr.substring(3) + "''");
                            }
                        } else if (time < minRecordTime * 1000) {
                            if (time % 1000 == 0) {
                                voiceTimeLongStr = TimeTools.instance.calculatTime(time);
                                voice_time_long.setText(getResString("sobot_count_down") + (minRecordTime * 1000 - time) / 1000);
                            }
                        } else {
                            voice_time_long.setText(getResString("sobot_voiceTooLong"));
                        }
                    }
                    break;
                case ZhiChiConstant.hander_close_voice_view:
                    int longOrShort = msg.arg1;
                    txt_speak_content.setText(getResString("sobot_press_say"));
                    currentVoiceLong = 0;
                    recording_container.setVisibility(View.GONE);

                    if (longOrShort == 0){
                        for (int i = messageList.size() - 1; i > 0 ; i--) {
                            if (!TextUtils.isEmpty(messageList.get(i).getSenderType()) &&
                                    Integer.parseInt(messageList.get(i).getSenderType()) == 8){
                                messageList.remove(i);
                                break;
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    protected void initData() {
        setToolBar();
        initBrocastReceiver();
        initListener();
        setupListView();
        loadUnreadNum();
        initSdk(false);
        Intent intent = new Intent(mAppContext, SobotSessionServer.class);
        intent.putExtra(ZhiChiConstant.SOBOT_CURRENT_IM_PARTNERID, info.getUid());
        mAppContext.startService(intent);
    }

    private void setToolBar() {
        if (getView() == null) {
            return;
        }

        if (!TextUtils.isEmpty(info.getColor())) {
            relative.setBackgroundColor(Color.parseColor(info.getColor()));
        }

        if (info != null && info.getTitleImgId() != 0) {
            relative.setBackgroundResource(info.getTitleImgId());
        }
        View rootView = getView();
        View toolBar = rootView.findViewById(getResId("sobot_layout_titlebar"));
        View sobot_tv_left = rootView.findViewById(getResId("sobot_tv_left"));
        View sobot_tv_right = rootView.findViewById(getResId("sobot_tv_right"));
        if (toolBar != null) {
            if (sobot_tv_left != null) {
                //?????? Toolbar ???????????????,????????????????????????,?????????????????? Activity
                //???????????????????????????
                showLeftMenu(sobot_tv_left, getResDrawableId("sobot_btn_back_selector"), getResString("sobot_back"));
                sobot_tv_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onLeftMenuClick(v);
                    }
                });
            }

            if (sobot_tv_right != null) {
                showRightMenu(sobot_tv_right, getResDrawableId("sobot_delete_hismsg_selector"), "");
                sobot_tv_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRightMenuClick(v);
                    }
                });
            }
        }
    }

    private void initBrocastReceiver() {
        if (receiver == null) {
            receiver = new MyMessageReceiver();
        }
        // ???????????????????????????action????????????????????????action?????????
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION); // ?????????????????????
        filter.addAction(ZhiChiConstants.chat_remind_post_msg);
        filter.addAction(ZhiChiConstants.sobot_click_cancle);
        filter.addAction(ZhiChiConstants.dcrc_comment_state);/* ???????????????????????? */
        filter.addAction(ZhiChiConstants.sobot_close_now);/* ???????????? */
        filter.addAction(ZhiChiConstants.sobot_close_now_clear_cache);// ????????????????????????
        filter.addAction(ZhiChiConstants.SOBOT_CHANNEL_STATUS_CHANGE);/* ???????????????????????? */
        // ?????????????????????
        getActivity().registerReceiver(receiver, filter);


        if (localReceiver == null) {
            localReceiver = new LocalReceiver();
        }
        localBroadcastManager = LocalBroadcastManager.getInstance(mAppContext);
        // ???????????????????????????action????????????????????????action?????????
        IntentFilter localFilter = new IntentFilter();
        localFilter.addAction(ZhiChiConstants.receiveMessageBrocast);
        // ?????????????????????
        localBroadcastManager.registerReceiver(localReceiver, localFilter);
    }

    private void initListener() {
        //?????????????????????
        mKPSwitchListener = KeyboardUtil.attach(getActivity(), mPanelRoot,
                new KeyboardUtil.OnKeyboardShowingListener() {
                    @Override
                    public void onKeyboardShowing(boolean isShowing) {
                        resetEmoticonBtn();
                        if (isShowing) {
                            lv_message.setSelection(messageAdapter.getCount());
                        }
                    }
                });

        notReadInfo.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        btn_upload_view.setOnClickListener(this);
        btn_emoticon_view.setOnClickListener(this);
        btn_model_edit.setOnClickListener(this);
        btn_model_voice.setOnClickListener(this);

        btn_set_mode_rengong.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                doClickTransferBtn();
            }
        });

        lv_message.setDropdownListScrollListener(new DropdownListView.DropdownListScrollListener() {
            @Override
            public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2, int arg3) {
                if(notReadInfo.getVisibility() == View.VISIBLE && messageList.size() > 0){
                    if (messageList.get(firstVisiableItem) != null && messageList.get(firstVisiableItem).getAnswer() != null
                            && ZhiChiConstant.sobot_remind_type_below_unread == messageList.get(firstVisiableItem).getAnswer().getRemindType()){
                        notReadInfo.setVisibility(View.GONE);
                    }
                }
            }
        });

        et_sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                resetBtnUploadAndSend();
            }
        });
        et_sendmessage.setSobotAutoCompleteListener(this);

        et_sendmessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean isFocused) {
                if (isFocused) {
                    int length = et_sendmessage.getText().toString().trim().length();
                    if (length != 0) {
                        btn_send.setVisibility(View.VISIBLE);
                        btn_upload_view.setVisibility(View.GONE);
                    }
                    //??????????????????????????????????????????
                    edittext_layout.setBackgroundResource(getResDrawableId("sobot_chatting_bottom_bg_focus"));
                }else{
                    edittext_layout.setBackgroundResource(getResDrawableId("sobot_chatting_bottom_bg_blur"));
                }
            }
        });

        et_sendmessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                resetBtnUploadAndSend();
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }

            @Override
            public void afterTextChanged(Editable arg0) { }
        });

        btn_press_to_speak.setOnTouchListener(new PressToSpeakListen());
        lv_message.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    hidePanelAndKeyboard(mPanelRoot);
                }
                return false;
            }
        });

        // ???????????????
        sobot_txt_restart_talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                initSdk(true);
            }
        });

        sobot_tv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startToPostMsgActivty(false);
            }
        });

        sobot_tv_satisfaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                submitEvaluation(true,5);
            }
        });
    }

    private void setupListView() {
        messageAdapter = new SobotMsgAdapter(getContext(), messageList,this);
        lv_message.setAdapter(messageAdapter);
        lv_message.setPullRefreshEnable(true);// ????????????????????????
        lv_message.setOnRefreshListenerHead(this);
    }

    /**
     * ????????????????????????
     */
    private void startMicAnimate(){
        mic_image_animate.setBackgroundResource(getResDrawableId("sobot_voice_animation"));
        animationDrawable = (AnimationDrawable) mic_image_animate.getBackground();
        mic_image_animate.post(new Runnable() {
            @Override
            public void run() {
                animationDrawable.start();
            }
        });
        recording_hint.setText(getResString("sobot_move_up_to_cancel"));
        recording_hint.setBackgroundResource(getResDrawableId("sobot_recording_text_hint_bg1"));
    }

    public void closeVoiceWindows(int toLongOrShort) {
        Message message = handler.obtainMessage();
        message.what = ZhiChiConstant.hander_close_voice_view;
        message.arg1 = toLongOrShort;
        handler.sendMessageDelayed(message,500);
    }

    // ???????????????1????????????????????????
    public void voiceCuttingMethod() {
        stopVoice();
        sendVoiceMap(1,voiceMsgId);
        voice_time_long.setText("00" + "''");
    }

    /**
     * ????????????
     */
    private void startVoice() {
        try {
            stopVoice();
            mFileName = mVoicePath + UUID.randomUUID().toString() + ".wav";
            String state = android.os.Environment.getExternalStorageState();
            if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
                LogUtils.i("sd???????????????");
            }
            File directory = new File(mFileName).getParentFile();
            if (!directory.exists() && !directory.mkdirs()) {
                LogUtils.i("?????????????????????");
            }
            extAudioRecorder = ExtAudioRecorder.getInstanse(false);
            extAudioRecorder.setOutputFile(mFileName);
            extAudioRecorder.prepare();
            extAudioRecorder.start(new ExtAudioRecorder.AudioRecorderListener() {
                @Override
                public void onHasPermission() {
                    startMicAnimate();
                    startVoiceTimeTask(handler);
                    sendVoiceMap(0,voiceMsgId);
                }

                @Override
                public void onNoPermission() {
                    ToastUtil.showToast(mAppContext, getResString("sobot_no_record_audio_permission"));
                }
            });
        } catch (Exception e) {
            LogUtils.i("prepare() failed");
        }
    }

    /* ???????????? */
    private void stopVoice() {
		/* ??????????????? */
        try {
            if (extAudioRecorder != null) {
                stopVoiceTimeTask();
                extAudioRecorder.stop();
                extAudioRecorder.release();
            }
        } catch (Exception e) {}
    }

    /**
     * ?????????????????????
     */
    public void startVoiceTimeTask(final Handler handler) {
        voiceTimerLong = 0;
        stopVoiceTimeTask();
        voiceTimer = new Timer();
        voiceTimerTask = new TimerTask() {
            @Override
            public void run() {
                // ???????????????:????????????
                sendVoiceTimeTask(handler);
            }
        };
        // 500ms??????????????????
        voiceTimer.schedule(voiceTimerTask, 0, 500);

    }

    /**
     * ??????????????????????????????
     *
     * @param handler
     */
    public void sendVoiceTimeTask(Handler handler) {
        Message message = handler.obtainMessage();
        message.what = ZhiChiConstant.voiceIsRecoding;
        voiceTimerLong = voiceTimerLong + 500;
        message.obj = voiceTimerLong;
        handler.sendMessage(message);
    }

    public void stopVoiceTimeTask() {
        if (voiceTimer != null) {
            voiceTimer.cancel();
            voiceTimer = null;
        }
        if (voiceTimerTask != null) {
            voiceTimerTask.cancel();
            voiceTimerTask = null;
        }
        voiceTimerLong = 0;
    }

    /**
     * ?????????????????????
     * @param type 0????????????????????????  1??????????????????2????????????????????????????????????
     * @param voiceMsgId  ????????????ID
     */
    private void sendVoiceMap(int type,String voiceMsgId) {
        // ?????????????????????
        if (type == 0) {
            sendVoiceMessageToHandler(voiceMsgId, mFileName, voiceTimeLongStr, ZhiChiConstant.MSG_SEND_STATUS_ANIM, SEND_VOICE, handler);
        }else if(type == 2){
            sendVoiceMessageToHandler(voiceMsgId, mFileName, voiceTimeLongStr, ZhiChiConstant.MSG_SEND_STATUS_ERROR, CANCEL_VOICE, handler);
        } else {
            sendVoiceMessageToHandler(voiceMsgId, mFileName, voiceTimeLongStr, ZhiChiConstant.MSG_SEND_STATUS_LOADING, UPDATE_VOICE, handler);
            // ??????http ???????????????????????????
            sendVoice(voiceMsgId, voiceTimeLongStr, initModel.getCid(), initModel.getUid(), mFileName, handler);
            lv_message.setSelection(messageAdapter.getCount());
        }
        gotoLastItem();
    }

    /**
     * ??????????????????
     */
    private void loadUnreadNum(){
        mUnreadNum = SobotMsgManager.getInstance(mAppContext).getUnreadCount(info.getAppkey(),true,info.getUid());
    }

    /**
     * ?????????sdk
     * @param isReConnect ?????????????????????
     **/
    private void initSdk(boolean isReConnect) {
        if(isReConnect){
            current_client_model = ZhiChiConstant.client_model_robot;
            showTimeVisiableCustomBtn = 0;
            messageList.clear();
            messageAdapter.notifyDataSetChanged();
            cids.clear();
            currentCidPosition = 0;
            queryCidsStatus = ZhiChiConstant.QUERY_CIDS_STATUS_INITIAL;
            isNoMoreHistoryMsg = false;
            isAboveZero = false;
            isComment = false;// ????????????????????? ????????? ???????????????
            customerState = CustomerState.Offline;
            remindRobotMessageTimes = 0;
            queueTimes = 0;
            isSessionOver = false;

            sobot_txt_restart_talk.setVisibility(View.GONE);
            sobot_tv_message.setVisibility(View.GONE);
            sobot_tv_satisfaction.setVisibility(View.GONE);
            image_reLoading.setVisibility(View.VISIBLE);
            AnimationUtil.rotate(image_reLoading);

            lv_message.setPullRefreshEnable(true);// ????????????????????????

            String last_current_dreceptionistId = SharedPreferencesUtil.getStringData(
                    mAppContext,info.getAppkey()+"_"+ZhiChiConstant.SOBOT_RECEPTIONISTID,"");
            info.setReceptionistId(last_current_dreceptionistId);
            resetUser();
        } else {
            //?????????????????????????????????
            if(ChatUtils.checkConfigChange(mAppContext,info.getAppkey(),info)){
                resetUser();
            } else {
                doKeepsessionInit();
            }
        }
    }

    /**
     * ????????????
     */
    private void resetUser(){
        if (!SobotVerControl.isPlatformVer) {
            zhiChiApi.disconnChannel();
        }
        clearCache();
        SharedPreferencesUtil.saveStringData(mAppContext,
                info.getAppkey()+"_"+ZhiChiConstant.sobot_last_login_group_id, TextUtils.isEmpty(info.getSkillSetId())?"":info.getSkillSetId());
        customerInit();
    }

    /**
     * ?????????????????????
     */
    private void customerInit() {

        if (info.getInitModeType() == ZhiChiConstant.type_robot_only){
            ChatUtils.userLogout(mAppContext);
        }

        zhiChiApi.sobotInit(SobotChatFragment.this,info, new StringResultCallBack<ZhiChiInitModeBase>() {
            @Override
            public void onSuccess(ZhiChiInitModeBase result) {
                if (!isActive()) {
                    return;
                }
                initModel = result;
                getAnnouncement();
                if(info.getInitModeType() > 0){
                    initModel.setType(info.getInitModeType()+"");
                }
                type = Integer.parseInt(initModel.getType());
                SharedPreferencesUtil.saveIntData(mAppContext,
                        info.getAppkey()+"_"+ZhiChiConstant.initType, type);
                //???????????????cid
                queryCids();

                //????????????layout,???????????????????????????????????????UI????????????
                showRobotLayout();

                if (!TextUtils.isEmpty(initModel.getUid())){
                    SharedPreferencesUtil.saveStringData(mAppContext, Const.SOBOT_CID,initModel.getUid());
                }
                SharedPreferencesUtil.saveIntData(mAppContext,
                        ZhiChiConstant.sobot_msg_flag, initModel.getMsgFlag());
                SharedPreferencesUtil.saveStringData(mAppContext,
                        "lastCid",initModel.getCid());
                SharedPreferencesUtil.saveStringData(mAppContext,
                        info.getAppkey()+"_"+ZhiChiConstant.sobot_last_current_partnerId,info.getUid());
                SharedPreferencesUtil.saveStringData(mAppContext,
                        ZhiChiConstant.sobot_last_current_appkey,info.getAppkey());

                SharedPreferencesUtil.saveStringData(mAppContext,info.getAppkey()+"_"+ZhiChiConstant.SOBOT_RECEPTIONISTID, TextUtils.isEmpty(info.getReceptionistId())?"":info.getReceptionistId());
                SharedPreferencesUtil.saveStringData(mAppContext,info.getAppkey()+"_"+ZhiChiConstant.SOBOT_ROBOT_CODE, TextUtils.isEmpty(info.getRobotCode())?"":info.getRobotCode());
                SharedPreferencesUtil.saveBooleanData(mAppContext,ZhiChiConstant.SOBOT_POSTMSG_TELSHOWFLAG,initModel.isTelShowFlag());
                SharedPreferencesUtil.saveBooleanData(mAppContext,ZhiChiConstant.SOBOT_POSTMSG_TELFLAG,initModel.isTelFlag());
                SharedPreferencesUtil.saveBooleanData(mAppContext,ZhiChiConstant.SOBOT_POSTMSG_EMAILFLAG,initModel.isEmailFlag());
                SharedPreferencesUtil.saveBooleanData(mAppContext,ZhiChiConstant.SOBOT_POSTMSG_EMAILSHOWFLAG,initModel.isEmailShowFlag());
                SharedPreferencesUtil.saveBooleanData(mAppContext,ZhiChiConstant.SOBOT_POSTMSG_ENCLOSURESHOWFLAG,initModel.isEnclosureShowFlag());
                SharedPreferencesUtil.saveBooleanData(mAppContext,ZhiChiConstant.SOBOT_POSTMSG_ENCLOSUREFLAG,initModel.isEnclosureFlag());

                initModel.setColor(info.getColor());

                if (type == ZhiChiConstant.type_robot_only){
                    remindRobotMessage(handler,initModel,info);
                } else if (type == ZhiChiConstant.type_robot_first) {
                    //???????????????
                    if(initModel.getUstatus() == ZhiChiConstant.ustatus_online || initModel.getUstatus() == ZhiChiConstant.ustatus_queue){
                        //??????????????? ???????????????  ????????????????????????
                        if(initModel.getUstatus() == ZhiChiConstant.ustatus_queue){
                            remindRobotMessage(handler,initModel,info);
                        }
                        //?????????????????????
                        connectCustomerService("","");
                    }else{
                        //?????????????????????????????????????????????????????????
                        remindRobotMessage(handler,initModel,info);
                    }
                } else {
                    if (type == ZhiChiConstant.type_custom_only) {
                        //???????????????
                        if (isUserBlack()) {
                            showLeaveMsg();
                        } else {
                            transfer2Custom();
                        }
                    } else if (type == ZhiChiConstant.type_custom_first) {
                        //????????????
                        transfer2Custom();
                    }
                }
                isSessionOver = false;
            }

            @Override
            public void onFailure(Exception e, String des) {
                if (!isActive()) {
                    return;
                }
                if(e instanceof IllegalArgumentException){
                    if(LogUtils.isDebug){
                        ToastUtil.showLongToast(mAppContext,des);
                    }
                    finish();
                }else{
                    showInitError();
                }
                isSessionOver=true;
            }
        });
    }

    /**
     * ??????????????????????????????
     */
    private void doKeepsessionInit(){
        List<ZhiChiMessageBase> tmpList = SobotMsgManager.getInstance(mAppContext).getConfig(info.getAppkey()).getMessageList();
        if(tmpList != null && SobotMsgManager.getInstance(mAppContext).getConfig(info.getAppkey()).getInitModel() != null){
            //?????????
            int lastType =  SharedPreferencesUtil.getIntData(mAppContext,
                    info.getAppkey()+"_"+ZhiChiConstant.initType, -1);
            if(info.getInitModeType() < 0 || lastType == info.getInitModeType()){
                if(!TextUtils.isEmpty(info.getSkillSetId())){
                    //?????????????????????????????????
                    String lastUseGroupId = SharedPreferencesUtil.getStringData(mAppContext, info.getAppkey()+"_"+ZhiChiConstant.sobot_last_login_group_id, "");
                    if(lastUseGroupId.equals(info.getSkillSetId())){
                        keepSession(tmpList);
                    } else {
                        resetUser();
                    }
                }else{
                    keepSession(tmpList);
                }
            }else{
                resetUser();
            }
        } else {
            resetUser();
        }
    }

    /**
     * ?????????????????????
     * @param initModel
     * @param outLineType  ???????????????
     */
    public void customerServiceOffline(ZhiChiInitModeBase initModel, int outLineType) {
        if(initModel == null){
            return;
        }
        queueNum = 0;
        stopInputListener();
        stopUserInfoTimeTask();
        stopCustomTimeTask();
        customerState = CustomerState.Offline;

        // ????????????
        showOutlineTip(initModel,outLineType);
        //??????????????????
        setBottomView(ZhiChiConstant.bottomViewtype_outline);
        mBottomViewtype = ZhiChiConstant.bottomViewtype_outline;

        if(Integer.parseInt(initModel.getType()) == ZhiChiConstant.type_custom_only) {
            if(1 == outLineType){
                //?????????????????? ????????????????????????????????????
                showLogicTitle(getResString("sobot_no_access"),false);
            }
        }

        if (6 == outLineType) {
            LogUtils.i("???????????????");
        }
        isSessionOver = true;
        // ???????????????????????????
        CommonUtils.sendLocalBroadcast(mAppContext,new Intent(Const.SOBOT_CHAT_USER_OUTLINE));
    }

    /**
     * ??????????????????
     * @param initModel
     * @param outLineType ????????????
     */
    private void showOutlineTip(ZhiChiInitModeBase initModel,int outLineType){
        ZhiChiMessageBase base = new ZhiChiMessageBase();
        ZhiChiReplyAnswer reply = new ZhiChiReplyAnswer();
        base.setSenderType(ZhiChiConstant.message_sender_type_remide_info + "");
        reply.setMsg(ChatUtils.getMessageContentByOutLineType(mAppContext, initModel, outLineType));
        reply.setRemindType(ZhiChiConstant.sobot_remind_type_outline);
        base.setAnswer(reply);
        if (1 == outLineType) {
            base.setAction(ZhiChiConstant.sobot_outline_leverByManager);
        } else if (2 == outLineType) {
            base.setAction(ZhiChiConstant.sobot_outline_leverByManager);
        } else if (3 == outLineType) {
            base.setAction(ZhiChiConstant.sobot_outline_leverByManager);
            if(initModel != null){
                initModel.setIsblack("1");
            }
        } else if (4 == outLineType) {
            base.setAction(ZhiChiConstant.action_remind_past_time);
        } else if (6 == outLineType) {
            base.setAction(ZhiChiConstant.sobot_outline_leverByManager);
        }
        // ??????????????????
        updateUiMessage(messageAdapter, base);
    }

    /**
     * ??????????????????
     */
    private void showInLineHint(){
        // ?????????????????????
        updateUiMessage(messageAdapter, ChatUtils.getInLineHint(mAppContext,queueNum));
        gotoLastItem();
    }

    //????????????
    private void keepSession(List<ZhiChiMessageBase> tmpList) {
        ZhiChiConfig config = SobotMsgManager.getInstance(mAppContext).getConfig(info.getAppkey());
        initModel =  config.getInitModel();
        updateFloatUnreadIcon();
        mUnreadNum = 0;
        messageAdapter.addData(tmpList);
        messageAdapter.notifyDataSetChanged();
        current_client_model = config.current_client_model;
        type = Integer.parseInt(initModel.getType());
        SharedPreferencesUtil.saveIntData(mAppContext,
                info.getAppkey()+"_"+ZhiChiConstant.initType, type);
        LogUtils.i("sobot----type---->" + type);
        initModel.setColor(info.getColor());
        showLogicTitle(config.activityTitle,false);
        customerState = config.customerState;
        remindRobotMessageTimes = config.remindRobotMessageTimes;
        isComment = config.isComment;
        isAboveZero = config.isAboveZero;
        currentUserName=config.currentUserName;
        isNoMoreHistoryMsg=config.isNoMoreHistoryMsg;
        currentCidPosition = config.currentCidPosition ;
        queryCidsStatus = config.queryCidsStatus ;
        if(config.cids != null ){
            cids.addAll(config.cids);
        }
        showTimeVisiableCustomBtn = config.showTimeVisiableCustomBtn;
        queueNum = config.queueNum;
        if(isNoMoreHistoryMsg){
            lv_message.setPullRefreshEnable(false);// ????????????????????????
        }
        setAdminFace(config.adminFace);
        mBottomViewtype = config.bottomViewtype;
        setBottomView(config.bottomViewtype);
        if(config.userInfoTimeTask){
            stopUserInfoTimeTask();
            startUserInfoTimeTask(handler);
        }
        if(config.customTimeTask){
            stopCustomTimeTask();
            startCustomTimeTask(handler);
        }
        //????????????????????????
        et_sendmessage.setRequestParams(initModel.getUid(),initModel.getCurrentRobotFlag());
        if(customerState == CustomerState.Online &&current_client_model == ZhiChiConstant.client_model_customService){
            createConsultingContent();
            //????????????????????????????????????
            et_sendmessage.setAutoCompleteEnable(false);
        } else {
            //?????????????????????????????????
            et_sendmessage.setAutoCompleteEnable(true);
        }
        lv_message.setSelection(messageAdapter.getCount());
        getAnnouncement();
        config.clearMessageList();
        config.clearInitModel();
        isSessionOver = false;
    }

    /**
     * ?????????????????????????????????????????????????????????????????????
     */
    private void showTransferCustomer(){
        showTimeVisiableCustomBtn++;
        if (showTimeVisiableCustomBtn >= info.getArtificialIntelligenceNum()){
            btn_set_mode_rengong.setVisibility(View.VISIBLE);
        }
    }

    /**
     * ??????????????????????????????id ???????????????
     */
    private void transfer2CustomBySkillId(){
        requestQueryFrom(info.getSkillSetId(),info.getSkillSetName());
    }

    /**
     * ??????????????????   ????????????????????????????????????????????????
     */
    private void showEmotionBtn(){
        Map<String, Integer> mapAll = DisplayRules.getMapAll(mAppContext);
        if(mapAll.size() > 0){
            btn_emoticon_view.setVisibility(View.VISIBLE);
        } else {
            btn_emoticon_view.setVisibility(View.GONE);
        }
    }

    /**
     * ??????????????????????????????
     * ?????????????????????skillId ??????????????????id???????????????
     * ???????????????  ??????????????????????????????????????????
     */
    private void transfer2Custom(){
        if(isUserBlack()){
            connectCustomerService("","");
        } else if (!TextUtils.isEmpty(info.getSkillSetId())){
            //????????????????????????
            transfer2CustomBySkillId();
        } else {
            if (initModel.getGroupflag().equals(
                    ZhiChiConstant.groupflag_on) && TextUtils.isEmpty(info.getReceptionistId())) {
                //??????????????????????????????????????????????????????????????????????????????
                getGroupInfo();
            } else {
                //???????????????????????????????????????  ???????????????
                requestQueryFrom("","");
            }
        }
    }

    /**
     * ???????????????
     */
    private void getGroupInfo() {
        zhiChiApi.getGroupList(SobotChatFragment.this,info.getAppkey(),initModel.getUid(), new StringResultCallBack<ZhiChiGroup>() {
            @Override
            public void onSuccess(ZhiChiGroup zhiChiGroup) {
                if (!isActive()) {
                    return;
                }
                boolean hasOnlineCustom = false;
                if (ZhiChiConstant.groupList_ustatus_time_out.equals(zhiChiGroup.getUstatus())){
                    customerServiceOffline(initModel,4);
                } else {
                    list_group = zhiChiGroup.getData();
                    if (list_group != null && list_group.size() > 0) {
                        for (int i = 0; i < list_group.size(); i++) {
                            if ("true".equals(list_group.get(i).isOnline())) {
                                hasOnlineCustom = true;
                                break;
                            }
                        }
                        if (hasOnlineCustom) {
                            if (list_group.size() >= 2) {
                                if (initModel.getUstatus() == ZhiChiConstant.ustatus_online || initModel.getUstatus() == ZhiChiConstant.ustatus_queue) {
                                    // ???????????????????????????
                                    connectCustomerService("","");
                                } else {
                                    if (!TextUtils.isEmpty(info.getSkillSetId())) {
                                        //???????????????
                                        transfer2CustomBySkillId();
                                    } else {
                                        Intent intent = new Intent(mAppContext, SobotSkillGroupActivity.class);
                                        intent.putExtra("grouplist", (Serializable) list_group);
                                        intent.putExtra("uid", initModel.getUid());
                                        intent.putExtra("type", type);
                                        intent.putExtra("appkey", info.getAppkey());
                                        intent.putExtra("companyId", initModel.getCompanyId());
                                        intent.putExtra("msgTmp", initModel.getMsgTmp());
                                        intent.putExtra("msgTxt", initModel.getMsgTxt());
                                        intent.putExtra("msgFlag", initModel.getMsgFlag());
                                        startActivityForResult(intent, ZhiChiConstant.REQUEST_COCE_TO_GRROUP);
                                    }
                                }
                            } else {
                                //?????????????????????
                                requestQueryFrom(list_group.get(0).getGroupId(),list_group.get(0).getGroupName());
                            }
                        } else {
                            //???????????????????????????
                            connCustomerServiceFail();
                        }
                    } else {
                        //?????????????????????
                        requestQueryFrom("","");
                    }
                }
            }

            @Override
            public void onFailure(Exception e, String des) {
                if (!isActive()) {
                    return;
                }
                ToastUtil.showToast(mAppContext,des);
            }
        });
    }

    /**
     * ???????????????
     */
    private void connCustomerServiceFail(){
        if (type == 2) {
            showLeaveMsg();
        } else {
            showLogicTitle(initModel.getRobotName(),false);
            showCustomerOfflineTip();
            if (type == ZhiChiConstant.type_custom_first && current_client_model ==
                    ZhiChiConstant.client_model_robot) {
                remindRobotMessage(handler,initModel,info);
            }
        }
        gotoLastItem();
    }

    /**
     * ????????? ??????????????????
     */
    private void connCustomerServiceBlack() {
        showLogicTitle(initModel.getRobotName(),false);
        showCustomerUanbleTip();
        if (type == ZhiChiConstant.type_custom_first) {
            remindRobotMessage(handler,initModel,info);
        }
    }

    /**
     * ????????????????????????
     */
    private void showRobotLayout(){
        if (initModel != null) {
            if (type == 1) {
                //????????????
                setBottomView(ZhiChiConstant.bottomViewtype_onlyrobot);
                mBottomViewtype = ZhiChiConstant.bottomViewtype_onlyrobot;
                showLogicTitle(initModel.getRobotName(),false);
            } else if (type == 3 || type == 4) {
                //???????????????
                setBottomView(ZhiChiConstant.bottomViewtype_robot);
                mBottomViewtype = ZhiChiConstant.bottomViewtype_robot;
                showLogicTitle(initModel.getRobotName(),false);
            }
            //???????????????????????????????????????
            if (type != ZhiChiConstant.type_custom_only) {
                //?????????????????????????????????????????????????????????
                et_sendmessage.setRequestParams(initModel.getUid(),initModel.getCurrentRobotFlag());
                et_sendmessage.setAutoCompleteEnable(true);
            }
        }

    }

    /**
     * ???????????????
     */
    protected void connectCustomerService(String groupId,String groupName) {
        if(isConnCustomerService){
            return;
        }
        isConnCustomerService = true;
        boolean currentFlag = (customerState == CustomerState.Queuing || customerState == CustomerState.Online);
        zhiChiApi.connnect(SobotChatFragment.this,info.getReceptionistId(),info.getTranReceptionistFlag(),initModel.getUid(), initModel.getCid(), groupId, groupName,currentFlag,
                new StringResultCallBack<ZhiChiMessageBase>() {
                    @Override
                    public void onSuccess(ZhiChiMessageBase zhichiMessageBase) {
                        isConnCustomerService = false;
                        if (!isActive()) {
                            return;
                        }
                        int status = Integer.parseInt(zhichiMessageBase.getStatus());
                        setAdminFace(zhichiMessageBase.getAface());
                        LogUtils.i("status---:" + status);
                        if (status != 0) {
                            if (status == ZhiChiConstant.transfer_robot_customServeive){
                                //??????????????????????????????
                                customerServiceOffline(initModel,4);
                            } else if (status == ZhiChiConstant.transfer_robot_custom_status){
                                //???????????????????????????id???????????????????????????????????????????????????status=6.?????????????????????receptionistId???null
                                //???null?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                                showLogicTitle(initModel.getRobotName(),false);
                                info.setReceptionistId(null);
                                transfer2Custom();
                            } else {
                                if (ZhiChiConstant.transfer_customeServeive_success == status) {
                                    connCustomerServiceSuccess(zhichiMessageBase);
                                } else if (ZhiChiConstant.transfer_customeServeive_fail == status) {
                                    connCustomerServiceFail();
                                } else if (ZhiChiConstant.transfer_customeServeive_isBalk == status) {
                                    connCustomerServiceBlack();
                                } else if (ZhiChiConstant.transfer_customeServeive_already == status) {
                                    connCustomerServiceSuccess(zhichiMessageBase);
                                } else if (ZhiChiConstant.transfer_robot_custom_max_status == status){
                                    if (type == 2){
                                        showLogicTitle(getResString("sobot_wait_full"),true);
                                        setBottomView(ZhiChiConstant.bottomViewtype_custom_only_msgclose);
                                        mBottomViewtype = ZhiChiConstant.bottomViewtype_custom_only_msgclose;
                                    }

                                    if (initModel.getMsgFlag() == ZhiChiConstant.sobot_msg_flag_open){
                                        if (!TextUtils.isEmpty(zhichiMessageBase.getMsg())){
                                            ToastUtil.showLongToast(mAppContext,zhichiMessageBase.getMsg());
                                        } else {
                                            ToastUtil.showLongToast(mAppContext,"??????,??????????????????,????????????,?????????????????????????????????????????????????????????~");
                                        }
                                        startToPostMsgActivty(false);
                                    }
                                }
                            }
                        } else {
                            LogUtils.i("?????????--??????");
                            //????????????
                            zhiChiApi.connChannel(zhichiMessageBase.getWslinkBak(),
                                    zhichiMessageBase.getWslinkDefault(),initModel.getUid(),zhichiMessageBase.getPuid(),info.getAppkey(),zhichiMessageBase.getWayHttp());
                            customerState = CustomerState.Queuing;
                            createCustomerQueue(zhichiMessageBase.getCount()+"",status);
                        }
                    }

                    @Override
                    public void onFailure(Exception e, String des) {
                        isConnCustomerService = false;
                        if (!isActive()) {
                            return;
                        }
                        if(type == 2){
                            setBottomView(ZhiChiConstant.bottomViewtype_custom_only_msgclose);
                            showLogicTitle(getResString("sobot_no_access"),false);
                            isSessionOver=true;
                        }
                        ToastUtil.showToast(mAppContext,des);
                    }
                });
    }

    private void gotoLastItem(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                lv_message.setSelection(messageAdapter.getCount());
            }
        });
    }

    private void deleteUnReadUi(){
        //?????????????????????????????????
        for (int i = messageList.size() - 1; i >= 0; i--) {
            if (messageList.get(i).getAnswer() != null && ZhiChiConstant
                    .sobot_remind_type_below_unread == messageList.get(i).getAnswer().getRemindType()) {
                messageList.remove(i);
                break;
            }
        }
    }

    /**
     * ????????????????????????????????????UI  ???XX??????????????????
     */
    private void updateFloatUnreadIcon(){
        if (mUnreadNum >= 10){
            notReadInfo.setVisibility(View.VISIBLE);
            notReadInfo.setText(mUnreadNum + getResString("sobot_new_msg"));
        } else {
            notReadInfo.setVisibility(View.GONE);
        }
    }

    /**
     * ????????????????????????
     */
    private void connCustomerServiceSuccess(ZhiChiMessageBase base){
        if (base == null || initModel == null){
            return;
        }
        initModel.setAdminHelloWord(!TextUtils.isEmpty(base.getAdminHelloWord())?base.getAdminHelloWord():initModel.getAdminHelloWord());
        initModel.setAdminTipTime(!TextUtils.isEmpty(base.getServiceOutTime())?base.getServiceOutTime():initModel.getAdminTipTime());
        initModel.setAdminTipWord(!TextUtils.isEmpty(base.getServiceOutDoc())?base.getServiceOutDoc():initModel.getAdminTipWord());

        //????????????
        zhiChiApi.connChannel(base.getWslinkBak(), base.getWslinkDefault(),initModel.getUid(),
                base.getPuid(),info.getAppkey(),base.getWayHttp());
        createCustomerService(base.getAname(),base.getAface());
    }

    /**
     * ????????????????????????
     * @param name ???????????????
     * @param face  ???????????????
     */
    private void createCustomerService(String name,String face){
        //????????????
        current_client_model = ZhiChiConstant.client_model_customService;
        customerState = CustomerState.Online;
        isAboveZero = false;
        isComment = false;// ???????????? ????????? ?????????
        queueNum = 0;
        currentUserName = TextUtils.isEmpty(name)?"":name;
        //?????????xx????????????
        updateUiMessage(messageAdapter, ChatUtils.getServiceAcceptTip(mAppContext,name));

        String adminHelloWord = SharedPreferencesUtil.getStringData(mAppContext,ZhiChiConstant.SOBOT_CUSTOMADMINHELLOWORD,"");
        //?????????????????????
        if (!TextUtils.isEmpty(adminHelloWord)){
            updateUiMessage(messageAdapter, ChatUtils.getServiceHelloTip(name,face,adminHelloWord));
        } else {
            updateUiMessage(messageAdapter, ChatUtils.getServiceHelloTip(name,face,initModel.getAdminHelloWord()));
        }

        //????????????
        showLogicTitle(name,false);
        //??????????????????
        createConsultingContent();
        gotoLastItem();
        //??????????????????
        setBottomView(ZhiChiConstant.bottomViewtype_customer);
        mBottomViewtype = ZhiChiConstant.bottomViewtype_customer;

        // ??????????????????
        restartInputListener();
        stopUserInfoTimeTask();
        is_startCustomTimerTask = false;
        startUserInfoTimeTask(handler);
        hideItemTransferBtn();
        //????????????????????????
        et_sendmessage.setAutoCompleteEnable(false);
    }

    /**
     * ?????????????????????????????????
     */
    public void hideItemTransferBtn(){
        if (!isActive()) {
            return;
        }
        // ???????????????????????????????????????????????????
        lv_message.post(new Runnable() {

            @Override
            public void run() {

                for (int i = 0, count = lv_message.getChildCount(); i < count; i++) {
                    View child = lv_message.getChildAt(i);
                    if (child == null || child.getTag() == null || !(child.getTag() instanceof RichTextMessageHolder)) {
                        continue;
                    }
                    RichTextMessageHolder holder = (RichTextMessageHolder) child.getTag();
                    holder.hideTransferBtn();
                }
            }
        });
    }

    /**
     * ??????????????????????????????
     */
    private void showCustomerOfflineTip(){
        ZhiChiReplyAnswer reply = new ZhiChiReplyAnswer();
        reply.setMsgType(null);
        String adminNoneLineTitle = SharedPreferencesUtil.getStringData(mAppContext,ZhiChiConstant.SOBOT_CUSTOMADMINNONELINETITLE,"");
        if (!TextUtils.isEmpty(adminNoneLineTitle)){
            reply.setMsg(adminNoneLineTitle);
        } else {
            reply.setMsg(initModel.getAdminNonelineTitle());
        }
        reply.setRemindType(ZhiChiConstant.sobot_remind_type_customer_offline);
        ZhiChiMessageBase base = new ZhiChiMessageBase();
        base.setSenderType(ZhiChiConstant.message_sender_type_remide_info + "");
        base.setAnswer(reply);
        base.setAction(ZhiChiConstant.action_remind_info_post_msg);
        updateUiMessage(messageAdapter, base);
    }

    /**
     * ????????????????????????
     */
    private void showCustomerUanbleTip(){
        ZhiChiReplyAnswer reply = new ZhiChiReplyAnswer();
        reply.setMsgType(null);
        reply.setMsg(getResString("sobot_unable_transfer_to_customer_service"));
        reply.setRemindType(ZhiChiConstant.sobot_remind_type_unable_to_customer);
        ZhiChiMessageBase base = new ZhiChiMessageBase();
        base.setSenderType(ZhiChiConstant.message_sender_type_remide_info + "");
        base.setAnswer(reply);
        base.setAction(ZhiChiConstant.action_remind_info_post_msg);
        updateUiMessage(messageAdapter, base);
    }

    /**
     * ??????????????????????????????
     * ???????????????????????????
     * @param num		?????????????????????
     * @param status	??????????????????????????????????????????7???????????????????????????????????????????????????????????????
     */
    private void createCustomerQueue(String num, int status){
        if (customerState == CustomerState.Queuing && !TextUtils.isEmpty(num)
                && Integer.parseInt(num) > 0) {
            stopUserInfoTimeTask();
            stopCustomTimeTask();
            stopInputListener();

            queueNum = Integer.parseInt(num);
            //???????????????????????????
            if (status != ZhiChiConstant.transfer_robot_custom_max_status){
                showInLineHint();
            }

            if (type == ZhiChiConstant.type_custom_only) {
                showLogicTitle(getResString("sobot_in_line_title"),false);
                setBottomView(ZhiChiConstant.bottomViewtype_onlycustomer_paidui);
                mBottomViewtype = ZhiChiConstant.bottomViewtype_onlycustomer_paidui;
            } else {
                showLogicTitle(initModel.getRobotName(),false);
                setBottomView(ZhiChiConstant.bottomViewtype_paidui);
                mBottomViewtype = ZhiChiConstant.bottomViewtype_paidui;
            }

            queueTimes = queueTimes + 1;
            if (type == ZhiChiConstant.type_custom_first) {
                if (queueTimes == 1) {
                    //?????????????????????????????????????????????????????????
                    remindRobotMessage(handler,initModel,info);
                }
            }
        }
    }

    /**
     * ???????????????cid?????????
     */
    private void queryCids() {
        //??????initmodel ??????  querycid?????????????????????????????????????????????????????????????????????
        if(initModel == null || queryCidsStatus == ZhiChiConstant.QUERY_CIDS_STATUS_LOADING
                || queryCidsStatus == ZhiChiConstant.QUERY_CIDS_STATUS_SUCCESS){
            return;
        }
        long time = SharedPreferencesUtil.getLongData(mAppContext, ZhiChiConstant.SOBOT_CHAT_HIDE_HISTORYMSG_TIME, 0);
        queryCidsStatus = ZhiChiConstant.QUERY_CIDS_STATUS_LOADING;
        // ???????????????cid?????????
        zhiChiApi.queryCids(SobotChatFragment.this,initModel.getUid(),time, new StringResultCallBack<ZhiChiCidsModel>() {

            @Override
            public void onSuccess(ZhiChiCidsModel data) {
                if (!isActive()) {
                    return;
                }
                queryCidsStatus = ZhiChiConstant.QUERY_CIDS_STATUS_SUCCESS;
                cids = data.getCids();
                if(cids != null){
                    boolean hasRepeat = false;
                    for (int i = 0; i < cids.size(); i++) {
                        if(cids.get(i).equals(initModel.getCid())){
                            hasRepeat = true;
                            break;
                        }
                    }
                    if(!hasRepeat){
                        cids.add(initModel.getCid());
                    }
                    Collections.reverse(cids);
                }
                //??????????????????
                getHistoryMessage(true);
            }

            @Override
            public void onFailure(Exception e, String des) {
                queryCidsStatus = ZhiChiConstant.QUERY_CIDS_STATUS_FAILURE;
            }
        });
    }

    private void showInitError(){
        setTitle(getResString("sobot_prompt"));
        loading_anim_view.setVisibility(View.GONE);
        txt_loading.setVisibility(View.GONE);
        textReConnect.setVisibility(View.VISIBLE);
        icon_nonet.setVisibility(View.VISIBLE);
        btn_reconnect.setVisibility(View.VISIBLE);
        et_sendmessage.setVisibility(View.GONE);
        relative.setVisibility(View.GONE);
        welcome.setVisibility(View.VISIBLE);
    }

    /*
	 * ??????????????????
	 *
	 */
    @Override
    public void sendConsultingContent(){
        if(customerState == CustomerState.Online && current_client_model == ZhiChiConstant
                .client_model_customService){
            final String title = info.getConsultingContent().getSobotGoodsTitle().trim();
            final String describe = TextUtils.isEmpty(info.getConsultingContent().getSobotGoodsDescribe())?"":info.getConsultingContent().getSobotGoodsDescribe().trim();
            final String lable = TextUtils.isEmpty(info.getConsultingContent().getSobotGoodsLable())?"":info.getConsultingContent().getSobotGoodsLable().trim();
            final String fromUrl = info.getConsultingContent().getSobotGoodsFromUrl().trim();
            if (!TextUtils.isEmpty(fromUrl) && !TextUtils.isEmpty(title)) {
                String content = getResString("sobot_consulting_title") + title + "\n"+
                        (!TextUtils.isEmpty(describe)?getResString("sobot_consulting_describe") + describe + "\n":"")
                        +(!TextUtils.isEmpty(lable)?getResString("sobot_consulting_lable") + lable + "\n":"")
                        + getResString("sobot_consulting_fromurl") + fromUrl;
                sendMsg(content);
            }
        }
    }

    /**
     *
     * @param base
     * @param type
     * @param questionFlag
     * 			0 ????????????????????????
     * 		    1 ??????docId?????????
     * 		    2 ???????????????
     * @param docId ????????????Null
     */
    @Override
    public void sendMessageToRobot(ZhiChiMessageBase base,int type, int questionFlag, String docId){
        sendMessageToRobot(base,type, questionFlag, docId, null);
    }

    /*??????0?????????????????? 1?????????  2?????????  3?????????*/
    @Override
    public void sendMessageToRobot(ZhiChiMessageBase base,int type, int questionFlag, String docId, String multiRoundMsg){
        if (type == 4){
            sendMsgToRobot(base, SEND_TEXT, questionFlag, docId, multiRoundMsg);
        }

		/*????????????*/
        else if(type == 3){
            // ???????????????url ???????????? ???????????????????????????
            messageAdapter.updatePicStatusById(base.getId(), base.getSendSuccessState());
            messageAdapter.notifyDataSetChanged();
            ChatUtils.sendPicture(mAppContext,initModel.getCid(), initModel.getUid(),
                    base.getContent(), handler, base.getId(), lv_message,messageAdapter);
        }

		/*????????????*/
        else if (type == 2){
            // ?????????????????????
            sendVoiceMessageToHandler(base.getId(),  base.getContent(), base.getAnswer()
                    .getDuration(), ZhiChiConstant.MSG_SEND_STATUS_LOADING, UPDATE_VOICE, handler);
            sendVoice(base.getId(), base.getAnswer().getDuration(), initModel.getCid(),
                    initModel.getUid(), base.getContent(), handler);
        }

		/*????????????*/
        else if (type == 1){
            // ???????????????
            sendMsgToRobot(base, UPDATE_TEXT, questionFlag, docId);
        }

		/*???????????????*/
        else if (type == 0){

            if(!isSessionOver){
                // ???????????????
                ZhiChiReplyAnswer answer = new ZhiChiReplyAnswer();
                answer.setMsgType(ZhiChiConstant.message_type_text + "");
                answer.setMsg(base.getContent());
                base.setAnswer(answer);
                base.setSenderType(ZhiChiConstant.message_sender_type_customer + "");
                if (base.getId() == null || TextUtils.isEmpty(base.getId())) {
                    updateUiMessage(messageAdapter, base);
                }
                sendMessageWithLogic(base.getId(), base.getContent(), initModel, handler, current_client_model,  questionFlag, docId);
            }else{
                showOutlineTip(initModel,1);
            }
        }
        gotoLastItem();
    }

    /**
     * ????????????????????????
     */
    @Override
    public void doClickTransferBtn() {
        //???????????????
        hidePanelAndKeyboard(mPanelRoot);
        doEmoticonBtn2Blur();
        transfer2Custom();
    }

    // ???????????????????????????
    @Override
    public void clickAudioItem(ZhiChiMessageBase message) {
        if(mAudioPlayPresenter == null){
            mAudioPlayPresenter = new AudioPlayPresenter(mAppContext);
        }
        if (mAudioPlayCallBack == null) {
            mAudioPlayCallBack = new AudioPlayCallBack() {
                @Override
                public void onPlayStart(ZhiChiMessageBase mCurrentMsg) {
                    showVoiceAnim(mCurrentMsg,true);
                }

                @Override
                public void onPlayEnd(ZhiChiMessageBase mCurrentMsg) {
                    showVoiceAnim(mCurrentMsg,false);
                }
            };
        }
        mAudioPlayPresenter.clickAudio(message,mAudioPlayCallBack);
    }

    public void showVoiceAnim(final ZhiChiMessageBase info, final boolean isShow) {
        if (!isActive()) {
            return;
        }
        lv_message.post(new Runnable() {

            @Override
            public void run() {
                if (info == null) {
                    return;
                }
                for (int i = 0, count = lv_message.getChildCount(); i < count; i++) {
                    View child = lv_message.getChildAt(i);
                    if (child == null || child.getTag() == null || !(child.getTag() instanceof VoiceMessageHolder)) {
                        continue;
                    }
                    VoiceMessageHolder holder = (VoiceMessageHolder) child.getTag();
                    holder.stopAnim();
                    if (holder.message == info) {
                        if (isShow) {
                            holder.startAnim();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void hidePanelAndKeyboard(){
        hidePanelAndKeyboard(mPanelRoot);
    }

    /**
     * ??????????????????
     * @param revaluateFlag true ???  false ???
     * @param message ???????????? model
     */
    @Override
    public void doRevaluate(final boolean revaluateFlag,final ZhiChiMessageBase message){
        if(isSessionOver){
            showOutlineTip(initModel,1);
            return;
        }
        zhiChiApi.rbAnswerComment(SobotChatFragment.this,initModel.getUid(), initModel.getCid(), initModel.getCurrentRobotFlag(),
                message.getDocId(), message.getDocName(), revaluateFlag, new StringResultCallBack<CommonModelBase>() {
                    @Override
                    public void onSuccess(CommonModelBase data) {
                        if (!isActive()) {
                            return;
                        }
                        if (ZhiChiConstant.client_sendmsg_to_custom_fali.equals(data.getStatus())) {
                            customerServiceOffline(initModel,1);
                        } else if(ZhiChiConstant.client_sendmsg_to_custom_success.equals(data.getStatus())){
                            //???????????????????????????
                            message.setRevaluateState(revaluateFlag?2:3);
                            resetRevaluateBtn(message);
                        }
                    }

                    @Override
                    public void onFailure(Exception e, String des) {
                        ToastUtil.showToast(mAppContext,"????????????");
                    }
                });
    }

    /**
     * ??????????????????
     * @param evaluateFlag true ????????????  false ??????????????????
     * @param message data
     */
    @Override
    public void doEvaluate(final boolean evaluateFlag,final ZhiChiMessageBase message){
        if(initModel == null || message == null){
            return;
        }
        SobotEvaluateModel sobotEvaluateModel = message.getSobotEvaluateModel();
        if(sobotEvaluateModel == null){
            return;
        }
        if(evaluateFlag){

            SobotCommentParam sobotCommentParam = new SobotCommentParam();
            sobotCommentParam.setType("1");
            sobotCommentParam.setScore("5");
            sobotCommentParam.setCommentType(0);
            sobotCommentParam.setIsresolve(sobotEvaluateModel.getIsResolved());
            zhiChiApi.comment(SobotChatFragment.this,initModel.getCid(), initModel.getUid(), sobotCommentParam, new StringResultCallBack<CommonModel>() {
                @Override
                public void onSuccess(CommonModel commonModel) {
                    if (!isActive()) {
                        return;
                    }
                    Intent intent = new Intent();
                    intent.setAction(ZhiChiConstants.dcrc_comment_state);
                    intent.putExtra("commentState", true);
                    intent.putExtra("commentType", 0);
                    intent.putExtra("score", message.getSobotEvaluateModel().getScore());
                    intent.putExtra("isResolved", message.getSobotEvaluateModel().getIsResolved());
                    CommonUtils.sendLocalBroadcast(mAppContext, intent);
                }

                @Override
                public void onFailure(Exception e, String des) {

                }
            });
        } else {
            submitEvaluation(false,sobotEvaluateModel.getScore());
        }

    }

    /**
     * ?????? ?????? ??????
     * @param message
     */
    private void resetRevaluateBtn(final ZhiChiMessageBase message){
        if (!isActive()) {
            return;
        }
        lv_message.post(new Runnable() {

            @Override
            public void run() {
                if (message == null) {
                    return;
                }
                for (int i = 0, count = lv_message.getChildCount(); i < count; i++) {
                    View child = lv_message.getChildAt(i);
                    if (child == null || child.getTag() == null || !(child.getTag() instanceof RichTextMessageHolder)) {
                        continue;
                    }
                    RichTextMessageHolder holder = (RichTextMessageHolder) child.getTag();
                    holder.resetRevaluateBtn();
                }
            }
        });
    }

    /**
     * ?????? ??????????????????
     */
    private void resetCusEvaluate(){
        if (!isActive()) {
            return;
        }
        lv_message.post(new Runnable() {

            @Override
            public void run() {
                for (int i = 0, count = lv_message.getChildCount(); i < count; i++) {
                    View child = lv_message.getChildAt(i);
                    if (child == null || child.getTag() == null || !(child.getTag() instanceof CusEvaluateMessageHolder)) {
                        continue;
                    }
                    CusEvaluateMessageHolder holder = (CusEvaluateMessageHolder) child.getTag();
                    holder.checkEvaluateStatus();
                }
            }
        });
    }

    //????????????
    private void getAnnouncement(){
        if (!TextUtils.isEmpty(initModel.getAnnounceClickUrl())){
            sobot_announcement_right_icon.setVisibility(View.VISIBLE);
        } else {
            sobot_announcement_right_icon.setVisibility(View.GONE);
        }

        if (initModel.getAnnounceMsgFlag() && !TextUtils.isEmpty(initModel.getAnnounceMsg())){
            sobot_announcement.setVisibility(View.VISIBLE);
            sobot_announcement_title.setText(initModel.getAnnounceMsg());
            sobot_announcement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // ???????????????
                    if (!TextUtils.isEmpty(initModel.getAnnounceClickUrl()) && initModel.getAnnounceClickFlag()){
                        Intent intent = new Intent(mAppContext, WebViewActivity.class);
                        intent.putExtra("url", initModel.getAnnounceClickUrl());
                        startActivity(intent);
                    }
                }
            });
        } else {
            sobot_announcement.setVisibility(View.GONE);
        }
    }

    /**
     * ??????????????????UI
     * @param viewType
     */
    public void setBottomView(int viewType){
        welcome.setVisibility(View.GONE);
        relative.setVisibility(View.VISIBLE);
        chat_main.setVisibility(View.VISIBLE);
        et_sendmessage.setVisibility(View.VISIBLE);
        sobot_ll_restart_talk.setVisibility(View.GONE);
        sobot_ll_bottom.setVisibility(View.VISIBLE);

        hideReLoading();
        if (isUserBlack()) {
            sobot_ll_restart_talk.setVisibility(View.GONE);
            sobot_ll_bottom.setVisibility(View.VISIBLE);
            btn_model_voice.setVisibility(View.GONE);
            btn_emoticon_view.setVisibility(View.GONE);
        }
        sobot_tv_satisfaction.setVisibility(View.VISIBLE);
        sobot_txt_restart_talk.setVisibility(View.VISIBLE);
        sobot_tv_message.setVisibility(View.VISIBLE);

        LogUtils.i("setBottomView:"+viewType);
        switch(viewType){
            case ZhiChiConstant.bottomViewtype_onlyrobot:
                // ????????????
                showVoiceBtn();
                if (image_reLoading.getVisibility() == View.VISIBLE) {
                    sobot_ll_bottom.setVisibility(View.VISIBLE);/* ?????????????????? */
                    edittext_layout.setVisibility(View.VISIBLE);/* ????????????????????? */
                    sobot_ll_restart_talk.setVisibility(View.GONE);

                    if (btn_press_to_speak.getVisibility() == View.VISIBLE) {
                        btn_press_to_speak.setVisibility(View.GONE);
                    }
                    btn_set_mode_rengong.setClickable(false);
                    btn_set_mode_rengong.setVisibility(View.GONE);
                }
                btn_emoticon_view.setVisibility(View.GONE);
                btn_upload_view.setVisibility(View.VISIBLE);
                break;
            case ZhiChiConstant.bottomViewtype_robot:
                //??????????????????
                if (info.isArtificialIntelligence() && type == ZhiChiConstant.type_robot_first){
                    //??????????????????????????????????????????
                    if (showTimeVisiableCustomBtn >= info.getArtificialIntelligenceNum()){
                        btn_set_mode_rengong.setVisibility(View.VISIBLE);
                    } else {
                        btn_set_mode_rengong.setVisibility(View.GONE);
                    }
                } else {
                    btn_set_mode_rengong.setVisibility(View.VISIBLE);
                }

                btn_set_mode_rengong.setClickable(true);
                showVoiceBtn();
                if (Build.VERSION.SDK_INT >= 11)
                    btn_set_mode_rengong.setAlpha(1f);
                if (image_reLoading.getVisibility() == View.VISIBLE) {
                    sobot_ll_bottom.setVisibility(View.VISIBLE);/* ?????????????????? */
                    edittext_layout.setVisibility(View.VISIBLE);/* ????????????????????? */
                    sobot_ll_restart_talk.setVisibility(View.GONE);

                    if (btn_press_to_speak.getVisibility() == View.VISIBLE) {
                        btn_press_to_speak.setVisibility(View.GONE);
                    }
                    btn_set_mode_rengong.setClickable(true);
                    btn_set_mode_rengong.setEnabled(true);
                }
                btn_upload_view.setVisibility(View.VISIBLE);
                btn_emoticon_view.setVisibility(View.GONE);
                break;
            case ZhiChiConstant.bottomViewtype_customer:
                //???????????????
                hideRobotVoiceHint();
                btn_model_edit.setVisibility(View.GONE);
                btn_set_mode_rengong.setVisibility(View.GONE);
                btn_upload_view.setVisibility(View.VISIBLE);
                showEmotionBtn();
                showVoiceBtn();
                btn_model_voice.setEnabled(true);
                btn_model_voice.setClickable(true);
                btn_upload_view.setEnabled(true);
                btn_upload_view.setClickable(true);
                btn_emoticon_view.setClickable(true);
                btn_emoticon_view.setEnabled(true);
                if (Build.VERSION.SDK_INT >= 11){
                    btn_model_voice.setAlpha(1f);
                    btn_upload_view.setAlpha(1f);
                }

                edittext_layout.setVisibility(View.VISIBLE);
                sobot_ll_bottom.setVisibility(View.VISIBLE);
                btn_press_to_speak.setVisibility(View.GONE);
                btn_press_to_speak.setClickable(true);
                btn_press_to_speak.setEnabled(true);
                txt_speak_content.setText(getResString("sobot_press_say"));
                break;
            case ZhiChiConstant.bottomViewtype_onlycustomer_paidui:
                //??????????????????
                onlyCustomPaidui();

                hidePanelAndKeyboard(mPanelRoot);
                if(lv_message.getLastVisiblePosition()!=messageAdapter.getCount()){
                    lv_message.setSelection(messageAdapter.getCount());
                }
                break;
            case ZhiChiConstant.bottomViewtype_outline:
                //?????????
                hideReLoading();
                hidePanelAndKeyboard(mPanelRoot);/*????????????*/
                sobot_ll_bottom.setVisibility(View.GONE);
                sobot_ll_restart_talk.setVisibility(View.VISIBLE);
                sobot_tv_satisfaction.setVisibility(View.VISIBLE);
                sobot_txt_restart_talk.setVisibility(View.VISIBLE);
                btn_model_edit.setVisibility(View.GONE);
                sobot_tv_message.setVisibility(initModel.getMsgFlag() == ZhiChiConstant.sobot_msg_flag_close?View
                        .GONE:View.VISIBLE);
                btn_model_voice.setVisibility(View.GONE);
                lv_message.setSelection(messageAdapter.getCount());
                break;
            case ZhiChiConstant.bottomViewtype_paidui:
                //????????????????????????
                if (btn_press_to_speak.getVisibility() == View.GONE) {
                    showVoiceBtn();
                }
                btn_set_mode_rengong.setVisibility(View.VISIBLE);
                btn_emoticon_view.setVisibility(View.GONE);
                if (image_reLoading.getVisibility() == View.VISIBLE) {
                    sobot_ll_bottom.setVisibility(View.VISIBLE);/* ?????????????????? */
                    edittext_layout.setVisibility(View.VISIBLE);/* ????????????????????? */
                    btn_model_voice.setVisibility(View.GONE);
                    sobot_ll_restart_talk.setVisibility(View.GONE);

                    if (btn_press_to_speak.getVisibility() == View.VISIBLE) {
                        btn_press_to_speak.setVisibility(View.GONE);
                    }
                }
                break;
            case ZhiChiConstant.bottomViewtype_custom_only_msgclose:
                sobot_ll_restart_talk.setVisibility(View.VISIBLE);

                sobot_ll_bottom.setVisibility(View.GONE);
                if (image_reLoading.getVisibility() == View.VISIBLE){
                    sobot_txt_restart_talk.setVisibility(View.VISIBLE);
                    sobot_txt_restart_talk.setClickable(true);
                    sobot_txt_restart_talk.setEnabled(true);
                }
                if (initModel.getMsgFlag() == ZhiChiConstant.sobot_msg_flag_close){
                    //????????????
                    sobot_tv_satisfaction.setVisibility(View.INVISIBLE);
                    sobot_tv_message.setVisibility(View.INVISIBLE);
                } else {
                    sobot_tv_satisfaction.setVisibility(View.GONE);
                    sobot_tv_message.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    //??????????????????UI??????
    private void onlyCustomPaidui() {
        sobot_ll_bottom.setVisibility(View.VISIBLE);

        btn_set_mode_rengong.setVisibility(View.GONE);
        btn_set_mode_rengong.setClickable(false);

        btn_upload_view.setVisibility(View.VISIBLE);
        btn_upload_view.setClickable(false);
        btn_upload_view.setEnabled(false);

        showEmotionBtn();
        btn_emoticon_view.setClickable(false);
        btn_emoticon_view.setEnabled(false);

        showVoiceBtn();
        btn_model_voice.setClickable(false);
        btn_model_voice.setEnabled(false);

        if (Build.VERSION.SDK_INT >= 11){
            btn_upload_view.setAlpha(0.4f);
            btn_model_voice.setAlpha(0.4f);
        }

        edittext_layout.setVisibility(View.GONE);
        btn_press_to_speak.setClickable(false);
        btn_press_to_speak.setEnabled(false);
        btn_press_to_speak.setVisibility(View.VISIBLE);
        txt_speak_content.setText(getResString("sobot_in_line"));

        if (sobot_ll_restart_talk.getVisibility() == View.VISIBLE) {
            sobot_ll_restart_talk.setVisibility(View.GONE);
        }
    }

    private void createConsultingContent() {
        ConsultingContent consultingContent = info.getConsultingContent();
        if(consultingContent != null && !TextUtils.isEmpty(consultingContent.getSobotGoodsTitle()) && !TextUtils.isEmpty(consultingContent.getSobotGoodsFromUrl())){
            ZhiChiMessageBase zhichiMessageBase = new ZhiChiMessageBase();
            zhichiMessageBase.setSenderType(ZhiChiConstant.message_sender_type_consult_info + "");
            if(!TextUtils.isEmpty(consultingContent.getSobotGoodsImgUrl())){
                zhichiMessageBase.setPicurl(consultingContent.getSobotGoodsImgUrl());
            }
            ZhiChiReplyAnswer reply = new ZhiChiReplyAnswer();
            zhichiMessageBase.setAnswer(reply);
            zhichiMessageBase.setT(consultingContent.getSobotGoodsTitle());
            zhichiMessageBase.setUrl(consultingContent.getSobotGoodsFromUrl());
            zhichiMessageBase.setCid(initModel == null?"":initModel.getCid());
            zhichiMessageBase.setAname(consultingContent.getSobotGoodsLable());
            zhichiMessageBase.setReceiverFace(consultingContent.getSobotGoodsDescribe());

            zhichiMessageBase.setAction(ZhiChiConstant.action_consultingContent_info);
            updateUiMessage(messageAdapter, zhichiMessageBase);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    lv_message.setSelection(messageAdapter.getCount());
                }
            });
        } else {
            if (messageAdapter != null){
                messageAdapter.removeConsulting();
            }
        }
    }

    /**
     * ???????????????????????????????????????  ??????????????????????????????????????????
     */
    private void resetBtnUploadAndSend(){
        if (et_sendmessage.getText().toString().length() > 0) {
            btn_upload_view.setVisibility(View.GONE);
            btn_send.setVisibility(View.VISIBLE);
        } else {
            btn_send.setVisibility(View.GONE);
            btn_upload_view.setVisibility(View.VISIBLE);
            btn_upload_view.setEnabled(true);
            btn_upload_view.setClickable(true);
            if (Build.VERSION.SDK_INT >= 11){
                btn_upload_view.setAlpha(1f);
            }
        }
    }

    /**
     * ?????????????????????????????????title
     * ?????????????????????title???????????????????????????????????????
     * @param title ???????????????????????????????????????????????? ??????????????????
     * @param ignoreLogic ??????????????????????????????
     */
    private void showLogicTitle(String title,boolean ignoreLogic){
        String str = ChatUtils.getLogicTitle(mAppContext,ignoreLogic, title, initModel.getCompanyName());
        if(!TextUtils.isEmpty(str)){
            setTitle(str);
        }
    }

    // ??????????????????
    public void setTitle(CharSequence title) {
        mTitleTextView.setText(title);
    }

    /**
     * ???????????????????????????
     *
     * @param view
     */
    protected void onLeftMenuClick(View view) {
        hidePanelAndKeyboard(mPanelRoot);
        onBackPress();
    }

    /**
     * ???????????????????????????
     *
     * @param view
     */
    protected void onRightMenuClick(View view) {
        hidePanelAndKeyboard(mPanelRoot);
        ClearHistoryDialog clearHistoryDialog = new ClearHistoryDialog(getActivity());
        clearHistoryDialog.setCanceledOnTouchOutside(true);
        clearHistoryDialog.setOnClickListener(new ClearHistoryDialog.DialogOnClickListener() {
            @Override
            public void onSure() {
                clearHistory();
            }
        });
        clearHistoryDialog.show();
    }

    public void clearHistory(){
        zhiChiApi.deleteHisMsg(SobotChatFragment.this,initModel.getUid(), new StringResultCallBack<CommonModelBase>() {
            @Override
            public void onSuccess(CommonModelBase modelBase) {
                if (!isActive()) {
                    return;
                }
                messageList.clear();
                cids.clear();
                messageAdapter.notifyDataSetChanged();
                lv_message.setPullRefreshEnable(true);// ????????????????????????
            }

            @Override
            public void onFailure(Exception e, String des) {}
        });
    }

    /**
     * ?????????????????????????????????
     */
    public void hideReLoading(){
        image_reLoading.clearAnimation();
        image_reLoading.setVisibility(View.GONE);
    }

    /**
     * ?????????????????????????????????
     */
    public void resetEmoticonBtn() {
        String panelViewTag = getPanelViewTag(mPanelRoot);
        String instanceTag = CustomeViewFactory.getInstanceTag(mAppContext, btn_emoticon_view.getId());
        if (mPanelRoot.getVisibility() == View.VISIBLE && instanceTag.equals(panelViewTag)) {
            doEmoticonBtn2Focus();
        } else {
            doEmoticonBtn2Blur();
        }
    }

    /**
     * ???????????????????????????
     */
    public void doEmoticonBtn2Focus() {
        btn_emoticon_view.setSelected(true);
    }

    /**
     * ???????????????????????????
     */
    public void doEmoticonBtn2Blur() {
        btn_emoticon_view.setSelected(false);
    }

    /**
     * ????????????????????????????????????
     */
    private void showLeaveMsg(){
        LogUtils.i("???????????????????????????");
        showLogicTitle(getResString("sobot_no_access"),false);
        setBottomView(ZhiChiConstant.bottomViewtype_custom_only_msgclose);
        mBottomViewtype = ZhiChiConstant.bottomViewtype_custom_only_msgclose;
        if (isUserBlack()){
            showCustomerUanbleTip();
        } else {
            showCustomerOfflineTip();
        }
        isSessionOver = true;
    }

    /**
     * ?????????????????????
     * @param item
     */
    @Override
    public void inputEmoticon(Emojicon item){
        InputHelper.input2OSC(et_sendmessage,item);
    }

    /**
     * ????????????????????????
     */
    @Override
    public void backspace(){
        InputHelper.backspace(et_sendmessage);
    }

    /**
     * ????????????????????????????????????
     * ??????
     */
    @Override
    public void btnPicture(){
        hidePanelAndKeyboard(mPanelRoot);
        selectPicFromLocal();
        lv_message.setSelection(messageAdapter.getCount());
    }

    /**
     * ????????????????????????????????????
     * ??????
     */
    @Override
    public void btnCameraPicture(){
        hidePanelAndKeyboard(mPanelRoot);
        selectPicFromCamera(); // ?????? ??????
        lv_message.setSelection(messageAdapter.getCount());
    }

    /**
     * ????????????????????????????????????
     * ?????????
     */
    @Override
    public void btnSatisfaction(){
        lv_message.setSelection(messageAdapter.getCount());
        //??????????????? ???????????????????????????????????? ????????? ????????????????????? ????????? ?????????????????????????????????
        hidePanelAndKeyboard(mPanelRoot);
        submitEvaluation(true,5);
    }

    @Override
    public void startToPostMsgActivty(boolean FLAG_EXIT_SDK) {
        if (initModel == null){
            return;
        }

        if(SobotOption.sobotLeaveMsgListener  != null){
            SobotOption.sobotLeaveMsgListener.onLeaveMsg();
            return;
        }

        Intent intent = new Intent(mAppContext, SobotPostMsgActivity.class);
        intent.putExtra("uid", initModel.getUid());
        intent.putExtra("companyId", initModel.getCompanyId());
        intent.putExtra(ZhiChiConstant.FLAG_EXIT_SDK, FLAG_EXIT_SDK);
        intent.putExtra("msgTmp", initModel.getMsgTmp());
        intent.putExtra("msgTxt", initModel.getMsgTxt());
        intent.putExtra("groupId", info.getSkillSetId());
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().overridePendingTransition(ResourceUtils.getIdByName(mAppContext, "anim", "push_left_in"),
                    ResourceUtils.getIdByName(mAppContext, "anim", "push_left_out"));
        }
    }

    /**
     * ????????????????????????
     */
    public void switchEmoticonBtn(){
        boolean flag = btn_emoticon_view.isSelected();
        if(flag){
            doEmoticonBtn2Blur();
        }else{
            doEmoticonBtn2Focus();
        }
    }

    //??????????????????????????????
    public void switchPanelAndKeyboard(final View panelLayout,final View switchPanelKeyboardBtn, final View focusView) {
        if(currentPanelId == 0 || currentPanelId == switchPanelKeyboardBtn.getId()){
            //????????????????????????  ???????????????????????????????????????????????????
            boolean switchToPanel = panelLayout.getVisibility() != View.VISIBLE;
            if (!switchToPanel) {
                KPSwitchConflictUtil.showKeyboard(panelLayout, focusView);
            } else {
                KPSwitchConflictUtil.showPanel(panelLayout);
                setPanelView(panelLayout, switchPanelKeyboardBtn.getId());
            }
        } else {
            //????????????  ??????????????????????????????????????????  ?????????????????????
            KPSwitchConflictUtil.showPanel(panelLayout);
            setPanelView(panelLayout, switchPanelKeyboardBtn.getId());
        }
        currentPanelId = switchPanelKeyboardBtn.getId();
    }

    /*
     * ??????????????????????????????   ????????????????????????????????????????????? ????????????????????????
     * ??????????????????????????????
     */
    public void pressSpeakSwitchPanelAndKeyboard(final View switchPanelKeyboardBtn){
        if (btn_press_to_speak.isShown()) {
            btn_model_edit.setVisibility(View.GONE);
            showVoiceBtn();
            btn_press_to_speak.setVisibility(View.GONE);
            edittext_layout.setVisibility(View.VISIBLE);

            et_sendmessage.requestFocus();
            KPSwitchConflictUtil.showPanel(mPanelRoot);
            setPanelView(mPanelRoot, switchPanelKeyboardBtn.getId());
            currentPanelId = switchPanelKeyboardBtn.getId();
        }else{
            //???????????????????????????
            switchPanelAndKeyboard(mPanelRoot, switchPanelKeyboardBtn, et_sendmessage);
        }
    }

    /**
     * ?????????????????????view
     * @param panelLayout
     * @param btnId
     */
    private void setPanelView(final View panelLayout,int btnId){
        if(panelLayout instanceof KPSwitchPanelLinearLayout){
            KPSwitchPanelLinearLayout tmpView = (KPSwitchPanelLinearLayout) panelLayout;
            View childView = tmpView.getChildAt(0);
            if(childView != null && childView instanceof CustomeChattingPanel){
                CustomeChattingPanel customeChattingPanel = (CustomeChattingPanel) childView;
                Bundle bundle = new Bundle();
                bundle.putInt("current_client_model",current_client_model);
                customeChattingPanel.setupView(btnId,bundle,SobotChatFragment.this);
            }
        }
    }

    /**
     * ????????????????????????????????????tag
     *
     * @param panelLayout
     */
    private String getPanelViewTag(final View panelLayout) {
        String str = "";
        if (panelLayout instanceof KPSwitchPanelLinearLayout) {
            KPSwitchPanelLinearLayout tmpView = (KPSwitchPanelLinearLayout) panelLayout;
            View childView = tmpView.getChildAt(0);
            if (childView != null && childView instanceof CustomeChattingPanel) {
                CustomeChattingPanel customeChattingPanel = (CustomeChattingPanel) childView;
                str = customeChattingPanel.getPanelViewTag();
            }
        }
        return str;
    }

    /**
     * ?????????????????????
     * @param layout
     */
    public void hidePanelAndKeyboard(KPSwitchPanelLinearLayout layout){
        et_sendmessage.dismissPop();
        KPSwitchConflictUtil.hidePanelAndKeyboard(layout);
        doEmoticonBtn2Blur();
        currentPanelId = 0;
    }

    /*
     * ????????????
     */
    private void showHint(String content) {
        ZhiChiMessageBase zhichiMessageBase = new ZhiChiMessageBase();
        ZhiChiReplyAnswer reply = new ZhiChiReplyAnswer();
        zhichiMessageBase.setSenderType(ZhiChiConstant.message_sender_type_remide_info + "");
        reply.setMsg(content);
        reply.setRemindType(ZhiChiConstant.sobot_remind_type_evaluate);
        zhichiMessageBase.setAnswer(reply);
        zhichiMessageBase.setAction(ZhiChiConstant.action_remind_no_service);
        updateUiMessage(messageAdapter,zhichiMessageBase);
    }

    @Override
    public void onRobotGuessComplete(String question) {
        //???????????? ????????????
        et_sendmessage.setText("");
        sendMsg(question);
    }

    @Override
    public void onRefresh() {
        getHistoryMessage(false);
    }

    /**
     * ??????????????????
     * @param isFirst ???????????????????????????
     */
    public void getHistoryMessage(final boolean isFirst) {
        if (initModel == null)
            return;

        if(queryCidsStatus == ZhiChiConstant.QUERY_CIDS_STATUS_INITIAL || queryCidsStatus == ZhiChiConstant.QUERY_CIDS_STATUS_FAILURE){
            //cid?????????????????????????????????????????????????????????cid
            onLoad();
            queryCids();
        } else if((queryCidsStatus == ZhiChiConstant.QUERY_CIDS_STATUS_LOADING && !isFirst) || isInGethistory){
            //1.??????cid??????????????? ????????????????????????????????????  ?????? ??????????????????????????????
            //2.??????????????????????????????????????????   ?????????????????????
            onLoad();
        } else {
            String currentCid = ChatUtils.getCurrentCid(initModel,cids,currentCidPosition);
            if("-1".equals(currentCid)){
                showNoHistory();
                onLoad();
                return;
            }
            isInGethistory = true;
            zhiChiApi.getChatDetailByCid(SobotChatFragment.this,initModel.getUid(), currentCid, new StringResultCallBack<ZhiChiHistoryMessage>() {
                @Override
                public void onSuccess(ZhiChiHistoryMessage zhiChiHistoryMessage) {
                    isInGethistory = false;
                    if (!isActive()) {
                        return;
                    }
                    onLoad();
                    currentCidPosition++;
                    List<ZhiChiHistoryMessageBase> data = zhiChiHistoryMessage.getData();
                    if(data != null && data.size() > 0){
                        showData(data);
                    } else {
                        //??????????????????????????????
                        getHistoryMessage(false);
                    }
                }

                @Override
                public void onFailure(Exception e, String des) {
                    isInGethistory = false;
                    if (!isActive()) {
                        return;
                    }
                    mUnreadNum = 0;
                    updateFloatUnreadIcon();
                    onLoad();
                }
            });
        }
    }

    private void showData(List<ZhiChiHistoryMessageBase> result){
        List<ZhiChiMessageBase> msgLists = new ArrayList<>();
        List<ZhiChiMessageBase> msgList;
        for (int i = 0; i < result.size(); i++) {
            ZhiChiHistoryMessageBase historyMsg = result.get(i);
            msgList = historyMsg.getContent();

            for (ZhiChiMessageBase base : msgList) {
                base.setSugguestionsFontColor(1);
                if (base.getSdkMsg() != null) {
                    ZhiChiReplyAnswer answer = base.getSdkMsg().getAnswer();
                    if (answer != null){
                        if (answer.getMsgType() == null){
                            answer.setMsgType("0");
                        }

                        if (!TextUtils.isEmpty(answer.getMsg()) && answer.getMsg().length() > 4){
                            String msg = answer.getMsg().replace("&lt;/p&gt;","<br>");
                            if (msg.endsWith("<br>")){
                                msg = msg.substring(0, msg.length()-4);
                            }
                            answer.setMsg(msg);
                        }
                    }
                    if (ZhiChiConstant.message_sender_type_robot == Integer
                            .parseInt(base.getSenderType())) {
                        base.setSenderName(TextUtils.isEmpty(base.getSenderName()) ? initModel
                                .getRobotName() : base.getSenderName());
                        base.setSenderFace(TextUtils.isEmpty(base.getSenderFace()) ? initModel
                                .getRobotLogo() : base.getSenderFace());
                    }
                    base.setAnswer(answer);
                    base.setSugguestions(base.getSdkMsg()
                            .getSugguestions());
                    base.setStripe(base.getSdkMsg().getStripe());
                    base.setAnswerType(base.getSdkMsg()
                            .getAnswerType());
                }
            }
            msgLists.addAll(msgList);
        }

        if (msgLists.size() > 0) {
            if(mUnreadNum > 0){
                ZhiChiMessageBase unreadMsg = ChatUtils.getUnreadMode(mAppContext);
                unreadMsg.setCid(msgLists.get(msgLists.size()-1).getCid());
                msgLists.add((msgLists.size() - mUnreadNum) < 0 ? 0:(msgLists.size() - mUnreadNum)
                        ,unreadMsg);
                updateFloatUnreadIcon();
                mUnreadNum = 0;
            }
            messageAdapter.addData(msgLists);
            messageAdapter.notifyDataSetChanged();
            lv_message.setSelection(msgLists.size());
        }
    }

    /**
     * ??????????????????????????????
     */
    private void showNoHistory(){
        ZhiChiMessageBase base = new ZhiChiMessageBase();

        base.setSenderType(ZhiChiConstant.message_sender_type_remide_info + "");

        ZhiChiReplyAnswer reply1 = new ZhiChiReplyAnswer();
        reply1.setRemindType(ZhiChiConstant.sobot_remind_type_nomore);
        reply1.setMsg(getResString("sobot_no_more_data"));
        base.setAnswer(reply1);
        // ?????????????????????
        updateUiMessageBefore(messageAdapter, base);
        lv_message.setSelection(0);

        lv_message.setPullRefreshEnable(false);// ????????????????????????
        isNoMoreHistoryMsg = true;
        mUnreadNum = 0;
    }

    private void onLoad() {
        lv_message.onRefreshCompleteHeader();
    }

    // ???????????????????????????????????????
    private void editModelToVoice(int typeModel, String str) {
        btn_model_edit.setVisibility(View.GONE == typeModel ? View.GONE
                : View.VISIBLE); // ??????????????????
        btn_model_voice.setVisibility(View.VISIBLE != typeModel ? View.VISIBLE
                : View.GONE);// ??????????????????
        btn_press_to_speak.setVisibility(View.GONE != typeModel ? View.VISIBLE
                : View.GONE);
        edittext_layout.setVisibility(View.VISIBLE == typeModel ? View.GONE
                : View.VISIBLE);

        if (!TextUtils.isEmpty(et_sendmessage.getText().toString()) && str.equals("123")) {
            btn_send.setVisibility(View.VISIBLE);
            btn_upload_view.setVisibility(View.GONE);
        } else {
            btn_send.setVisibility(View.GONE);
            btn_upload_view.setVisibility(View.VISIBLE);
        }
    }

    public void setShowNetRemind(boolean isShow) {
        net_status_remide.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * ??????????????????
     */
    public class MyMessageReceiver extends BroadcastReceiver {
        @SuppressWarnings("deprecation")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                if (!CommonUtils.isNetWorkConnected(mAppContext)) {
                    //????????????
                    if (welcome.getVisibility() != View.VISIBLE){
                        setShowNetRemind(true);
                    }
                } else {
                    // ?????????
                    setShowNetRemind(false);
                    if(IntenetUtil.isWifiConnected(mAppContext) && logCollectTime == 0){
                        logCollectTime++;
                        zhiChiApi.logCollect(mAppContext,info.getAppkey());
                    }
                }
            } else if (ZhiChiConstants.chat_remind_post_msg.equals(intent.getAction())) {
                startToPostMsgActivty(false);
            } else if (ZhiChiConstants.sobot_click_cancle.equals(intent.getAction())) {
                //?????????????????????????????????
                if (type == ZhiChiConstant.type_custom_first && current_client_model ==
                        ZhiChiConstant.client_model_robot) {
                    remindRobotMessage(handler,initModel,info);
                }
            } else if (ZhiChiConstants.dcrc_comment_state.equals(intent.getAction())) {
                //???????????????????????????????????????
                isComment = intent.getBooleanExtra("commentState", false);
                boolean isFinish = intent.getBooleanExtra("isFinish", false);
                int commentType = intent.getIntExtra("commentType", 1);

                //????????????????????? ??????ui
                int score = intent.getIntExtra("score", 5);
                int isResolved = intent.getIntExtra("isResolved", 0);
                messageAdapter.submitEvaluateData(isResolved,score);
                resetCusEvaluate();

                //??????????????????????????????????????????????????????
                if(ChatUtils.isEvaluationCompletedExit(mAppContext,isComment,current_client_model)){
                    //????????????????????????????????????????????????
                    customerServiceOffline(initModel,1);
                    ChatUtils.userLogout(mAppContext);
                }
                if (isActive()) {
                    ChatUtils.showThankDialog(getActivity(),handler,isFinish);
                }
            } else if (ZhiChiConstants.sobot_close_now.equals(intent.getAction())){
                finish();
            } else if (ZhiChiConstants.sobot_close_now_clear_cache.equals(intent.getAction())){
                isSessionOver = true;
                finish();
            } else if (ZhiChiConstants.SOBOT_CHANNEL_STATUS_CHANGE.equals(intent.getAction())){
                if(customerState == CustomerState.Online || customerState == CustomerState.Queuing){
                    int connStatus = intent.getIntExtra("connStatus", Const.CONNTYPE_IN_CONNECTION);
                    LogUtils.i("connStatus:"+connStatus);
                    switch (connStatus){
                        case Const.CONNTYPE_IN_CONNECTION:
                            sobot_container_conn_status.setVisibility(View.VISIBLE);
                            sobot_title_conn_status.setText(getResString("sobot_conntype_in_connection"));
                            mTitleTextView.setVisibility(View.GONE);
                            sobot_conn_loading.setVisibility(View.VISIBLE);
                            break;
                        case Const.CONNTYPE_CONNECT_SUCCESS:
                            setShowNetRemind(false);
                            sobot_container_conn_status.setVisibility(View.GONE);
                            sobot_title_conn_status.setText(getResString("sobot_conntype_connect_success"));
                            mTitleTextView.setVisibility(View.VISIBLE);
                            sobot_conn_loading.setVisibility(View.GONE);
                            break;
                        case Const.CONNTYPE_UNCONNECTED:
                            sobot_container_conn_status.setVisibility(View.VISIBLE);
                            sobot_title_conn_status.setText(getResString("sobot_conntype_unconnected"));
                            mTitleTextView.setVisibility(View.GONE);
                            sobot_conn_loading.setVisibility(View.GONE);
                            if (welcome.getVisibility() != View.VISIBLE){
                                setShowNetRemind(true);
                            }
                            break;
                    }
                }else{
                    mTitleTextView.setVisibility(View.VISIBLE);
                    sobot_container_conn_status.setVisibility(View.GONE);
                }
            }
        }
    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.i("?????????  :" + intent.getAction());
            if (ZhiChiConstants.receiveMessageBrocast.equals(intent.getAction())) {
                // ?????????????????????
                ZhiChiPushMessage pushMessage = null;
                try {
                    Bundle extras = intent.getExtras();
                    if (extras != null) {
                        pushMessage = (ZhiChiPushMessage) extras.getSerializable(ZhiChiConstants.ZHICHI_PUSH_MESSAGE);
                    }
                } catch (Exception e) {
                    //ignor
                }
                if(pushMessage == null || !info.getAppkey().equals(pushMessage.getAppId())){
                    return;
                }
                ZhiChiMessageBase base = new ZhiChiMessageBase();
                base.setSenderName(pushMessage.getAname());

                if (ZhiChiConstant.push_message_createChat == pushMessage.getType()) {
                    setAdminFace(pushMessage.getAface());
                    if (type == 2 || type == 3 || type == 4) {
                        createCustomerService(pushMessage.getAname(),pushMessage.getAface());
                    }
                } else if (ZhiChiConstant.push_message_paidui == pushMessage.getType()) {
                    // ?????????????????????
                    createCustomerQueue(pushMessage.getCount(), 0);
                } else if (ZhiChiConstant.push_message_receverNewMessage == pushMessage.getType()) {
                    // ?????????????????????
                    if (customerState == CustomerState.Online) {
                        base.setSender(pushMessage.getAname());
                        base.setSenderName(pushMessage.getAname());
                        base.setSenderFace(pushMessage.getAface());
                        base.setSenderType(ZhiChiConstant.message_sender_type_service + "");
                        ZhiChiReplyAnswer reply;
                        if(TextUtils.isEmpty(pushMessage.getMsgType())){
                            return;
                        }
                        if ("7".equals(pushMessage.getMsgType())) {
                            reply = GsonUtil.jsonToZhiChiReplyAnswer(pushMessage.getContent());
                        } else {
                            reply = new ZhiChiReplyAnswer();
                            reply.setMsgType(pushMessage.getMsgType() + "");
                            reply.setMsg(pushMessage.getContent());
                        }

                        base.setAnswer(reply);
                        // ?????????????????????
                        updateUiMessage(messageAdapter, base);
                        stopCustomTimeTask();
                        startUserInfoTimeTask(handler);
                    }/*else{
						int localUnreadNum = SharedPreferencesUtil.getIntData(getApplicationContext(),
								"sobot_unread_count",0);
						localUnreadNum++;
						SharedPreferencesUtil.saveIntData(getApplicationContext(),"sobot_unread_count",localUnreadNum);
					}*/
                } else if (ZhiChiConstant.push_message_outLine == pushMessage.getType()) {
                    // ???????????????
                    customerServiceOffline(initModel,Integer.parseInt(pushMessage.getStatus()));
                } else if (ZhiChiConstant.push_message_transfer == pushMessage.getType()) {
                    LogUtils.i("???????????????--->"+pushMessage.getName());
                    //????????????
                    showLogicTitle(pushMessage.getName(),false);
                    setAdminFace(pushMessage.getFace());
                    currentUserName = pushMessage.getName();
                } else if (ZhiChiConstant.push_message_custom_evaluate == pushMessage.getType()){
                    LogUtils.i("???????????????????????????.................");
                    //?????????????????????
                    if (isAboveZero && !isComment && customerState == CustomerState.Online) {
                        // ?????????????????????????????????????????????????????? ?????? ????????????
                        ZhiChiMessageBase customEvaluateMode = ChatUtils.getCustomEvaluateMode(pushMessage);
                        // ?????????????????????
                        updateUiMessage(messageAdapter, customEvaluateMode);
                    }
                }
            }
        }
    }

    //??????????????????????????????????????????
    private void saveCache() {
        //?????????????????????????????????
        deleteUnReadUi();
        ZhiChiConfig config = SobotMsgManager.getInstance(mAppContext).getConfig(info.getAppkey());
        config.isShowUnreadUi = true;
        config.setMessageList(messageList);
        config.setInitModel(initModel);
        config.current_client_model = current_client_model;
        if (queryCidsStatus == ZhiChiConstant.QUERY_CIDS_STATUS_SUCCESS) {
            config.cids = cids;
            config.currentCidPosition = currentCidPosition;
            config.queryCidsStatus = queryCidsStatus;
        }

        config.activityTitle = getActivityTitle();
        config.customerState = customerState;
        config.remindRobotMessageTimes=remindRobotMessageTimes;
        config.isAboveZero=isAboveZero;
        config.isComment=isComment;
        config.adminFace=getAdminFace();
        config.customTimeTask=customTimeTask;
        config.userInfoTimeTask=userInfoTimeTask;
        config.currentUserName=currentUserName;
        config.isNoMoreHistoryMsg=isNoMoreHistoryMsg;
        config.showTimeVisiableCustomBtn = showTimeVisiableCustomBtn;
        config.bottomViewtype = mBottomViewtype;
        config.queueNum = queueNum;

    }

    @Override
    public void onClick(View view) {
        if (view == notReadInfo) {
            for (int i = messageList.size() - 1; i >= 0; i--) {
                if (messageList.get(i).getAnswer() != null && ZhiChiConstant.
                        sobot_remind_type_below_unread == messageList.get(i).getAnswer().getRemindType()){
                    lv_message.setSelection(i);
                    break;
                }
            }
            notReadInfo.setVisibility(View.GONE);
        }

        if (view == btn_send) {// ??????????????????
            //??????????????????
            final String message_result = et_sendmessage.getText().toString().trim();
            if (!TextUtils.isEmpty(message_result) && !isConnCustomerService) {
                //?????????????????????????????????  ???????????????????????????????????????
                resetEmoticonBtn();
                try {
                    et_sendmessage.setText("");
                    sendMsg(message_result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (view == btn_upload_view) {// ????????????view
            pressSpeakSwitchPanelAndKeyboard(btn_upload_view);
            doEmoticonBtn2Blur();
            gotoLastItem();
        }

        if(view == btn_emoticon_view){//??????????????????
            // ??????????????????
            pressSpeakSwitchPanelAndKeyboard(btn_emoticon_view);
            //???????????????????????????
            switchEmoticonBtn();
            gotoLastItem();
        }

        if (view == btn_model_edit) {// ??????????????????????????????
            hideRobotVoiceHint();
            doEmoticonBtn2Blur();
            // ??????????????????
            KPSwitchConflictUtil.showKeyboard(mPanelRoot, et_sendmessage);
            editModelToVoice(View.GONE, "123");// ?????????????????? ?????????????????????
        }

        if (view == btn_model_voice) { // ??????????????????????????????
            showRobotVoiceHint();
            doEmoticonBtn2Blur();
            if(!checkStorageAndAudioPermission()){
                return;
            }
            try {
                mFileName = mVoicePath + "sobot_tmp.wav";
                String state = android.os.Environment.getExternalStorageState();
                if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
                    LogUtils.i("SD Card is not mounted,It is  " + state + ".");
                }
                File directory = new File(mFileName).getParentFile();
                if (!directory.exists() && !directory.mkdirs()) {
                    LogUtils.i("Path to file could not be created");
                }
                extAudioRecorder = ExtAudioRecorder.getInstanse(false);
                extAudioRecorder.setOutputFile(mFileName);
                extAudioRecorder.prepare();
                extAudioRecorder.start(new ExtAudioRecorder.AudioRecorderListener() {
                    @Override
                    public void onHasPermission() {
                        hidePanelAndKeyboard(mPanelRoot);
                        editModelToVoice(View.VISIBLE, "");// ??????????????????
                        if (btn_press_to_speak.getVisibility() == View.VISIBLE){
                            btn_press_to_speak.setVisibility(View.VISIBLE);
                            btn_press_to_speak.setClickable(true);
                            btn_press_to_speak.setOnTouchListener(new PressToSpeakListen());
                            btn_press_to_speak.setEnabled(true);
                            txt_speak_content.setText(getResString("sobot_press_say"));
                            txt_speak_content.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNoPermission() {
                        ToastUtil.showToast(mAppContext, getResString("sobot_no_record_audio_permission"));
                    }
                });
                stopVoice();
            } catch (Exception e) {
                LogUtils.i("prepare() failed");
            }

        }

    }

    private void showRobotVoiceHint() {
        send_voice_robot_hint.setVisibility(current_client_model == ZhiChiConstant.client_model_robot?View.VISIBLE:View.GONE);
    }

    private void hideRobotVoiceHint() {
        send_voice_robot_hint.setVisibility(View.GONE);
    }

    /**
     * ?????????????????????
     * @param content
     */
    private void sendMsg(String content) {
        if(initModel == null){
            return;
        }

        String msgId = System.currentTimeMillis() + "";

        if (ZhiChiConstant.client_model_robot == current_client_model) {
            if (type == 2) {
                doClickTransferBtn();
                return;
            } else if ((type == 3 || type == 4 ) && !TextUtils.isEmpty(info.getTransferKeyWord())) {
                //??????????????????????????? ???????????????
                if (content != null && content.equals(info.getTransferKeyWord())) {
                    sendTextMessageToHandler(msgId, content, handler, 1, SEND_TEXT);
                    doClickTransferBtn();
                    return;
                }
            }
        }

        // ??????Handler?????? ????????????ui
        sendTextMessageToHandler(msgId, content, handler, 2, SEND_TEXT);

        LogUtils.i("???????????????????????????" + current_client_model);
        setTimeTaskMethod(handler);
        sendMessageWithLogic(msgId, content, initModel, handler, current_client_model,0,"");
    }

    /**
     * ???????????????
     * ??????????????????????????? ????????? ????????????????????? ????????? ?????????????????????????????????
     * @param isActive ?????????????????????  true ??????  flase ??????
     */
    public void submitEvaluation(boolean isActive,int score){
        if (isComment) {
            showHint(getResString("sobot_completed_the_evaluation"));
        } else {
            if (isUserBlack()){
                showHint(getResString("sobot_unable_to_evaluate"));
            } else if (isAboveZero) {
                if (isActive()) {
                    mEvaluateDialog = ChatUtils.showEvaluateDialog(getActivity(),false,initModel,current_client_model,isActive?1:0,currentUserName,score);
                }
            } else {
                showHint(getResString("sobot_after_consultation_to_evaluate_custome_service"));
            }
        }
    }

    public void showVoiceBtn() {
        if (current_client_model == ZhiChiConstant.client_model_robot && type != 2) {
            btn_model_voice.setVisibility(info.isUseVoice() && info.isUseRobotVoice()?View.VISIBLE : View.GONE);
        } else {
            btn_model_voice.setVisibility(info.isUseVoice()?View.VISIBLE : View.GONE);
        }
    }

    private void sendMsgToRobot(ZhiChiMessageBase base, int sendType, int questionFlag, String docId){
        sendMsgToRobot(base, sendType, questionFlag, docId, null);
    }

    private void sendMsgToRobot(ZhiChiMessageBase base, int sendType, int questionFlag, String docId, String multiRoundMsg){
        if (!TextUtils.isEmpty(multiRoundMsg)){
            sendTextMessageToHandler(base.getId(), multiRoundMsg, handler, 2, sendType);
        } else {
            sendTextMessageToHandler(base.getId(), base.getContent(), handler, 2, sendType);
        }
        ZhiChiReplyAnswer answer = new ZhiChiReplyAnswer();
        answer.setMsgType(ZhiChiConstant.message_type_text + "");
        answer.setMsg(base.getContent());
        base.setAnswer(answer);
        base.setSenderType(ZhiChiConstant.message_sender_type_customer + "");
        sendMessageWithLogic(base.getId(), base.getContent(), initModel, handler, current_client_model, questionFlag, docId);
    }

    /**
     * ?????? ?????????????????????
     */
    private void restMultiMsg(){
        for (int i = 0; i < messageList.size(); i++) {
            ZhiChiMessageBase data = messageList.get(i);
            if (data.getAnswer() != null && data.getAnswer().getMultiDiaRespInfo() != null
                    && !data.getAnswer().getMultiDiaRespInfo().getEndFlag()){
                data.setMultiDiaRespEnd(1);
            }
        }
        messageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            LogUtils.i("???????????????????????????" + requestCode + "--" + resultCode + "--" + data);

            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == ZhiChiConstant.REQUEST_CODE_picture) { // ??????????????????
                    if (data != null && data.getData() != null) {
                        Uri selectedImage = data.getData();
                        // ??????handler????????????
                        ChatUtils.sendPicByUri(mAppContext, handler, selectedImage, initModel, lv_message,messageAdapter);
                    } else {
                        ToastUtil.showLongToast(mAppContext,getResString("sobot_did_not_get_picture_path"));
                    }
                } else if (requestCode == ZhiChiConstant.REQUEST_CODE_makePictureFromCamera) {
                    if (cameraFile != null && cameraFile.exists()) {
                        LogUtils.i("cameraFile.getAbsolutePath()------>>>>" + cameraFile.getAbsolutePath());
                        String id = System.currentTimeMillis() + "";
                        ChatUtils.sendPicLimitBySize(cameraFile.getAbsolutePath(), initModel.getCid(),
                                initModel.getUid(), handler, mAppContext, lv_message,messageAdapter);
                    } else {
                        ToastUtil.showLongToast(mAppContext,getResString("sobot_pic_select_again"));
                    }
                }
                hidePanelAndKeyboard(mPanelRoot);
            }
            if(data != null){
                switch (requestCode) {
                    case ZhiChiConstant.REQUEST_COCE_TO_GRROUP:
                        int groupIndex = data.getIntExtra("groupIndex",-1);
                        LogUtils.i("groupIndex-->" + groupIndex);
                        if (groupIndex >= 0) {
                            requestQueryFrom(list_group.get(groupIndex).getGroupId(),list_group.get(groupIndex).getGroupName());
                        }
                        break;
                    case ZhiChiConstant.REQUEST_COCE_TO_QUERY_FROM:
                        //??????????????????????????????
                        if (resultCode == ZhiChiConstant.REQUEST_COCE_TO_QUERY_FROM) {
                            String groupId = data.getStringExtra(ZhiChiConstant.SOBOT_INTENT_BUNDLE_DATA_GROUPID);
                            String groupName = data.getStringExtra(ZhiChiConstant.SOBOT_INTENT_BUNDLE_DATA_GROUPNAME);
                            connectCustomerService(groupId, groupName);
                        } else {
                            //??????????????????
                            if (type == ZhiChiConstant.type_custom_only) {
                                //???????????????????????????
                                isSessionOver = true;
                                finish();
                            }
                        }
                        break;
                }
            }
        } catch (Exception e) {
//			e.printStackTrace();
        }
    }

    class PressToSpeakListen implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            isCutVoice = false;
            // ?????????????????????????????????
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    voiceMsgId = System.currentTimeMillis() + "";
                    // ????????????????????????
                    btn_upload_view.setClickable(false);
                    btn_model_edit.setClickable(false);
                    btn_upload_view.setEnabled(false);
                    btn_model_edit.setEnabled(false);
                    if (Build.VERSION.SDK_INT >= 11){
                        btn_upload_view.setAlpha(0.4f);
                        btn_model_edit.setAlpha(0.4f);
                    }
                    stopVoiceTimeTask();
                    v.setPressed(true);
                    voice_time_long.setText("00" + "''");
                    voiceTimeLongStr = "00:00";
                    voiceTimerLong = 0;
                    currentVoiceLong = 0;
                    recording_container.setVisibility(View.VISIBLE);
                    voice_top_image.setVisibility(View.VISIBLE);
                    mic_image.setVisibility(View.VISIBLE);
                    mic_image_animate.setVisibility(View.VISIBLE);
                    voice_time_long.setVisibility(View.VISIBLE);
                    recording_timeshort.setVisibility(View.GONE);
                    image_endVoice.setVisibility(View.GONE);
                    txt_speak_content.setText(getResString("sobot_up_send"));
                    // ???????????????????????????
                    startVoice();
                    return true;
                // ?????????????????????
                case MotionEvent.ACTION_POINTER_DOWN:
                    return true;
                case MotionEvent.ACTION_POINTER_UP:
                    return true;
                case MotionEvent.ACTION_MOVE: {
                    if (!is_startCustomTimerTask) {
                        noReplyTimeUserInfo = 0;
                    }

                    if (event.getY() < 10) {
                        // ?????????????????????
                        voice_top_image.setVisibility(View.GONE);
                        image_endVoice.setVisibility(View.VISIBLE);
                        mic_image.setVisibility(View.GONE);
                        mic_image_animate.setVisibility(View.GONE);
                        recording_timeshort.setVisibility(View.GONE);
                        txt_speak_content.setText(getResString("sobot_up_send_calcel"));
                        recording_hint.setText(getResString("sobot_release_to_cancel"));
                        recording_hint.setBackgroundResource(getResDrawableId("sobot_recording_text_hint_bg"));
                    } else {
                        if (voiceTimerLong != 0) {
                            txt_speak_content.setText(getResString("sobot_up_send"));
                            voice_top_image.setVisibility(View.VISIBLE);
                            mic_image_animate.setVisibility(View.VISIBLE);
                            image_endVoice.setVisibility(View.GONE);
                            mic_image.setVisibility(View.VISIBLE);
                            recording_timeshort.setVisibility(View.GONE);
                            recording_hint.setText(getResString("sobot_move_up_to_cancel"));
                            recording_hint.setBackgroundResource(getResDrawableId("sobot_recording_text_hint_bg1"));
                        }
                    }
                    return true;
                }
                case MotionEvent.ACTION_UP:
                    // ?????????????????????
                    int toLongOrShort = 0;
                    btn_upload_view.setClickable(true);
                    btn_model_edit.setClickable(true);
                    btn_upload_view.setEnabled(true);
                    btn_model_edit.setEnabled(true);
                    if (Build.VERSION.SDK_INT >= 11){
                        btn_upload_view.setAlpha(1f);
                        btn_model_edit.setAlpha(1f);
                    }
                    v.setPressed(false);
                    txt_speak_content.setText(getResString("sobot_press_say"));
                    stopVoiceTimeTask();
                    stopVoice();
                    if (recording_container.getVisibility() == View.VISIBLE
                            && !isCutVoice) {
                        hidePanelAndKeyboard(mPanelRoot);
                        if(animationDrawable != null){
                            animationDrawable.stop();
                        }
                        voice_time_long.setText("00" + "''");
                        voice_time_long.setVisibility(View.INVISIBLE);
                        if (event.getY() < 0) {
                            recording_container.setVisibility(View.GONE);
                            sendVoiceMap(2,voiceMsgId);
                            return true;
                            // ??????????????????
                        } else {
                            // ????????????
                            if (currentVoiceLong < 1 * 1000) {
                                voice_top_image.setVisibility(View.VISIBLE);
                                recording_hint.setText(getResString("sobot_voice_can_not_be_less_than_one_second"));
                                recording_hint.setBackgroundResource(getResDrawableId("sobot_recording_text_hint_bg"));
                                recording_timeshort.setVisibility(View.VISIBLE);
                                voice_time_long.setVisibility(View.VISIBLE);
                                voice_time_long.setText("00:00");
                                mic_image.setVisibility(View.GONE);
                                mic_image_animate.setVisibility(View.GONE);
                                toLongOrShort = 0;
                                sendVoiceMap(2,voiceMsgId);
                            } else if (currentVoiceLong < minRecordTime * 1000) {
                                recording_container.setVisibility(View.GONE);
                                sendVoiceMap(1,voiceMsgId);
                                return true;
                            } else if (currentVoiceLong > minRecordTime * 1000) {
                                toLongOrShort = 1;
                                voice_top_image.setVisibility(View.VISIBLE);
                                recording_hint.setText(getResString("sobot_voiceTooLong"));
                                recording_hint.setBackgroundResource(getResDrawableId("sobot_recording_text_hint_bg"));
                                recording_timeshort.setVisibility(View.VISIBLE);
                                mic_image.setVisibility(View.GONE);
                                mic_image_animate.setVisibility(View.GONE);
                            } else {
                                sendVoiceMap(2,voiceMsgId);
                            }
                        }
                        currentVoiceLong = 0;
                        closeVoiceWindows(toLongOrShort);
                    }
                    voiceTimerLong = 0;
                    restartMyTimeTask(handler);
                    // mFileName
                    return true;
                default:
                    closeVoiceWindows(2);
                    return true;
            }
        }
    }

    // ??????????????????
    public String getActivityTitle() {
        return mTitleTextView.getText().toString();
    }

    /**
     * ???????????????
     * @return true ????????????
     */
    public void onBackPress() {
        if (isActive()) {
            //???????????????????????? ?????????????????????????????????  ???????????????????????????????????????????????????
            if (mPanelRoot.getVisibility() == View.VISIBLE) {
                hidePanelAndKeyboard(mPanelRoot);
                return;
            } else {
                if (info.isShowSatisfaction()) {
                    if (isAboveZero && !isComment) {
                        // ????????? ??????????????????????????? ?????? ????????????
                        mEvaluateDialog = ChatUtils.showEvaluateDialog(getActivity(),true,initModel,
                                current_client_model,1,currentUserName,5);
                        return;
                    }
                }
            }
            finish();
        }
    }

    protected String getSendMessageStr(){
        return et_sendmessage.getText().toString().trim();
    }
}
