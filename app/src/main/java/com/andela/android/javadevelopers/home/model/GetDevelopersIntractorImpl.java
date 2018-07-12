package com.andela.android.javadevelopers.home.model;

import android.support.annotation.NonNull;

import com.andela.android.javadevelopers.home.contract.HomeContract;
import com.andela.android.javadevelopers.service.DeveloperService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * The type Get developers intractor.
 */
public class GetDevelopersIntractorImpl implements HomeContract.GetDevelopersIntractor {
    /**
     * Retrofit Service instance.
     */
    private final DeveloperService developerService = new DeveloperService();

    @Override
    public void getDevelopersArrayList(String url, final OnFinishedListener onFinishedListener) {
        developerService
                .getAPI()
                .getDevelopersLists(url)
                .enqueue(new Callback<DevelopersListResponse>() {

                    /**
                     * Receives response promise and calls the display developers method
                     *
                     * @param call     - retrofit http network instance
                     * @param response - array(object) response/feedback promise
                     */
                    @Override
                    public void onResponse(@NonNull Call<DevelopersListResponse> call,
                                           @NonNull Response<DevelopersListResponse> response) {

                        DevelopersListResponse developersResponse = response.body();
                        ArrayList<DevelopersList> developersList;

                        assert developersResponse != null;

                        developersList = developersResponse.getDevelopersLists();

                        onFinishedListener.onFinished(developersList);
                    }

                    /**
                     * Listens for request errors from api queries and dismisses any running dialogs
                     *
                     * @param call - retrofit http network instance
                     * @param t    - exception error object
                     */
                    @Override
                    public void onFailure(@NonNull Call<DevelopersListResponse> call,
                                          @NonNull Throwable t) {
                        onFinishedListener.onFailure(t);
                    }
                });

    }
}
