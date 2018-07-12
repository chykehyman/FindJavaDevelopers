package com.andela.android.javadevelopers.home.presenter;

import com.andela.android.javadevelopers.home.contract.HomeContract;
import com.andela.android.javadevelopers.home.model.DevelopersList;
import com.andela.android.javadevelopers.util.CheckNetworkConnection;

import java.util.ArrayList;


/**
 * The type Developer presenter.
 */
public class DeveloperPresenter implements HomeContract.HomePresenter,
        HomeContract.GetDevelopersIntractor.OnFinishedListener {
    private final HomeContract.HomeView view;
    private final HomeContract.GetDevelopersIntractor getDevelopersIntractor;

    /**
     * Instantiates a new Developer presenter.
     *
     * @param view                   - MainActivity's view context
     * @param getDevelopersIntractor the get developers intractor
     */
    public DeveloperPresenter(HomeContract.HomeView view,
                              HomeContract.GetDevelopersIntractor getDevelopersIntractor) {
        this.view = view;
        this.getDevelopersIntractor = getDevelopersIntractor;
    }

    /**
     * Communicates with github service class to receive list of java developers.
     *
     * @param location - string representing selected city
     * @param limit    - string representing selected limit(number of developers to fetch)
     */
    @Override
    public void requestDataFromServer(String location, String limit) {
        String url = "search/users?q=language:java+location:"
                + location + "&per_page=" + limit + "&sort=followers";

        view.showLoader();

        getDevelopersIntractor.getDevelopersArrayList(url, this);
    }

    /**
     * Check network connection.
     */
    @Override
    public Boolean checkNetworkConnection() {
        return CheckNetworkConnection.getConnectivityStatus(view.getViewContext());
    }

    @Override
    public void onFinished(ArrayList<DevelopersList> list) {
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
