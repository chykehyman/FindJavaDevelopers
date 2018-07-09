package com.andela.android.javadevelopers.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chike on 07/03/2018.
 */

public class DevelopersList implements Parcelable {
    @SerializedName("login")
    private final String username;

    @SerializedName("avatar_url")
    private final String profileImage;

    @SerializedName("html_url")
    private final String githubLink;


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

    protected DevelopersList(Parcel in) {
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

    public static final Creator<DevelopersList> CREATOR = new Creator<DevelopersList>() {
        @Override
        public DevelopersList createFromParcel(Parcel in) {
            return new DevelopersList(in);
        }

        @Override
        public DevelopersList[] newArray(int size) {
            return new DevelopersList[size];
        }
    };
}


