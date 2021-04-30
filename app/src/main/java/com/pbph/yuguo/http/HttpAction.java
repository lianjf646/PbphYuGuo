package com.pbph.yuguo.http;


import android.text.TextUtils;
import android.util.Log;

import com.lzy.imagepicker.bean.ImageItem;
import com.pbph.yuguo.base.BaseRequest;
import com.pbph.yuguo.base.BaseResponse;
import com.pbph.yuguo.base.SCMRequest;
import com.pbph.yuguo.constant.ConstantData;
import com.pbph.yuguo.request.AddShoppingCartResquest;
import com.pbph.yuguo.request.AlipayauthsignResquest;
import com.pbph.yuguo.request.AppChangeVipInfoResquest;
import com.pbph.yuguo.request.AppChangestoredMoneyResquest;
import com.pbph.yuguo.request.AppversionResquest;
import com.pbph.yuguo.request.CommitFeedbackResquest;
import com.pbph.yuguo.request.DelReceiverAddressResquest;
import com.pbph.yuguo.request.DelShoppingCartResquest;
import com.pbph.yuguo.request.DeleteMessageRequest;
import com.pbph.yuguo.request.GetActivityGoodsInfoListResquest;
import com.pbph.yuguo.request.GetAddressListByCustomerIdResquest;
import com.pbph.yuguo.request.GetAppChangeCustomerInfoRequest;
import com.pbph.yuguo.request.GetAppChangeCustomerPayPasswordRequest;
import com.pbph.yuguo.request.GetAppChangeCustomerPhoneRequest;
import com.pbph.yuguo.request.GetAppRedPackageDealsInfoRequest;
import com.pbph.yuguo.request.GetAppSmsCodeResquest;
import com.pbph.yuguo.request.GetAppStorageValueRecordResquest;
import com.pbph.yuguo.request.GetAppWithdrawCashListResquest;
import com.pbph.yuguo.request.GetAuthenticationResquest;
import com.pbph.yuguo.request.GetCancelOrderRequest;
import com.pbph.yuguo.request.GetConfirmReceiptRequest;
import com.pbph.yuguo.request.GetCouponListForSubmitOrderRequest;
import com.pbph.yuguo.request.GetCouponListRequest;
import com.pbph.yuguo.request.GetCustomerInfoByIdRequest;
import com.pbph.yuguo.request.GetEvaluateOrderRequest;
import com.pbph.yuguo.request.GetGoodsDetailResquest;
import com.pbph.yuguo.request.GetGoodsEvaluateListRequest;
import com.pbph.yuguo.request.GetGoodsEvaluateOrderDetailsRequest;
import com.pbph.yuguo.request.GetGoodsFirstCategoryResquest;
import com.pbph.yuguo.request.GetGoodsGroupActivityListResquest;
import com.pbph.yuguo.request.GetGoodsGroupDetailResquest;
import com.pbph.yuguo.request.GetGoodsListResquest;
import com.pbph.yuguo.request.GetGoodsStorageNumRequest;
import com.pbph.yuguo.request.GetHomePageImgResquest;
import com.pbph.yuguo.request.GetHotSaleGoodsListResquest;
import com.pbph.yuguo.request.GetIndexPopupCouponResquest;
import com.pbph.yuguo.request.GetIndexRotationChartRequest;
import com.pbph.yuguo.request.GetInviteGallopListResquest;
import com.pbph.yuguo.request.GetInviteICouponListResquest;
import com.pbph.yuguo.request.GetInviteVipCouponListResquest;
import com.pbph.yuguo.request.GetJpushIdResquest;
import com.pbph.yuguo.request.GetJuniorUserMoneyListResquest;
import com.pbph.yuguo.request.GetMemberBenefitsListRequest;
import com.pbph.yuguo.request.GetMemberDayActivityGoodsRequest;
import com.pbph.yuguo.request.GetMemberOpenPriceRequest;
import com.pbph.yuguo.request.GetMemberProfitPercentRequest;
import com.pbph.yuguo.request.GetMessageInfoAndUnreadMessageCountRequest;
import com.pbph.yuguo.request.GetMessageInfoListResquest;
import com.pbph.yuguo.request.GetMyGoodsGroupActivityListResquest;
import com.pbph.yuguo.request.GetMyInvitationListListResquest;
import com.pbph.yuguo.request.GetMyPointSourceListRequest;
import com.pbph.yuguo.request.GetNoReadMessageCountRequest;
import com.pbph.yuguo.request.GetOrderConfigResquest;
import com.pbph.yuguo.request.GetOrderDetailRequest;
import com.pbph.yuguo.request.GetOrderListRequest;
import com.pbph.yuguo.request.GetOrderNumRequest;
import com.pbph.yuguo.request.GetOssTokenResquest;
import com.pbph.yuguo.request.GetPageInfoForSubmitOrderResquest;
import com.pbph.yuguo.request.GetPinkageActivityGoodsResquest;
import com.pbph.yuguo.request.GetProjectTokenResquest;
import com.pbph.yuguo.request.GetReceiveCouponRequest;
import com.pbph.yuguo.request.GetReceiverAddressListResquest;
import com.pbph.yuguo.request.GetRedPacketWithdrawRuleResquest;
import com.pbph.yuguo.request.GetShoppingCartCountResquest;
import com.pbph.yuguo.request.GetShoppingCartResquest;
import com.pbph.yuguo.request.GetStoreGoodsSpecListRequest;
import com.pbph.yuguo.request.GetStoreInfoByLocationResquest;
import com.pbph.yuguo.request.GetStoreListRequest;
import com.pbph.yuguo.request.GetStoredConfigResquest;
import com.pbph.yuguo.request.GetSysConfigRequest;
import com.pbph.yuguo.request.GetUsableCouponListForGoodsRequest;
import com.pbph.yuguo.request.GetUserInviteNumberListResquest;
import com.pbph.yuguo.request.GetVipGoodsListRequest;
import com.pbph.yuguo.request.GetVoucherListForSubmitOrderRequest;
import com.pbph.yuguo.request.GetWaitEvaluateListRequest;
import com.pbph.yuguo.request.LoginResquest;
import com.pbph.yuguo.request.OrderAgainRequest;
import com.pbph.yuguo.request.PayOrderResquest;
import com.pbph.yuguo.request.QrOrBarCodeRequest;
import com.pbph.yuguo.request.ReadMessageRequest;
import com.pbph.yuguo.request.ReceiveIndexAllCouponResquest;
import com.pbph.yuguo.request.Riderh5ViewResquest;
import com.pbph.yuguo.request.SaveReceiverAddressResquest;
import com.pbph.yuguo.request.SendRedResquest;
import com.pbph.yuguo.request.SetActivityGiftCheckedStatusResquest;
import com.pbph.yuguo.request.StoreOrderListRequest;
import com.pbph.yuguo.request.SubmitOrderForGroupResquest;
import com.pbph.yuguo.request.SubmitOrderResquest;
import com.pbph.yuguo.request.ToAuthenticationResquest;
import com.pbph.yuguo.request.UpdateReceiverAddressResquest;
import com.pbph.yuguo.request.UpdateShoppingCartAllResquest;
import com.pbph.yuguo.request.UpdateShoppingCartResquest;
import com.pbph.yuguo.response.AddShoppingCartResponse;
import com.pbph.yuguo.response.AliPayAuthSignResponse;
import com.pbph.yuguo.response.AliPayOrderResponse;
import com.pbph.yuguo.response.AppChangeVipInfoResponse;
import com.pbph.yuguo.response.AppChangestoredMoneyResponse;
import com.pbph.yuguo.response.BackOrderListResponse;
import com.pbph.yuguo.response.CancelOrderResponse;
import com.pbph.yuguo.response.CommitFeedbackResponse;
import com.pbph.yuguo.response.CouponResponse;
import com.pbph.yuguo.response.DelReceiverAddressResponse;
import com.pbph.yuguo.response.DelShoppingCartResponse;
import com.pbph.yuguo.response.DeleteMessageResponse;
import com.pbph.yuguo.response.EvaluateOrderResponse;
import com.pbph.yuguo.response.GetActivityGoodsInfoListResponse;
import com.pbph.yuguo.response.GetAddressListByCustomerIdResponse;
import com.pbph.yuguo.response.GetAppRedPackageDealsInfoResponse;
import com.pbph.yuguo.response.GetAppStorageValueRecordResponse;
import com.pbph.yuguo.response.GetAppWithdrawCashListResponse;
import com.pbph.yuguo.response.GetAuthenticationResponse;
import com.pbph.yuguo.response.GetCustomerInfoByIdResponse;
import com.pbph.yuguo.response.GetGoodsDetailResponse;
import com.pbph.yuguo.response.GetGoodsFirstCategoryResponse;
import com.pbph.yuguo.response.GetGoodsGroupDetailResponse;
import com.pbph.yuguo.response.GetGoodsListResponse;
import com.pbph.yuguo.response.GetGoodsStorageNumResponse;
import com.pbph.yuguo.response.GetHomePageImgResponse;
import com.pbph.yuguo.response.GetHotSaleGoodsListResponse;
import com.pbph.yuguo.response.GetIndexPopupCouponResponse;
import com.pbph.yuguo.response.GetIndexRotationChartResponse;
import com.pbph.yuguo.response.GetInviteGallopListResponse;
import com.pbph.yuguo.response.GetInviteICouponListResponse;
import com.pbph.yuguo.response.GetInviteVipCouponListResponse;
import com.pbph.yuguo.response.GetJpushIdResponse;
import com.pbph.yuguo.response.GetJuniorUserMoneyListResponse;
import com.pbph.yuguo.response.GetMemberDayActivityGoodsResponse;
import com.pbph.yuguo.response.GetMemberOpenPriceResponse;
import com.pbph.yuguo.response.GetMemberProfitPercentResponse;
import com.pbph.yuguo.response.GetMessageInfoAndUnreadMessageCountResponse;
import com.pbph.yuguo.response.GetMessageInfoListResponse;
import com.pbph.yuguo.response.GetMyInvitationListResponse;
import com.pbph.yuguo.response.GetMyPointSourceListResponse;
import com.pbph.yuguo.response.GetNoReadMessageCountResponse;
import com.pbph.yuguo.response.GetOrderConfigResponse;
import com.pbph.yuguo.response.GetOrderNumResponse;
import com.pbph.yuguo.response.GetOssTokenResponse;
import com.pbph.yuguo.response.GetPageInfoForSubmitOrderResponse;
import com.pbph.yuguo.response.GetPinkageActivityGoodsResponse;
import com.pbph.yuguo.response.GetProjectTokenResponse;
import com.pbph.yuguo.response.GetReceiverAddressListResponse;
import com.pbph.yuguo.response.GetRedPacketWithdrawRuleResponse;
import com.pbph.yuguo.response.GetShoppingCartCountResponse;
import com.pbph.yuguo.response.GetShoppingCartResponse;
import com.pbph.yuguo.response.GetStoreGoodsSpecListResponse;
import com.pbph.yuguo.response.GetStoreInfoByLocationResponse;
import com.pbph.yuguo.response.GetStoreListResponse;
import com.pbph.yuguo.response.GetStoredConfigResponse;
import com.pbph.yuguo.response.GetSysConfigResponse;
import com.pbph.yuguo.response.GetUserInviteNumberListResponse;
import com.pbph.yuguo.response.GoodsEvaluateOrderDetailsResponse;
import com.pbph.yuguo.response.GoodsEvaluateResponse;
import com.pbph.yuguo.response.GroupListResponse;
import com.pbph.yuguo.response.LoginResponse;
import com.pbph.yuguo.response.MemberBenefitsListResponse;
import com.pbph.yuguo.response.OrderAgainResponse;
import com.pbph.yuguo.response.OrderDetailResponse;
import com.pbph.yuguo.response.OrderListResponse;
import com.pbph.yuguo.response.QrOrBarCodeResponse;
import com.pbph.yuguo.response.ReadMessageResponse;
import com.pbph.yuguo.response.ReceiveIndexAllCouponResponse;
import com.pbph.yuguo.response.Riderh5ViewResponse;
import com.pbph.yuguo.response.SaveReceiverAddressResponse;
import com.pbph.yuguo.response.SendRedResponse;
import com.pbph.yuguo.response.SetActivityGiftCheckedStatusResponse;
import com.pbph.yuguo.response.StoreOrderDetailResponse;
import com.pbph.yuguo.response.StoreOrderListResponse;
import com.pbph.yuguo.response.SubmitOrderForGroupResponse;
import com.pbph.yuguo.response.SubmitOrderResponse;
import com.pbph.yuguo.response.ToAuthenticationResponse;
import com.pbph.yuguo.response.UpdateBeanResponse;
import com.pbph.yuguo.response.UpdateReceiverAddressResponse;
import com.pbph.yuguo.response.UpdateShoppingCartAllResponse;
import com.pbph.yuguo.response.UpdateShoppingCartResponse;
import com.pbph.yuguo.response.Upload2ossResponse;
import com.pbph.yuguo.response.VipGoodsListResponse;
import com.pbph.yuguo.response.WaitEvaluateListResponse;
import com.pbph.yuguo.response.WxPayOrderResponse;
import com.pbph.yuguo.update.UpdateManager;
import com.pbph.yuguo.util.FileUtil;
import com.pbph.yuguo.util.MD5Utils;
import com.pbph.yuguo.util.RSAUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;


public class HttpAction {

    private HttpAction() {

    }

    public static HttpAction getInstance() {
        return HttpActionHolder.instance;
    }

    //登录
    public Flowable<LoginResponse> login(LoginResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().login(getBody(resquest)));
    }

    //获取短信验证码
    public Flowable<BaseResponse> getAppSmsCode(GetAppSmsCodeResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getAppSmsCode(getBody(resquest)));
    }

    //获取商品详情
    public Flowable<GetGoodsDetailResponse> getGoodsDetail(GetGoodsDetailResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getGoodsDetail(getBody(resquest)));
    }

    public Flowable<GetGoodsGroupDetailResponse> getGoodsGroupDetail(GetGoodsGroupDetailResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getGoodsGroupDetail(getBody(resquest)));
    }

    //    获取包邮活动商品清单
    public Flowable<GetPinkageActivityGoodsResponse> getPinkageActivityGoods(GetPinkageActivityGoodsResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getPinkageActivityGoods(getBody(resquest)));
    }

    public Flowable<GetGoodsStorageNumResponse> getGoodsStorageNum(GetGoodsStorageNumRequest resquest) {
        return applySchedulers(HttpClient.getHttpService().getGoodsStorageNum(getBody(resquest)));
    }

    //获取订单填写页面信息的方法
    public Flowable<GetPageInfoForSubmitOrderResponse> getPageInfoForSubmitOrder(GetPageInfoForSubmitOrderResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getPageInfoForSubmitOrder(getBody(resquest)));
    }

    //下单
    public Flowable<SubmitOrderResponse> submitOrder(SubmitOrderResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().submitOrder(getBody(resquest)));
    }

    public Flowable<SubmitOrderForGroupResponse> submitOrderForGroup(SubmitOrderForGroupResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().submitOrderForGroup(getBody(resquest)));
    }

    public Flowable<ResponseBody> uploadFiles(String url, ArrayList<ImageItem> items) {
        Flowable<ResponseBody> flowable = Flowable.fromIterable(items).map((imageItem) -> new File(imageItem.path)).flatMap(
                (file) -> {
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), fileBody);
                    return HttpClient.getHttpService().uploadFiles(url, body);
                });
        return applySchedulers(flowable);
    }


    public Flowable<ResponseBody> uploadFiles(ImageItem items) {
        File file = new File(items.path);
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", items.name, fileBody);
        return applySchedulers(HttpClient.getHttpService().uploadFiles(body));
    }


    public Flowable<UpdateManager.DownInfo> downloadFile(String fileUrl, UpdateManager.OnDownLoadUpdateListener updateListener) {
        return HttpClient.getHttpService().downloadFile(fileUrl).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .map((ResponseBody responseBody) -> {
                    return new UpdateManager.DownInfo(responseBody.contentLength(), responseBody.byteStream());
                }).observeOn(Schedulers.computation()) // 用于计算任务
                .doOnNext((UpdateManager.DownInfo downInfo) -> FileUtil.getInstance().writeFile(downInfo, updateListener))
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Flowable<ResponseBody> get(String url) {
        return applySchedulers(HttpClient.getHttpService().get(url));
    }


    private RequestBody getBody(String request, boolean isEncryption) {
        String json = request;
        Log.e("getBody", json);
        if (isEncryption) {
            json = getEncryptionString(json);
            Log.e("getEncryptionString", json);
        }
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    }

    private RequestBody getBody(BaseRequest request) {
        return getBody(request.getJsonData().toString(), false);
    }

    private RequestBody getBody(SCMRequest request) {
        return getBody(request.getJsonData().toString(), false);
    }


    private RequestBody getBody(BaseRequest request, boolean isEncryption) {
        JSONObject object = request.toJson();
//        Log.e("getBody", "getBody=" + object.toString());
        FormBody.Builder builder = new FormBody.Builder();
        Iterator<String> keys = object.keys();

        List<String> list = new ArrayList<>(object.length());
        Map<String, String> map = new HashMap<>();

        while (keys.hasNext()) {
            String key = keys.next();
            list.add(key);
            try {
                String data = object.getString(key);

                map.put(key, data);

                builder.add(key, data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String saltValueSign = getSaltValueSign(list, map);
        builder.add("saltValueSign", saltValueSign);

        return builder.build();
//        return getBody(request.toJson().toString(), isEncryption);
    }


    public String getSaltValueSign(List<String> keys, Map<String, String> map) {

        keys.add("saltValue");
        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();
        for (String k : keys) {
            String v = null;

            if (k.equals("saltValue")) {
                v = "*#06#JM666#*";
            } else {
                v = map.get(k).trim();
            }

            if (TextUtils.isEmpty(v)) continue;

            sb.append(k).append("=").append(v);
        }

        Log.e("===========>", "加密前签名：" + sb.toString());
        String saltValueSign = null;
        try {
            saltValueSign = MD5Utils.encrypt(sb.toString().getBytes("utf-8")).toLowerCase();
            Log.e("===========>", "加密后签名：" + saltValueSign);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saltValueSign;
    }


    private String getEncryptionString(@NonNull String string) {
        return RSAUtil.getInstance().encryptDataByPublicKey(string.getBytes());
//        return TestRSAUtil.getInstance().encryptByPublicKey(string.getBytes());
    }


    public Flowable<Upload2ossResponse> upload2oss(String path, String fileType) {

        File file = new File(path);
        String fileName = file.getName();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        MultipartBody.Builder builder = new MultipartBody.Builder();
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
        builder.addFormDataPart("file", file.getName(), fileBody);

//        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(),
// fileBody);
//        builder.addPart(body);
///////////
        builder.addFormDataPart("fileType", fileType);
        builder.addFormDataPart("suffixName", suffixName);
        /////////
        builder.setType(MultipartBody.FORM);

        MultipartBody multipartBody = builder.build();

        return applySchedulers(HttpClient.getHttpService().upload2oss(multipartBody));
    }


    private static class HttpActionHolder {
        private static HttpAction instance = new HttpAction();
    }

    protected <T> Flowable<T> applySchedulers(Flowable<T> responseFlowable) {
        return responseFlowable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
//                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
//                .flatMap((t) -> flatFlowableResponse(t));

    }


    private static String convertParams2String(Map<String, Object> requestParamsMap) {
        StringBuffer params = new StringBuffer();
        Iterator it = requestParamsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry element = (Map.Entry) it.next();
            params.append(element.getKey());
            params.append("=");
            params.append(element.getValue());
            params.append("&");
        }
        if (params.length() > 0) {
            params.deleteCharAt(params.length() - 1);
        }
        Log.e("convertParams2String", "params=   " + params.toString());
        return params.toString();
    }

//    private <T> Flowable<T> flatFlowableResponse(@NonNull final T response) {
//        return Flowable.create((flowableEmitter) -> {
//            flowableEmitter.onNext(response);
//            flowableEmitter.onComplete();
//        }, BackpressureStrategy.BUFFER);
//    }

    public Flowable<GetShoppingCartResponse> getShoppingCart(GetShoppingCartResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getShoppingCart(getBody(resquest)));
    }

    public Flowable<UpdateShoppingCartResponse> updateShoppingCart(UpdateShoppingCartResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().updateShoppingCart(getBody(resquest)));
    }

    public Flowable<GetActivityGoodsInfoListResponse> getActivityGoodsInfoList(GetActivityGoodsInfoListResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getActivityGoodsInfoList(getBody(resquest)));
    }

    public Flowable<DelShoppingCartResponse> delShoppingCart(DelShoppingCartResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().delShoppingCart(getBody(resquest)));
    }

    public Flowable<UpdateShoppingCartAllResponse> updateShoppingCartAll(UpdateShoppingCartAllResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().updateShoppingCartAll(getBody(resquest)));
    }

    public Flowable<SetActivityGiftCheckedStatusResponse> setActivityGiftCheckedStatus(SetActivityGiftCheckedStatusResquest
                                                                                               resquest) {
        return applySchedulers(HttpClient.getHttpService().setActivityGiftCheckedStatus(getBody(resquest)));
    }

    public Flowable<GetAddressListByCustomerIdResponse> getAddressListByCustomerId(GetAddressListByCustomerIdResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getAddressListByCustomerId(getBody(resquest)));
    }

    public Flowable<GetJpushIdResponse> getJpushId(GetJpushIdResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getJpushId(getBody(resquest)));
    }

    public Flowable<GetShoppingCartCountResponse> getShoppingCartCount(GetShoppingCartCountResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getShoppingCartCount(getBody(resquest)));
    }

    public Flowable<GetStoreInfoByLocationResponse> getStoreInfoByLocation(GetStoreInfoByLocationResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getStoreInfoByLocation(getBody(resquest)));
    }

    public Flowable<GetHotSaleGoodsListResponse> getHotSaleGoodsList(GetHotSaleGoodsListResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getHotSaleGoodsList(getBody(resquest)));
    }

    public Flowable<GetIndexRotationChartResponse> getIndexRotationChart(GetIndexRotationChartRequest resquest) {
        return applySchedulers(HttpClient.getHttpService().getIndexRotationChart(getBody(resquest)));
    }

    public Flowable<GetIndexPopupCouponResponse> getIndexPopupCoupon(GetIndexPopupCouponResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getIndexPopupCoupon(getBody(resquest)));
    }

    public Flowable<ReceiveIndexAllCouponResponse> receiveIndexAllCoupon(ReceiveIndexAllCouponResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().receiveIndexAllCoupon(getBody(resquest)));
    }

    public Flowable<GetGoodsFirstCategoryResponse> getGoodsFirstCategory(GetGoodsFirstCategoryResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getGoodsFirstCategory(getBody(resquest)));
    }

    /**
     * 获取商品列表（上架时间排序,一级分类页面）(包含搜索)
     *
     * @param resquest
     * @return
     */
    public Flowable<GetGoodsListResponse> getGoodsList(GetGoodsListResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getGoodsList(getBody(resquest)));
    }

    public Flowable<AddShoppingCartResponse> addShoppingCart(AddShoppingCartResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().addShoppingCart(getBody(resquest)));
    }

    //邀请好友获得优惠券
    public Flowable<GetInviteICouponListResponse> getInviteICouponList(GetInviteICouponListResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getInviteICouponList(getBody(resquest)));
    }

    //邀请会员获得优惠券
    public Flowable<GetInviteVipCouponListResponse> getInviteVipCouponList(GetInviteVipCouponListResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getInviteVipCouponList(getBody(resquest)));
    }

    //我的邀请
    public Flowable<GetMyInvitationListResponse> getMyInvitationList(GetMyInvitationListListResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getMyInvitationList(getBody(resquest)));
    }

    public Flowable<GetUserInviteNumberListResponse> getUserInviteNumberList(GetUserInviteNumberListResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getUserInviteNumberList(getBody(resquest)));
    }

    //认证状态
    public Flowable<GetAuthenticationResponse> getAuthentication(GetAuthenticationResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getAuthentication(getBody(resquest)));
    }

    //提交认证
    public Flowable<ToAuthenticationResponse> toAuthentication(ToAuthenticationResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().toAuthentication(getBody(resquest)));
    }

    //获取订单列表
    public Flowable<OrderListResponse> getOrderList(GetOrderListRequest request) {
        return applySchedulers(HttpClient.getHttpService().getOrderList(getBody(request)));
    }

    public Flowable<GetJuniorUserMoneyListResponse> getJuniorUserMoneyList(GetJuniorUserMoneyListResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getJuniorUserMoneyList(getBody(resquest)));
    }

//    public Flowable<GetAppRedPackageMoneyResponse> getAppRedPackageMoney(GetAppRedPackageMoneyResquest resquest) {
//        return applySchedulers(HttpClient.getHttpService().getAppRedPackageMoney(getBody(resquest)));
//    }

    public Flowable<GetRedPacketWithdrawRuleResponse> getRedPacketWithdrawRule(GetRedPacketWithdrawRuleResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getRedPacketWithdrawRule(getBody(resquest)));
    }

    public Flowable<GetAppWithdrawCashListResponse> getAppWithdrawCashList(GetAppWithdrawCashListResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getAppWithdrawCashList(getBody(resquest)));
    }

    public Flowable<GroupListResponse> getMyGoodsGroupActivityList(GetMyGoodsGroupActivityListResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getMyGoodsGroupActivityList(getBody(resquest)));
    }

    public Flowable<GroupListResponse> getGoodsGroupActivityList(GetGoodsGroupActivityListResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getGoodsGroupActivityList(getBody(resquest)));
    }

    public Flowable<GetInviteGallopListResponse> getInviteGallopList(GetInviteGallopListResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getInviteGallopList(getBody(resquest)));
    }

    public Flowable<GetStoredConfigResponse> getStoredConfig(GetStoredConfigResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getStoredConfig(getBody(resquest)));
    }

//    public Flowable<GetAppStoredMoneyResponse> getAppStoredMoney(GetAppStoredMoneyResquest resquest) {
//        return applySchedulers(HttpClient.getHttpService().getAppStoredMoney(getBody(resquest)));
//    }

    public Flowable<GetAppStorageValueRecordResponse> getAppStorageValueRecord(GetAppStorageValueRecordResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getAppStorageValueRecord(getBody(resquest)));
    }


    //保存收货地址
    public Flowable<SaveReceiverAddressResponse> saveReceiverAddress(SaveReceiverAddressResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().saveReceiverAddress(getBody(resquest)));
    }

    //获取收货地址列表（收货地址是否在配送范围）
    public Flowable<GetReceiverAddressListResponse> getReceiverAddressList(GetReceiverAddressListResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().getReceiverAddressList(getBody(resquest)));
    }

    //删除收货地址
    public Flowable<DelReceiverAddressResponse> delReceiverAddress(DelReceiverAddressResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().delReceiverAddress(getBody(resquest)));
    }

    //获取订单详情
    public Flowable<OrderDetailResponse> getOrderDetail(GetOrderDetailRequest request) {
        return applySchedulers(HttpClient.getHttpService().getOrderDetail(getBody(request)));
    }

    //修改地址
    public Flowable<UpdateReceiverAddressResponse> updateReceiverAddress(UpdateReceiverAddressResquest resquest) {
        return applySchedulers(HttpClient.getHttpService().updateReceiverAddress(getBody(resquest)));
    }

    //获取优惠券列表
    public Flowable<CouponResponse> getCustomerCouponList(GetCouponListRequest request) {
        return applySchedulers(HttpClient.getHttpService().getCustomerCouponList(getBody(request)));
    }

    //获取抵扣券列表
    public Flowable<CouponResponse> getVoucherListForSubmitOrder(GetVoucherListForSubmitOrderRequest request) {
        return applySchedulers(HttpClient.getHttpService().getVoucherListForSubmitOrder(getBody(request)));
    }


    public Flowable<CouponResponse> getUsableCouponListForGoods(GetUsableCouponListForGoodsRequest request) {
        return applySchedulers(HttpClient.getHttpService().getUsableCouponListForGoods(getBody(request)));
    }

    public Flowable<CouponResponse> getCouponListForSubmitOrder(GetCouponListForSubmitOrderRequest request) {
        return applySchedulers(HttpClient.getHttpService().getCouponListForSubmitOrder(getBody(request)));
    }

    //获取收支明细
    public Flowable<GetAppRedPackageDealsInfoResponse> getAppRedPackageDealsInfo(GetAppRedPackageDealsInfoRequest request) {
        return applySchedulers(HttpClient.getHttpService().getAppRedPackageDealsInfo(getBody(request)));
    }

    //根据用户id查询用户详情
    public Flowable<GetCustomerInfoByIdResponse> getCustomerInfoById(GetCustomerInfoByIdRequest request) {
        return applySchedulers(HttpClient.getHttpService().getCustomerInfoById(getBody(request)));
    }

    //
    public Flowable<GetMyPointSourceListResponse> getMyPointSourceList(GetMyPointSourceListRequest request) {
        return applySchedulers(HttpClient.getHttpService().getMyPointSourceList(getBody(request)));
    }

    //获取待评价商品列表
    public Flowable<WaitEvaluateListResponse> getWaitEvaluateList(GetWaitEvaluateListRequest request) {
        return applySchedulers(HttpClient.getHttpService().getWaitEvaluateList(getBody(request)));
    }

    public Flowable<GoodsEvaluateOrderDetailsResponse> getGoodsEvaluateOrderDetails(GetGoodsEvaluateOrderDetailsRequest request) {
        return applySchedulers(HttpClient.getHttpService().getGoodsEvaluateOrderDetails(getBody(request)));
    }

    public Flowable<EvaluateOrderResponse> evaluateOrder(GetEvaluateOrderRequest request) {
        return applySchedulers(HttpClient.getHttpService().evaluateOrder(getBody(request)));
    }

    public Flowable<EvaluateOrderResponse> evaluateBaskOrder(GetEvaluateOrderRequest request) {
        return applySchedulers(HttpClient.getHttpService().evaluateBaskOrder(getBody(request)));
    }

    //会员-充值成功更新会员信息,交易记录
    public Flowable<AppChangeVipInfoResponse> appChangeVipInfo(AppChangeVipInfoResquest request) {
        return applySchedulers(HttpClient.getHttpService().appChangeVipInfo(getBody(request)));
    }

    //支付订单(待付款订单) Wx
    public Flowable<WxPayOrderResponse> wxPayOrder(PayOrderResquest request) {
        return applySchedulers(HttpClient.getHttpService().wxPayOrder(getBody(request)));
    }

    //支付订单(待付款订单) Ali
    public Flowable<AliPayOrderResponse> aLiPayOrder(PayOrderResquest request) {
        return applySchedulers(HttpClient.getHttpService().aLiPayOrder(getBody(request)));
    }

    //意见反馈
    public Flowable<CommitFeedbackResponse> commitFeedback(CommitFeedbackResquest request) {
        return applySchedulers(HttpClient.getHttpService().commitFeedback(getBody(request)));
    }

    //获取订单状态数量
    public Flowable<GetOrderNumResponse> getOrderStatusNum(GetOrderNumRequest request) {
        return applySchedulers(HttpClient.getHttpService().getOrderStatusNum(getBody(request)));
    }

    //余额储值-储值-更新充值记录表
    public Flowable<AppChangestoredMoneyResponse> appChangestoredMoney(AppChangestoredMoneyResquest request) {
        return applySchedulers(HttpClient.getHttpService().appChangestoredMoney(getBody(request)));
    }

    //验证手机号
    public Flowable<AppChangestoredMoneyResponse> appChangeCustomerPhone(GetAppChangeCustomerPhoneRequest request) {
        return applySchedulers(HttpClient.getHttpService().appChangeCustomerPhone(getBody(request)));
    }

    //获取退款订单列表
    public Flowable<BackOrderListResponse> getBackOrderList(GetOrderListRequest request) {
        return applySchedulers(HttpClient.getHttpService().getBackOrderList(getBody(request)));
    }

    //修改支付密码
    public Flowable<BaseResponse> appChangeCustomerPayPassword(GetAppChangeCustomerPayPasswordRequest request) {
        return applySchedulers(HttpClient.getHttpService().appChangeCustomerPayPassword(getBody(request)));
    }

    //修改用户信息
    public Flowable<BaseResponse> appChangeCustomerInfo(GetAppChangeCustomerInfoRequest request) {
        return applySchedulers(HttpClient.getHttpService().appChangeCustomerInfo(getBody(request)));
    }

    //领取优惠券
    public Flowable<BaseResponse> receiveCoupon(GetReceiveCouponRequest request) {
        return applySchedulers(HttpClient.getHttpService().receiveCoupon(getBody(request)));
    }

    //获取商品评价列表
    public Flowable<GoodsEvaluateResponse> getGoodsEvaluateList(GetGoodsEvaluateListRequest request) {
        return applySchedulers(HttpClient.getHttpService().getGoodsEvaluateList(getBody(request)));
    }

    //售后规则
    public Flowable<GetSysConfigResponse> getSysConfig(GetSysConfigRequest request) {
        return applySchedulers(HttpClient.getHttpService().getSysConfig(getBody(request)));
    }

    //取消订单
    public Flowable<CancelOrderResponse> cancelOrder(GetCancelOrderRequest request) {
        return applySchedulers(HttpClient.getHttpService().cancelOrder(getBody(request)));
    }


    //我的红包-提现成功更新余额,交易记录
    public Flowable<SendRedResponse> sendRed(SendRedResquest request) {
        return applySchedulers(HttpClient.getHttpService().sendRed(getBody(request)));
    }

    //获取会员返利比例
    public Flowable<GetMemberProfitPercentResponse> getMemberProfitPercent(GetMemberProfitPercentRequest request) {
        return applySchedulers(HttpClient.getHttpService().getMemberProfitPercent(getBody(request)));
    }

    //获取会员充值价格
    public Flowable<GetMemberOpenPriceResponse> getOpenMemberPrice(GetMemberOpenPriceRequest request) {
        return applySchedulers(HttpClient.getHttpService().getOpenMemberPrice(getBody(request)));
    }

    //确认收货
    public Flowable<BaseResponse> confirmReceipt(GetConfirmReceiptRequest request) {
        return applySchedulers(HttpClient.getHttpService().confirmReceipt(getBody(request)));
    }

    //获取订单配置信息
    public Flowable<GetOrderConfigResponse> getOrderConfig(GetOrderConfigResquest request) {
        return applySchedulers(HttpClient.getHttpService().getOrderConfig(getBody(request)));
    }

    //校验验证码
    public Flowable<BaseResponse> checkSms(GetAppChangeCustomerPhoneRequest request) {
        return applySchedulers(HttpClient.getHttpService().checkSms(getBody(request)));
    }

    //    首页图片接口（广告位/今日特卖图/服务支持图片）
    public Flowable<GetHomePageImgResponse> getHomePageImg(GetHomePageImgResquest request) {
        return applySchedulers(HttpClient.getHttpService().getHomePageImg(getBody(request)));
    }

    //    获取商品下的规格及规格值列表
    public Flowable<GetStoreGoodsSpecListResponse> getStoreGoodsSpecList(GetStoreGoodsSpecListRequest request) {
        return applySchedulers(HttpClient.getHttpService().getStoreGoodsSpecList(getBody(request)));
    }

    //////////////////////
    //获取token
    public Flowable<GetProjectTokenResponse> getProjectToken(GetProjectTokenResquest request) {
        return applySchedulers(HttpClient.getHttpService().getProjectToken(ConstantData.PB_SCM_HOST + "getProjectToken",
                getBody(request)));
    }

    //getOssToken
    public Flowable<GetOssTokenResponse> getOssToken(GetOssTokenResquest request) {
        return applySchedulers(HttpClient.getHttpService().getOssToken(ConstantData.PB_SCM_HOST + "getOssToken", getBody
                (request)));
    }

    //获取支付宝登录Auth
    public Flowable<AliPayAuthSignResponse> aliAuthSign(AlipayauthsignResquest request) {
        return applySchedulers(HttpClient.getHttpService().aliAuthSign(ConstantData.PB_SCM_HOST + "api/alipay/alipayauthsign",
                getBody(request)));
    }

    //获取版本信息
    public Flowable<UpdateBeanResponse> checkVersion(AppversionResquest request) {
        return applySchedulers(HttpClient.getHttpService().checkVersion(ConstantData.PB_SCM_HOST + "api/appversion", getBody
                (request)));
    }

    //    获取轨迹
    public Flowable<Riderh5ViewResponse> riderh5View(Riderh5ViewResquest request) {
        return applySchedulers(HttpClient.getHttpService().riderh5View(ConstantData.PB_SCM_HOST + "api/sf/riderh5View", getBody
                (request)));
    }

    public Flowable<MemberBenefitsListResponse> getMemberBenefitsList(GetMemberBenefitsListRequest request) {
        return applySchedulers(HttpClient.getHttpService().getMemberBenefitsList(getBody(request)));
    }

    public Flowable<VipGoodsListResponse> getVipGoodsList(GetVipGoodsListRequest request) {
        return applySchedulers(HttpClient.getHttpService().getVipGoodsList(getBody(request)));
    }

    //再来一单
    public Flowable<OrderAgainResponse> orderAgain(OrderAgainRequest request) {
        return applySchedulers(HttpClient.getHttpService().orderAgain(getBody(request)));
    }


    public Flowable<GetMessageInfoAndUnreadMessageCountResponse> getMessageInfoAndUnreadMessageCount
            (GetMessageInfoAndUnreadMessageCountRequest request) {
        return applySchedulers(HttpClient.getHttpService().getMessageInfoAndUnreadMessageCount(getBody(request)));
    }

    public Flowable<GetMessageInfoListResponse> getMessageInfoList(GetMessageInfoListResquest request) {
        return applySchedulers(HttpClient.getHttpService().getMessageInfoList(getBody(request)));
    }

    public Flowable<QrOrBarCodeResponse> getQrOrBarCode(QrOrBarCodeRequest request) {
        return applySchedulers(HttpClient.getHttpService().getQrOrBarCode(getBody(request)));
    }

    public Flowable<DeleteMessageResponse> deleteMessage(DeleteMessageRequest request) {
        return applySchedulers(HttpClient.getHttpService().deleteMessage(getBody(request)));
    }

    public Flowable<ReadMessageResponse> readMessage(ReadMessageRequest request) {
        return applySchedulers(HttpClient.getHttpService().readMessage(getBody(request)));
    }

    public Flowable<GetStoreListResponse> getStoreList(GetStoreListRequest request) {
        return applySchedulers(HttpClient.getHttpService().getStoreList(getBody(request)));
    }

    public Flowable<GetMemberDayActivityGoodsResponse> getMemberDayActivityGoods(GetMemberDayActivityGoodsRequest request) {
        return applySchedulers(HttpClient.getHttpService().getMemberDayActivityGoods(getBody(request)));
    }

    public Flowable<StoreOrderListResponse> getStoreOrderList(StoreOrderListRequest request) {
        return applySchedulers(HttpClient.getHttpService().getStoreOrderList(getBody(request)));
    }

    public Flowable<StoreOrderDetailResponse> getStoreOrderDetail(GetOrderDetailRequest request) {
        return applySchedulers(HttpClient.getHttpService().getStoreOrderDetail(getBody(request)));
    }

    public Flowable<GetNoReadMessageCountResponse> getNoReadMessageCount(GetNoReadMessageCountRequest request) {
        return applySchedulers(HttpClient.getHttpService().getNoReadMessageCount(getBody(request)));
    }
}
