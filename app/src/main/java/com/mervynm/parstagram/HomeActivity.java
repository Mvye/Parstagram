package com.mervynm.parstagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
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
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    Context context;

    RecyclerView recyclerViewFeed;
    Button buttonLogOut;
    BottomNavigationView bottomNavigationView;

    List<Post> feedPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = this;

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        recyclerViewFeed = findViewById(R.id.recyclerViewFeed);
        feedPosts = new ArrayList<>();
        final PostAdapter postAdapter = new PostAdapter(this, feedPosts);
        recyclerViewFeed.setAdapter(postAdapter);
        recyclerViewFeed.setLayoutManager(new LinearLayoutManager(this));

        queryPosts(postAdapter);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                if (menuItem.getItemId() == R.id.action_compose) {
                    Intent i = new Intent(context, MainActivity.class);
                    startActivity(i);
                }
                return true;
            }
        });

        buttonLogOut = findViewById(R.id.buttonLogOut);
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Intent i = new Intent(context, LoginActivity.class);
                Toast.makeText(context, "Successfully Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();
            }
        });
    }

    private void queryPosts(final PostAdapter postAdapter) {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post " + post.getDescription() +  ", username " + post.getUser().getUsername());
                }
                postAdapter.addAll(posts);
            }
        });
    }
}