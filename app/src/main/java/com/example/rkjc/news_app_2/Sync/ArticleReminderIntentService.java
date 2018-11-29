package com.example.rkjc.news_app_2.Sync;

import android.app.IntentService;
import android.content.Intent;

public class ArticleReminderIntentService extends IntentService {
    public ArticleReminderIntentService() {
        super("ArticleReminderIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        ReminderTask.executeTask(this, action);
    }
}
