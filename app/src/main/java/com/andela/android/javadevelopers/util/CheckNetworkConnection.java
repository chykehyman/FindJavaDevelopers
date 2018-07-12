package com.andela.android.javadevelopers.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * The type Check network connection.
 */
public final class CheckNetworkConnection {
    /**
     * Private Constructor.
     */
    private CheckNetworkConnection() {
        // intentionally left blank
    }

    /**
     * Checks network/internet connectivity state.
     *
     * @param context the context
     * @return - true or false depending on network connectivity state
     */
    public static Boolean getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
