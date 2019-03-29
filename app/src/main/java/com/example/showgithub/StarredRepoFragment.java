package com.example.showgithub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StarredRepoFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.starredrepo_layout, container, false);
        return view;
    }

    public static StarredRepoFragment newInstance() {
        StarredRepoFragment f = new StarredRepoFragment();
        Bundle args = new Bundle();
        args.putInt("index", 1);
        f.setArguments(args);
        return f;
    }
}
