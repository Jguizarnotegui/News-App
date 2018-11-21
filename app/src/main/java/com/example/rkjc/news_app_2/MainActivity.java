package com.example.rkjc.news_app_2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NewsRecyclerViewAdapter.ListItemClickListener {
    NewsRecyclerViewAdapter mAdapter;
    RecyclerView mNewsList;
    ArrayList<NewsItem> newsArticles;
    NewsItemViewModel mNewsItemViewModel;
    ArticleRepository mArticleRepository;

    // Member variable for the Database
    //private ArticleDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //might go in other activity
        //mDb = ArticleDatabase.getDatabase(getApplicationContext());

        mNewsList = (RecyclerView) findViewById(R.id.news_recyclerview);
        mNewsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);
        final NewsRecyclerViewAdapter adapter = new NewsRecyclerViewAdapter(mNewsItemViewModel,MainActivity.this);
        mNewsList.setAdapter(adapter);
        mNewsList.setLayoutManager(new LinearLayoutManager(this));
        mNewsItemViewModel.loadAllNewsItems().observe(this, new Observer<List<NewsItem>>() {
            @Override
            public void onChanged(@Nullable List<NewsItem> newsItems) {
                adapter.setNewsArticle(newsItems);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNewsList.setLayoutManager(layoutManager);
        mNewsList.setHasFixedSize(true);
        //makeNewsQuery();
    }
    //private void makeNewsQuery() {
    //    URL newsSearchUrl = NetworkUtils.buildUrl();
    //    new NewsQueryTask().execute(newsSearchUrl);
    //}
    //class NewsQueryTask extends AsyncTask<URL, Void, String> {
    //    Connects to the url in the background thread
    //    @Override
    //    protected String doInBackground(URL... urls) {
    //        URL searchUrl = urls[0];
    //        String newsSearchResults = null;
    //        try {
    //            newsSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //        return newsSearchResults;
    //    }
    //          Organizes the Json data and displays in the recycler view using the adapter
    //    @Override
    //    protected void onPostExecute(String result) {
    //        super.onPostExecute(result);
    //        newsArticles = JsonUtils.parseNews(result);
    //
    //        mAdapter = new NewsRecyclerViewAdapter(newsArticles, MainActivity.this);
    //        mNewsList.setAdapter(mAdapter);
    //    }
    //}
    //Creates the refresh button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    //Refresh when button is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        if (itemClicked == R.id.action_search) {
            mNewsItemViewModel.update();
            //makeNewsQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Go to browser for whichever article is pressed.
    public void onListItemClick(int clickedArticle) {
        Uri uri = Uri.parse(mNewsItemViewModel.loadAllNewsItems().getValue().get(clickedArticle).getUrl());
        //sends to internet app
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
