package com.pbph.yuguo.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/23.
 */
public class GoodsInfoBean implements Serializable {
    /**
     * activeType : 0
     * avaliableFlag : 1
     * goodsId : 290
     * goodsInfoId : 23
     * goodsInfoMemberPrice : 36
     * goodsInfoName : 大枣(红色XL),而提供人
     * goodsInfoNameSub : 副标题
     * goodsInfoNum : 1
     * goodsInfoPack : 包装
     * goodsInfoPackCharges : 1
     * goodsInfoPicUrl : http://image.pbdsh.com/ygtestfold/16/184FDCA5ED4318D709166E6EF0DBCFE0.png
     * goodsInfoSalePrice : 36
     * goodsInfoSpecValue : 颜色:红色;大小:XL
     * goodsInfoWeight : 12
     * storeGoodsInfoId : 61
     * storeId : 124
     */
    public boolean gifts = false;

    private int evaluateFlag;
    private int memberPrice;
    private int shareFlag;

    private int activeType;
    private int avaliableFlag;
    private int goodsId;
    private int goodsInfoId;
    private int goodsInfoMemberPrice;
    private String goodsInfoName;
    private String goodsInfoNameSub;
    private int goodsInfoNum;
    private String goodsInfoPack;
    private int goodsInfoPackCharges;
    private String goodsInfoPicUrl;
    private int goodsInfoSalePrice;
    private String goodsInfoSpecValue;
    private int goodsInfoWeight;
    private int storeGoodsInfoId;
    private int storeId;

    private int averagePrice;

    public int getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(int averagePrice) {
        this.averagePrice = averagePrice;
    }

    public int getActiveType() {
        return activeType;
    }

    public void setActiveType(int activeType) {
        this.activeType = activeType;
    }

    public int getAvaliableFlag() {
        return avaliableFlag;
    }

    public void setAvaliableFlag(int avaliableFlag) {
        this.avaliableFlag = avaliableFlag;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getGoodsInfoId() {
        return goodsInfoId;
    }

    public void setGoodsInfoId(int goodsInfoId) {
        this.goodsInfoId = goodsInfoId;
    }

    public int getGoodsInfoMemberPrice() {
        return goodsInfoMemberPrice;
    }

    public void setGoodsInfoMemberPrice(int goodsInfoMemberPrice) {
        this.goodsInfoMemberPrice = goodsInfoMemberPrice;
    }

    public String getGoodsInfoName() {
        return goodsInfoName;
    }

    public void setGoodsInfoName(String goodsInfoName) {
        this.goodsInfoName = goodsInfoName;
    }

    public String getGoodsInfoNameSub() {
        return goodsInfoNameSub;
    }

    public void setGoodsInfoNameSub(String goodsInfoNameSub) {
        this.goodsInfoNameSub = goodsInfoNameSub;
    }

    public int getGoodsInfoNum() {
        return goodsInfoNum;
    }

    public void setGoodsInfoNum(int goodsInfoNum) {
        this.goodsInfoNum = goodsInfoNum;
    }

    public String getGoodsInfoPack() {
        return goodsInfoPack;
    }

    public void setGoodsInfoPack(String goodsInfoPack) {
        this.goodsInfoPack = goodsInfoPack;
    }

    public int getGoodsInfoPackCharges() {
        return goodsInfoPackCharges;
    }

    public void setGoodsInfoPackCharges(int goodsInfoPackCharges) {
        this.goodsInfoPackCharges = goodsInfoPackCharges;
    }

    public String getGoodsInfoPicUrl() {
        return goodsInfoPicUrl;
    }

    public void setGoodsInfoPicUrl(String goodsInfoPicUrl) {
        this.goodsInfoPicUrl = goodsInfoPicUrl;
    }

    public int getGoodsInfoSalePrice() {
        return goodsInfoSalePrice;
    }

    public void setGoodsInfoSalePrice(int goodsInfoSalePrice) {
        this.goodsInfoSalePrice = goodsInfoSalePrice;
    }

    public String getGoodsInfoSpecValue() {
        return goodsInfoSpecValue;
    }

    public void setGoodsInfoSpecValue(String goodsInfoSpecValue) {
        this.goodsInfoSpecValue = goodsInfoSpecValue;
    }

    public int getGoodsInfoWeight() {
        return goodsInfoWeight;
    }

    public void setGoodsInfoWeight(int goodsInfoWeight) {
        this.goodsInfoWeight = goodsInfoWeight;
    }

    public int getStoreGoodsInfoId() {
        return storeGoodsInfoId;
    }

    public void setStoreGoodsInfoId(int storeGoodsInfoId) {
        this.storeGoodsInfoId = storeGoodsInfoId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getEvaluateFlag() {
        return evaluateFlag;
    }

    public void setEvaluateFlag(int evaluateFlag) {
        this.evaluateFlag = evaluateFlag;
    }

    public int getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(int memberPrice) {
        this.memberPrice = memberPrice;
    }

    public int getShareFlag() {
        return shareFlag;
    }

    public void setShareFlag(int shareFlag) {
        this.shareFlag = shareFlag;
    }
}
