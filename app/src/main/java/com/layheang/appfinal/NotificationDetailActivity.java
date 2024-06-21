package com.layheang.appfinal;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        TextView title = findViewById(R.id.detail_title);
        TextView message = findViewById(R.id.detail_message);

        String notificationTitle = getIntent().getStringExtra("title");
        String notificationMessage = getIntent().getStringExtra("message");

        title.setText(notificationTitle);
        message.setText(notificationMessage);
    }
}
