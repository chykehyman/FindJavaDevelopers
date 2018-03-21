package com.andela.android.javadevelopers.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);


    @Override
    public void displayDevelopersList(ArrayList<DevelopersList> listOfDevelopers) {
        developersList = listOfDevelopers;
        if (developersList != null) {
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);

            recyclerView.setLayoutManager(layoutManager);

            RecyclerView.Adapter adapter = new ListAdapter(developersList);
            recyclerView.setAdapter(adapter);
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
            developerPresenter.getDevelopers();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(DEVELOPERS_LIST, developersList);
    }
}
