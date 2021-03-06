package com.pbph.yuguo.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.application.YuGuoApplication;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.dialog.BothBtnCenterPopWin;
import com.pbph.yuguo.dialog.PayPasswordPopWin;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.interfaces.OnPopWinBothBtnClickListener;
import com.pbph.yuguo.myview.WaitUI;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.GetCustomerInfoByIdRequest;
import com.pbph.yuguo.request.QrOrBarCodeRequest;
import com.pbph.yuguo.response.GetCustomerInfoByIdResponse;
import com.pbph.yuguo.response.QrOrBarCodeResponse;
import com.pbph.yuguo.util.GlideUtil;
import com.pbph.yuguo.util.MoneyHelper;
import com.pbph.yuguo.util.PublicViewUtil;
import com.sobot.chat.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class PaymentActivity extends BaseActivity {
    private static final String TAG = "TAG";

    private static final String CONNECT_SUCCESS = "901";
    private static final String PAY_SUCCESS = "902";

    private ImageView ivUserFace;
    private TextView tvPhone;
    private TextView tvBalance;
    private ImageView ivBarCode;
    private ImageView ivQrCode;

//    private static final String HOST = "47.92.203.164";
//    private static final int PORT = 9999;

    private static final String HOST = "39.97.5.68";
    private static final int PORT = 9998
            ;

    private SocketChannel socketChannel;
    private boolean isExit = false;

    private String payPassword;
    private String customerPhone;
    private String customerImgUrl;
    private String storeMoney;

    private Handler handler;
    private String customerUuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initTitle(TITLE_STYLE_MAIN_COLOR, "???????????????", true, false);
        initView();
        initData();
//        connect();    ?????????????????????????????????
        initClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCustomerInfoById();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isExit = true;
        handler.removeCallbacks(runnable);
        close();
    }

    private void initView() {
        handler = new Handler();
        ivUserFace = findViewById(R.id.iv_user_face);
        tvPhone = findViewById(R.id.tv_phone);
        tvBalance = findViewById(R.id.tv_balance);
        ivBarCode = findViewById(R.id.iv_bar_code);
        ivQrCode = findViewById(R.id.iv_qr_code);

    }

    private void initData() {

    }

    private void initClick() {

    }

    @Override
    public void onLeftClick() {
        finish();
    }

    public void connect() {
        if (isExit) {
            return;
        }

        if (socketChannel != null && socketChannel.isActive()) {
            return;
        }

        NioEventLoopGroup group = new NioEventLoopGroup();
        new Bootstrap()
                .channel(NioSocketChannel.class)
                .group(group)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new IdleStateHandler(0, 30, 0));
                        pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                        pipeline.addLast(new ChannelHandle());
                    }
                })
                .connect(new InetSocketAddress(HOST, PORT))
                .addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        socketChannel = (SocketChannel) future.channel();
                        socketChannel.writeAndFlush(customerUuid + "&" + YuGuoApplication.userInfo.getToken());
                        Log.e(TAG, "connect success");
                    } else {
                        Log.e(TAG, "connect failed");
                        close();
                        // ???????????????????????????????????????????????????OOM
                        future.channel().close();
                        group.shutdownGracefully();
                    }
                });
    }

    private void close() {
        if (socketChannel != null) {
            socketChannel.close();
            socketChannel = null;
        }
    }

    private class ChannelHandle extends SimpleChannelInboundHandler<String> {

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
            close();
            connect();
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            super.userEventTriggered(ctx, evt);
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent e = (IdleStateEvent) evt;
                if (e.state() == IdleState.WRITER_IDLE) {
                    ctx.writeAndFlush(customerUuid + "&" + YuGuoApplication.userInfo.getToken());
                    Log.e(TAG, "send ping to server");
                }
            }
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            if (!TextUtils.isEmpty(msg)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showPaySuccess(msg);
                    }
                });
            }

            ReferenceCountUtil.release(msg);
        }
    }

    private void showPaySuccess(String msg) {
        /*???????????? {"msg":"????????????","code":"901"}
          ???????????? {"msg":"????????????","code":"902","data":{"payPrice":123}}*/
        try {
            JSONObject object = new JSONObject(msg);
            String code = object.getString("code");

            if (PAY_SUCCESS.equals(code)) {
                //????????????????????????
                JSONObject data = object.getJSONObject("data");
                int payPrice = data.getInt("payPrice");
                View view = LayoutInflater.from(mContext).inflate(R.layout.popwindow_pay_success, null);
                TextView tv_pay_money = view.findViewById(R.id.tv_pay_money);
                TextView tv_ok = view.findViewById(R.id.tv_ok);

                tv_pay_money.setText(String.format("???%s", (float) payPrice / 100));
                Dialog dialog = new Dialog(mContext);
                dialog.setContentView(view);
                dialog.setCancelable(false);
                dialog.show();

                tv_ok.setOnClickListener(v -> dialog.dismiss());
                dialog.setOnDismissListener(dialogInterface -> finish());
            }
            Log.e(TAG, "showPaySuccess:" + msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //????????????id??????????????????
    private void getCustomerInfoById() {
        GetCustomerInfoByIdRequest request = new GetCustomerInfoByIdRequest(YuGuoApplication.userInfo.getCustomerId());
        HttpAction.getInstance().getCustomerInfoById(request).subscribe(new BaseObserver<>(mContext, response -> {
            if (response.getCode() != 200) {
                Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }

            GetCustomerInfoByIdResponse.DataBean.CustomerBean customerBean = response.getData().getCustomer();

            String payPassword = customerBean.getPayPassword();
            customerUuid = customerBean.getCustomerUuid();
            customerPhone = customerBean.getCustomerPhone();
            customerImgUrl = customerBean.getCustomerImgUrl();
            storeMoney = MoneyHelper.getInstance4Fen(customerBean.getStoredMoney()).change2Yuan().getString();
            showCustomerInfo(payPassword);
        }));
    }

    boolean isPwdRight = false;

    private void showCustomerInfo(String payPassword) {


        GlideUtil.displayCircleBitmap(mContext, customerImgUrl, ivUserFace);
        tvPhone.setText(customerPhone);
        tvBalance.setText(String.format("???%s", storeMoney));

        if (TextUtils.isEmpty(payPassword)) {
            //???????????????????????????  ????????????
            BothBtnCenterPopWin popWin = new BothBtnCenterPopWin(mContext, tvPhone, "??????????????????", "?????????????????????????????????", "??????", "?????????", false);
            popWin.setOnPopWinBothBtnClickListener(new OnPopWinBothBtnClickListener() {
                @Override
                public void onFirstBtnClick() {
                    popWin.dismiss();
                    finish();
                }

                @Override
                public void onSecondBtnClick() {
                    startActivity(new Intent(mContext, PasswordSetActivity.class));
                    popWin.dismiss();
                }
            });
        } else {
            //???????????????????????????  ?????????????????????
            PayPasswordPopWin popWin = new PayPasswordPopWin(mContext);
            popWin.show(tvPhone);
            popWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    PublicViewUtil.backgroundAlpha(mContext, 1f);
                    if (!isPwdRight) {
                        finish();
                    }
                }
            });
            popWin.setOnPayPasswordInputOverListener(inputPassword -> {
                //???????????? ????????????????????????
                this.payPassword = inputPassword;
                WaitUI.Show(mContext);
                getQrOrBarCode(popWin, inputPassword);
            });
        }
    }

    private void getQrOrBarCode(PayPasswordPopWin popWin, String inputPassword) {
        QrOrBarCodeRequest request = new QrOrBarCodeRequest(YuGuoApplication.userInfo.getCustomerId(), inputPassword);
        HttpAction.getInstance().getQrOrBarCode(request).subscribe(new BaseObserver<>(mContext, response -> {
            WaitUI.Cancel();
            if (response.getCode() == 200) {
                connect();
                isPwdRight = true;
                popWin.dismiss();
                QrOrBarCodeResponse.DataBean data = response.getData();
                GlideUtil.displayImage(mContext, data.getBarUrl(), ivBarCode);
                GlideUtil.displayImage(mContext, data.getQrUrl(), ivQrCode);
                //?????????????????????  ???????????????  5??????????????????????????? ???????????????
                handler.postDelayed(runnable, 5 * 60 * 1000);
            } else {
                isPwdRight = false;
                ToastUtil.showToast(mContext, response.getMsg());
            }

        }, (code, message) -> {
            WaitUI.Cancel();
            isPwdRight = false;
            ToastUtil.showToast(mContext, message);
        }));
    }

    Runnable runnable = this::getQrOrBarCode;

    private void getQrOrBarCode() {
        QrOrBarCodeRequest request = new QrOrBarCodeRequest(YuGuoApplication.userInfo.getCustomerId(), payPassword);
        HttpAction.getInstance().getQrOrBarCode(request).subscribe(new BaseObserver<>(mContext, response -> {
            if (response.getCode() == 200) {
                QrOrBarCodeResponse.DataBean data = response.getData();
                GlideUtil.displayImage(mContext, data.getBarUrl(), ivBarCode);
                GlideUtil.displayImage(mContext, data.getQrUrl(), ivQrCode);
                //?????????  5??????????????????????????? ???????????????
                handler.postDelayed(runnable, 5 * 60 * 1000);
            } else {
                ToastUtil.showToast(mContext, response.getMsg());
                finish();
            }

        }, (code, message) -> {
            //???????????????????????????  ??????????????????????????????
            ToastUtil.showToast(mContext, message);
            finish();
        }));
    }
}
