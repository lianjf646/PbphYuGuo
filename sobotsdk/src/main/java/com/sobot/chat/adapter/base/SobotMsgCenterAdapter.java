package com.sobot.chat.adapter.base;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sobot.chat.api.model.SobotMsgCenterModel;
import com.sobot.chat.utils.BitmapUtil;
import com.sobot.chat.utils.ResourceUtils;

import java.util.List;

/**
 * 消息中心
 * Created by jinxl on 2017/9/6.
 */
public class SobotMsgCenterAdapter extends SobotBaseAdapter<SobotMsgCenterModel> {

    public SobotMsgCenterAdapter(Context context, List<SobotMsgCenterModel> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SobotMsgCenterModel model = list.get(position);
        SobotMsgCenterViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(ResourceUtils.getIdByName(context, "layout", "sobot_msg_center_item"), null);
            viewHolder = new SobotMsgCenterViewHolder(context, convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SobotMsgCenterViewHolder) convertView.getTag();
        }
        viewHolder.bindData(model);
        return convertView;
    }


    public static class SobotMsgCenterViewHolder {
        TextView sobot_tv_title;
        TextView sobot_tv_content;
        TextView sobot_tv_unread_count;
        TextView sobot_tv_date;
        ImageView sobot_iv_face;
        Context context;
        public SobotMsgCenterModel data=null;

        public SobotMsgCenterViewHolder(Context context, View convertView) {
            this.context = context;
            sobot_tv_title = convertView.findViewById(ResourceUtils.getIdByName(context, "id", "sobot_tv_title"));
            sobot_tv_unread_count = convertView.findViewById(ResourceUtils.getIdByName(context, "id", "sobot_tv_unread_count"));
            sobot_tv_content = convertView.findViewById(ResourceUtils.getIdByName(context, "id", "sobot_tv_content"));
            sobot_tv_date = convertView.findViewById(ResourceUtils.getIdByName(context, "id", "sobot_tv_date"));
            sobot_iv_face = convertView.findViewById(ResourceUtils.getIdByName(context, "id", "sobot_iv_face"));

        }

        public void bindData(SobotMsgCenterModel model) {
            if (model == null) {
                return;
            }
            this.data = model;
            BitmapUtil.display(context, model.getFace(), sobot_iv_face);
            sobot_tv_title.setText(model.getName());
            sobot_tv_content.setText(TextUtils.isEmpty(model.getLastMsg())?"":Html.fromHtml(model.getLastMsg()).toString());
            sobot_tv_date.setText(model.getLastDate());
            setUnReadNum(sobot_tv_unread_count, model.getUnreadCount());
        }

        private void setUnReadNum(TextView view, int count) {
            if (count > 0) {
                if (count <= 9) {
                    view.setBackgroundResource(ResourceUtils.getIdByName(context, "drawable", "sobot_message_bubble_1"));
                    view.setText(count + "");
                } else if (count > 9 && count <= 99) {
                    view.setBackgroundResource(ResourceUtils.getIdByName(context, "drawable", "sobot_message_bubble_2"));
                    view.setText(count + "");
                } else {
                    view.setBackgroundResource(ResourceUtils.getIdByName(context, "drawable", "sobot_message_bubble_3"));
                    view.setText("99+");
                }
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }
}
