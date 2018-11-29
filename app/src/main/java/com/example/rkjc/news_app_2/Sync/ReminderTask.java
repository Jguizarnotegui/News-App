package com.example.rkjc.news_app_2.Sync;

import android.content.Context;
import com.example.rkjc.news_app_2.Utils.NotificationUtils;

public class ReminderTask {
    public static final String UPDATE_ARTICLES = "update-Articles";
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-Notification";

    public static void executeTask(Context context, String action) {
        if(ACTION_DISMISS_NOTIFICATION.equals(action)) {
            NotificationUtils.ClearNotifications(context);
        } else if(UPDATE_ARTICLES.equals(action)) {
            NotificationUtils.notifyNews(context);
        }
    }
}
