package com.mervynm.parstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    Context context;
    List<Post> posts;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View postView = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void addAll(List<Post> postList) {
        posts.addAll(postList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        TextView postUsername;
        TextView postUsername2;
        ImageView postPicture;
        TextView postDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postUsername = itemView.findViewById(R.id.textViewUsername);
            postUsername2 = itemView.findViewById(R.id.textViewUsername2);
            postPicture = itemView.findViewById(R.id.imageViewPostPicture);
            postDescription = itemView.findViewById(R.id.textViewPostDescription);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Toast.makeText(context, "Item at position " + position + " clicked!", Toast.LENGTH_SHORT).show();
            }
        }

        public void bind(Post post) {
            String username = post.getUser().getUsername();
            postUsername.setText(username);
            postUsername2.setText(username);

            postDescription.setText(post.getDescription());

            ParseFile imageFile = post.getImage();
            if (imageFile != null) {
                Glide.with(context).load(imageFile.getUrl())
                        .into(postPicture);
            }
            else {
                postPicture.setVisibility(View.GONE);
            }
        }
    }

}
