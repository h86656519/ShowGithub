package com.example.mvvvmretrofitrxjava;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyViewModel extends AndroidViewModel {
    private final String TAG = "MyViewModel";
    PostApi postApi;
    Observable<List<GithubRepo>> data;
    Observable observable;
    MutableLiveData<List<GithubRepo>> datalive = new MutableLiveData();
    //    private int selectItem;
    public ObservableBoolean isDataEmpty = new ObservableBoolean(false);
    public ObservableBoolean isLoading = new ObservableBoolean(false);
    public ObservableInt selectItem = new ObservableInt();

    public MyViewModel(@NonNull Application application) {
        super(application);
        postApi = AppClientManager.getGithubInstance().create(PostApi.class);
    }

    public Observable<List<GithubRepo>> login(String account) {
        data = postApi.getGithubRX(account);
        return data;
    }

    //練習是沒有return的需求，寫void 就可以了
    public MutableLiveData<List<GithubRepo>> getData(String account, final MyAdapter adapter) {
        observable = postApi.getGithubRX(account);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GithubRepo>>() { //接回login回傳的資料
                    @Override
                    public void onSubscribe(Disposable d) {

                        Log.i(TAG, "onSubscribe: 開始連接");
                        isLoading.set(true);

                    }

                    @Override
                    public void onNext(List<GithubRepo> githubRepos) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        isLoading.set(false);
                        isDataEmpty.set(true); //顯示recycleView
//                        datalive.setValue(githubRepos);
                        datalive.setValue(githubRepos);
                        //  adapter.setGithubRepos(githubRepos);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: 連接失敗" + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: 連接完成");
                    }
                });
        return datalive;
    }

//    public void setSelect(int item) {
//        this.selectItem = item;
//    }
//
//    public int getSelectItem() {
//        return selectItem;
//    }
}
