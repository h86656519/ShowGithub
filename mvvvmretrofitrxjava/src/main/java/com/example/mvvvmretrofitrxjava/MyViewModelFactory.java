package com.example.mvvvmretrofitrxjava;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import io.reactivex.Observable;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    PostApi postApi;
    Observable<List<GithubRepo>> data;
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
