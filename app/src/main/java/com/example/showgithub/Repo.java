package com.example.showgithub;

public class Repo {
    public String id = "";
    public String name = "";
    public String node_id = "";
    public String fullName = ""; //用gson 變數不能亂取，要跟資料一樣

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getFullName() {
        return fullName;
    }

    public String getNodeId() {
        return node_id;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("id:").append(getName()).append(", name:").append(", node_id:").append(getFullName()).toString();
    }
}
