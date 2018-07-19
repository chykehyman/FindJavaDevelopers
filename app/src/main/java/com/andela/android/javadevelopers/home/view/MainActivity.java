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

import com.andela.android.javadevelopers.BuildConfig;
import com.andela.android.javadevelopers.R;
import com.andela.android.javadevelopers.dagger.application.App;
import com.andela.android.javadevelopers.dagger.component.AppComponent;
import com.andela.android.javadevelopers.dagger.component.DaggerMainActivityComponent;
import com.andela.android.javadevelopers.dagger.component.MainActivityComponent;
import com.andela.android.javadevelopers.dagger.module.ApiModule;
import com.andela.android.javadevelopers.dagger.module.GitHubAdapterModule;
import com.andela.android.javadevelopers.dagger.module.MainActivityContextModule;
import com.andela.android.javadevelopers.dagger.qualifier.ActivityContext;
import com.andela.android.javadevelopers.dagger.qualifier.ApplicationContext;
import com.andela.android.javadevelopers.details.view.DetailsActivity;
import com.andela.android.javadevelopers.home.adapter.GitHubUsersAdapter;
import com.andela.android.javadevelopers.home.api.GitHubApi;
import com.andela.android.javadevelopers.home.contract.HomeContract.HomePresenter;
import com.andela.android.javadevelopers.home.contract.HomeContract.RecyclerItemClickListener;
import com.andela.android.javadevelopers.home.model.GitHubUser;
import com.andela.android.javadevelopers.util.CheckNetworkConnection;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.andela.android.javadevelopers.home.contract.HomeContract.HomeView;


/**
 * Application entry point activity
 */
public class MainActivity extends AppCompatActivity implements HomeView, RecyclerItemClickListener {
    /**
     * The Developers list.
     */
    public ArrayList<GitHubUser> gitHubUser;

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
     * The Main activity component.
     */
    MainActivityComponent mainActivityComponent;

    /**
     * The App context.
     */
    @Inject
    @ApplicationContext
    public Context appContext;

    /**
     * The Activity context.
     */
    @Inject
    @ActivityContext
    public Context activityContext;

    /**
     * The Developer presenter.
     */
    @Inject
    HomePresenter developerPresenter;

    /**
     * The GitHub API interface.
     */
    @Inject
    GitHubApi gitHubApi;

    /**
     * The Git hub users adapter.
     */
    @Inject
    GitHubUsersAdapter gitHubUsersAdapter;

    /**
     * The Application saved state.
     */
    Bundle savedState;

    /**
     * Called upon start of application
     *
     * @param savedInstanceState - a bundle of objects holding different application states
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        savedState = savedInstanceState;

        ButterKnife.bind(this);
        AppComponent appComponent = App.get(this).getAppComponent();
        mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityContextModule(new MainActivityContextModule(this))
                .apiModule(new ApiModule(BuildConfig.BASEURL))
                .gitHubAdapterModule(new GitHubAdapterModule())
                .appComponent(appComponent)
                .build();

        mainActivityComponent.inject(this);

        Intent intent = getIntent();
        location = intent.getStringExtra("city");
        limit = intent.getStringExtra("limit");

        setSwipeRefreshWidget();
    }

    @Override
    protected void onResume() {
        super.onResume();
        developerPresenter.setView(this);

        requestDevelopersList(savedState);
    }

    /**
     * Called before application states are destroyed
     *
     * @param outState - a bundle of objects holding different application states
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(DEVELOPERS_LIST, gitHubUser);
    }


    /**
     * Check network connection.
     */
    public Boolean checkNetworkConnection() {
        return CheckNetworkConnection.getConnectivityStatus(activityContext);
    }

    /**
     * Requests list od developers from api.
     *
     * @param savedInstanceState - current save application state
     */
    private void requestDevelopersList(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            gitHubUser = savedInstanceState.getParcelableArrayList(DEVELOPERS_LIST);
            displayDevelopersList(gitHubUser);
            setSwipeRefreshWidget();
        } else {
            if (checkNetworkConnection()) {
                developerPresenter.requestDataFromServer(gitHubApi, location, limit);
            } else {
                displaySnackBar(R.string.no_connection, gitHubUser);
            }
        }
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

    /**
     * Initializes recyclerview and sets the list of developers to GitHubUsersAdapter class.
     *
     * @param listOfDevelopers - array list of java developers
     */
    @Override
    public void displayDevelopersList(ArrayList<GitHubUser> listOfDevelopers) {
        gitHubUser = listOfDevelopers;

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);

        gitHubUsersAdapter.setData(listOfDevelopers);

        RecyclerView.Adapter adapter = gitHubUsersAdapter;
        recyclerView.setAdapter(adapter);

        hideSwipeRefresh("success");
    }

    /**
     * Show snack bar.
     */
    @Override
    public void showSnackBar() {
        isConnected = checkNetworkConnection();

        int networkStatus = R.string.no_connection;
        if (isConnected) {
            networkStatus = R.string.fetch_failed;
        }

        displaySnackBar(networkStatus, gitHubUser);
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
                Toast.makeText(activityContext, "Developers list refreshed",
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

        swipeRefreshLayout
                .setOnRefreshListener(() -> developerPresenter
                        .requestDataFromServer(gitHubApi, location, limit));
    }

    /**
     * Displays internet connectivity notifications
     *
     * @param internetStatus - contains current internet connectivity state
     * @param gitHubUser     - array list of java developers
     */
    public void displaySnackBar(
            int internetStatus, final ArrayList<GitHubUser> gitHubUser) {

        CharSequence actionText = "CLOSE";

        if (gitHubUser == null) {
            actionText = "TRY AGAIN";
        }

        snackbar = Snackbar
                .make(swipeRefreshLayout, internetStatus, Snackbar.LENGTH_INDEFINITE)
                .setAction(actionText, view -> {
                    if (gitHubUser != null) {
                        hideSnackBar();
                    } else {
                        developerPresenter.requestDataFromServer(gitHubApi, location, limit);
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
     * Sets context.
     *
     * @return the application view context
     */
    @Override
    public Context setViewContext() {
        return appContext;
    }

    /**
     * On item click.
     *
     * @param gitHubUser - the developers list
     */
    @Override
    public void onItemClick(GitHubUser gitHubUser) {
        Intent intent = new Intent(activityContext, DetailsActivity.class);
        intent.putExtra("devDetails", gitHubUser);
        startActivity(intent);
    }
}
