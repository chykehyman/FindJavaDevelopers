package com.andela.android.javadevelopers.home.api;

import com.andela.android.javadevelopers.home.model.GitHubUsersResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;


/**
 * The interface Git hub api.
 */
public interface GitHubApi {
    /**
     * Gets developers lists.
     *
     * @param url the url
     * @return the developers lists
     */
    @GET
    Observable<GitHubUsersResponse> getDevelopersLists(
            @Url String url
    );
}
