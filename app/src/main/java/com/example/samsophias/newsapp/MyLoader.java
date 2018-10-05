package com.example.samsophias.newsapp;
import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class MyLoader extends AsyncTaskLoader<List<MyDataNews>> {
    private String mUrl;

    @Override
    public List<MyDataNews> loadInBackground() {
        if (mUrl.length() < 1 || mUrl == null) {
            return null;
        }
        List<MyDataNews> myDataNews = MyUtilities.fetchMyNews(mUrl);
        return myDataNews;
    }

    public MyLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
