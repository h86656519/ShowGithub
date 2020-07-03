package com.example.mvvvmretrofitrxjava;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
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

/**
 * 實作功能:
 * 1.recycleView 沒資料時就不顯示gone
 * 2.模擬loaging，接完資料後睡3秒，再關掉 ProgressBar
 **/
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private EditText api_ed;
    View view;
    private String account;
    private MyViewModel myViewModel;
    MyViewModelFactory factory; //MyViewModelFactory 要 implements 或 extends ViewModelProvider.Factory
    FragmentMainBinding binding; //FragmentMainBinding 自動產生的，在fragment的話，命名應該會是fragmentXXXBinding，要試一下啊
    List<GithubRepo> githubReposList = new ArrayList<>();

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        binding.setLifecycleOwner(this);//綁定生命週期
        initView();

//        viewModel = new ViewModel(getActivity().getApplication()); //不可以這樣寫，要用ViewModelProvider 來取得viewModle，直接new 出生命週期就關聯不起來
        //非工廠模式，直接就用provider 建立viewModel
//        myViewModel = new ViewModelProvider(MainFragment.this).get(MyViewModel.class); //如果有共用同一個viewModel 的話，就不能綁在自己身上，不然另一個fragment 會get錯
        myViewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class); //要綁到activity
        //因為數據就在model裡儲存了，所以就不在繞一圈接到activity 再給factory再去產生viewNodel，所以不採用factory 模式

        /**工廠模式:ViewModelProvider.Factory 每次都會重新創建一個新的 ViewModel */
//        factory = new MyViewModelFactory(); //也可以透過這樣傳參數給viewModel ???
//        myViewModel = new ViewModelProvider(MainFragment.this, factory).get(MyViewModel.class);
//        myViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
//                .create(MyViewModel.class);

        myAdapter = new MyAdapter(getActivity(), myViewModel);
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RepoFragment repoFragment = RepoFragment.getInstance();
//                myViewModel.setSelect(position); //reporsname 用viewModel 共享方式得到資料
                myViewModel.selectItem.set(position);
                Bundle bundle = new Bundle();
//                bundle.putString("reporsname", myViewModel.datalive.getValue().get(position).getName());
                bundle.putString("account", account);
                repoFragment.setArguments(bundle); //把資料加進 fragment
                replaceFragment(R.id.main_layout, repoFragment, true);
//                getViewModelStore().clear(); 清除viewModel 的資料，練習用，依需求決定
            }
        });
        recyclerView.setAdapter(myAdapter);
        binding.setViewModel(myViewModel); //要設定這個，xml 裡的@{}，才會有作用
        return binding.getRoot();
    }

    private void initView() {
        api_ed = binding.edGithubaccount;
        api_ed.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) { //按下ENTER 的動作
                    //delay();
                    account = api_ed.getText().toString();
                    loginGithub();
                    return false;
                }
                return false;
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL, false);
        //  recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView); 一般寫法
        recyclerView = binding.myRecyclerView; //因採用livedata，故需要這樣綁view

        recyclerView.setLayoutManager(linearLayoutManager);
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