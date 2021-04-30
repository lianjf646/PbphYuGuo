package com.pbph.yuguo.constant;

import android.os.Environment;

public class ConstantData {

    public final static String NAME = "/yuguo/";
    public final static String PATH = Environment.getExternalStorageDirectory().toString() + NAME;
    public final static String DOWN_LOAD_PATH = Environment.getExternalStorageDirectory().toString() + "/yuguo/down/";

//    正式地址
    public final static String HOST = "https://www.pbdsh.com/yuguo_web/";
    public static final String ALIOSS_PROJECT_APP_ID = "1000000800374099";
    public static final String ALIOSS_PROJECT_APP_SECRET = "1000001906571578";
    public static final String SHARE_URL = "https://www.pbdsh.com/ygweb/#/register?parentUserId=";
    public static final String NET_WORK_PAGE = "https://www.pbdsh.com/";

//    //    测试地址
//    public final static String HOST = "https://testhgs.pcc58.com/huaguoshan_web/";
//
//    public static final String ALIOSS_PROJECT_APP_ID = "1000001687487045";
//    public static final String ALIOSS_PROJECT_APP_SECRET = "1000001168437517";
//    public static final String SHARE_URL = "https://testhgs.pcc58.com/ygweb/?from=singlemessage&isappinstalled=0#/register?parentUserId=";
//    public static final String NET_WORK_PAGE = "https://testhgs.pcc58.com/";

    //vip会员权限 正式
    public static final String VIP_EQUITY_URL = NET_WORK_PAGE + "ygweb/vip_introduce.html";

    // 规则说明
    public static final String RULES = NET_WORK_PAGE + "ygweb/rules.html";

    // 地位失败
//    http://bshop.pbphkj.com/ygweb/about.html?tdsourcetag=s_pctim_aiomsg
    public static final String LOC_FAIL = NET_WORK_PAGE + "ygweb/about.html";

    public static int PayTypeSuccessOrFail = 0;// 0支付失败，1支付成功
    /**
     * 获取第三 token
     */
    public final static String PB_SCM_HOST = "https://api.pcc58.com/scmwebserver/";
//    public final static String PB_SCM_HOST = "http://testhgs.pcc58.com/";

    /****************************** ************************/
    public final static String SOBOT_APP_KEY = "2ebdd8a2036f45bf90d501435f183b7b";

    public final static int PAGE_SIZE = 20;

    public final static int VOUCHER_TYPE = 1;
    public final static int COUPON_TYPE = 2;

}
