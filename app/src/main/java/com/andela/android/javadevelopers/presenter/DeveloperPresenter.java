package com.andela.android.javadevelopers.presenter;

import android.support.annotation.NonNull;

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

    /**
     * @param view - MainActivity's view context
     */
    public DeveloperPresenter(View view) {
        this.view = view;
        if (this.developerService == null) {
            this.developerService = new DeveloperService();
        }
    }

    /**
     * Interface implementable by its view activity (MainActivity)
     */
    public interface View {
        void displayDevelopersList(ArrayList<DevelopersList> list);
        void dismissDialog(String fetchStatus);
    }

    /**
     * Communicates with github service class to receive list of java developers.
     *
     * @param location - string representing selected city
     * @param limit - string representing selected limit(number of developers to fetch)
     */
    public void getDevelopers(String location, String limit) {
        String url = "search/users?q=language:java+location:"
                + location + "&per_page=" + limit + "&sort=followers";

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

                    view.displayDevelopersList(developersList);
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

                      view.dismissDialog("failure");
                }
            });
    }
}
