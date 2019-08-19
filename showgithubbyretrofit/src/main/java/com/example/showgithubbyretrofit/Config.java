package com.example.showgithubbyretrofit;

import androidx.fragment.app.Fragment;

public class Config {
    public static final String mygitHubURL = "https://api.github.com/users/h86656519/";
//    public static final String mygitHubURL = "https://api.github.com/users/h86656519/repos";

    public static final String jsonURL = "http://demo.kidtech.com.tw/files/appexam/";
    public static final String inputgitHubURL = "https://api.github.com/users/";
    public static final String postURL =       "https://api.github.com/repos/";
    public static final String baseURL =       "https://api.github.com/";

    // could be Main2Fragment or RepoFragment
    public static Fragment createFragment() {
        if(BuildConfig.DEBUG) {
            return Main2Fragment.getInstance();
        }
        RepoFragment repoFragment = RepoFragment.getInstance();
        return repoFragment;
    }

}
