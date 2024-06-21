package com.layheang.appfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<NotificationItem> notificationList;
    private Context context;
    private OnItemClickListener listener;

    public NotificationAdapter(List<NotificationItem> notificationList, OnItemClickListener listener) {
        this.notificationList = notificationList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.notification_layout, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationItem item = notificationList.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(NotificationItem item);
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView messageTextView;
        private ImageView profileImageView;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.notification_title);
            messageTextView = itemView.findViewById(R.id.notification_message);
            profileImageView = itemView.findViewById(R.id.profile_image);
        }

        public void bind(final NotificationItem item, final OnItemClickListener listener) {
            titleTextView.setText(item.getTitle());
            messageTextView.setText(item.getMessage());
            // Set profile image here, example:
            // profileImageView.setImageResource(item.getProfileImageRes()); // Use this if you have drawable resource for each item
            // or
            // profileImageView.setImageDrawable(item.getProfileImageDrawable()); // Use this if you have drawable for each item

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
