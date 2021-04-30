package com.pbph.yuguo.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pbph.yuguo.R;
import com.pbph.yuguo.response.GetMessageInfoAndUnreadMessageCountResponse;

import java.util.List;

/**
 * Created by 挡风的纱窗 on 2019/4/28.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<GetMessageInfoAndUnreadMessageCountResponse.DataBean.ListBean> dataBeanList;
    private OnClickRemoveListener onClickRemoveListener;


    public MessageAdapter(OnClickRemoveListener onClickRemoveListener) {
        this.onClickRemoveListener = onClickRemoveListener;
    }

    public void setStringList(List<GetMessageInfoAndUnreadMessageCountResponse.DataBean.ListBean> dataBeanList) {
        this.dataBeanList = dataBeanList;

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GetMessageInfoAndUnreadMessageCountResponse.DataBean.ListBean dataBean = dataBeanList.get(position);
        holder.showView(dataBean, position);

    }

    @Override
    public int getItemCount() {
        return dataBeanList != null ? dataBeanList.size() : 0;
    }

    public interface OnClickRemoveListener {
        void clickRemoveListener(int messageType,int pos, Type type);
    }

    public enum Type {
        DELETE, JUMP;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvTime;
        private TextView tvPointNum;
        private Button delete;
        private ConstraintLayout constraint;

        private GetMessageInfoAndUnreadMessageCountResponse.DataBean.ListBean dataBean;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            constraint = itemView.findViewById(R.id.constraint);
            delete = itemView.findViewById(R.id.delete);
            tvTitle = itemView.findViewById(R.id.title);
            tvContent = itemView.findViewById(R.id.content);
            tvTime = itemView.findViewById(R.id.time);
            tvPointNum = itemView.findViewById(R.id.tv_point_num);

            constraint.setOnClickListener(v -> {
                onClickRemoveListener.clickRemoveListener(dataBean.getMessageType(),position, Type.JUMP);
            });
            delete.setOnClickListener(v -> {
                onClickRemoveListener.clickRemoveListener(dataBean.getMessageType(),position, Type.DELETE);
            });

        }

        public void showView(GetMessageInfoAndUnreadMessageCountResponse.DataBean.ListBean dataBean, int position) {
            this.dataBean = dataBean;
            this.position = position;

            tvTitle.setText(dataBean.getMessageTypeName());
            tvContent.setText(dataBean.getMessageContent());
            tvTime.setText(dataBean.getCreateTime());
            if (dataBean.getCount() == 0) {
                tvPointNum.setVisibility(View.GONE);
            } else {
                tvPointNum.setVisibility(View.VISIBLE);
                tvPointNum.setText(String.valueOf(dataBean.getCount()));
            }
        }
    }
}
