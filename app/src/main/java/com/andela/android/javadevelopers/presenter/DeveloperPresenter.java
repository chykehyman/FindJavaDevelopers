package com.andela.android.javadevelopers.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.andela.android.javadevelopers.adapter.ListAdapter;
import com.andela.android.javadevelopers.model.DevelopersList;
import com.andela.android.javadevelopers.model.DevelopersListResponse;
import com.andela.android.javadevelopers.service.DeveloperService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chike on 13/03/2018.
 */

public class DeveloperPresenter {
    private DeveloperService developerService;
    private final Context context;

    public DeveloperPresenter(Context context) {
        this.context = context;
        if (this.developerService == null) {
            this.developerService = new DeveloperService();
        }
    }

    public void getDevelopers(final RecyclerView recyclerView) {
        developerService
            .getAPI()
            .getDevelopersLists()
            .enqueue(new Callback<DevelopersListResponse>() {

                @Override
                public void onResponse(@NonNull Call<DevelopersListResponse> call,
                                       @NonNull Response<DevelopersListResponse> response) {
                    List<DevelopersList> developersList = response.body().getDevelopersLists();

                    if (developersList != null) {
                        RecyclerView.Adapter adapter = new ListAdapter(developersList, context);
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DevelopersListResponse> call,
                                      @NonNull Throwable t) {
                    try {
                        throw new InterruptedException("Something went wrong!");
                    } catch (InterruptedException e) {
                        Log.e("onFailure", e + "An error occurred");
                    }
                }
            });
    }
}
