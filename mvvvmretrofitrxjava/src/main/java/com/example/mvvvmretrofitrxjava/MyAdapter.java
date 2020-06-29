package com.example.mvvvmretrofitrxjava;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvvmretrofitrxjava.databinding.ListlayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
//    ArrayList<String> name_list = new ArrayList<>(); 改傳物件(GithubRepo)了，故用不到了，可以刪
    List<GithubRepo> githubReposList = new ArrayList<>();
    private final String TAG = "MyAdapter";
    private OnItemClickListener mOnItemClickListener = null;
    Context Mycontext;


    public MyAdapter(Context context) {
        this.Mycontext = context;
    }

//    public void setNames(ArrayList<String> nameslist) {
//        name_list = nameslist;
//    }

    public void setGithubRepos(List<GithubRepo> githubRepos) {
        this.githubReposList = githubRepos;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listlayout, viewGroup, false);
//        view.setOnClickListener(this);
//        return new MyAdapter.ViewHolder(view);

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ListlayoutBinding binding = ListlayoutBinding.inflate(layoutInflater, viewGroup, false);
        return new MyAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder holder, final int position) {
//        String name = githubReposList.get(position).getName(); //before
        GithubRepo repo = githubReposList.get(position); //after

//        holder.name_holder.setText(name); //before
        holder.bind(repo);  //after

        if (highlightIndex == position) {
            //    holder.name_holder.setTextColor(Color.RED); //before
            holder.binding.name.setTextColor(Color.RED); //after 改用binding
        } else {
            //  holder.name_holder.setTextColor(Color.BLACK);
            holder.binding.name.setTextColor(Color.BLACK);
        }

//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Log.i(TAG, "view position : " + position);
//
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                highlightIndex = position;
                mOnItemClickListener.onItemClick(v, position);
                hilightouch(position);
                notifyDataSetChanged();
            }
        });
    }

    private int highlightIndex = -1;

    @Override
    public int getItemCount() {
//        return name_list.size();
        return githubReposList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name_holder;
        private View view;
        /**
         * ListlayoutBinding 是自動生成的，
         * 是以xmlLayout名字(就是一開始 "Convert to data binding layout" 的那個mxl ) + Binding 去拼成的
         */
        private final ListlayoutBinding binding;
//before
//        ViewHolder(final View itemView) {
//            super(itemView);
//            view = itemView;
//            this.name_holder = (TextView) itemView.findViewById(R.id.name);
//        }
//after
        ViewHolder(ListlayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            view = itemView;
        }

        //
        void bind(GithubRepo repo) {
            binding.setRepo(repo);
            binding.executePendingBindings(); //立刻綁定data，必寫，不然數據會對不上view，會報錯
        }

        public void setOnItemClick(View.OnClickListener l) {
            this.view.setOnClickListener(l);
        }


    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());

        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void hilightouch(int touch) {
        if (highlightIndex == touch) {
            highlightIndex = -1;
        } else {
            highlightIndex = touch;
        }
    }
}