package com.andela.android.javadevelopers.home.presenter;

import com.andela.android.javadevelopers.home.api.GitHubApi;
import com.andela.android.javadevelopers.home.contract.HomeContract;
import com.andela.android.javadevelopers.home.model.GitHubUser;

import java.util.ArrayList;

import javax.inject.Inject;


/**
 * The type Developer presenter.
 */
public class GitHubPresenter implements HomeContract.HomePresenter,
        HomeContract.GetDevelopersIntractor.OnFinishedListener {

    private HomeContract.HomeView view;
    private final HomeContract.GetDevelopersIntractor intractor;

    /**
     * Instantiates a new Developer presenter.
     *
     * @param intractor the get developers intractor
     */
    @Inject
    public GitHubPresenter(HomeContract.GetDevelopersIntractor intractor) {
        this.intractor = intractor;
    }

    @Override
    public void setView(HomeContract.HomeView view) {
        this.view = view;
    }

    /**
     * Communicates with github service class to receive list of java developers.
     *
     * @param location - string representing selected city
     * @param limit    - string representing selected limit(number of developers to fetch)
     */
    @Override
    public void requestDataFromServer(GitHubApi gitHubApi, String location, String limit) {
        String url = "search/users?q=language:java+location:"
                + location + "&per_page=" + limit + "&sort=followers";

        view.showLoader();

        intractor.getDevelopersArrayList(gitHubApi, url, this);
    }

    @Override
    public void onFinished(ArrayList<GitHubUser> list) {
        if (view != null) {
            view.displayDevelopersList(list);
            view.hideLoader();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (view != null) {
            view.hideLoader();
            view.showSnackBar();
            view.hideSwipeRefresh("failure");
        }
    }
}
