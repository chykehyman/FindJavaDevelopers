package com.andela.android.javadevelopers.home.model;

import android.support.annotation.NonNull;

import com.andela.android.javadevelopers.home.contract.HomeContract;
import com.andela.android.javadevelopers.home.api.GitHubApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * The type Get developers intractor.
 */
public class GetDevelopersIntractorImpl implements HomeContract.GetDevelopersIntractor {

    @Override
    public void getDevelopersArrayList(GitHubApi gitHubApi,
                                       String url,
                                       final OnFinishedListener onFinishedListener) {

        Call<GitHubUsersResponse> call = gitHubApi.getDevelopersLists(url);

        call.enqueue(new Callback<GitHubUsersResponse>() {

            /**
             * Receives response promise and calls the display developers method
             *
             * @param call     - retrofit http network instance
             * @param response - array(object) response/feedback promise
             */
            @Override
            public void onResponse(@NonNull Call<GitHubUsersResponse> call,
                                   @NonNull Response<GitHubUsersResponse> response) {

                GitHubUsersResponse developersResponse = response.body();
                ArrayList<GitHubUser> gitHubUser;

                assert developersResponse != null;

                gitHubUser = developersResponse.getGitHubUsers();

                onFinishedListener.onFinished(gitHubUser);
            }

            /**
             * Listens for request errors from api queries and dismisses any running dialogs
             *
             * @param call - retrofit http network instance
             * @param t    - exception error object
             */
            @Override
            public void onFailure(@NonNull Call<GitHubUsersResponse> call,
                                  @NonNull Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }
}
