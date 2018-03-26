package com.andela.android.javadevelopers.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by chike on 22/03/2018.
 */


public class CheckNetworkConnection {
    private final Context context;

    /**
     * Constructor
     *
     * @param context - activity context
     */
    public CheckNetworkConnection(Context context) {
        this.context = context;
    }

    /**
     * Checks network/internet connectivity state
     *
     * @return - true or false depending on network connectivity state
     */
    public Boolean getConnectivityStatus() {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
