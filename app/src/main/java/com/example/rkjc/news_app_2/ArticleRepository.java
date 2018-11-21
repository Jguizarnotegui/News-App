package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ArticleRepository {
    private NewsItemDao mArticleDao;
    private LiveData<List<NewsItem>> mAllArticles;
    private NewsItemViewModel mNewsItemViewModel;
    NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    List<NewsItem> articles;

    public ArticleRepository(Application application) {
        ArticleDatabase db = ArticleDatabase.getDatabase(application.getApplicationContext());
        mArticleDao = db.newsItemDao();
        mAllArticles = mArticleDao.loadAllNewsItems();
    }
    LiveData<List<NewsItem>> loadAllNewsItems() {
        return mAllArticles;
    }
    public void insert(List<NewsItem> newsItems) {
        new insertAsyncTask(mArticleDao).execute(newsItems);
    }
    public void delete() {
        new deleteAsyncTask(mArticleDao).execute();
    }
    private static class insertAsyncTask extends AsyncTask<List<NewsItem>, Void, Void> {
        private NewsItemDao mAsyncTaskDao;
        insertAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final List<NewsItem>... params) {
            for(int i = 0; i < params.length; i++) {
                mAsyncTaskDao.insert(params[i]);
            }
            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<List<NewsItem>, Void, Void> {
        private NewsItemDao mAsyncTaskDao;
        deleteAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final List<NewsItem>... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
    public void makeNewsSearchQuery() {
        URL newsUrl = NetworkUtils.buildUrl();
        new NewsQueryTask().execute(newsUrl);
    }
    public class NewsQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            delete();
        }
        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String newsSearchResults = null;
            try {
                newsSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsSearchResults;
        }
        @Override
        protected void onPostExecute(String newsSearchResult) {
            super.onPostExecute(newsSearchResult);
            articles = JsonUtils.parseNews(newsSearchResult);
            insert(articles);
        }
    }
}