package com.example.rkjc.news_app_2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {
    private ArticleRepository mRepository;
    private LiveData<List<NewsItem>> mAllNewsItems;

    public NewsItemViewModel(Application application) {
        super(application);
        mRepository = new ArticleRepository(application);
        mAllNewsItems = mRepository.loadAllNewsItems();
    }
    public LiveData<List<NewsItem>> loadAllNewsItems() {
        return mAllNewsItems;
    }
    public void update() {mRepository.makeNewsSearchQuery();}
    //public void insert(NewsItem newsItem) {
    //    mRepository.insert(newsItem);
    //}
    //public void delete(NewsItem newsItem) {
    //    mRepository.delete(newsItem);
    //}
}
