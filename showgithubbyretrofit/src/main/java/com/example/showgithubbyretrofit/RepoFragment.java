package com.example.showgithubbyretrofit;


import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class RepoFragment extends Fragment {


    public RepoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo, container, false);
    }
    public static RepoFragment getInstance(){
        RepoFragment rightFragment = new RepoFragment();
        return rightFragment;
    }
}
