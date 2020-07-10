package com.mervynm.parstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mervynm.parstagram.fragments.ComposeFragment;
import com.mervynm.parstagram.fragments.HomeFragment;
import com.mervynm.parstagram.fragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = "HomeActivity";

    final FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        setupBottomNavigationItemSelected();
    }

    private void setupBottomNavigationItemSelected() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = new HomeFragment();
                if (menuItem.getItemId() == R.id.action_compose) {
                    fragment = new ComposeFragment();
                }
                else if (menuItem.getItemId() == R.id.action_home) {
                    fragment = new HomeFragment();
                }
                else if (menuItem.getItemId() == R.id.action_profile) {
                    fragment = new ProfileFragment();
                }
                fragmentManager.beginTransaction().replace(R.id.frameLayoutContainer, fragment).commit();
                return true;
            }
        });
    }
}