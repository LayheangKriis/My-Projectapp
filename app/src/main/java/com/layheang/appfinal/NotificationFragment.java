package com.layheang.appfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private List<NotificationItem> notificationList;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notificationList = new ArrayList<>();
        adapter = new NotificationAdapter(notificationList, new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NotificationItem item) {
                // Handle item click here
                Toast.makeText(getContext(), "Clicked: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                // Navigate to detail activity here
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("message", item.getMessage());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

        loadNotifications();

        return view;
    }

    private void loadNotifications() {
        // Add sample notifications
        notificationList.add(new NotificationItem("Notification Title 1", "Message 1"));
        notificationList.add(new NotificationItem("Notification Title 2", "Message 2"));
        notificationList.add(new NotificationItem("Notification Title 3", "Message 3"));
        notificationList.add(new NotificationItem("Notification Title 4", "Message 4"));
        notificationList.add(new NotificationItem("Notification Title 5", "Message 5"));
        notificationList.add(new NotificationItem("Notification Title 6", "Message 6"));
        notificationList.add(new NotificationItem("Notification Title 7", "Message 7"));
        notificationList.add(new NotificationItem("Notification Title 8", "Message 8"));
        adapter.notifyDataSetChanged();
    }
}
