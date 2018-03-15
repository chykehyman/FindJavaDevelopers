package com.andela.android.javadevelopers.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.andela.android.javadevelopers.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        displayProfile();
    }

    private void displayProfile() {
        Intent intent = this.getIntent();

        String devProfileImage = intent.getStringExtra("PROFILE_IMAGE");
        String devUsername = intent.getStringExtra("USER_NAME");
        String devGithubLink = intent.getStringExtra("GITHUB_LINK");

        ImageView profileImage = findViewById(R.id.profile_image_header);
        TextView username = findViewById(R.id.username);
        TextView githubLink = findViewById(R.id.github_url);
        username.setText(devUsername);
        Picasso.with(this)
                .load(devProfileImage)
                .placeholder(R.drawable.no_profile)
                .into(profileImage);
        githubLink.setText(devGithubLink);
    }
}
