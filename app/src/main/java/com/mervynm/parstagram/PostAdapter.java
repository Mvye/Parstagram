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
import com.bumptech.glide.request.target.Target;
import com.parse.ParseFile;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    public interface OnClickListener {
        void OnItemClicked(int position);
    }

    Context context;

    List<Post> posts;
    OnClickListener clickListener;

    public PostAdapter(Context context, List<Post> posts, OnClickListener clickListener) {
        this.context = context;
        this.posts = posts;
        this.clickListener = clickListener;
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

    public class ViewHolder extends RecyclerView.ViewHolder {

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

        public void bind(Post post) {
            String username = post.getUser().getUsername();
            postUsername.setText(username);
            postUsername2.setText(username);

            postDescription.setText(post.getDescription());

            ParseFile imageFile = post.getImage();
            if (imageFile != null) {
                postPicture.setVisibility(View.VISIBLE);
                Glide.with(context).load(imageFile.getUrl())
                                   .override(Target.SIZE_ORIGINAL)
                                   .into(postPicture);
            }
            else {
                postPicture.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.OnItemClicked(getAdapterPosition());
                }
            });
        }
    }
}
