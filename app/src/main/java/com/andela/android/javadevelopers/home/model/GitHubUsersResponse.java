package com.andela.android.javadevelopers.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by chike on 12/03/2018.
 */

public class GitHubUsersResponse {
    @SerializedName("items")
    private final ArrayList<GitHubUser> gitHubUsers = new ArrayList<>();

    ArrayList<GitHubUser> getGitHubUsers() {
        return gitHubUsers;
    }
}
