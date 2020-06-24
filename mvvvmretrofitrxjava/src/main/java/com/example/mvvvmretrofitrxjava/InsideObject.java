package com.example.mvvvmretrofitrxjava;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsideObject {
    @SerializedName("login")
    @Expose
    private String login = "";

    @SerializedName("id")
    @Expose
    private String id = "";

    @SerializedName("node_id")
    @Expose
    private String node_id = "";

    @SerializedName("avatar_url")
    @Expose
    private String avatar_url = "";

    @SerializedName("gravatar_id")
    @Expose
    private String gravatar_id = "";

    @SerializedName("url")
    @Expose
    private String url = "";

    @SerializedName("html_url")
    @Expose
    private String html_url = "";

    @SerializedName("followers_url")
    @Expose
    private String followers_url = "";

    public String getLogin() {
        return login;
    }

    public String getId() {
        return id;
    }

    public String getNode_id() {
        return node_id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public String getUrl() {
        return url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }
}
