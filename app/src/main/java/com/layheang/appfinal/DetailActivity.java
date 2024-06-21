package com.layheang.appfinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    private TextView detailTitle, detailMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Retrieve data from intent
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");

        // Initialize views
        detailTitle = findViewById(R.id.detail_title);
        detailMessage = findViewById(R.id.detail_message);

        // Set data to views
        detailTitle.setText(title);
        detailMessage.setText(description);
    }
}

