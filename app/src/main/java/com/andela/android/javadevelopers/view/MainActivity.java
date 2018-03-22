package com.andela.android.javadevelopers.view;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.andela.android.javadevelopers.R;
import com.andela.android.javadevelopers.adapter.ListAdapter;
import com.andela.android.javadevelopers.model.DevelopersList;
import com.andela.android.javadevelopers.presenter.DeveloperPresenter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements DeveloperPresenter.View {
    public ArrayList<DevelopersList> developersList;
    DeveloperPresenter developerPresenter = new DeveloperPresenter(this);

    public static final String DEVELOPERS_LIST = "saved_state";

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);

    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeContainer;


    @Override
    public void displayDevelopersList(ArrayList<DevelopersList> listOfDevelopers) {
        developersList = listOfDevelopers;

        if (developersList != null) {
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.getLayoutManager().isAttachedToWindow();

            RecyclerView.Adapter adapter = new ListAdapter(developersList);
            recyclerView.setAdapter(adapter);
        }

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (swipeContainer != null && swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(false);
            Toast.makeText(this, "Developers list refreshed",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            developersList = savedInstanceState.getParcelableArrayList(DEVELOPERS_LIST);
            displayDevelopersList(developersList);
        } else {
            progressDialog = ProgressDialog.show(this, "Kenya Java Developers",
                    "Loading... Please wait!!!", false, false);

            developerPresenter.getDevelopers();
        }

        swipeContainer = findViewById(R.id.swipe_container);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                developerPresenter.getDevelopers();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(DEVELOPERS_LIST, developersList);
    }
}
