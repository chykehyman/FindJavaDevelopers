package com.andela.android.javadevelopers.service;

import com.andela.android.javadevelopers.model.DevelopersListResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by chike on 12/03/2018.
 */

public interface DevelopersApi {
    @GET("search/users?q=language:java+location:nairobi&per_page=100&sort=followers")
    Call<DevelopersListResponse> getDevelopersLists();
}
