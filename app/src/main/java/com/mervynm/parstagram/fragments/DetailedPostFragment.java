package com.mervynm.parstagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.mervynm.parstagram.Post;
import com.mervynm.parstagram.R;
import com.mervynm.parstagram.TimeFormatter;
import com.parse.ParseFile;

import java.util.Objects;

public class DetailedPostFragment extends Fragment {

    TextView postUsername;
    TextView postUsername2;
    ImageView postPicture;
    TextView postDescription;
    TextView postCreatedAt;
    Post clickedPost;

    public DetailedPostFragment() {}

    public DetailedPostFragment(Post clickedPost) {
        this.clickedPost = clickedPost;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detailed_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(R.color.white));
        setUpVariables(view);
        populateView();
    }

    private void setUpVariables(View view) {
        postUsername = view.findViewById(R.id.textViewUsername);
        postUsername2 = view.findViewById(R.id.textViewUsername2);
        postPicture = view.findViewById(R.id.imageViewPostPicture);
        postDescription = view.findViewById(R.id.textViewPostDescription);
        postCreatedAt = view.findViewById(R.id.textViewCreatedAt);
    }

    private void populateView() {
        String username = clickedPost.getUser().getUsername();
        postUsername.setText(username);
        ParseFile imageFile = clickedPost.getImage();
        if (imageFile != null) {
            postPicture.setVisibility(View.VISIBLE);
            Glide.with(Objects.requireNonNull(getContext())).load(imageFile.getUrl())
                    .override(Target.SIZE_ORIGINAL)
                    .into(postPicture);
        } else {
            postPicture.setVisibility(View.GONE);
        }
        postUsername2.setText(username);
        postDescription.setText(clickedPost.getDescription());
        postCreatedAt.setText(String.format("%s ago", TimeFormatter.getTimeDifference(clickedPost.getCreatedAt().toString())));
    }
}