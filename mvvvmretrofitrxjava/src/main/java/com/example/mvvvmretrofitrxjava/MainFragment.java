package com.example.mvvvmretrofitrxjava;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mvvvmretrofitrxjava.databinding.FragmentMainBinding;
import com.example.mvvvmretrofitrxjava.databinding.ListlayoutBinding;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private EditText api_ed;
    View view;
    private String account;
    private ArrayList<String> name_list = new ArrayList<>();
    private MyViewModel myViewModel;
    MyViewModelFactory factory;
    FragmentMainBinding binding;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        initView();
        myAdapter = new MyAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL, false);
        recyclerView = binding.recyclerView;
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

//        viewModel = new ViewModel(getActivity().getApplication()); //不可以這樣寫，要用ViewModelProvider 來取得viewModle，直接new 出生命週期就關聯不起來
        //不用工廠模式
        myViewModel = new ViewModelProvider(MainFragment.this).get(MyViewModel.class);
        //採用工廠模式
        factory = new MyViewModelFactory();                           //factory 要 implements 或 extends ViewModelProvider.Factory
        myViewModel = new ViewModelProvider(MainFragment.this, factory).get(MyViewModel.class);

        return binding.getRoot();
    }

    private void initView() {
        api_ed = binding.edGithubaccount;
        api_ed.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) { //按下ENTER 的動作
                    account = api_ed.getText().toString();
                    loginGithub();
                    return false;
                }
                return false;
            }
        });
        //  recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    }

    private void loginGithub() {
//        1.將準備retrofit 的動作給viewModel 來做
//        2.viewModel 直接就回傳
        myViewModel.login(account).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GithubRepo>>() { //接回login回傳的資料
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: 開始連接");
                    }

                    @Override
                    public void onNext(List<GithubRepo> githubRepos) {
                        //Log.i(TAG, "回傳的資料" + githubRepos.toString());
//                        for (int i = 0; i < githubRepos.size(); i++) {
//                            name_list.add(githubRepos.get(i).getName());
//                        }
                        //myAdapter.setNames(name_list);
                        myAdapter.setGithubRepos(githubRepos);
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