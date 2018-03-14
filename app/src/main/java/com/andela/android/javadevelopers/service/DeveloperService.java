package com.andela.android.javadevelopers.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chike on 12/03/2018.
 */

public class DeveloperService {
    private Retrofit retrofit = null;
    public static final String BASE_URL = "https://api.github.com";

    public DevelopersApi getAPI() {

        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(DevelopersApi.class);
    }
}
