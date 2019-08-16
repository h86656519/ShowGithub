package com.example.showgithubbyretrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Repo {
    @SerializedName("ID")
    @Expose
    public String ID = "";
    @SerializedName("Name")
    @Expose
    public String Name = "";
    @SerializedName("Attack")
    @Expose
    public int Attack = 0;
    @SerializedName("Defense")
    @Expose
    public int Defense = 0; //用gson 變數不能亂取，要跟資料一樣

    public void setDefense(int defense) {
        this.Defense = defense;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setAttack(int attack) {
        Attack = attack;
    }

    public String getName() {
        return Name;
    }

    public String getID() {
        return ID;
    }

    public int getDefense() {
        return Defense;
    }

    public int getAttack() {
        return Attack;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("ID:").append(getName()).append(", Name:").append(", Attack:").append(getDefense()).toString();
    }
}
