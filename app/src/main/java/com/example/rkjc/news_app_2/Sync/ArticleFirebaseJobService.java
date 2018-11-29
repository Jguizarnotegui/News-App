package com.example.rkjc.news_app_2.Sync;

import android.content.Context;
import android.os.AsyncTask;

import com.example.rkjc.news_app_2.ArticleRepository;
import com.firebase.jobdispatcher.JobService;

public class ArticleFirebaseJobService extends JobService {
    private AsyncTask mBackgroundAsyncTask;
    ArticleRepository mArticleRepository;

    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        mBackgroundAsyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = ArticleFirebaseJobService.this;
                ReminderTask.executeTask(context, ReminderTask.UPDATE_ARTICLES);
                mArticleRepository = new ArticleRepository(getApplication());
                mArticleRepository.makeNewsSearchQuery();
                return null;
            }
            @Override
            protected void onPostExecute(Object object) {
                jobFinished(job, false);
            }
        };
        mBackgroundAsyncTask.execute();
        return true;
    }
    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        if(mBackgroundAsyncTask != null)
            mBackgroundAsyncTask.cancel(true);
        return true;
    }
}