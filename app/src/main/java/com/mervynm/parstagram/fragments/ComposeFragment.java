package com.mervynm.parstagram.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mervynm.parstagram.BitmapScaler;
import com.mervynm.parstagram.HomeActivity;
import com.mervynm.parstagram.LoginActivity;
import com.mervynm.parstagram.Post;
import com.mervynm.parstagram.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComposeFragment#} factory method to
 * create an instance of this fragment.
 */
public class ComposeFragment extends Fragment {

    public static final String TAG = "ComposeFragment";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 72;

    Context context;

    Button buttonLogOut;
    Button buttonTakePicture;
    Button buttonMakePost;
    EditText editTextPostDescription;
    ImageView imageViewPostImage;

    private File photoFile;
    public String photoFileName = "photo.jpg";

    public ComposeFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();
        editTextPostDescription = view.findViewById(R.id.editTextPostDescription);
        imageViewPostImage = view.findViewById(R.id.imageViewPostPicture);
        buttonLogOut = view.findViewById(R.id.buttonLogOut);
        buttonTakePicture = view.findViewById(R.id.buttonTakePicture);
        buttonMakePost = view.findViewById(R.id.buttonMakePost);

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                Intent i = new Intent(context, LoginActivity.class);
                Toast.makeText(context, "Successfully Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(i);
                //finish();
            }
        });
        buttonTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });
        buttonMakePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = editTextPostDescription.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(context, "Description cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (photoFile == null || imageViewPostImage.getDrawable() == null) {
                    Toast.makeText(context, "There is no image", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(description, currentUser, photoFile);
            }
        });
    }
    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);

        Uri fileProvider = FileProvider.getUriForFile(context, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    private void savePost(String description, ParseUser currentUser, File photoFile) {
        Post post = new Post();
        post.setKeyDescription(description);
        post.setImage(new ParseFile(photoFile));
        post.setUser(currentUser);

        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue saving post", e);
                    Toast.makeText(context, "Issue saving post", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG, "Post successsfully saved");
                editTextPostDescription.setText(null);
                imageViewPostImage.setImageResource(0);
            }
        });
        goToFeed();
    }

    private void goToFeed() {
        Intent i = new Intent(context, HomeActivity.class);
        startActivity(i);
        //finish();
    }
    @Override

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                Uri takenPhotoUri = Uri.fromFile(getPhotoFileUri(photoFileName));
                Bitmap rawTakenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
                Bitmap resizedBitmap = BitmapScaler.scaleToFitWidth(rawTakenImage, 400);

                imageViewPostImage.setImageBitmap(resizedBitmap);
            } else {
                Toast.makeText(context, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }
}