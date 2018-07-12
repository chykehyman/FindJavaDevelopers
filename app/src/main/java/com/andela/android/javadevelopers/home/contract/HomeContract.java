package com.andela.android.javadevelopers.home.contract;

import android.content.Context;

import com.andela.android.javadevelopers.home.model.DevelopersList;

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
         * @param list the list
         */
        void displayDevelopersList(ArrayList<DevelopersList> list);


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
         * Gets context.
         *
         * @return the context
         */
        Context getViewContext();

    }

    /**
     * The interface Home presenter.
     */
    interface HomePresenter {
        /**
         * Communicates with github service class to receive list of java developers.
         *
         * @param location - string representing selected city
         * @param limit    - string representing selected limit(number of developers to fetch)
         */
        void requestDataFromServer(String location, String limit);

        /**
         * Check network connection.
         *
         * @return the boolean
         */
        Boolean checkNetworkConnection();
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
             * @param list the list
             */
            void onFinished(ArrayList<DevelopersList> list);

            /**
             * On failure.
             *
             * @param t the t
             */
            void onFailure(Throwable t);
        }

        /**
         * Gets developers array list.
         *
         * @param url                the url
         * @param onFinishedListener the on finished listener
         */
        void getDevelopersArrayList(String url, OnFinishedListener onFinishedListener);
    }

    /**
     * The interface Recycler item click listener.
     */
    interface RecyclerItemClickListener {

        /**
         * On item click.
         *
         * @param developersList the developers list
         */
        void onItemClick(DevelopersList developersList);
    }
}
