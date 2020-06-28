package com.example.mvvvmretrofitrxjava;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import io.reactivex.Observable;

public class MyViewModel extends AndroidViewModel {
    PostApi postApi;
    Observable<List<GithubRepo>> data;

    public MyViewModel(@NonNull Application application) {
        super(application);
        postApi = AppClientManager.getGithubInstance().create(PostApi.class);
    }

    public Observable<List<GithubRepo>> login(String account) {
        data = postApi.getGithubRX(account);
        return data;
    }
}
