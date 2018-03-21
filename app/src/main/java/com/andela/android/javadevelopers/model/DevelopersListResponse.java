package com.andela.android.javadevelopers.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by chike on 12/03/2018.
 */

public class DevelopersListResponse {
    @SerializedName("items")
    private ArrayList<DevelopersList> developersLists;

    public ArrayList<DevelopersList> getDevelopersLists() {
        return developersLists;
    }
}
