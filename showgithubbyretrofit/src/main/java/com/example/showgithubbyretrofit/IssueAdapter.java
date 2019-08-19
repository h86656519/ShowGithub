package com.example.showgithubbyretrofit;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.ViewHolder> implements View.OnClickListener {
    ArrayList<String> title_list, comments_list = new ArrayList<>();

    private final String TAG = "IssueAdapter";
    private IssueAdapter.OnItemClickListener mOnItemClickListener = null;
    Context Mycontext;

    public IssueAdapter(Context context) {
        this.Mycontext = context;
    }

    public void setList(ArrayList<String> titlelist, ArrayList<String> commentslist) {
        title_list = titlelist;
        comments_list = commentslist;
    }

    @Override
    public IssueAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.issuelistlayout, viewGroup, false);
        view.setOnClickListener(this);
        return new IssueAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final IssueAdapter.ViewHolder holder, final int position) {
        String title = title_list.get(position);
        String comments = comments_list.get(position);
        holder.title_holder.setText(title);
        holder.comments_holder.setText(comments);
        if (highlightIndex == position) {
            holder.title_holder.setTextColor(Color.RED);
        } else {
            holder.title_holder.setTextColor(Color.BLACK);
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

//                mOnItemClickListener.onItemClick(v, position);
                hilightouch(position);
                notifyDataSetChanged();
            }
        });
    }

    private int highlightIndex = -1;

    @Override
    public int getItemCount() {
        return title_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title_holder,comments_holder;
        private View view;

        ViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            this.title_holder = (TextView) itemView.findViewById(R.id.issueTitle);
            this.comments_holder = (TextView) itemView.findViewById(R.id.issueComments);
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

    public void setOnItemClickListener(IssueAdapter.OnItemClickListener listener) {
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
