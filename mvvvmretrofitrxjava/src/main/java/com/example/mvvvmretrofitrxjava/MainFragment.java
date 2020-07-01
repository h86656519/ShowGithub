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

// TODO: 2020/6/29 1.google sample 參考 2.有沒有東西還要移到viewModel?  > 將rx改到viewModel8
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private EditText api_ed;
    View view;
    private String account;
    private MyViewModel myViewModel;
    MyViewModelFactory factory;
    FragmentMainBinding binding; //FragmentMainBinding 自動產生的，在fragment的話，命名應該會是fragmentXXXBinding，要試一下啊
    List<GithubRepo> githubReposList = new ArrayList<>();

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        initView();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL, false);
        recyclerView = binding.myRecyclerView; //重新綁view
        recyclerView.setLayoutManager(linearLayoutManager);


//        viewModel = new ViewModel(getActivity().getApplication()); //不可以這樣寫，要用ViewModelProvider 來取得viewModle，直接new 出生命週期就關聯不起來
        //不用工廠模式
        myViewModel = new ViewModelProvider(MainFragment.this).get(MyViewModel.class);
        //採用工廠模式
        factory = new MyViewModelFactory();                           //factory 要 implements 或 extends ViewModelProvider.Factory
        myViewModel = new ViewModelProvider(MainFragment.this, factory).get(MyViewModel.class);
        myAdapter = new MyAdapter(getActivity(), myViewModel);
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RepoFragment repoFragment = RepoFragment.getInstance();
                Bundle bundle = new Bundle();
                bundle.putString("reporsname", myViewModel.datalive.getValue().get(position).getName());
                bundle.putString("account", account);
                repoFragment.setArguments(bundle); //把資料加進 fragment
                replaceFragment(R.id.main_layout, repoFragment, true);
            }
        });
        recyclerView.setAdapter(myAdapter);
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
        myViewModel.getData(account, myAdapter);
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