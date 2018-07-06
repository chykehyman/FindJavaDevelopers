package com.andela.android.javadevelopers.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.andela.android.javadevelopers.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public final class LocationActivity extends AppCompatActivity {
    List<String> listOfCities = new ArrayList<>();
    List<String> listOfLimit = new ArrayList<>();

    public static String city, limit;

    Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        ButterKnife.bind(this);

        intent = new Intent(this, MainActivity.class);

        setUpSpinner(R.id.spinner_city, listOfCities, "city", "lagos", "nairobi", "kampala");
        setUpSpinner(R.id.spinner_limit, listOfLimit, "limit", "10", "50", "100");
    }

    @OnClick(R.id.button)
    public void lauchActivityWithIntent() {
        intent
                .putExtra("city", city)
                .putExtra("limit", limit);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setUpSpinner(
            int id, List<String> spinnerDropdown,
            final String selectedSpinner,
            String... elements) {

        Spinner spinner = findViewById(id);

        spinnerDropdown.addAll(Arrays.asList(elements));

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerDropdown);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setElevation(5);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // attaching onItemSelected listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                switch (selectedSpinner) {
                    case "city":
                        city = selectedItem;
                        break;
                    case "limit":
                        limit = selectedItem;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
}
