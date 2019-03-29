package com.example.showgithub;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
     ArrayList<String> name_list = new ArrayList<>();
    private final String TAG = "MyAdapter";

    public MyAdapter(Context context) {

    }

    public void setNames(ArrayList<String> nameslist) {
        name_list = nameslist;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listlayout, viewGroup, false);
        return new MyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
        String name = name_list.get(position);
        holder.name_holder.setText(name);

    }

    @Override
    public int getItemCount() {
        return name_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name_holder;
        private View view;

        ViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            this.name_holder = (TextView) itemView.findViewById(R.id.name);
        }

        public void setOnItemClick(View.OnClickListener l) {
            this.view.setOnClickListener(l);
        }
    }
}