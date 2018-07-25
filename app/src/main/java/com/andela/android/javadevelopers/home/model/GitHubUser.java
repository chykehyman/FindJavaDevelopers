package com.andela.android.javadevelopers.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chike on 07/03/2018.
 */

public class GitHubUser implements Parcelable {
    @SerializedName("login")
    private String username;

    @SerializedName("avatar_url")
    private String profileImage;

    @SerializedName("html_url")
    private String githubLink;

    public GitHubUser(String username, String profileImage, String githubLink) {
        this.username = username;
        this.profileImage = profileImage;
        this.githubLink = githubLink;
    }

    public GitHubUser() {
        // left blank
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

    @Override
    public int describeContents() {
        return 0;
    }

    protected GitHubUser(Parcel in) {
        username = in.readString();
        profileImage = in.readString();
        githubLink = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(profileImage);
        dest.writeString(githubLink);
    }

    public static final Creator<GitHubUser> CREATOR = new Creator<GitHubUser>() {
        @Override
        public GitHubUser createFromParcel(Parcel in) {
            return new GitHubUser(in);
        }

        @Override
        public GitHubUser[] newArray(int size) {
            return new GitHubUser[size];
        }
    };
}


