package com.andela.android.javadevelopers;

/**
 * Created by chike on 07/03/2018.
 */

public class DevelopersList {
    private String username;
    private String workplace;

    public DevelopersList(String username, String workplace) {
        this.username = username;
        this.workplace = workplace;
    }

    public String getUsername() {
        return username;
    }

    public String getWorkplace() {
        return workplace;
    }
}
