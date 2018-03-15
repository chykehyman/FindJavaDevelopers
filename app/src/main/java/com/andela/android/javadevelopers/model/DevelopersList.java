package com.andela.android.javadevelopers.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chike on 07/03/2018.
 */

public class DevelopersList {
    @SerializedName("login")
    private String username;

    @SerializedName("avatar_url")
    private String profileImage;

    @SerializedName("html_url")
    private String githubLink;

    public DevelopersList(String username, String profileImage, String githubLink) {
        this.username = username;
        this.profileImage = profileImage;
        this.githubLink = githubLink;
    }

    public String getUsername() {
        return username;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getGithubLink() {
        return githubLink;
    }
}


