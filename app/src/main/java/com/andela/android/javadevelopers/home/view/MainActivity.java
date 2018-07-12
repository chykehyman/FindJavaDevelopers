package com.andela.android.javadevelopers.home.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.android.javadevelopers.R;
import com.andela.android.javadevelopers.details.view.DetailsActivity;
import com.andela.android.javadevelopers.home.adapter.ListAdapter;
import com.andela.android.javadevelopers.home.contract.HomeContract;
import com.andela.android.javadevelopers.home.contract.HomeContract.HomePresenter;
import com.andela.android.javadevelopers.home.contract.HomeContract.RecyclerItemClickListener;
import com.andela.android.javadevelopers.home.model.DevelopersList;
import com.andela.android.javadevelopers.home.model.GetDevelopersIntractorImpl;
import com.andela.android.javadevelopers.home.presenter.DeveloperPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Application entry point activity
 */
public class MainActivity extends AppCompatActivity implements HomeContract.HomeView {
    /**
     * The Developers list.
     */
    public ArrayList<DevelopersList> developersList;
    /**
     * The Developer presenter.
     */
    HomePresenter developerPresenter = new DeveloperPresenter(this,
            new GetDevelopersIntractorImpl());
    /**
     * Boolean variable that represents if there is network connection or not.
     */
    Boolean isConnected;

    /**
     * The SnackBar object.
     */
    Snackbar snackbar;

    /**
     * The Developers list key constant.
     */
    static final String DEVELOPERS_LIST = "saved_state";

    /**
     * The Recycler view.
     */
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    /**
     * The Layout manager.
     */
    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);

    /**
     * The Progress dialog.
     */
    ProgressDialog progressDialog;

    /**
     * The Swipe refresh layout.
     */
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    /**
     * The Location.
     */
    String location, /**
     * The Limit.
     */
    limit;

    /**
     * Called upon start of application
     *
     * @param savedInstanceState - a bundle of objects holding different application states
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSwipeRefreshWidget();

        Intent intent = getIntent();
        location = intent.getStringExtra("city");
        limit = intent.getStringExtra("limit");

        if (savedInstanceState != null) {
            developersList = savedInstanceState.getParcelableArrayList(DEVELOPERS_LIST);
            displayDevelopersList(developersList);
        } else {
            if (developerPresenter.checkNetworkConnection()) {
                developerPresenter.requestDataFromServer(location, limit);
            } else {
                displaySnackBar(R.string.no_connection, developersList);
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
     * Show loader.
     */
    @Override
    public void showLoader() {
        String title = String.format("%s java developers", location);
        if (!swipeRefreshLayout.isRefreshing()) {
            progressDialog = ProgressDialog.show(this, title,
                    "Loading... Please wait!!!", false, false);
        }
    }

    /**
     * Hide loader.
     */
    @Override
    public void hideLoader() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private final RecyclerItemClickListener recyclerItemClickListener =
            new RecyclerItemClickListener() {
        @Override
        public void onItemClick(DevelopersList developersList) {
            Intent intent = new Intent(getViewContext(), DetailsActivity.class);
                intent.putExtra("devDetails", developersList);
            startActivity(intent);

        }
    };

    /**
     * Initializes recyclerview and sets the list of developers to ListAdapter class, for display
     *
     * @param listOfDevelopers - array list of java developers
     */
    @Override
    public void displayDevelopersList(ArrayList<DevelopersList> listOfDevelopers) {
        developersList = listOfDevelopers;

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new ListAdapter(developersList, recyclerItemClickListener);
        recyclerView.setAdapter(adapter);

        hideSwipeRefresh("success");
    }

    /**
     * Show snack bar.
     */
    @Override
    public void showSnackBar() {
        isConnected = developerPresenter.checkNetworkConnection();

        int networkStatus = R.string.no_connection;
        if (isConnected) {
            networkStatus = R.string.fetch_failed;
        }

        displaySnackBar(networkStatus, developersList);
    }

    /**
     * Hide snack bar.
     */
    @Override
    public void hideSnackBar() {
        snackbar.dismiss();
    }

    /**
     * Dismisses/closes any running dialogs (progress dialog or swipeRefresh) after api queries
     * Also displays toast message or snack bar depending on 'success' or 'failure' of api queries
     *
     * @param fetchStatus - a message string representing 'success' or 'failure' of api query
     */
    @Override
    public void hideSwipeRefresh(String fetchStatus) {
        if (swipeRefreshLayout.isRefreshing()) {
            if ("success".equalsIgnoreCase(fetchStatus)) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(this, "Developers list refreshed",
                        Toast.LENGTH_LONG).show();
            } else {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    /**
     * Initializes the SwipeRefreshLayout and sets an onRefresh listener
     */
    private void setSwipeRefreshWidget() {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                developerPresenter.requestDataFromServer(location, limit);
            }
        });
    }

    /**
     * Displays internet connectivity notifications
     *
     * @param internetStatus - contains current internet connectivity state
     * @param developersList - array list of java developers
     */
    public void displaySnackBar(
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
                            hideSnackBar();
                        } else {
                            developerPresenter.requestDataFromServer(location, limit);
                        }
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

    /**
     * Gets context.
     *
     * @return the context
     */
    @Override
    public Context getViewContext() {
        return getApplicationContext();
    }
}
