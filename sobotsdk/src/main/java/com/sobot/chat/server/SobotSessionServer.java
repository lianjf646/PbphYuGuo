package com.sobot.chat.server;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.sobot.chat.api.apiUtils.GsonUtil;
import com.sobot.chat.api.apiUtils.ZhiChiConstants;
import com.sobot.chat.api.enumtype.CustomerState;
import com.sobot.chat.api.model.ZhiChiInitModeBase;
import com.sobot.chat.api.model.ZhiChiMessageBase;
import com.sobot.chat.api.model.ZhiChiPushMessage;
import com.sobot.chat.api.model.ZhiChiReplyAnswer;
import com.sobot.chat.core.channel.Const;
import com.sobot.chat.core.channel.SobotMsgManager;
import com.sobot.chat.utils.ChatUtils;
import com.sobot.chat.utils.CommonUtils;
import com.sobot.chat.utils.DateUtil;
import com.sobot.chat.utils.LogUtils;
import com.sobot.chat.utils.NotificationUtils;
import com.sobot.chat.utils.ResourceUtils;
import com.sobot.chat.utils.SharedPreferencesUtil;
import com.sobot.chat.utils.ZhiChiConfig;
import com.sobot.chat.utils.ZhiChiConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by jinxl on 2016/9/13.
 */
public class SobotSessionServer extends Service {
    private LocalBroadcastManager localBroadcastManager;
    private MyMessageReceiver receiver;
    private int tmpNotificationId = 0;
    private String currentUid="";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            currentUid = intent.getStringExtra(ZhiChiConstant.SOBOT_CURRENT_IM_PARTNERID);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.i("SobotSessionServer  ---> onCreate");
        initBrocastReceiver();
    }

    /* ???????????????????????? */
    private void initBrocastReceiver() {
        if (receiver == null) {
            receiver = new MyMessageReceiver();
        }
        // ???????????????????????????action????????????????????????action?????????
        IntentFilter filter = new IntentFilter();
        filter.addAction(ZhiChiConstants.receiveMessageBrocast);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        // ?????????????????????
        localBroadcastManager.registerReceiver(receiver, filter);
    }

    public class MyMessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ZhiChiConstants.receiveMessageBrocast.equals(intent.getAction())) {
                // ?????????????????????
                try {
                    Bundle extras = intent.getExtras();
                    if (extras != null) {
                        ZhiChiPushMessage pushMessage = (ZhiChiPushMessage) extras.getSerializable(ZhiChiConstants.ZHICHI_PUSH_MESSAGE);
                        if (pushMessage != null && isNeedShowMessage(pushMessage.getAppId())) {
                            receiveMessage(pushMessage);
                        }
                    }
                } catch (Exception e) {
                    //ignor
                }
            }
        }
    }

    private void receiveMessage(ZhiChiPushMessage pushMessage) {
        if(pushMessage == null){
            return;
        }
        // ?????????????????????
        ZhiChiMessageBase base = new ZhiChiMessageBase();
        base.setSenderName(pushMessage.getAname());
        ZhiChiConfig config = SobotMsgManager.getInstance(getApplication()).getConfig(pushMessage.getAppId());
        if (ZhiChiConstant.push_message_createChat == pushMessage.getType()) {
            if (config.getInitModel() != null) {
                config.adminFace = pushMessage.getAface();
                int type = Integer.parseInt(config.getInitModel().getType());
                if (type == 2 || type == 3 || type == 4) {
                    ZhiChiInitModeBase initModel = config.getInitModel();
                    if (initModel != null) {
                        initModel.setAdminHelloWord(!TextUtils.isEmpty(pushMessage.getAdminHelloWord())?pushMessage.getAdminHelloWord():initModel.getAdminHelloWord());
                        initModel.setAdminTipTime(!TextUtils.isEmpty(pushMessage.getServiceOutTime())?pushMessage.getServiceOutTime():initModel.getAdminTipTime());
                        initModel.setAdminTipWord(!TextUtils.isEmpty(pushMessage.getServiceOutDoc())?pushMessage.getServiceOutDoc():initModel.getAdminTipWord());
                    }
                    createCustomerService(pushMessage.getAppId(),pushMessage.getAname(),pushMessage.getAface());
                }
            }
        } else if (ZhiChiConstant.push_message_receverNewMessage == pushMessage
                .getType()) {// ?????????????????????
            if (config.getInitModel() != null) {
                if (config.customerState == CustomerState.Online) {
                    base.setSender(pushMessage.getAname());
                    base.setSenderName(pushMessage.getAname());
                    base.setSenderFace(pushMessage.getAface());
                    base.setSenderType(ZhiChiConstant.message_sender_type_service + "");
                    ZhiChiReplyAnswer reply;
                    if(TextUtils.isEmpty(pushMessage.getMsgType())){
                        return;
                    }
                    if ("7".equals(pushMessage.getMsgType())) {
                        reply = GsonUtil.jsonToZhiChiReplyAnswer(pushMessage
                                .getContent());
                    } else {
                        reply = new ZhiChiReplyAnswer();
                        reply.setMsgType(pushMessage.getMsgType() + "");
                        reply.setMsg(pushMessage.getContent());
                    }
                    base.setAnswer(reply);
                    // ?????????????????????
                    //?????????????????????????????????
                    if (config.isShowUnreadUi) {
                        config.addMessage(ChatUtils.getUnreadMode(getApplicationContext()));
                        config.isShowUnreadUi = false;
                    }
                    config.addMessage(base);
                    if (config.customerState == CustomerState.Online) {
                        config.customTimeTask = false;
                        config.userInfoTimeTask = true;
                    }
                }
            }

            if(isNeedShowMessage(pushMessage.getAppId())){

                String content;
                int msgType = -1;
                try {
                    JSONObject jsonObject = new JSONObject(pushMessage.getContent());
                    content = jsonObject.optString("msg");
                    msgType = jsonObject.optInt("msgType");
                } catch (JSONException e) {
                    content = "";
                    e.printStackTrace();
                }
                if (msgType != -1 && !TextUtils.isEmpty(content)) {
                    String notificationContent = content;
                    if (msgType == ZhiChiConstant.message_type_textAndPic || msgType ==
                            ZhiChiConstant.message_type_textAndText) {
                        content = "[?????????]";
                        notificationContent = "???????????????????????????";
                    } else if (msgType == ZhiChiConstant.message_type_pic) {
                        content = "[??????]";
                        notificationContent = "[??????]";
                    }
//                    if (!CommonUtils.getRunningActivityName(getApplicationContext()).contains(
//                            "SobotChatActivity")){
                        int localUnreadNum = SobotMsgManager.getInstance(getApplicationContext()).addUnreadCount(pushMessage, DateUtil.toDate(Calendar.getInstance().getTime().getTime(),DateUtil.DATE_FORMAT5),currentUid);
                        Intent intent = new Intent();
                        intent.setAction(ZhiChiConstant.sobot_unreadCountBrocast);
                        intent.putExtra("noReadCount", localUnreadNum);
                        intent.putExtra("content", content);
                        CommonUtils.sendLocalBroadcast(getApplicationContext(), intent);
//                    }
                    showNotification(notificationContent);
                }
            }
        } else if (ZhiChiConstant.push_message_paidui == pushMessage.getType()) {
            // ?????????????????????
            if (config.getInitModel() != null) {
                createCustomerQueue(pushMessage.getAppId(),pushMessage.getCount());
            }
        } else if (ZhiChiConstant.push_message_outLine == pushMessage.getType()) {// ???????????????
            // ??????????????????????????????
            SobotMsgManager.getInstance(getApplication()).clearAllConfig();
            CommonUtils.sendLocalBroadcast(getApplicationContext(),new Intent(Const.SOBOT_CHAT_USER_OUTLINE));
            showNotification("??????????????????????????????");
        }  else if (ZhiChiConstant.push_message_transfer == pushMessage.getType()) {
            if (config.getInitModel() != null) {
                LogUtils.i("???????????????--->"+pushMessage.getName());
                //????????????
                config.activityTitle = pushMessage.getName(); // ?????????????????????????????????
                config.adminFace = pushMessage.getFace();
                config.currentUserName = pushMessage.getName();
            }
        }
    }

    /**
     * ??????????????????????????????
     * ???????????????????????????
     * @param num ?????????????????????
     */
    private void createCustomerQueue(String appId,String num){
        ZhiChiConfig config = SobotMsgManager.getInstance(getApplication()).getConfig(appId);
        if (config.customerState == CustomerState.Queuing && !TextUtils
                .isEmpty(num) && Integer.parseInt(num) > 0) {
            ZhiChiInitModeBase initModel = config.getInitModel();
            if(initModel == null){
                return;
            }
            int type = Integer.parseInt(initModel.getType());
            config.queueNum = Integer.parseInt(num);
            //???????????????????????????
            config.addMessage(ChatUtils.getInLineHint(getApplicationContext(),config.queueNum));

            if (type == ZhiChiConstant.type_custom_only) {
                //????????????
                config.activityTitle = ChatUtils.getLogicTitle(getApplicationContext(),false, getResString("sobot_in_line_title"),
                        initModel.getCompanyName());
                config.bottomViewtype = ZhiChiConstant.bottomViewtype_onlycustomer_paidui;
            } else {
                config.activityTitle = ChatUtils.getLogicTitle(getApplicationContext(),false, initModel.getRobotName(),
                        initModel.getCompanyName());
                config.bottomViewtype = ZhiChiConstant.bottomViewtype_paidui;
            }
        }
    }

    /**
     * ????????????????????????
     * @param name ???????????????
     * @param face  ???????????????
     */
    private void createCustomerService(String appId,String name,String face){
        ZhiChiConfig config = SobotMsgManager.getInstance(getApplication()).getConfig(appId);
        ZhiChiInitModeBase initModel = config.getInitModel();
        if(initModel == null){
            return;
        }
        //????????????????????????????????????
        //????????????
        config.current_client_model = ZhiChiConstant.client_model_customService;
        config.customerState = CustomerState.Online;
        config.isAboveZero = false;
        config.isComment = false;// ???????????? ????????? ?????????
        config.queueNum = 0;
        config.currentUserName = TextUtils.isEmpty(name)?"":name;
        //?????????xx????????????
        config.addMessage(ChatUtils.getServiceAcceptTip(getApplicationContext(),name));


        //?????????????????????
        String adminHolloWord = SharedPreferencesUtil.getStringData(getApplicationContext(),ZhiChiConstant.SOBOT_CUSTOMADMINHELLOWORD,"");
        if (!TextUtils.isEmpty(adminHolloWord)){
            config.addMessage(ChatUtils.getServiceHelloTip(name,face,adminHolloWord));
        } else {
            config.addMessage(ChatUtils.getServiceHelloTip(name,face,initModel.getAdminHelloWord()));
        }

        //????????????
        config.activityTitle = ChatUtils.getLogicTitle(getApplicationContext(),false, name,
                initModel.getCompanyName());
        //??????????????????
        config.bottomViewtype = ZhiChiConstant.bottomViewtype_customer;

        // ??????????????????
        config.userInfoTimeTask = true;
        config.customTimeTask = false;

        // ???????????????????????????????????????????????????
        config.hideItemTransferBtn();

        if(isNeedShowMessage(appId)){
            showNotification(String.format(getResString("sobot_service_accept"), config.currentUserName));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // ?????????????????????
        if (localBroadcastManager != null) {
            localBroadcastManager.unregisterReceiver(receiver);
        }
        LogUtils.i("SobotSessionServer  ---> onDestroy");
    }

    public String getResString(String name) {
        return getResources().getString(getResStringId(name));
    }

    public int getResStringId(String name) {
        return ResourceUtils.getIdByName(getApplicationContext(), "string", name);
    }

    /**
     * ???????????????
     *
     * @param content
     */
    private void showNotification(String content) {
        boolean notification_flag = SharedPreferencesUtil.getBooleanData(getApplicationContext(), Const
                .SOBOT_NOTIFICATION_FLAG, false);

        if (notification_flag) {
            String notificationTitle = "????????????";
            NotificationUtils.createNotification(getApplicationContext(), null, notificationTitle, content, content, getNotificationId());
        }
    }


    /**
     * ???????????????id  ??????id?????????999???????????????0??????1????????????
     *
     * @return
     */
    private int getNotificationId() {
        if (tmpNotificationId == 999) {
            tmpNotificationId = 0;
        }
        tmpNotificationId++;
        return tmpNotificationId;
    }

    private boolean isNeedShowMessage(String appkey) {
        String currentAppid = SharedPreferencesUtil.getStringData(getApplicationContext(), ZhiChiConstant.SOBOT_CURRENT_IM_APPID, "");
        return !currentAppid.equals(appkey);
    }
}