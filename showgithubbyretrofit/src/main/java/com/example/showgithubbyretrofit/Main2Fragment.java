package com.example.showgithubbyretrofit;


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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Main2Fragment extends Fragment {
    private static final String TAG = "Main2Fragment";
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private EditText api_ed;
    private String account,reporsname;
    private ArrayList<String> name_list = new ArrayList<>();
    private View Main2FragmentView;

    public Main2Fragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Main2FragmentView = inflater.inflate(R.layout.fragment_main2, container, false);
        initView();
        myAdapter = new MyAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Log.i(TAG, "position : " + position);
                RepoFragment repoFragment = RepoFragment.getInstance();
                Bundle bundle = new Bundle();
                bundle.putString("reporsname", name_list.get(position));
                bundle.putString("account",  account);
                repoFragment.setArguments(bundle); //把資料加進 fragment
                ((Main2Activity) getActivity()).replaceFragment(R.id.main2_layout, repoFragment, true); //調用replaceFragment 將RepoFragment 加入進去
            }
        });
        return Main2FragmentView;
    }

    private void initView() {
        api_ed = Main2FragmentView.findViewById(R.id.githubaccount);
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
        recyclerView = (RecyclerView) Main2FragmentView.findViewById(R.id.recyclerView);
    }

    private void requestgithub() {
        PostApi postApi = AppClientManager.getGithubInstance().create(PostApi.class);
        postApi.getDynamicGithub(account).enqueue(new Callback<List<GithubRepo>>() {
            @Override
            public void onResponse(Call<List<GithubRepo>> call, Response<List<GithubRepo>> response) {
//                Log.i("testt", "" + Thread.currentThread().getName());  取得目前跑在哪條thrread上
               // Log.i(TAG, "response.code " + response.code());
                // Log.i(TAG, "response.message " + response.message());
                if (response.isSuccessful()) {
                 //   Log.i(TAG, "response.body " + response.body());
                   // Log.i(TAG, "response.size " + response.body().size());

                    for (int i = 0; i < response.body().size(); i++) {
                        name_list.add(response.body().get(i).getName());
                    }
                    myAdapter.setNames(name_list);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<GithubRepo>> call, Throwable t) {
                Log.i(TAG, "onFailure", t);
            }
        });
    }

    public static Main2Fragment getInstance() {
        Main2Fragment main2Fragment = new Main2Fragment();
        return main2Fragment;
    }

}
