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
        initTitle(TITLE_STYLE_MAIN_COLOR, "我的花果山", true, false);
        initView();
        initData();
//        connect();    改为密码输入成功后连接
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
                        // 这里一定要关闭，不然一直重试会引发OOM
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
        /*连接成功 {"msg":"连接成功","code":"901"}
          支付成功 {"msg":"支付成功","code":"902","data":{"payPrice":123}}*/
        try {
            JSONObject object = new JSONObject(msg);
            String code = object.getString("code");

            if (PAY_SUCCESS.equals(code)) {
                //弹出支付成功弹窗
                JSONObject data = object.getJSONObject("data");
                int payPrice = data.getInt("payPrice");
                View view = LayoutInflater.from(mContext).inflate(R.layout.popwindow_pay_success, null);
                TextView tv_pay_money = view.findViewById(R.id.tv_pay_money);
                TextView tv_ok = view.findViewById(R.id.tv_ok);

                tv_pay_money.setText(String.format("￥%s", (float) payPrice / 100));
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

    //根据用户id查询用户详情
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
        tvBalance.setText(String.format("￥%s", storeMoney));

        if (TextUtils.isEmpty(payPassword)) {
            //用户未设置支付密码  弹出提示
            BothBtnCenterPopWin popWin = new BothBtnCenterPopWin(mContext, tvPhone, "设置支付密码", "无支付密码，请前往设置", "取消", "去设置", false);
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
            //用户已设置支付密码  弹出密码输入框
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
                //输入密码 调获取二维码接口
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
                //密码输入成功后  开始倒计时  5分钟后重新请求网络 刷新二维码
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
                //倒计时  5分钟后重新请求网络 刷新二维码
                handler.postDelayed(runnable, 5 * 60 * 1000);
            } else {
                ToastUtil.showToast(mContext, response.getMsg());
                finish();
            }

        }, (code, message) -> {
            //如果二维码获取失败  则提示并退出当前页面
            ToastUtil.showToast(mContext, message);
            finish();
        }));
    }
}
