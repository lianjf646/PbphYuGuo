package com.pbph.yuguo.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.choicegoods.ShoppingSelectView;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetStoreGoodsSpecListRequest;
import com.pbph.yuguo.request.GetSysConfigRequest;
import com.pbph.yuguo.response.GetStoreGoodsSpecListResponse;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.MoneyHelper;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 挡风的纱窗 on 2018/12/21.
 */
public class SpecChoicePop extends PopupWindow {


    private LayoutInflater inflater;
    private ShoppingSelectView shoppingSelectView;
    StringBuilder sbChildId = new StringBuilder();
    StringBuilder sbChildName = new StringBuilder();
    StringBuilder sbTypeId = new StringBuilder();
    GetStoreGoodsSpecListResponse.DataBean.AllSpecDetailIdListBean smallAttr;
    List<GetStoreGoodsSpecListResponse.DataBean.AllSpecDetailIdListBean> openSpecBeans;
    GetStoreGoodsSpecListResponse.DataBean.StoreGoodsInfoBean goodsInfoBean;
    private Context context;
    private ImageView ivDismiss;

    public int storeGoodsId;
    public int storeGoodsInfoId = 0;
    private Button btnAddShop, btnBuy;
    private OnGetGoodsIdListener onGetGoodsIdListener;
    private ImageButton ivbtnGoodsInfoReduce, ivbtnGoodsInfoAdd;
    private TextView tvGoodsInfoCount;
    int[] drawablesReduce = {R.drawable.reduce_grey, R.drawable.reduce_selected};
    int[] drawablesAdd = {R.drawable.add_grey, R.drawable.add_selected};
    private ImageView ivGoods;// 商品图片
    private TextView tvPrice; //购买价格
    private TextView tvOldPrice;//原始价格
    private TextView tvChoiceSpec;//已选
    private TextView tvStock;//库存
    private TextView tvLimitBug;

    public String childChoiceId;  //子规格id组合
    public String childChoiceName;// 子规格名称组合
    public String fChoiceId;    //父规格id 组合
    public int goodsNum = 1;// 商品数量
    public Type type;
    public int storeId;

    public int btnType;

    private View view1;
    private ProgressBar progressBar;
    public GetStoreGoodsSpecListResponse getStoreGoodsSpecListResponse;

    private int stockNum = 0;//库存数量

    private int totWeight = -1;

    public SpecChoicePop(Context context, OnGetGoodsIdListener onGetGoodsIdListener) {
        super(context);
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.popup_spec_view, null);
        this.context = context;
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.Popupwindow);
        this.setContentView(view);
        setBackgroundDrawable(new BitmapDrawable());
        this.setOnDismissListener(() -> {
//            onGetGoodsIdListener.onGetGoodsId(this);
            setBackgroundAlpha(1.0f);
        });
        this.onGetGoodsIdListener = onGetGoodsIdListener;
        initView(view);

        getSysConfig();

    }

    /**
     * @param storeGoodsInfoId
     * @param storeGoodsId
     * @param specDetailIdStr
     * @param btnType          类型0显示添加购物车 1显示立即购物 2 全显示
     * @param view             显示位置
     */
    public synchronized void showPop(int storeGoodsInfoId, int storeGoodsId, String specDetailIdStr, int btnType, View view) {

        this.btnType = btnType;
        this.storeGoodsInfoId = storeGoodsInfoId;
        this.storeGoodsId = storeGoodsId;
        getStoreGoodsSpecList(storeGoodsInfoId, storeGoodsId, specDetailIdStr, false);
        if (btnType == 0) {
            btnBuy.setVisibility(View.GONE);
            btnAddShop.setVisibility(View.VISIBLE);
        } else if (btnType == 1) {
            btnBuy.setVisibility(View.VISIBLE);
            btnAddShop.setVisibility(View.GONE);

        } else if (btnType == 2) {
            btnBuy.setVisibility(View.VISIBLE);
            btnAddShop.setVisibility(View.VISIBLE);
        }
        show(view);
    }

    private void initView(View view) {

        view.findViewById(R.id.pop_onstraintLayout).setOnClickListener(onSPClickListener);

        tvGoodsInfoCount = view.findViewById(R.id.tv_goods_info_count);
        ivbtnGoodsInfoReduce = view.findViewById(R.id.ivbtn_goods_info_reduce);
        ivbtnGoodsInfoReduce.setOnClickListener(onSPClickListener);
        ivbtnGoodsInfoAdd = view.findViewById(R.id.ivbtn_goods_info_add);
        ivbtnGoodsInfoAdd.setOnClickListener(onSPClickListener);
        btnAddShop = view.findViewById(R.id.btn_add_shop);
        btnAddShop.setOnClickListener(onSPClickListener);
        btnBuy = view.findViewById(R.id.btn_buy);
        btnBuy.setOnClickListener(onSPClickListener);
        shoppingSelectView = view.findViewById(R.id.shoppingSelectView);
        ivGoods = view.findViewById(R.id.iv_goods);
        tvPrice = view.findViewById(R.id.tv_price);
        tvOldPrice = view.findViewById(R.id.tv_old_price);
        tvStock = view.findViewById(R.id.tv_stock);
        tvChoiceSpec = view.findViewById(R.id.tv_choice_spec);
        tvLimitBug = view.findViewById(R.id.tv_limit_bug);
        view1 = view.findViewById(R.id.view1);
        ivDismiss = view.findViewById(R.id.iv_dismiss);
        progressBar = view.findViewById(R.id.progressBar);
        ivDismiss.setOnClickListener(onSPClickListener);
        view1.setOnClickListener(onSPClickListener);
        shoppingSelectView.setOnSelectedListener((rb, title, titleId, smallTitle, id) -> {
            sbTypeId = new StringBuilder();
            sbChildId = new StringBuilder();
            sbChildName = new StringBuilder();
            smallAttr = (GetStoreGoodsSpecListResponse.DataBean.AllSpecDetailIdListBean) rb.getTag();
            smallAttr.choose_child_id = id;
            smallAttr.choose_child_name = smallTitle;

            if (openSpecBeans == null) return;
            for (GetStoreGoodsSpecListResponse.DataBean.AllSpecDetailIdListBean vo : openSpecBeans) {
                sbChildId.append(",");
                sbChildId.append(vo.getChoose_child_id());
//                sbTypeId.append(",");
//                sbTypeId.append(vo.getSpecId());
//                sbChildName.append(",");
//                sbChildName.append(vo.getChoose_child_name());
            }
            type = Type.CHOICE;
            getStoreGoodsSpecList(null, storeGoodsId, sbChildId.toString().substring(1, sbChildId.length()), true);

        });
    }

    /**
     * @param storeGoodsInfoId
     * @param storeGoodsId
     * @param specDetailIdStr
     * @param b                是否调用 onGetGoodsIdListener接口。
     */
    private void getStoreGoodsSpecList(Integer storeGoodsInfoId, int storeGoodsId, String specDetailIdStr, boolean b) {
        progressBar.setVisibility(View.VISIBLE);
        view1.setVisibility(View.VISIBLE);
        HttpAction.getInstance().getStoreGoodsSpecList(new GetStoreGoodsSpecListRequest(storeGoodsInfoId, storeGoodsId,
                specDetailIdStr)).subscribe(new BaseObserver<>(context, response -> {
            progressBar.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            getStoreGoodsSpecListResponse = response;
            if (response.getCode() != 200) {
                if (response.getCode() != 3013 && response.getCode() != 3016) {
                    Toast.makeText(context, response.getMsg(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(context, response.getMsg(), Toast.LENGTH_SHORT).show();
            }


            scaleWeight();

            this.storeGoodsInfoId = getStoreGoodsSpecListResponse.getData().getStoreGoodsInfo().getStoreGoodsInfoId();

            openSpecBeans = response.getData().getAllSpecDetailIdList();
            List<Integer> getUnvaliableSpecDetailIdList = response.getData().getUnvaliableSpecDetailIdList();
            List<Integer> getSpecDetailIdList = response.getData().getStoreGoodsInfo().getSpecDetailIdList();
            goodsInfoBean = response.getData().getStoreGoodsInfo();
            stockNum = goodsInfoBean.getStorageNum();
            storeId = goodsInfoBean.getStoreId();
            GlideUtil.displayImage(context, goodsInfoBean.getGoodsInfoPicUrl(), ivGoods, R.drawable.no_goods);
            tvPrice.setText("￥");
            tvPrice.append(MoneyHelper.getInstance4Fen(goodsInfoBean.getGoodsInfoMemberPrice()).change2Yuan().getString());
            tvOldPrice.setText("￥");
            tvOldPrice.append(MoneyHelper.getInstance4Fen(goodsInfoBean.getGoodsInfoSalePrice()).change2Yuan().getString());

            //            tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
            tvStock.setText("库存：");
            tvStock.append(String.valueOf(goodsInfoBean.getStorageNum()));
            tvChoiceSpec.setText("已选:" + goodsInfoBean.getGoodsInfoName());

            shoppingSelectView.setData(openSpecBeans, getUnvaliableSpecDetailIdList, getSpecDetailIdList);
            if (goodsInfoBean.getPurchaseLimitationNum() == 0) {
                tvLimitBug.setVisibility(View.INVISIBLE);
            } else {
                tvLimitBug.setVisibility(View.VISIBLE);
            }
            tvLimitBug.setText("限购");
            tvLimitBug.append(String.valueOf(goodsInfoBean.getPurchaseLimitationNum()));
            tvLimitBug.append("件");

            if (goodsInfoBean.getStorageNum() <= 0) {
                goodsNum = 0;
                tvGoodsInfoCount.setText(String.valueOf(goodsNum));
                ivbtnGoodsInfoReduce.setImageDrawable(context.getResources().getDrawable(drawablesReduce[0]));
                ivbtnGoodsInfoAdd.setImageDrawable(context.getResources().getDrawable(drawablesAdd[0]));
            } else {
                goodsNum = 1;
                tvGoodsInfoCount.setText(String.valueOf(goodsNum));
                ivbtnGoodsInfoAdd.setImageDrawable(context.getResources().getDrawable(drawablesAdd[1]));
            }
            if (b) onGetGoodsIdListener.onGetGoodsId(this);

        }, (code, message) -> {
            progressBar.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }));
    }

    private void show(View view) {
        if (isShowing()) return;
        this.showAtLocation(view, Gravity.BOTTOM, 0, 0);// 显示位置
        this.update(); //刷新状态
        setBackgroundAlpha(0.5f);
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) context).getWindow().setAttributes(lp);
    }

    OnSPClickListener onSPClickListener = new OnSPClickListener() {
        @Override
        public void onClickSucc(View v) {
            switch (v.getId()) {
                case R.id.pop_onstraintLayout: {
                    type = Type.CLOSE;
                    onGetGoodsIdListener.onGetGoodsId(SpecChoicePop.this);
                    goodsNum = Integer.valueOf(tvGoodsInfoCount.getText().toString());
                    dismiss();
                }
                break;

                case R.id.iv_dismiss: {
                    if (getStoreGoodsSpecListResponse == null) return;
                    if (getStoreGoodsSpecListResponse.getCode() != 200) {
                        showSPToast(getStoreGoodsSpecListResponse.getMsg());
                        dismiss();
                        return;
                    }
                    type = Type.CLOSE;
                    onGetGoodsIdListener.onGetGoodsId(SpecChoicePop.this);
                    goodsNum = Integer.valueOf(tvGoodsInfoCount.getText().toString());
                    dismiss();
                }
                break;

                case R.id.btn_add_shop: {
                    if (getStoreGoodsSpecListResponse == null) return;
                    if (getStoreGoodsSpecListResponse.getCode() != 200) {
                        showSPToast(getStoreGoodsSpecListResponse.getMsg());
                        return;
                    }
                    if (stockNum <= 0) {
                        showSPToast("库存不足");
                        return;
                    }
                    type = Type.ADDSHOP;
                    goodsNum = Integer.valueOf(tvGoodsInfoCount.getText().toString());
                    if (goodsNum == 0) return;
                    onGetGoodsIdListener.onGetGoodsId(SpecChoicePop.this);
                    dismiss();
                }
                break;

                case R.id.btn_buy: {
                    if (getStoreGoodsSpecListResponse == null) return;
                    if (getStoreGoodsSpecListResponse.getCode() != 200) {
                        showSPToast(getStoreGoodsSpecListResponse.getMsg());
                        return;
                    }
                    if (stockNum <= 0) {
                        showSPToast("库存不足");
                        return;
                    }

                    type = Type.BUY;
                    goodsNum = Integer.valueOf(tvGoodsInfoCount.getText().toString());
                    if (goodsNum == 0) return;
                    onGetGoodsIdListener.onGetGoodsId(SpecChoicePop.this);
                    dismiss();
                }
                break;

                case R.id.ivbtn_goods_info_reduce: {
                    if (getStoreGoodsSpecListResponse == null) return;
                    if (getStoreGoodsSpecListResponse.getCode() != 200) {
                        showSPToast(getStoreGoodsSpecListResponse.getMsg());
                        return;
                    }
                    if (goodsNum <= 1) {
                        ivbtnGoodsInfoReduce.setImageDrawable(context.getResources().getDrawable(drawablesReduce[0]));
                        showSPToast("最少购买1件哦!");
                        return;
                    }

                    ivbtnGoodsInfoAdd.setImageDrawable(context.getResources().getDrawable(drawablesAdd[1]));
                    goodsNum--;
                    scaleWeight();
                    tvGoodsInfoCount.setText(String.valueOf(goodsNum));
                    if (goodsNum <= 1) {
                        ivbtnGoodsInfoReduce.setImageDrawable(context.getResources().getDrawable(drawablesReduce[0]));
                        return;
                    }

                }
                break;

                case R.id.ivbtn_goods_info_add: {
                    if (getStoreGoodsSpecListResponse == null) return;
                    if (getStoreGoodsSpecListResponse.getCode() != 200) {
                        ivbtnGoodsInfoAdd.setImageDrawable(context.getResources().getDrawable(drawablesAdd[0]));
                        showSPToast(getStoreGoodsSpecListResponse.getMsg());
                        return;
                    }
                    if (goodsInfoBean.getPurchaseLimitationNum() != 0 && goodsNum >= goodsInfoBean.getPurchaseLimitationNum()) {
                        showSPToast("不能再购买更多了哦！");
                        ivbtnGoodsInfoAdd.setImageDrawable(context.getResources().getDrawable(drawablesAdd[0]));
                        return;
                    }
                    if (goodsNum >= goodsInfoBean.getStorageNum()) {
                        showSPToast("库存不足");
                        ivbtnGoodsInfoAdd.setImageDrawable(context.getResources().getDrawable(drawablesAdd[0]));
                        return;
                    }
                    ivbtnGoodsInfoReduce.setImageDrawable(context.getResources().getDrawable(drawablesReduce[1]));
                    goodsNum++;
                    scaleWeight();
                    tvGoodsInfoCount.setText(String.valueOf(goodsNum));
                    if (goodsInfoBean.getPurchaseLimitationNum() != 0 && goodsNum >= goodsInfoBean.getPurchaseLimitationNum()) {
                        ivbtnGoodsInfoAdd.setImageDrawable(context.getResources().getDrawable(drawablesAdd[0]));
                        return;
                    }
                    if (goodsNum >= goodsInfoBean.getStorageNum()) {
                        ivbtnGoodsInfoAdd.setImageDrawable(context.getResources().getDrawable(drawablesAdd[0]));
                        return;
                    }

                }
                break;

                default: {

                }
                break;
            }
        }
    };


    private static long preTime = 0;

    public void showSPToast(String msg) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - preTime > 3000) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            preTime = currentTime;
        }
    }

    public interface OnGetGoodsIdListener {
        void onGetGoodsId(SpecChoicePop specChoicePop);

    }

    public enum Type {
        ADDSHOP, BUY, CLOSE, CHOICE
    }


    private void getSysConfig() {
        HttpAction.getInstance().getSysConfig(new GetSysConfigRequest()).subscribe(new BaseObserver<>(context, response -> {
            int code = response.getCode();
            if (code == 200) {
                totWeight = response.getData().getSysConfig().getTotalWeight();
            } else {
                totWeight = -2;
            }
        }));
    }


    private void scaleWeight() {
        if (totWeight == -1) {
            btnBuy.setBackgroundColor(context.getResources().getColor(R.color.light_gray));
            btnBuy.setClickable(false);
            return;
        }
        if (totWeight == -2) {
            btnBuy.setBackgroundColor(context.getResources().getColor(R.color.light_gray));
            btnBuy.setClickable(false);
            return;
        }
        BigDecimal res = new BigDecimal(totWeight).subtract(new BigDecimal(goodsNum).multiply(new BigDecimal
                (getStoreGoodsSpecListResponse.getData().getStoreGoodsInfo().getGoodsInfoWeight())));
        if (res.doubleValue() < 0) {
            btnBuy.setBackgroundColor(context.getResources().getColor(R.color.light_gray));
            btnBuy.setClickable(false);
            return;
        }
        btnBuy.setBackgroundColor(context.getResources().getColor(R.color.main_color));
        btnBuy.setClickable(true);
    }

}
