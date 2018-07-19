package com.andela.android.javadevelopers.home.contract;

import android.content.Context;

import com.andela.android.javadevelopers.home.model.GitHubUser;
import com.andela.android.javadevelopers.home.api.GitHubApi;

import java.util.ArrayList;


/**
 * The interface Home contract.
 */
public interface HomeContract {
    /**
     * The interface Home view.
     */
    interface HomeView {
        /**
         * Show loader.
         */
        void showLoader();

        /**
         * Hide loader.
         */
        void hideLoader();


        /**
         * Display developers list.
         *
         * @param list the list of github users
         */
        void displayDevelopersList(ArrayList<GitHubUser> list);


        /**
         * Show snack bar.
         */
        void showSnackBar();

        /**
         * Hide snack bar.
         */
        void hideSnackBar();

        /**
         * Hide swipe refresh.
         *
         * @param fetchStatus the fetch status
         */
        void hideSwipeRefresh(String fetchStatus);

        /**
         * Sets context.
         *
         * @return the application view context
         */
        Context setViewContext();
    }

    /**
     * The interface Home presenter.
     */
    interface HomePresenter {
        /**
         * Sets view.
         *
         * @param view - the view
         */
        void setView(HomeView view);

        /**
         * Communicates with model interactor class to receive list of java developers.
         *
         * @param gitHubApi - the GitHub api
         * @param location  - string representing selected city
         * @param limit     - string representing selected limit(number of developers to fetch)
         */
        void requestDataFromServer(GitHubApi gitHubApi, String location, String limit);
    }

    /**
     * The interface Get developers intractor.
     */
    interface GetDevelopersIntractor {

        /**
         * The interface On finished listener.
         */
        interface OnFinishedListener {
            /**
             * On finished.
             *
             * @param list - the list of github users
             */
            void onFinished(ArrayList<GitHubUser> list);

            /**
             * On failure.
             *
             * @param t - the t
             */
            void onFailure(Throwable t);
        }

        /**
         * Gets developers array list.
         *
         * @param gitHubApi          - the GitHub api
         * @param url                - the url
         * @param onFinishedListener - the on finished listener
         */
        void getDevelopersArrayList(GitHubApi gitHubApi,
                                    String url,
                                    OnFinishedListener onFinishedListener);
    }

    /**
     * The interface Recycler item click listener.
     */
    interface RecyclerItemClickListener {

        /**
         * On item click.
         *
         * @param gitHubUser - the developers list
         */
        void onItemClick(GitHubUser gitHubUser);
    }
}
