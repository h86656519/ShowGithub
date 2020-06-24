package com.example.mvvvmretrofitrxjava;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
    ArrayList<String> name_list = new ArrayList<>();
    private final String TAG = "MyAdapter";
    private OnItemClickListener mOnItemClickListener = null;
    Context Mycontext;

    public MyAdapter(Context context) {
        this.Mycontext = context;
    }

    public void setNames(ArrayList<String> nameslist) {
        name_list = nameslist;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listlayout, viewGroup, false);
        view.setOnClickListener(this);
        return new MyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder holder, final int position) {
        String name = name_list.get(position);
        holder.name_holder.setText(name);
        if (highlightIndex == position) {
            holder.name_holder.setTextColor(Color.RED);
        } else {
            holder.name_holder.setTextColor(Color.BLACK);
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i(TAG, "view position : " + position);

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                highlightIndex = position;


                mOnItemClickListener.onItemClick(v,position);
                hilightouch(position);
                notifyDataSetChanged();
            }
        });
    }

    private int highlightIndex = -1;

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

//        public void setOnItemClick(View.OnClickListener l) {
//            this.view.setOnClickListener(l);
//        }


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