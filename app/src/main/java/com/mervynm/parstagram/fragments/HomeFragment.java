package com.mervynm.parstagram.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.mervynm.parstagram.HomeActivity;
import com.mervynm.parstagram.Post;
import com.mervynm.parstagram.PostAdapter;
import com.mervynm.parstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";

    SwipeRefreshLayout swipeContainer;
    RecyclerView recyclerViewFeed;
    List<Post> feedPosts;
    PostAdapter postAdapter;

    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupSwipeToRefresh(view);
        setupRecyclerView(view);
        queryPosts(postAdapter);
    }

    private void setupSwipeToRefresh(View view) {
        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPosts(postAdapter);
                swipeContainer.setRefreshing(false);
            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void setupRecyclerView(View view) {
        recyclerViewFeed = view.findViewById(R.id.recyclerViewFeed);
        feedPosts = new ArrayList<>();
        PostAdapter.OnClickListener onClickListener = new PostAdapter.OnClickListener() {
            @Override
            public void OnItemClicked(int position) {
                createDetailedPostFragment(position);
            }
        };
        postAdapter = new PostAdapter(getContext(), feedPosts, onClickListener);
        recyclerViewFeed.setAdapter(postAdapter);
        recyclerViewFeed.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void createDetailedPostFragment(int position) {
        Post clickedPost = feedPosts.get(position);
        DetailedPostFragment detailedPost = new DetailedPostFragment(clickedPost);
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction().replace(R.id.frameLayoutContainer, detailedPost).commit();
    }

    protected void queryPosts(final PostAdapter postAdapter) {
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
                postAdapter.clear();
                postAdapter.addAll(posts);
            }
        });
    }
}