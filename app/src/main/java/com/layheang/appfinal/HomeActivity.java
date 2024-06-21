package com.layheang.appfinal;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    public BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        frameLayout = findViewById(R.id.frame_layout);
        drawerLayout = findViewById(R.id.drawerlayout);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_home) {
                    loadFragment(new HomeFragment());
                    return true;
                } else if (itemId == R.id.action_upload) {
                    loadFragment(new UploadFragment());
                    return true;
                } else if (itemId == R.id.action_profile) {
                    loadFragment(new Profile());
                    return true;
                } else if (itemId == R.id.action_menu) {
                    drawerLayout.open();
                    return true;
                } else if (itemId == R.id.action_notification) {
                    loadFragment(new NotificationFragment());
                    return true;
                }
                return false;
            }
        });

        // Load the default fragment
        loadFragment(new HomeFragment());
    }

    public void loadFragment(Fragment fragment) {
        // Set the current timestamp
        long currentTimeMillis = System.currentTimeMillis();

        // Format the timestamp into a readable date format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String timePublished = sdf.format(new Date(currentTimeMillis));

        // Pass the timePublished to the fragment (if needed)

        // Create a bundle to pass data to the fragment
        Bundle bundle = new Bundle();
        bundle.putString("time_published", timePublished);

        // Set the bundle to the fragment
        fragment.setArguments(bundle);

        // Load the fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
