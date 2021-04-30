package com.pbph.yuguo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pbph.yuguo.R;
import com.pbph.yuguo.adapter.MessageAdapter;
import com.pbph.yuguo.application.UserInfo;
import com.pbph.yuguo.base.BaseActivity;
import com.pbph.yuguo.http.HttpAction;
import com.pbph.yuguo.myview.RecycleViewDivider;
import com.pbph.yuguo.observer.BaseObserver;
import com.pbph.yuguo.request.DeleteMessageRequest;
import com.pbph.yuguo.request.GetMessageInfoAndUnreadMessageCountRequest;
import com.pbph.yuguo.request.ReadMessageRequest;
import com.pbph.yuguo.response.GetMessageInfoAndUnreadMessageCountResponse;
import com.pbph.yuguo.util.YesNoDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 挡风的纱窗 on 2019/4/28.
 */
public class MessageActivity extends BaseActivity implements MessageAdapter.OnClickRemoveListener, View.OnClickListener {
    private MessageAdapter messageAdapter;
    private RecyclerView recycler;
    private List<GetMessageInfoAndUnreadMessageCountResponse.DataBean.ListBean> listBeanList = new ArrayList<>();
    private TextView tvNoMessage;
    private int customerId;
    private int totalCount = 0;// 未读消息数

    @Override
    public void onLeftClick() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        hideTitleView();
        customerId = UserInfo.getInstance().getCustomerId();
        initView();
    }


    public void initView() {
        View view = findViewById(R.id.include);
        ImageView ivLeft = view.findViewById(R.id.btn_left);
        ivLeft.setOnClickListener(this);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setOnClickListener(this);
        tvNoMessage = findViewById(R.id.tv_no_message);

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        messageAdapter = new MessageAdapter(this);
        recycler.setAdapter(messageAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMessageInfoAndUnreadMessageCount();
    }

    @Override
    public void clickRemoveListener(int messageType, int pos, MessageAdapter.Type type) {
        switch (type) {
            case JUMP:
                Intent intent = new Intent(this, OrderInfoActivity.class);
                intent.putExtra("messageType", messageType);
                startActivity(intent);
                break;

            case DELETE:
                YesNoDialog.show(this, "是否删除消息?", new YesNoDialog.OnClickRateDialog() {
                    @Override
                    public void onClickLeft() {
                    }

                    @Override
                    public void onClickRight() {
                        deleteMessage(messageType);
                    }
                }, false);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left: {
                finish();
                break;
            }
            case R.id.tv_title: {
                if (totalCount == 0) {
                    Toast.makeText(mContext, "消息全部已读", Toast.LENGTH_SHORT).show();
                    return;
                }
                YesNoDialog.show(this, "是否清除所有未读消息?", new YesNoDialog.OnClickRateDialog() {
                    @Override
                    public void onClickLeft() {

                    }

                    @Override
                    public void onClickRight() {
                        readMessage();
                    }
                }, false);
                break;
            }
        }
    }

    /**
     * 查询消息信息以及未读消息数量
     */
    private void getMessageInfoAndUnreadMessageCount() {
        HttpAction.getInstance().getMessageInfoAndUnreadMessageCount(new GetMessageInfoAndUnreadMessageCountRequest(customerId)
        ).subscribe(new BaseObserver<>(this, response -> {
            int code = response.getCode();
            if (code != 200) {
                Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }
            listBeanList = response.getData().getList();
            totalCount = response.getData().getTotalCount();
            if (listBeanList == null || listBeanList.size() == 0) {
                tvNoMessage.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.GONE);
            } else {
                tvNoMessage.setVisibility(View.GONE);
                recycler.setVisibility(View.VISIBLE);
                messageAdapter.setStringList(listBeanList);
            }
        }, (code, message) -> {
            tvNoMessage.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
        }));
    }

    /**
     * 删除消息
     *
     * @param messageType 消息类型
     */
    private void deleteMessage(int messageType) {
        HttpAction.getInstance().deleteMessage(new DeleteMessageRequest(customerId, messageType)).subscribe(new BaseObserver<>
                (this, response -> {
            if (response.getCode() != 200) {
                Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }
            getMessageInfoAndUnreadMessageCount();
//            listBeanList.remove(pos);
//            messageAdapter.notifyItemRemoved(pos);
//            messageAdapter.notifyItemRangeChanged(pos, listBeanList.size() - pos);
        }, (code, message) -> {
        }));
    }

    /**
     * 消息已读_
     */
    private void readMessage() {
        HttpAction.getInstance().readMessage(new ReadMessageRequest(customerId)).subscribe(new BaseObserver<>(this, response -> {
            if (response.getCode() != 200) {
                Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();
                return;
            }
            getMessageInfoAndUnreadMessageCount();
        }, (code, message) -> {

        }));
    }

}
