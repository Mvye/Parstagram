package com.mervynm.parstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mervynm.parstagram.fragments.ComposeFragment;
import com.mervynm.parstagram.fragments.HomeFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = "HomeActivity";

    Context context;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;

        bottomNavigationView = findViewById(R.id.bottom_navigation);
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
                fragmentManager.beginTransaction().replace(R.id.frameLayoutContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_home);

        /*buttonLogOut = findViewById(R.id.buttonLogOut);
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Intent i = new Intent(context, LoginActivity.class);
                Toast.makeText(context, "Successfully Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();
            }
        });*/
    }
}