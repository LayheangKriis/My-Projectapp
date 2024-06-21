package com.layheang.appfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private Button viewMoreButton1, viewMoreButton2, viewMoreButton3;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find buttons by their IDs
        viewMoreButton1 = view.findViewById(R.id.view_more_button1);
        viewMoreButton2 = view.findViewById(R.id.view_more_button2);
        viewMoreButton3 = view.findViewById(R.id.view_more_button3);

        // Set click listeners for the buttons
        viewMoreButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for item 1
                navigateToDetailActivity("Title 1", "Description 1");
            }
        });

        viewMoreButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for item 2
                navigateToDetailActivity("Title 2", "Description 2");
            }
        });

        viewMoreButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click for item 3
                navigateToDetailActivity("Title 3", "Description 3");
            }
        });

        return view;
    }

    private void navigateToDetailActivity(String title, String description) {
        // Create an intent to launch the detail activity
        Intent intent = new Intent(getActivity(), ItemDetailActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("description", description);
        startActivity(intent);
    }
}
