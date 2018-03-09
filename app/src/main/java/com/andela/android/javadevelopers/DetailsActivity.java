package com.andela.android.javadevelopers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        displayProfile();
    }

    private void displayProfile() {
        Intent intent = this.getIntent();
        String devUsername = intent.getStringExtra("USER_NAME");
        String devCompany = intent.getStringExtra("COMPANY");

        // Capture the layout's TextView and set the string as its text
        TextView username = findViewById(R.id.username);
        TextView company = findViewById(R.id.company);
        username.setText(devUsername);
        company.setText(devCompany);
    }
}
