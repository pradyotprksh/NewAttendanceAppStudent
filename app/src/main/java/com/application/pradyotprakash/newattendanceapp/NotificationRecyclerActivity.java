package com.application.pradyotprakash.newattendanceapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.like.LikeButton;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pradyotprakash on 04/03/18.
 */

public class NotificationRecyclerActivity extends RecyclerView.Adapter<NotificationRecyclerActivity.ViewHolder> {

    private List<Notification> notificationList;
    private Context context;

    public NotificationRecyclerActivity(List<Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_notification_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String notificationId = notificationList.get(position).notificationId;
        holder.from.setText(notificationList.get(position).getSenderName());
        CircleImageView mImageView = holder.mImage;
        Glide.with(context).load(notificationList.get(position).getSenderImage()).into(mImageView);
        String message = notificationList.get(position).getMessage();
        String likedValue = notificationList.get(position).getLiked();
        try {
            if (likedValue.equals("true")) {
                holder.starValue.setLiked(true);
            } else {
                holder.starValue.setLiked(false);
            }
        } catch (Exception e) {
            holder.starValue.setLiked(false);
        }
        if (message.length() >= 15) {
            message = message.substring(0, Math.min(message.length(), 10));
            message = message + "...";
            holder.message.setText(message);
        } else {
            holder.message.setText(notificationList.get(position).getMessage());
        }
        holder.on.setText(notificationList.get(position).getOn());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notificationIndent = new Intent(context, NotificationActivity.class);
                notificationIndent.putExtra("message", notificationList.get(position).getMessage());
                notificationIndent.putExtra("from_user_id", notificationList.get(position).getFrom());
                notificationIndent.putExtra("from_designation", notificationList.get(position).getDesignation());
                notificationIndent.putExtra("message_on", notificationList.get(position).getOn());
                notificationIndent.putExtra("notificationId", notificationId);
                context.startActivity(notificationIndent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView message, from, on;
        private CircleImageView mImage;
        private LikeButton starValue;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            message = mView.findViewById(R.id.message_value);
            from = mView.findViewById(R.id.sender_name);
            on = mView.findViewById(R.id.message_on);
            mImage = mView.findViewById(R.id.sender_list_image);
            starValue = mView.findViewById(R.id.star_button);
        }
    }
}
