
package com.mervynm.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    Button buttonLogOut;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

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
}