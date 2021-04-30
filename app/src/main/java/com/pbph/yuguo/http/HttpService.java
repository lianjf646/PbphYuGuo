package com.pbph.yuguo.http;


import com.pbph.yuguo.base.BaseResponse;
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
import com.pbph.yuguo.response.GetAppRedPackageMoneyResponse;
import com.pbph.yuguo.response.GetAppStorageValueRecordResponse;
import com.pbph.yuguo.response.GetAppStoredMoneyResponse;
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
import com.pbph.yuguo.response.GetSlideResponse;
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

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface HttpService {


    @Streaming
    @GET
    Flowable<ResponseBody> downloadFile(@Url String fileUrl);

    @Multipart
    @POST("File/UploadSampleImage")
    Flowable<ResponseBody> uploadFiles(@Part MultipartBody.Part file);

    @Multipart
    @POST()
    Flowable<ResponseBody> uploadFiles(@Url String url, @Part MultipartBody.Part file);

    //    @Multipart
//    @POST("File/UploadCompleteImage/{taskID}")
//    Flowable<ResponseBody> UploadCompleteImage(@Path("taskID") String taskID, @Part MultipartBody.Part file);
    @Multipart
    @POST()
    Flowable<ResponseBody> UploadCompleteImage(@Url String url, @Part MultipartBody.Part file);

    @Multipart
    @POST("File/UploadSampleImage")
    Flowable<ResponseBody> uploadFiles(@Part List<MultipartBody.Part> parts);


    @POST("customer/login/appUserLogin.htm")
    Flowable<LoginResponse> login(@Body RequestBody requestBod);

    @POST("customer/login/getAppSmsCode.htm")
    Flowable<BaseResponse> getAppSmsCode(@Body RequestBody requestBody);

    @POST("goods/goods/getGoodsDetail.htm")
    Flowable<GetGoodsDetailResponse> getGoodsDetail(@Body RequestBody requestBody);

    @POST("goods/goodsgroupactivity/getGoodsGroupDetail.htm")
    Flowable<GetGoodsGroupDetailResponse> getGoodsGroupDetail(@Body RequestBody requestBody);

    @POST("goods/pinkageActivity/getPinkageActivityGoods.htm")
    Flowable<GetPinkageActivityGoodsResponse> getPinkageActivityGoods(@Body RequestBody requestBody);

    @POST("goods/goods/getGoodsStorageNum.htm")
    Flowable<GetGoodsStorageNumResponse> getGoodsStorageNum(@Body RequestBody requestBody);

    @POST("order/order/getPageInfoForSubmitOrder.htm")
    Flowable<GetPageInfoForSubmitOrderResponse> getPageInfoForSubmitOrder(@Body RequestBody requestBody);

    @POST("order/order/submitOrder.htm")
    Flowable<SubmitOrderResponse> submitOrder(@Body RequestBody requestBody);

    @POST("order/order/submitOrderForGroup.htm")
    Flowable<SubmitOrderForGroupResponse> submitOrderForGroup(@Body RequestBody requestBody);

    @POST("{action}")
    Flowable<ResponseBody> post(@Url String url, @Body RequestBody requestBody);//传入的参数为RequestBody

    @GET("{action}")
    Flowable<ResponseBody> get(@Path("action") String action, @QueryMap Map<String, Object> params);//传入的参数为RequestBody

//    @POST
//    Flowable<UpdateBeanResponse> checkVersion(@Url String url, @Body RequestBody requestBod);//传入的参数为RequestBody

    @GET
    Flowable<ResponseBody> get(@Url String url);//传入的参数为RequestBody


    @POST("upload2oss.htm")
    Flowable<Upload2ossResponse> upload2oss(@Body MultipartBody multipartBody);


    @POST("order/shopping/getShoppingCart.htm")
    Flowable<GetShoppingCartResponse> getShoppingCart(@Body RequestBody requestBod);

    @POST("order/shopping/updateShoppingCart.htm")
    Flowable<UpdateShoppingCartResponse> updateShoppingCart(@Body RequestBody requestBod);

    @POST("order/shopping/getActivityGoodsInfoList.htm")
    Flowable<GetActivityGoodsInfoListResponse> getActivityGoodsInfoList(@Body RequestBody requestBod);

    @POST("order/shopping/delShoppingCart.htm")
    Flowable<DelShoppingCartResponse> delShoppingCart(@Body RequestBody requestBod);

    @POST("order/shopping/updateShoppingCartAll.htm")
    Flowable<UpdateShoppingCartAllResponse> updateShoppingCartAll(@Body RequestBody requestBod);

    @POST("order/shopping/setActivityGiftCheckedStatus.htm")
    Flowable<SetActivityGiftCheckedStatusResponse> setActivityGiftCheckedStatus(@Body RequestBody requestBod);

    @POST("customer/address/getAddressListByCustomerId.htm")
    Flowable<GetAddressListByCustomerIdResponse> getAddressListByCustomerId(@Body RequestBody requestBod);

    @POST("customer/customer/getJpushId.htm")
    Flowable<GetJpushIdResponse> getJpushId(@Body RequestBody requestBod);

    @POST("order/shopping/getShoppingCartCount.htm")
    Flowable<GetShoppingCartCountResponse> getShoppingCartCount(@Body RequestBody requestBod);

    @POST("store/store/getStoreInfoByLocation.htm")
    Flowable<GetStoreInfoByLocationResponse> getStoreInfoByLocation(@Body RequestBody requestBod);

    @POST("goods/goods/getHotSaleGoodsList.htm")
    Flowable<GetHotSaleGoodsListResponse> getHotSaleGoodsList(@Body RequestBody requestBod);

    @POST("system/slide/getSlide.htm")
    Flowable<GetSlideResponse> getSlide(@Body RequestBody requestBod);

    @POST("index/imgage/getIndexRotationChart.htm")
    Flowable<GetIndexRotationChartResponse> getIndexRotationChart(@Body RequestBody requestBod);

    @POST("coupon/coupon/getIndexPopupCoupon.htm")
    Flowable<GetIndexPopupCouponResponse> getIndexPopupCoupon(@Body RequestBody requestBod);

    @POST("coupon/coupon/receiveIndexAllCoupon.htm")
    Flowable<ReceiveIndexAllCouponResponse> receiveIndexAllCoupon(@Body RequestBody requestBod);

    @POST("goods/goods/getGoodsFirstCategory.htm")
    Flowable<GetGoodsFirstCategoryResponse> getGoodsFirstCategory(@Body RequestBody requestBod);

    @POST("goods/goods/getGoodsList.htm")
    Flowable<GetGoodsListResponse> getGoodsList(@Body RequestBody requestBod);

    @POST("order/shopping/addShoppingCart.htm")
    Flowable<AddShoppingCartResponse> addShoppingCart(@Body RequestBody requestBod);

    //邀请好友获得优惠券
    @POST("invitation/invitation/getInviteICouponList.htm")
    Flowable<GetInviteICouponListResponse> getInviteICouponList(@Body RequestBody requestBod);

    //邀请会员获得优惠券
    @POST("invitation/invitation/getInviteVipCouponList.htm")
    Flowable<GetInviteVipCouponListResponse> getInviteVipCouponList(@Body RequestBody requestBod);

    //我的邀请
    @POST("invitation/invitation/getMyInvitationList.htm")
    Flowable<GetMyInvitationListResponse> getMyInvitationList(@Body RequestBody requestBod);

    //保存收货地址
    @POST("customer/address/saveReceiverAddress.htm")
    Flowable<SaveReceiverAddressResponse> saveReceiverAddress(@Body RequestBody requestBod);

    //获取收货地址列表（收货地址是否在配送范围）
    @POST("receiverAddress/receiverAddress/getReceiverAddressList.htm")
    Flowable<GetReceiverAddressListResponse> getReceiverAddressList(@Body RequestBody requestBod);

    //获取收货地址列表（收货地址是否在配送范围）
    @POST("customer/address/deleteAddressById.htm")
    Flowable<DelReceiverAddressResponse> delReceiverAddress(@Body RequestBody requestBod);

    @POST("invitation/invitation/getUserInviteNumberList.htm")
    Flowable<GetUserInviteNumberListResponse> getUserInviteNumberList(@Body RequestBody requestBod);

    //认证状态
    @POST("customer/authentication/getAuthentication.htm")
    Flowable<GetAuthenticationResponse> getAuthentication(@Body RequestBody requestBod);

    //认证状态
    @POST("customer/authentication/toAuthentication.htm")
    Flowable<ToAuthenticationResponse> toAuthentication(@Body RequestBody requestBod);


    @POST("invitation/invitation/getJuniorUserMoneyList.htm")
    Flowable<GetJuniorUserMoneyListResponse> getJuniorUserMoneyList(@Body RequestBody requestBod);

    @POST("customer/customer/getAppRedPackageMoney.htm")
    Flowable<GetAppRedPackageMoneyResponse> getAppRedPackageMoney(@Body RequestBody requestBod);

    @POST("system/withdrawconfig/getRedPacketWithdrawRule.htm")
    Flowable<GetRedPacketWithdrawRuleResponse> getRedPacketWithdrawRule(@Body RequestBody requestBod);

    @POST("customer/customer/getAppWithdrawCashList.htm")
    Flowable<GetAppWithdrawCashListResponse> getAppWithdrawCashList(@Body RequestBody requestBod);

    @POST("goods/goodsgroupactivity/getMyGoodsGroupActivityList.htm")
    Flowable<GroupListResponse> getMyGoodsGroupActivityList(@Body RequestBody requestBod);


    @POST("goods/goodsgroupactivity/getGoodsGroupActivityList.htm")
    Flowable<GroupListResponse> getGoodsGroupActivityList(@Body RequestBody requestBod);

    @POST("invitation/invitation/getInviteGallopList.htm")
    Flowable<GetInviteGallopListResponse> getInviteGallopList(@Body RequestBody requestBod);

    @POST("system/storedconfig/getStoredConfig.htm")
    Flowable<GetStoredConfigResponse> getStoredConfig(@Body RequestBody requestBod);

    @POST("customer/customer/getAppStoredMoney.htm")
    Flowable<GetAppStoredMoneyResponse> getAppStoredMoney(@Body RequestBody requestBod);

    @POST("customer/customer/getAppStorageValueRecord.htm")
    Flowable<GetAppStorageValueRecordResponse> getAppStorageValueRecord(@Body RequestBody requestBod);

    @POST("customer/address/updateReceiverAddress.htm")
    Flowable<UpdateReceiverAddressResponse> updateReceiverAddress(@Body RequestBody requestBod);


    @POST("order/order/getOrderList.htm")
    Flowable<OrderListResponse> getOrderList(@Body RequestBody requestBod);

    @POST("order/order/getOrderDetail.htm")
    Flowable<OrderDetailResponse> getOrderDetail(@Body RequestBody requestBod);

    @POST("coupon/coupon/getCustomerCouponList.htm")
    Flowable<CouponResponse> getCustomerCouponList(@Body RequestBody requestBod);

    @POST("coupon/coupon/getVoucherListForSubmitOrder.htm")
    Flowable<CouponResponse> getVoucherListForSubmitOrder(@Body RequestBody requestBod);

    @POST("coupon/coupon/getUsableCouponListForGoods.htm")
    Flowable<CouponResponse> getUsableCouponListForGoods(@Body RequestBody requestBod);

    @POST("coupon/coupon/getCouponListForSubmitOrder.htm")
    Flowable<CouponResponse> getCouponListForSubmitOrder(@Body RequestBody requestBod);

    @POST("customer/customer/getAppRedPackageDealsInfo.htm")
    Flowable<GetAppRedPackageDealsInfoResponse> getAppRedPackageDealsInfo(@Body RequestBody requestBod);

    @POST("customer/customer/getCustomerInfoById.htm")
    Flowable<GetCustomerInfoByIdResponse> getCustomerInfoById(@Body RequestBody requestBod);

    @POST("point/point/getMyPointList.htm")
    Flowable<GetMyPointSourceListResponse> getMyPointSourceList(@Body RequestBody requestBod);

    @POST("goods/goods/getWaitEvaluateGoodsList.htm")
    Flowable<WaitEvaluateListResponse> getWaitEvaluateList(@Body RequestBody requestBod);

    @POST("goods/evaluate/getGoodsEvaluateOrderDetails.htm")
    Flowable<GoodsEvaluateOrderDetailsResponse> getGoodsEvaluateOrderDetails(@Body RequestBody body);

    @POST("goods/evaluate/evaluateOrder.htm")
    Flowable<EvaluateOrderResponse> evaluateOrder(@Body RequestBody body);

    @POST("goods/evaluate/evaluateBaskOrder.htm")
    Flowable<EvaluateOrderResponse> evaluateBaskOrder(@Body RequestBody body);

    @POST("customer/customerMember/appChangeMemberInfo.htm")
    Flowable<AppChangeVipInfoResponse> appChangeVipInfo(@Body RequestBody requestBod);

    @POST("order/pay/payMent.htm")
    Flowable<WxPayOrderResponse> wxPayOrder(@Body RequestBody requestBod);

    @POST("order/pay/payMent.htm")
    Flowable<AliPayOrderResponse> aLiPayOrder(@Body RequestBody requestBod);

    @POST("feedback/feedback/commitFeedback.htm")
    Flowable<CommitFeedbackResponse> commitFeedback(@Body RequestBody requestBod);

    @POST("order/order/getOrderStatusNum.htm")
    Flowable<GetOrderNumResponse> getOrderStatusNum(@Body RequestBody requestBod);

    @POST("customer/customerMember/appChangestoredMoney.htm")
    Flowable<AppChangestoredMoneyResponse> appChangestoredMoney(@Body RequestBody requestBod);

    @POST("customer/customer/appChangeCustomerPhone.htm")
    Flowable<AppChangestoredMoneyResponse> appChangeCustomerPhone(@Body RequestBody requestBod);

    @POST("order/order/getBackOrderList.htm")
    Flowable<BackOrderListResponse> getBackOrderList(@Body RequestBody requestBod);

    @POST("customer/customer/appChangeCustomerPayPassword.htm")
    Flowable<BaseResponse> appChangeCustomerPayPassword(@Body RequestBody requestBod);

    @POST("customer/customer/appChangeCustomerInfo.htm")
    Flowable<BaseResponse> appChangeCustomerInfo(@Body RequestBody requestBod);

    @POST("coupon/coupon/receiveCoupon.htm")
    Flowable<BaseResponse> receiveCoupon(@Body RequestBody requestBod);

    @POST("goods/evaluate/getGoodsEvaluateList.htm")
    Flowable<GoodsEvaluateResponse> getGoodsEvaluateList(@Body RequestBody requestBod);

    @POST("system/sysConfig/getSysConfig.htm")
    Flowable<GetSysConfigResponse> getSysConfig(@Body RequestBody requestBod);

    @POST("order/order/cancelOrder.htm")
    Flowable<CancelOrderResponse> cancelOrder(@Body RequestBody requestBod);

    @POST("order/sendred/sendRed.htm")
    Flowable<SendRedResponse> sendRed(@Body RequestBody requestBod);

    @POST("system/memberconfig/getMemberProfitPercent.htm")
    Flowable<GetMemberProfitPercentResponse> getMemberProfitPercent(@Body RequestBody requestBod);

    @POST("member/getOpenMemberPrice.htm")
    Flowable<GetMemberOpenPriceResponse> getOpenMemberPrice(@Body RequestBody requestBod);

    @POST("order/order/confirmReceipt.htm")
    Flowable<BaseResponse> confirmReceipt(@Body RequestBody requestBod);

    @POST("system/orderconfig/getOrderConfig.htm")
    Flowable<GetOrderConfigResponse> getOrderConfig(@Body RequestBody requestBod);

    @POST("customer/customer/checkSms.htm")
    Flowable<BaseResponse> checkSms(@Body RequestBody requestBod);

    @POST("member/getMemberBenefitsList.htm")
    Flowable<MemberBenefitsListResponse> getMemberBenefitsList(@Body RequestBody requestBod);

    @POST("member/getVipGoodsList.htm")
    Flowable<VipGoodsListResponse> getVipGoodsList(@Body RequestBody requestBod);

    @POST("index/img/getHomePageImg.htm")
    Flowable<GetHomePageImgResponse> getHomePageImg(@Body RequestBody requestBod);

    @POST("goods/goods/getStoreGoodsSpecList.htm")
    Flowable<GetStoreGoodsSpecListResponse> getStoreGoodsSpecList(@Body RequestBody requestBod);

    @POST("message/getMessageInfoAndUnreadMessageCount.htm")
    Flowable<GetMessageInfoAndUnreadMessageCountResponse> getMessageInfoAndUnreadMessageCount(@Body RequestBody request);

    @POST("message/getMessageInfoList.htm")
    Flowable<GetMessageInfoListResponse> getMessageInfoList(@Body RequestBody request);


    @POST("message/deleteMessage.htm")
    Flowable<DeleteMessageResponse> deleteMessage(@Body RequestBody request);

    @POST("message/readMessage.htm")
    Flowable<ReadMessageResponse> readMessage(@Body RequestBody request);

    @POST("store/store/getStoreList.htm")
    Flowable<GetStoreListResponse> getStoreList(@Body RequestBody request);

    @POST("goods/goods/getMemberDayActivityGoods.htm")
    Flowable<GetMemberDayActivityGoodsResponse> getMemberDayActivityGoods(@Body RequestBody request);

    ///////////////////////////
    @POST()
    Flowable<GetProjectTokenResponse> getProjectToken(@Url String url, @Body RequestBody requestBod);

    @POST()
    Flowable<GetOssTokenResponse> getOssToken(@Url String url, @Body RequestBody requestBod);

    @POST()
    Flowable<AliPayAuthSignResponse> aliAuthSign(@Url String url, @Body RequestBody requestBod);

    @POST()
    Flowable<UpdateBeanResponse> checkVersion(@Url String url, @Body RequestBody requestBod);

    @POST()
    Flowable<Riderh5ViewResponse> riderh5View(@Url String url, @Body RequestBody requestBod);

    @POST("order/order/orderAgain.htm")
    Flowable<OrderAgainResponse> orderAgain(@Body RequestBody requestBod);

    @POST("system/qrCode/getQrOrBarCode.htm")
    Flowable<QrOrBarCodeResponse> getQrOrBarCode(@Body RequestBody requestBod);

    @POST("offline/order/getOrderList.htm")
    Flowable<StoreOrderListResponse> getStoreOrderList(@Body RequestBody requestBod);

    @POST("offline/order/getOrderDetail.htm")
    Flowable<StoreOrderDetailResponse> getStoreOrderDetail(@Body RequestBody requestBod);

    @POST("message/noReadMessageCount.htm")
    Flowable<GetNoReadMessageCountResponse> getNoReadMessageCount(@Body RequestBody request);
}
