package com.example.samsophias.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyDataNewsAdapter extends RecyclerView.Adapter<MyDataNewsAdapter.NewsViewHolder> {
    private List<MyDataNews> myDataNews;
    private OnItemClickListener itemListener;
    private Context context;

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_title)
        TextView mTitle;
        @BindView(R.id.news_type) TextView mType;
        @BindView(R.id.news_date) TextView mDate;
        @BindView(R.id.news_section) TextView mSection;

        public NewsViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void bind(final MyDataNews myDataNews, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(myDataNews);
                }
            });
        }
    }

    public MyDataNewsAdapter(List<MyDataNews> myDataNewsList, OnItemClickListener listener){
        myDataNews = myDataNewsList;
        itemListener = listener;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View newsView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(newsView);
        return newsViewHolder;
    }

    public void insertAll(List<MyDataNews> myDataNewsList){
        myDataNews.addAll(myDataNewsList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return myDataNews.size();
    }

    @Override
    public void onBindViewHolder(MyDataNewsAdapter.NewsViewHolder holder, int position) {
        MyDataNews myDataNews = this.myDataNews.get(position);
        holder.mTitle.setText(myDataNews.getTitle());
        holder.mType.setText(myDataNews.getType());
        holder.mDate.setText(myDataNews.getDate());
        holder.mSection.setText(myDataNews.getSection());
        holder.bind(this.myDataNews.get(position), itemListener);
    }


    public void clean(){
        myDataNews.clear();
        notifyDataSetChanged();
    }

}