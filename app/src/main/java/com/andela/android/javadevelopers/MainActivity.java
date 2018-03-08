package com.andela.android.javadevelopers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static List<DevelopersList> developersLists = new ArrayList<>();

    // dummy static data object
    static {
        developersLists.add(new DevelopersList("Chinwoke Hyginus", "Andela"));
        developersLists.add(new DevelopersList("Isioye Mohammed", "Andela"));
        developersLists.add(new DevelopersList("Kate Shaw", "Udacity"));
        developersLists.add(new DevelopersList("Bill Gates", "Windows"));
        developersLists.add(new DevelopersList("Anthony Martial", "Manchester United"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new ListAdapter(developersLists, this);
        recyclerView.setAdapter(adapter);
    }
}
