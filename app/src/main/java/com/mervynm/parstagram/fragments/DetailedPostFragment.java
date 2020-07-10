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

import com.mervynm.parstagram.R;

public class DetailedPostFragment extends Fragment {

    TextView postUsername;
    TextView postUsername2;
    ImageView postPicture;
    TextView postDescription;

    public DetailedPostFragment() {}

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

        postUsername = view.findViewById(R.id.textViewUsername);
        postUsername2 = view.findViewById(R.id.textViewUsername2);
        postPicture = view.findViewById(R.id.imageViewPostPicture);
        postDescription = view.findViewById(R.id.textViewPostDescription);

        postUsername.setText("testing");

    }
}