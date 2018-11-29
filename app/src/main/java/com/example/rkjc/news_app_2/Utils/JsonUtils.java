package com.example.rkjc.news_app_2.Utils;

import android.util.Log;

import com.example.rkjc.news_app_2.NewsItem;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import org.json.JSONException;

public class JsonUtils {
    public static ArrayList<NewsItem> parseNews(String Json) {
        ArrayList<NewsItem> newsItems = new ArrayList<>();
        try {
            JSONObject result = new JSONObject(Json);
            JSONArray articles = result.getJSONArray("articles");
            //Populates the JasonArray with the results from the JsonObject
            for(int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);

                String author = article.getString("author");
                String title = article.getString("title");
                String description = article.getString("description");
                String url = article.getString("url");
                String urlToImage = article.getString("urlToImage");
                String publishedAt = article.getString("publishedAt");

                newsItems.add(new NewsItem(author, title, description, url, urlToImage, publishedAt));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsItems;
    }
}