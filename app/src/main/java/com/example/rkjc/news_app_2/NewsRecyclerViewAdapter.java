package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {
    private List<NewsItem> newsArticle;
    final private ListItemClickListener mListItemClickListener;
    private NewsItemViewModel mNewsItemViewModel;

    public NewsRecyclerViewAdapter(NewsItemViewModel mNewsItemViewModel, ListItemClickListener mListItemClickListener) {
        this.newsArticle = newsArticle;
        this.mListItemClickListener = mListItemClickListener;
    }
    public interface ListItemClickListener {
        void onListItemClick(int clickedArticle);
    }
    //Creates the view holder
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        Context context = viewGroup.getContext();
        int layoutListItems = R.layout.news_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParent = false;

        View view = inflater.inflate(layoutListItems, viewGroup, attachToParent);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }
    //Places the view holder into its position
    @Override
    public void onBindViewHolder(NewsViewHolder newsViewHolder, int position) {
        newsViewHolder.bind(position);
    }
    void setNewsArticle(List<NewsItem> newsItems) {
        newsArticle = newsItems;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(newsArticle != null) {
            return newsArticle.size();
        } else {
            return 0;
        }
    }
    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;
        TextView mDescription;
        TextView mPublishedAt;
        // The view holder
        public NewsViewHolder(View newsView) {
            super(newsView);
            mTitle = (TextView) newsView.findViewById(R.id.na_articles_title);
            mDescription = (TextView) newsView.findViewById(R.id.na_articles_description);
            mPublishedAt = (TextView) newsView.findViewById(R.id.na_articles_publishedAt);

            newsView.setOnClickListener(this);
        }
        //Links the content to the specific view for display
        void bind(int position) {
            mTitle.setText("Title: " + newsArticle.get(position).getTitle());
            mDescription.setText("Description: " + newsArticle.get(position).getDescription());
            mPublishedAt.setText("Date Published: " + newsArticle.get(position).getPublishedAt());
        }
        //Records which article has been clicked
        @Override
        public void onClick(View view) {
            int clickedArticlePosition = getAdapterPosition();
            mListItemClickListener.onListItemClick(clickedArticlePosition);
        }
    }
}