package com.example.showgithubbyretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private EditText api_ed;
    private String account;
    private static final String TAG = "MainActivity";
    private ArrayList<String> name_list = new ArrayList<>();
    private FrameLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        myAdapter = new MyAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("suvini", "position : " + position);
//                Intent intent = new Intent(MainActivity.this, RepoActivity.class);
//                intent.putExtra("reporsname", name_list.get(position));
//                intent.putExtra("account", account);
//                startActivity(intent);
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                RepoFragment repoFragment = RepoFragment.getInstance();

                Bundle bundle = new Bundle();
                bundle.putString("reporsname", name_list.get(position));
                bundle.putString("account", account);
                repoFragment.setArguments(bundle);
//                fragmentTransaction.add(R.id.fragment_repo, repoFragment);
                fragmentTransaction.replace(R.id.fragment, repoFragment);
                fragmentTransaction.commit();
            }
        });
    }

    public void initView() {
        mainLayout = (FrameLayout) findViewById(R.id.main_layout);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View repolist = layoutInflater.inflate(R.layout.repolist_layout, null);
        mainLayout.addView(repolist);
        api_ed = repolist.findViewById(R.id.githubaccount);
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
        recyclerView = (RecyclerView) repolist.findViewById(R.id.recyclerView);
    }

    public void requestgithub() {
        PostApi postApi = AppClientManager.getGithubInstance().create(PostApi.class);
        postApi.getDynamicGithub(account).enqueue(new Callback<List<GithubRepo>>() {
            @Override
            public void onResponse(Call<List<GithubRepo>> call, Response<List<GithubRepo>> response) {
//                Log.i("testt", "" + Thread.currentThread().getName());  取得目前跑在哪條thrread上
                Log.i(TAG, "response.code " + response.code());
                Log.i(TAG, "response.message " + response.message());
                if (response.isSuccessful()) {
                    Log.i(TAG, "response.body " + response.body());
                    Log.i(TAG, "response.size " + response.body().size());

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
}
