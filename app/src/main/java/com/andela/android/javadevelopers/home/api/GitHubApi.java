package com.andela.android.javadevelopers.home.api;

import com.andela.android.javadevelopers.home.model.GitHubUsersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by chike on 12/03/2018.
 */

public interface GitHubApi {
    @GET
    Call<GitHubUsersResponse> getDevelopersLists(
            @Url String url
    );
}
