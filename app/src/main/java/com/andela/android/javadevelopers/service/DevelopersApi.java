package com.andela.android.javadevelopers.service;

import com.andela.android.javadevelopers.model.DevelopersListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by chike on 12/03/2018.
 */

public interface DevelopersApi {
    @GET
    Call<DevelopersListResponse> getDevelopersLists(
            @Url String url
    );
}
