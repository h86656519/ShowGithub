package com.example.showgithubbyretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoActivity extends AppCompatActivity {
    private static final String TAG = "RepoActivity";
    String account, reporsName, issueTitle, issueComments;
    TextView reporsname_tv;
    IssueAdapter issueAdapter;
    ArrayList<String> title_list = new ArrayList<>();
    ArrayList<String> comments_list = new ArrayList<>();
    private RecyclerView issueRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);

        Intent intent = getIntent();
        reporsName = intent.getStringExtra("reporsname");
        account = intent.getStringExtra("account");

        initView();

        requestgithub();
    }

    public void requestgithub() {
        PostApi postApi = AppClientManager.getGithubInstance().create(PostApi.class);
//        Log.i(TAG, "account " + account);
//        Log.i(TAG, "reporsName " + reporsName);
        postApi.getGithubIssue(account, reporsName).enqueue(new Callback<List<GithubRepo>>() {
            @Override
            public void onResponse(Call<List<GithubRepo>> call, Response<List<GithubRepo>> response) {
                Log.i(TAG, "response " + response.code());
                Log.i(TAG, "response " + response.message());

                if (response.isSuccessful()) {
                    issueAdapter = new IssueAdapter(RepoActivity.this);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RepoActivity.this, RecyclerView.VERTICAL, false);
                    issueRecyclerView.setLayoutManager(linearLayoutManager);
                    issueRecyclerView.setAdapter(issueAdapter);

                    for (int i = 0; i < response.body().size(); i++) {
                        Log.i(TAG, "getTitle " + response.body().get(i).getTitle());
                        Log.i(TAG, "getBody " + response.body().get(i).getBody());
                        title_list.add(response.body().get(i).getTitle());
                        comments_list.add(response.body().get(i).getBody());
                    }

                    issueAdapter.setList(title_list, comments_list);
                    issueAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<GithubRepo>> call, Throwable t) {
                Log.i(TAG, "onFailure", t);
            }
        });
    }

    public void initView() {
        reporsname_tv = findViewById(R.id.reporsname);
        reporsname_tv.setText(reporsName);
        issueRecyclerView = (RecyclerView) findViewById(R.id.issueRecyclerView);
    }
}
