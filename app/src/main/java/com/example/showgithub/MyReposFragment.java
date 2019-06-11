package com.example.showgithub;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyReposFragment extends Fragment {
    private View view;
    private Context context;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private TextView id, nodeId, name, fullName;
   // private String HTML_URL = "https://api.github.com/user/repos";
  //  private String HTML_URL = "https://github.com/login/oauth/authorize?client_id=a72268d4b6855f2f208e&state=de08fa4c8b87d14e3a01f208b20d168e46db3483&redirect_uri=http://localhost/callback;";
    private String HTML_URL = "https://api.github.com/users/h86656519/repos";
    //private String token = "ab1d0248305b7c86dcf8690c42ecd23def3c05f3 ";
    private GsonParser gsonParser = new GsonParser();
    private ArrayList<String> name_list = new ArrayList<>();
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.myrepos_layout, container, false);
        id = (TextView) view.findViewById(R.id.id);
        nodeId = (TextView) view.findViewById(R.id.node_id);
        fullName = (TextView) view.findViewById(R.id.full_name);

        myAdapter = new MyAdapter(context);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        new Thread() {
            public void run() {
                try {
                    Message message = new Message();
                    message.what = 0x002;
                    String personData = GetData.getHtmlwithtoken(HTML_URL); //用token權限，抓下資料
                    ArrayList<Repo> persons = gsonParser.parse(personData.toString()); //方法2 傳過去用Gson做解析

                    message.obj = persons;
//                    message2.obj = GetData.getHtml(HTML_URL1); //second things
                    handler.sendMessage(message);
//                    handler.sendMessage(message2); //second things
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return view;
}
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x002:
                    ArrayList<Repo> persons = (ArrayList<Repo>) msg.obj;
                    // id.setText((String)msg.obj);
                    String nameString = "";
                    for (int i = 0; i < persons.size(); i++) {
                        nameString += persons.get(i).getName() + ", ";
                        name_list.add(persons.get(i).getName());
                    }
                    myAdapter.setNames(name_list);

                    recyclerView.setAdapter(myAdapter); //必須放在 myAdapter.setNames 之後做
                    myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener(){
                        @Override
                        public void onItemClick(View view , int position){
                            Log.i("suvini", "position : " + position);
                        }
                    });
                    id.setText(persons.get(0).getId()); //設定0，因為只想抓第一筆即可
                    nodeId.setText(persons.get(0).getNodeId());
//                    name.setText(nameString);
                    fullName.setText(persons.get(0).getfull_name());
                    Toast.makeText(getContext(), "HTML代码加载完毕", Toast.LENGTH_SHORT).show();
                    Log.i("suvini", "persons.get(0).getId() : " + persons.get(0).getId());
                    break;
                default:
                    break;
            }
        }
    };

    //工廠方法
    public static MyReposFragment newInstance(int index) {
        MyReposFragment f = new MyReposFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }
}
