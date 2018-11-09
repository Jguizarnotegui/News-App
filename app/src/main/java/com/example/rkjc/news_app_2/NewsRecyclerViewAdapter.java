package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {
    private ArrayList<NewsItem> newsArticle;
    final private ListItemClickListener mListItemClickListener;

    public NewsRecyclerViewAdapter(ArrayList<NewsItem> newsArticle, ListItemClickListener mListItemClickListener) {
        this.newsArticle = newsArticle;
        this.mListItemClickListener = mListItemClickListener;
    }
    public interface ListItemClickListener {
        void onListItemClick(int clickedArticle);
    }
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutListItems = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutListItems, viewGroup, shouldAttachToParentImmediately);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(NewsViewHolder newsViewHolder, int i) {
        newsViewHolder.bind(i);
    }
    @Override
    public int getItemCount() {
        return newsArticle.size();
    }
    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;
        TextView mDescription;
        TextView mPublishedAt;

        public NewsViewHolder(View newsView) {
            super(newsView);
            mTitle = (TextView) newsView.findViewById(R.id.na_articles_title);
            mDescription = (TextView) newsView.findViewById(R.id.na_articles_description);
            mPublishedAt = (TextView) newsView.findViewById(R.id.na_articles_publishedAt);

            newsView.setOnClickListener(this);
        }
        void bind(int position) {
            mTitle.setText("Title: " + newsArticle.get(position).getTitle());
            mDescription.setText("Description: " + newsArticle.get(position).getDescription());
            mPublishedAt.setText("Date Published: " + newsArticle.get(position).getPublishedAt());
        }
        @Override
        public void onClick(View view) {
            int clickedArticlePosition = getAdapterPosition();
            mListItemClickListener.onListItemClick(clickedArticlePosition);
        }
    }

}
