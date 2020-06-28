package com.example.mvvvmretrofitrxjava;

import androidx.lifecycle.LiveData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * 改用繼承LiveData
 * 好處:1.UI和資料保持一致，LiveData是使用觀察者模式，當LIfeCycle的狀態改變，LiveData會通知觀察者更新UI
 *     2.避免Memory Leak 及 Activity處於stop狀態而造成閃退
 *     3.在非avtivity focus 的情況下部會師到通知，只有在onStarted 和 onResumed 下才會自動更新資料
 *     4.不用處理跟生命週期有關的問題
 * */
public class GithubRepo  implements Serializable {
    @SerializedName("id") //gson 是透過這個去取得資料
    @Expose //Expose是用來聲明類成員是否需要進行JSON 的序列化或反序列化
    private String id = "";

    @SerializedName("name")
    @Expose
    private String name = "";
    @SerializedName("node_id")
    @Expose
    private String node_id = "";

    @SerializedName("full_name")
    @Expose
    private String full_name = ""; //用gson 變數不能亂取，要跟資料一樣

    @SerializedName("owner")
    @Expose
    private InsideObject owner;

    @SerializedName("body")
    @Expose
    private String body;

    @SerializedName("title")
    @Expose
    private String title;

    public void setfull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setNodeId(String nodeId) {
        this.node_id = nodeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId(){
        return id;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getNodeId() {
        return node_id;
    }

    public InsideObject getOwner() {
        return owner;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }
    @Override
    public String toString() {
       return new StringBuilder().append("id : ").append(getId()).append(",name : ").append(getName()).append(",node_id : ").append(getNodeId()).append(",full_name :").append(getFull_name()).append("\n").append("Owner : ").append(getOwner()).toString();
      //  return new StringBuilder().append("Owner : ").append(getOwner()).toString();
    }
}
