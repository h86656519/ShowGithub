package com.example.mvvvmretrofitrxjava;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import io.reactivex.Observable;

//這邊沒用到
public class MyViewModelFactory implements ViewModelProvider.Factory {
    PostApi postApi;
    Observable<List<GithubRepo>> data;
    MutableLiveData<List<GithubRepo>> datalive  = new MutableLiveData();

    public void MyViewModelFactory() {
        postApi = AppClientManager.getGithubInstance().create(PostApi.class);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return null;
    }

    public Observable<List<GithubRepo>> login(String account) {
        data = postApi.getGithubRX(account);
        return data;
    }
}
