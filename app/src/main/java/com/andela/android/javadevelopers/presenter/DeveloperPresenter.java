package com.andela.android.javadevelopers.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.andela.android.javadevelopers.model.DevelopersList;
import com.andela.android.javadevelopers.model.DevelopersListResponse;
import com.andela.android.javadevelopers.service.DeveloperService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chike on 13/03/2018.
 */

public class DeveloperPresenter {
    private DeveloperService developerService;
    private final View view;

    public DeveloperPresenter(View view) {
        this.view = view;
        if (this.developerService == null) {
            this.developerService = new DeveloperService();
        }
    }

    public interface View {
        void displayDevelopersList(ArrayList<DevelopersList> list);
    }

    public void getDevelopers() {
        developerService
            .getAPI()
            .getDevelopersLists()
            .enqueue(new Callback<DevelopersListResponse>() {

                @Override
                public void onResponse(@NonNull Call<DevelopersListResponse> call,
                                       @NonNull Response<DevelopersListResponse> response) {

                    DevelopersListResponse developersResponse = response.body();
                    ArrayList<DevelopersList> developersList;

                    assert developersResponse != null;

                    developersList = developersResponse.getDevelopersLists();

                    view.displayDevelopersList(developersList);
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
