package com.example.showgithubbyretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();

    }

    private void initView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        RepoFragment repoFragment = RepoFragment.getInstance(); 不需要一次將全部的fragment 都加入，除非是必須同時存在的2個畫面，不然一次加一個即可
        Main2Fragment main2Fragment = Main2Fragment.getInstance();
        fragmentTransaction.add(R.id.main2_layout, main2Fragment);
        fragmentTransaction.addToBackStack("list");
        fragmentTransaction.commit();
    }

    public void replaceFragment(int containerViewId, Fragment fragment, boolean addBackStack) {
        if (fragment != null && !isDestroyed() && !isFinishing()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(containerViewId, fragment);
            if (addBackStack)
                fragmentTransaction.addToBackStack("fragment");
            fragmentTransaction.commit();
        }
    }
}
