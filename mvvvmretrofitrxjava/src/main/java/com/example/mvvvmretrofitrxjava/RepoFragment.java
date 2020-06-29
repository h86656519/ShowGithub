package com.example.mvvvmretrofitrxjava;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RepoFragment extends Fragment {
    private static final String TAG = "RepoFragment";
    private Bundle bundle;
    private String account, reporsName;
    private View RepoFragmentView;
    private TextView reporsname_tv;
    private IssueAdapter issueAdapter;
    private ArrayList<String> title_list = new ArrayList<>();
    private ArrayList<String> comments_list = new ArrayList<>();
    private RecyclerView issueRecyclerView;

    public RepoFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RepoFragmentView = inflater.inflate(R.layout.fragment_repo, container, false);

        bundle = this.getArguments();
        account = bundle.getString("account");
        reporsName = bundle.getString("reporsname");
        Log.i(TAG, "account " + account + "reporsName " + reporsName);

        initView();

        requestgithub();
        return RepoFragmentView;
    }

    private void requestgithub() {
        PostApi postApi = AppClientManager.getGithubInstance().create(PostApi.class);
        postApi.getGithubIssue(account, reporsName).enqueue(new Callback<List<GithubRepo>>() {
            @Override
            public void onResponse(Call<List<GithubRepo>> call, Response<List<GithubRepo>> response) {
                Log.i(TAG, "response " + response.code());
                Log.i(TAG, "response " + response.message());

                if (response.isSuccessful()) {
                    issueAdapter = new IssueAdapter(getActivity());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
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
        reporsname_tv = RepoFragmentView.findViewById(R.id.reporsname);
        reporsname_tv.setText(reporsName);
        issueRecyclerView = (RecyclerView) RepoFragmentView.findViewById(R.id.issueRecyclerView);
    }


    public static RepoFragment getInstance() {
        RepoFragment rightFragment = new RepoFragment();
        return rightFragment;
    }
}
