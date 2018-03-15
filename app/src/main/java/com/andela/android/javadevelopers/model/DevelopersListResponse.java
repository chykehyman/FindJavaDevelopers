package com.andela.android.javadevelopers.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chike on 12/03/2018.
 */

public class DevelopersListResponse {
    @SerializedName("items")
    private List<DevelopersList> developersLists;

    public List<DevelopersList> getDevelopersLists() {
        return developersLists;
    }

}
