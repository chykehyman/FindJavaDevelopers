package com.andela.android.javadevelopers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.andela.android.javadevelopers.util.CheckNetworkConnection;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by chike on 28/03/2018.
 */
public class CheckNetworkConnectionTest {
    private Context context;
    private NetworkInfo networkInfo;
    private ConnectivityManager cnManager;

    @Before
    public void setUp() throws Exception {
        context = Mockito.mock(Context.class);
        networkInfo = Mockito.mock(NetworkInfo.class);
        cnManager = Mockito.mock(ConnectivityManager.class);

    }

    @Test
    public void getConnectivityStatusReturnsTrueWhenActiveNetworkInfoIsNotNull() throws Exception {
        Mockito.when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(cnManager);
        Mockito.when(cnManager.getActiveNetworkInfo()).thenReturn(networkInfo);
        Mockito.when(networkInfo.isConnectedOrConnecting()).thenReturn(true);

        Assert.assertTrue(CheckNetworkConnection.getConnectivityStatus(context));

        Mockito.verify(networkInfo).isConnectedOrConnecting();

    }

    @Test
    public void getConnectivityStatusReturnsFalseWhenActiveNetworkInfoIsNotNull() throws Exception {
        Mockito.when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(cnManager);
        Mockito.when(cnManager.getActiveNetworkInfo()).thenReturn(networkInfo);
        Mockito.when(networkInfo.isConnectedOrConnecting()).thenReturn(false);

        Assert.assertFalse(CheckNetworkConnection.getConnectivityStatus(context));

        Mockito.verify(networkInfo).isConnectedOrConnecting();

    }

    @Test
    public void getConnectivityStatusReturnsFalseWhenActiveNetworkInfoIsNull() throws Exception {
        Mockito.when(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(cnManager);
        Mockito.when(cnManager.getActiveNetworkInfo()).thenReturn(null);

        Assert.assertFalse(CheckNetworkConnection.getConnectivityStatus(context));
    }
}
