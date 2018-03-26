package com.andela.android.javadevelopers.view;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.android.javadevelopers.R;
import com.andela.android.javadevelopers.adapter.ListAdapter;
import com.andela.android.javadevelopers.model.DevelopersList;
import com.andela.android.javadevelopers.presenter.DeveloperPresenter;
import com.andela.android.javadevelopers.util.CheckNetworkConnection;

import java.util.ArrayList;


/**
 * Application entry point activity
 */
public class MainActivity extends AppCompatActivity implements DeveloperPresenter.View {
    public ArrayList<DevelopersList> developersList;
    DeveloperPresenter developerPresenter = new DeveloperPresenter(this);
    CheckNetworkConnection cnc;
    Boolean isConnected;

    Snackbar snackbar;

    static final String DEVELOPERS_LIST = "saved_state";

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Called upon start of application
     *
     * @param savedInstanceState - a bundle of objects holding different application states
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSwipeRefreshLayout();

        cnc = new CheckNetworkConnection(this);
        isConnected = cnc.getConnectivityStatus();

        if (savedInstanceState != null) {
            developersList = savedInstanceState.getParcelableArrayList(DEVELOPERS_LIST);
            displayDevelopersList(developersList);
        } else {
            if (isConnected) {
                queryApi(swipeRefreshLayout);
            } else {
                displaySnackBar(swipeRefreshLayout, R.string.no_connection, developersList);
            }
        }
    }

    /**
     * Called before application states are destroyed
     *
     * @param outState - a bundle of objects holding different application states
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(DEVELOPERS_LIST, developersList);
    }


    /**
     * Initializes recyclerview and sets the list of developers to ListAdapter class, for display
     *
     * @param listOfDevelopers - array list of java developers
     */
    @Override
    public void displayDevelopersList(ArrayList<DevelopersList> listOfDevelopers) {
        developersList = listOfDevelopers;

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new ListAdapter(developersList);
        recyclerView.setAdapter(adapter);

        dismissDialog("success");

    }

    /**
     * Dismisses/closes any running dialogs (progress dialog or swipeRefresh) after api queries
     * Also displays toast message or snack bar depending on 'success' or 'failure' of api queries
     *
     * @param fetchStatus - a message string representing 'success' or 'failure' of api query
     */
    @Override
    public void dismissDialog(String fetchStatus) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            if ("success".equalsIgnoreCase(fetchStatus)) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(this, "Developers list refreshed",
                        Toast.LENGTH_LONG).show();
            } else { swipeRefreshLayout.setRefreshing(false); }
        }

        isConnected = cnc.getConnectivityStatus();

        if (!("success".equalsIgnoreCase(fetchStatus))) {
            int networkStatus =  R.string.no_connection;
            if (isConnected) {
                networkStatus = R.string.fetch_failed;
            }

            displaySnackBar(swipeRefreshLayout, networkStatus, developersList);
        }
    }


    /**
     * Initializes the SwipeRefreshLayout and sets an onRefresh listener
     */
    private void setSwipeRefreshLayout() {
        swipeRefreshLayout = findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryApi(swipeRefreshLayout);
            }
        });
    }


    /**
     * Queries github api for list of java developers
     *
     * @param swipeRefreshLayout - layout containing list of developers
     */
    public void queryApi(SwipeRefreshLayout swipeRefreshLayout) {
        if (!swipeRefreshLayout.isRefreshing()) {
            progressDialog = ProgressDialog.show(this, "Kenya Java Developers",
                    "Loading... Please wait!!!", false, false);
        }
            developerPresenter.getDevelopers();
    }

    /**
     * Displays internet connectivity notifications
     *
     * @param swipeRefreshLayout - layout containing list of developers
     * @param internetStatus     - contains current internet connectivity state
     * @param developersList     - array list of java developers
     */
    public void displaySnackBar(final SwipeRefreshLayout swipeRefreshLayout,
                               int internetStatus, final ArrayList<DevelopersList> developersList) {

        CharSequence actionText = "CLOSE";

        if (developersList == null) {
            actionText = "TRY AGAIN";
        }

        snackbar = Snackbar
                .make(swipeRefreshLayout, internetStatus, Snackbar.LENGTH_INDEFINITE)
                .setAction(actionText, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (developersList != null) {
                            snackbar.dismiss();
                        } else { queryApi(swipeRefreshLayout); }
                    }
                });

        // Change text color for action button
        snackbar.setActionTextColor(Color.CYAN);

        // Change snack bar message text color
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);

        snackbar.show();
    }
}
