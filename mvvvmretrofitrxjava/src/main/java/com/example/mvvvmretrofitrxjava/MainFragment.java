package com.example.mvvvmretrofitrxjava;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private EditText api_ed;
    View view;
    private String account;
    private ArrayList<String> name_list = new ArrayList<>();
    private ViewModel viewModel;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main, container, false);
        initView();
        myAdapter = new MyAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RepoFragment repoFragment = RepoFragment.getInstance();
                Bundle bundle = new Bundle();
                bundle.putString("reporsname", name_list.get(position));
                bundle.putString("account", account);
                repoFragment.setArguments(bundle); //把資料加進 fragment
                replaceFragment(R.id.main_layout, repoFragment, true);
            }
        });
        return view;
    }

    private void initView() {
        api_ed = view.findViewById(R.id.ed_githubaccount);
        api_ed.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) { //按下ENTER 的動作
                    account = api_ed.getText().toString();
                    requestgithub();
                    return false;
                }
                return false;
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    private void requestgithub() {
        PostApi postApi = AppClientManager.getGithubInstance().create(PostApi.class);
        Observable<List<GithubRepo>> observable = postApi.getGithubRX(account);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())  // （新观察者）切换到主线程
                .subscribe(new Observer<List<GithubRepo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: 開始連接");
                    }

                    @Override
                    public void onNext(List<GithubRepo> githubRepos) {
                        Log.i(TAG, "回傳的資料" + githubRepos.toString());
                        for (int i = 0; i < githubRepos.size(); i++) {
                            name_list.add(githubRepos.get(i).getName());
                        }
                        myAdapter.setNames(name_list);
                        myAdapter.notifyDataSetChanged();
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

    }


    public void replaceFragment(int containerViewId, Fragment fragment, boolean addBackStack) {
        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(containerViewId, fragment);
            if (addBackStack)
                fragmentTransaction.addToBackStack("fragment");
            fragmentTransaction.commit();
        }
    }
}