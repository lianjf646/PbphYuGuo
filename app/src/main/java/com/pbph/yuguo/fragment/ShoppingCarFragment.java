package com.pbph.yuguo.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.activity.ChoiceAddressListActivity;
import com.pbph.yuguo.activity.EventCoudanActvity;
import com.pbph.yuguo.activity.EventCoudanGiftActvity;
import com.pbph.yuguo.activity.EventManjianActvity;
import com.pbph.yuguo.activity.LoginActivity;
import com.pbph.yuguo.activity.OrderFillActivity;
import com.pbph.yuguo.activity.VipDayActivity;
import com.pbph.yuguo.adapter.shoppingcaradapter.EventVIPDayViewHolder;
import com.pbph.yuguo.adapter.shoppingcaradapter.EventViewHolder;
import com.pbph.yuguo.adapter.shoppingcaradapter.FootViewHolder;
import com.pbph.yuguo.adapter.shoppingcaradapter.ShoppingCarAdapter;
import com.pbph.yuguo.adapter.shoppingcaradapter.ShoppingCarViewHolder;
import com.pbph.yuguo.adapter.shoppingcaradapter.SpeLineViewHolder;
import com.pbph.yuguo.adapter.shoppingcaradapter.TextViewHolder;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseFragment;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.msg.LocationMsg;
import com.pbph.yuguo.myview.LineDividerDecoration;
import com.pbph.yuguo.myview.adapter.choicehelper.recyclerview.MultipleChoiceHelper;
import com.pbph.yuguo.myview.singlepointlistener.OnSPClickListener;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.DelShoppingCartResquest;
import com.pbph.yuguo.request.GetPageInfoForSubmitOrderResquest;
import com.pbph.yuguo.request.GetShoppingCartResquest;
import com.pbph.yuguo.request.UpdateShoppingCartAllResquest;
import com.pbph.yuguo.request.UpdateShoppingCartResquest;
import com.pbph.yuguo.response.GetShoppingCartResponse;
import com.pbph.yuguo.util.MoneyHelper;
import com.pbph.yuguo.util.RxBusF;
import com.pbph.yuguo.util.ToastDialog;
import com.pbph.yuguo.util.YesNoDialog;
import com.pbph.yuguo.vo.ShoppingCarFootVo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCarFragment extends BaseFragment {

    View shop_logout;

    private TextView tvShoppingcarLoc;

    private RecyclerView rvShoppingcar;
    private ShoppingCarAdapter adapter;
    private View emptyView;


    private TextView tvPriceAll;
    private TextView tvPriceFav;
    private CheckBox cbCheckAll;

    TextView button;

    private String longitude;  //纬度
    private String latitude;   //经度

    @Override
    public void onStart() {
        super.onStart();
//        getAfterSaleRule();

        longitude = "";  //纬度
        latitude = "";   //经度

        cbCheckAll.setOnCheckedChangeListener(null);
        cbCheckAll.setChecked(false);
        cbCheckAll.setOnCheckedChangeListener(onCheckedChangeListener);
        tvPriceAll.setText("0.00");

        button.setBackgroundColor(getContext().getResources().getColor(R.color.main_color));
        button.setText("去结算");
        button.setClickable(false);

        footVo.exp = false;
        adapter.clearDatas();

        emptyView.setVisibility(View.VISIBLE);

        if (!YuGuoApplication.isLogin()) {
            shop_logout.setVisibility(View.VISIBLE);
        } else {
            shop_logout.setVisibility(View.GONE);
            tvShoppingcarLoc.setText("点击获取定位信息");
            location();
        }
    }

    @Override
    public void onStop() {
        RxBusF.removeDisposable0(ShoppingCarFragment.this, LocationMsg.class);
        super.onStop();
    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_shoppingcar;
    }

    @Override
    public void initView() {

        shop_logout = mContentView.findViewById(R.id.shop_logout);

        mContentView.findViewById(R.id.btn_shop_login).setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        tvShoppingcarLoc = (TextView) mContentView.findViewById(R.id.tv_shoppingcar_loc);
        tvShoppingcarLoc.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {
                startActivity(new Intent(getContext(), ChoiceAddressListActivity.class));
            }
        });


        tvPriceAll = (TextView) mContentView.findViewById(R.id.tv_price_all);
        tvPriceFav = (TextView) mContentView.findViewById(R.id.tv_price_fav);


        emptyView = mContentView.findViewById(R.id.in_empty);
        emptyView.setVisibility(View.GONE);

        rvShoppingcar = (RecyclerView) mContentView.findViewById(R.id.rv_shoppingcar);


        LineDividerDecoration decoration = new LineDividerDecoration(getContext());
        //创建分割线对象，第一个参数为上下文，第二个参数为RecyclerView排列方向
//        DividerItemDecoration decoration = new DividerItemDecoration(mContext,
// DividerItemDecoration.VERTICAL);
        rvShoppingcar.addItemDecoration(decoration);//为RecyclerView添加分割线

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvShoppingcar.setLayoutManager(layoutManager);
        adapter = new ShoppingCarAdapter(mContext, rvShoppingcar, 6, MultipleChoiceHelper.class);

//        height = mContext.getScreenWidth() / 3;
//        adapter.bundle.putInt("add_height", height);

        //item内控件点击
        adapter.onItemViewClickListener = (rid, holder, objs) -> {
            switch (rid) {
                case R.id.cb_shoppingcar: {
                    GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean vo = (GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean) holder.item;

                    if (vo.getAvaliableFlag() == 0) return;

                    updateShoppingCart(vo.getShoppingCartId(), vo.getGoodsNum(), !adapter.choiceHelper.isChoiced(vo));
                }
                break;
//////////
                case R.id.tv_jian: {
                    GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean vo = (GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean) holder.item;

                    if (vo.getGoodsNum() <= 1) {
                        showToast("最少购买1件哦！");
                        return;
                    }

                    int num = vo.getGoodsNum() - 1;

                    updateShoppingCart(vo.getShoppingCartId(), num, adapter.choiceHelper.isChoiced(vo));
                }
                break;
                case R.id.tv_jia: {
                    GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean vo = (GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean) holder.item;

                    if (vo.getGoodsNum() >= vo.getStorageNum()) {
                        showToast("库存不足！");
                        return;
                    }
                    if (vo.getPurchaseLimitationNum() > 0 && vo.getGoodsNum() >= vo.getPurchaseLimitationNum()) {
                        showToast("不能再购买更多了哦！");
                        return;
                    }

                    int num = vo.getGoodsNum() + 1;
                    updateShoppingCart(vo.getShoppingCartId(), num, adapter.choiceHelper.isChoiced(vo));
                }
                break;
                case R.id.menuText: {
                    GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean vo = (GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean) holder.item;


                    YesNoDialog.show(mContext, "是否删除商品？", new YesNoDialog
                            .OnClickRateDialog() {
                        @Override
                        public void onClickLeft() {
                        }

                        @Override
                        public void onClickRight() {
                            delShoppingCart(String.valueOf(vo.getShoppingCartId()));
                        }
                    }, true);
                }
                break;
                case R.id.tv_clear: {
                    YesNoDialog.show(mContext, "清除失效的商品\n\n确认清除全部失效的商品吗？", new YesNoDialog
                            .OnClickRateDialog() {
                        @Override
                        public void onClickLeft() {

                        }

                        @Override
                        public void onClickRight() {
                            delShoppingCartUnUseList();
                        }
                    }, true);
                }
                break;
                case R.id.tv_zk: {
                    expDatas();
                }
                break;
                case R.id.iv_zk: {
                    expDatas();
                }
                break;
                case R.id.ll_shopcar_event: {


                    if (holder.item instanceof GetShoppingCartResponse.DataBean.MemberDayActivityBean) {
                        GetShoppingCartResponse.DataBean.MemberDayActivityBean mdaVo = (GetShoppingCartResponse.DataBean.MemberDayActivityBean) holder.item;

                        Intent intent = new Intent(mContext, VipDayActivity.class);
                        intent.putExtra("storeId", String.valueOf(mdaVo.getStoreGoodsInfoMemberDayList().get(0).getStoreId()));
                        intent.putExtra("memberDayActivityId", mdaVo.getMemberDayActivityId());
                        startActivity(intent);
                        return;
                    }
                    GetShoppingCartResponse.DataBean.ShoppingActivityListBean act = (GetShoppingCartResponse.DataBean.ShoppingActivityListBean) holder.item;
                    if (act.getActiveType() == 0) {//活动类型(0:满赠; 1:满x元y件)

                        if (act.getIsActivityOk() == 0) {
                            startActivity(new Intent(getContext(), EventCoudanActvity.class)
                                    .putExtra("acticeId", act.getActiveId())
                                    .putExtra("storeId", act.getStoreGoodsInfoCartList().get(0).getStoreId())
                            );
                        } else {
                            startActivity(new Intent(getContext(), EventCoudanGiftActvity.class)
                                    .putExtra("acticeId", act.getActiveId())
                                    .putExtra("storeId", act.getStoreGoodsInfoCartList().get(0).getStoreId())
                            );
                        }
                    } else {

                        startActivity(new Intent(getContext(), EventManjianActvity.class)
                                .putExtra("acticeId", act.getActiveId())
                                .putExtra("storeId", act.getStoreGoodsInfoCartList().get(0).getStoreId())
                        );
                    }

                }
                break;

            }
        };
//        item点击
        adapter.onItemClickListener = (parent, viewHolder) -> {
        };


        cbCheckAll = mContentView.findViewById(R.id.cb_check_all);
        cbCheckAll.setOnCheckedChangeListener(onCheckedChangeListener);

        button = mContentView.findViewById(R.id.button);
        button.setOnClickListener(new OnSPClickListener() {
            @Override
            public void onClickSucc(View v) {


                if (limitNumFlag == 1) {
                    ToastDialog.show(mContext, "", "部分商品超出限购数量，请修改商品数量", null);
                    return;
                }
                MultipleChoiceHelper choiceHelper = (MultipleChoiceHelper) adapter.choiceHelper;
                if (choiceHelper.choice_data.isEmpty()) {
                    showToast("请选择有效商品");
                    return;
                }

                GetPageInfoForSubmitOrderResquest placeOrderResquest = new GetPageInfoForSubmitOrderResquest();

                placeOrderResquest.setOrderFillType(2);
                placeOrderResquest.setReceiverLat(latitude);
                placeOrderResquest.setReceiverLng(longitude);

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < choiceHelper.choice_data.size(); i++) {
                    GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean vo = (GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean) choiceHelper.choice_data.get(i);
                    sb.append(",");
                    sb.append(vo.getShoppingCartId());
                }
                if (sb.length() > 0)
                    placeOrderResquest.setShoppingCartIds(sb.substring(1));

                YuGuoApplication application = (YuGuoApplication) mContext.getApplication();
                application.object = placeOrderResquest;
                startActivity(new Intent(getContext(), OrderFillActivity.class));
            }
        });
    }

    private void delShoppingCartUnUseList() {

        if (unUseList.size() <= 0) return;

        StringBuilder sb = new StringBuilder();
        for (int i = 0, c = unUseList.size(); i < c; i++) {
            GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean vo = unUseList.get(i);
            sb.append(",");
            sb.append(vo.getShoppingCartId());
        }
        delShoppingCart(sb.substring(1).toString());
    }

    private void expDatas() {
//            无效
        for (int i = 1, c = unUseList.size(); i < c; i++) {
            GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean vo = unUseList.get(i);
            if (adapter.contains(vo)) {
                adapter.removeData(vo);
            } else {
                adapter.addData(adapter.getItemCount() - 1, vo, ShoppingCarViewHolder.class);
            }
        }
        footVo.exp = !footVo.exp;
        adapter.notifyDataSetChanged();
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (buttonView, isChecked) -> {
        updateShoppingCartAll(isChecked);
    };


    private void location() {

        if (YuGuoApplication.userInfo.getRecAddId() != null) {
            //先判断有没有本地收货地址
            tvShoppingcarLoc.setText(YuGuoApplication.userInfo.getRecStreet());

            longitude = String.valueOf(YuGuoApplication.userInfo.getRecLongitude());  //纬度
            latitude = String.valueOf(YuGuoApplication.userInfo.getRecLatitude());   //经度
            getShoppingCart(latitude, longitude);
            return;
        }

        if (YuGuoApplication.userInfo.getStreet() != null) {
            tvShoppingcarLoc.setText(YuGuoApplication.userInfo.getStreet());

            longitude = String.valueOf(YuGuoApplication.userInfo.getLongitude());  //纬度
            latitude = String.valueOf(YuGuoApplication.userInfo.getLatitude());   //经度
            getShoppingCart(latitude, longitude);
            return;
        }

        RxBusF.register0(ShoppingCarFragment.this, LocationMsg.class, locationMsg -> {

            if (locationMsg.succ) {
//                    当定位完成后 发送的 rxbus 消息 通知所有接听者。
                tvShoppingcarLoc.setText(YuGuoApplication.userInfo.getStreet());

                longitude = String.valueOf(YuGuoApplication.userInfo.getLongitude());  //纬度
                latitude = String.valueOf(YuGuoApplication.userInfo.getLatitude());   //经度
                getShoppingCart(latitude, longitude);
            } else {
                getShoppingCart(latitude = "", longitude = "");
                Toast.makeText(getContext(), "未获取到定位信息", Toast.LENGTH_SHORT).show();
            }
        });
    }

    List<GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean> useList;
    List<GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean> unUseList;
    ShoppingCarFootVo footVo = new ShoppingCarFootVo();

    int limitNumFlag; //  0:没有超出限购  1: 超出了

    private void getShoppingCart(String latitude, String longitude) {

        HttpAction.getInstance().getShoppingCart(new GetShoppingCartResquest(YuGuoApplication.userInfo.getCustomerId(), latitude, longitude)).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                showToast(response.getMsg());
                emptyView.setVisibility(View.VISIBLE);
                return;
            }


            adapter.clearDatas();

            GetShoppingCartResponse.DataBean data = response.getData();

            limitNumFlag = data.getLimitNumFlag();

            tvPriceAll.setText(MoneyHelper.getInstance4Fen(data.getTotalPrice()).change2Yuan().getString());
            tvPriceFav.setText(MoneyHelper.getInstance4Fen(data.getPreferencePrice()).change2Yuan().getString());

            if (data.getWeigthFlag() == 1) {
                button.setBackgroundColor(getContext().getResources().getColor(R.color.light_gray));

                BigDecimal bigDecimal = new BigDecimal(data.getTotalWeight()).divide(new BigDecimal(1000));
                StringBuilder sb = new StringBuilder();
                sb.append("已超重\n");
                sb.append(new DecimalFormat("#.###").format(bigDecimal));
                sb.append("KG");

                button.setText(sb.toString());
                button.setClickable(false);
            } else {
                button.setBackgroundColor(getContext().getResources().getColor(R.color.main_color));
                button.setText("去结算");
                button.setClickable(true);
            }

            List<GetShoppingCartResponse.DataBean.ShoppingActivityListBean> alist = data.getShoppingActivityList();
            List<GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean> blist = data.getNoActivityGoodsCartList();
            unUseList = data.getAvaliableStoreGoodsInfoList();

            List<GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean> clist = null;

            GetShoppingCartResponse.DataBean.MemberDayActivityBean mdaVo = data.getMemberDayActivity();
            if (mdaVo != null) {
                clist = mdaVo.getStoreGoodsInfoMemberDayList();
            }


            int ac = alist == null ? 0 : alist.size();
            int bc = blist == null ? 0 : blist.size();
            int cc = clist == null ? 0 : clist.size();
            int uc = unUseList == null ? 0 : unUseList.size();


            if (ac + bc + uc + cc <= 0)
                emptyView.setVisibility(View.VISIBLE);
            else
                emptyView.setVisibility(View.GONE);

            useList = new ArrayList<>();

            //添加一行没什么用的分隔栏
            adapter.addData("", SpeLineViewHolder.class);

            // 添加活动商品

            //添加会员日
            if (clist != null && !clist.isEmpty()) {

                adapter.addData(mdaVo, EventVIPDayViewHolder.class);

                for (GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean gvo : clist) {
                    useList.add(gvo);
                    adapter.addData(gvo, ShoppingCarViewHolder.class);
                    if (gvo.getIsChecked() == 1) adapter.choiceHelper.putChoice(gvo);
                }
            }

            if (alist != null && !alist.isEmpty()) {

                if (clist != null && !clist.isEmpty()) adapter.addData("", SpeLineViewHolder.class);

                for (GetShoppingCartResponse.DataBean.ShoppingActivityListBean act : alist) {
                    adapter.addData(act, EventViewHolder.class);
                    List<GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean> agoodslist = act.getStoreGoodsInfoCartList();

                    if (agoodslist != null && !agoodslist.isEmpty()) {
                        for (GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean gvo : agoodslist) {
                            useList.add(gvo);
                            adapter.addData(gvo, ShoppingCarViewHolder.class);
                            if (gvo.getIsChecked() == 1) adapter.choiceHelper.putChoice(gvo);
                        }
                    }
                }
            }
            //添加普通商品
            if (blist != null && !blist.isEmpty()) {
                if ((alist != null && !alist.isEmpty()) || (clist != null && !clist.isEmpty()))
                    adapter.addData("", SpeLineViewHolder.class);

                for (GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean gvo : blist) {
                    useList.add(gvo);
                    adapter.addData(gvo, ShoppingCarViewHolder.class);
                    if (gvo.getIsChecked() == 1) adapter.choiceHelper.putChoice(gvo);
                }
            }

            //            分隔栏
            if (unUseList == null || unUseList.size() <= 0) {
                adapter.notifyDataSetChanged();
                return;
            }

            adapter.addData("", TextViewHolder.class);

            adapter.addData(unUseList.get(0), ShoppingCarViewHolder.class);
            footVo.exp = false;
            adapter.addData(footVo, FootViewHolder.class);

            adapter.notifyDataSetChanged();


        })));
    }

    //更新购物车
    private void updateShoppingCart(Integer shoppingCartId, Integer goodsNum, boolean isCheck) {

        HttpAction.getInstance().updateShoppingCart(new UpdateShoppingCartResquest
                (shoppingCartId, goodsNum, isCheck ? 1 : 0)).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }

            getShoppingCart(latitude, longitude);
        })));
    }

    //    删除购物车
    private void delShoppingCart(String ids) {

        HttpAction.getInstance().delShoppingCart(new DelShoppingCartResquest(ids)).subscribe(new BaseObserver<>(mContext, (response -> {

            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }
            getShoppingCart(latitude, longitude);
        })));
    }

    //全选
    private void updateShoppingCartAll(boolean b) {

        String ids = getShoppingCartIds(useList);
        if (TextUtils.isEmpty(ids)) return;

        cbCheckAll.setOnCheckedChangeListener(null);

        HttpAction.getInstance().updateShoppingCartAll(new UpdateShoppingCartAllResquest(b ? 1 : 0, ids)).subscribe(new BaseObserver<>(mContext, (response -> {

            cbCheckAll.setOnCheckedChangeListener(onCheckedChangeListener);

            if (200 != response.getCode()) {
                showToast(response.getMsg());
                return;
            }
            getShoppingCart(latitude, longitude);
        })));
    }


    private String getShoppingCartIds(List<GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean> list) {
//        MultipleChoiceHelper choiceHelper = (MultipleChoiceHelper) adapter.choiceHelper;
//        StringBuilder ids = new StringBuilder();
//        for (int i = 0; i < choiceHelper.choice_data.size(); i++) {
//            Object obj = choiceHelper.choice_data.get(i);
//            if (!(obj instanceof GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean)) continue;
//            GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean vo = (GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean) obj;
//            ids.append(",").append(vo.getShoppingCartId());
//        }
//        if (ids.length() <= 0) return null;
//        return ids.substring(1);

        if (list == null) return null;

        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            GetShoppingCartResponse.DataBean.StoreGoodsInfoListBean vo = list.get(i);
            ids.append(",").append(vo.getShoppingCartId());
        }
        if (ids.length() <= 0) return null;
        return ids.substring(1);
    }

}
