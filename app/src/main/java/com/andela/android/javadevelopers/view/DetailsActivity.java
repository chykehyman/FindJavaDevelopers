package com.andela.android.javadevelopers.view;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.andela.android.javadevelopers.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Details activity.
 */
public class DetailsActivity extends AppCompatActivity {
    private String devProfileImage;
    private String devUsername;
    private String devGitHubLink;

    /**
     * The Profile image.
     */
    @BindView(R.id.profile_image_header) ImageView profileImage;
    /**
     * The Username.
     */
    @BindView(R.id.username) TextView username;
    /**
     * The Github link.
     */
    @BindView(R.id.github_url) TextView githubLink;

    /**
     * The Toolbar.
     */
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        Intent intent = getIntent();
        devProfileImage = intent.getStringExtra("profileImage");
        devUsername = intent.getStringExtra("username");
        devGitHubLink = intent.getStringExtra("gitHubLink");

        displayProfile();
    }

    /**
     * Displays user profile in view.
     */
    private void displayProfile() {
        username.setText(devUsername);

        Picasso.with(this)
                .load(devProfileImage)
                .placeholder(R.drawable.no_profile)
                .into(profileImage);

        githubLink.setText(devGitHubLink);
    }

    /**
     * Generates and return share intent.
     *
     * @return - share intent
     */
    private Intent createShareIntent() {
        StringBuilder shareMessage = new StringBuilder();
        shareMessage.append(getString(R.string.share_part_message))
                .append(devUsername).append(", ").append(devGitHubLink);

        return ShareCompat.IntentBuilder.from(this)
                .setType(getString(R.string.share_intent_type))
                .setText(shareMessage)
                .getIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.share, menu);
        MenuItem menuItem = menu.findItem(R.id.share_action_button);
        menuItem.setIntent(createShareIntent());
        return true;
    }

}
