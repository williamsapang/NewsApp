package com.example.samsophias.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, android.app.LoaderManager.LoaderCallbacks<List<MyDataNews>> {
    private MyDataNewsAdapter mydatanewsadapter;
    private RecyclerView.LayoutManager layoutManager;
    private String searchQuery;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.NoInternetConnetion) TextView noInternetConnection;
    @BindView(R.id.progress_bar) View progressBar;

    private static final int NEWS_LOADER_ID = 1;

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String NEWS_REQUEST_URL = "https://content.guardianapis.com/search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mydatanewsadapter = new MyDataNewsAdapter(new ArrayList<MyDataNews>(), new OnItemClickListener() {

            @Override public void onItemClick(MyDataNews myDataNews) {
                String url = myDataNews.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        recyclerView.setAdapter(mydatanewsadapter);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            noInternetConnection.setVisibility(View.GONE);
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            noInternetConnection.setVisibility(View.VISIBLE);
            noInternetConnection.setText(R.string.failed_connection);
        }
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<MyDataNews>> loader, List<MyDataNews> myDataNewsList) {
        progressBar.setVisibility(View.GONE);
        noInternetConnection.setVisibility(View.VISIBLE);
        noInternetConnection.setText(R.string.failed_connection);

        mydatanewsadapter.clean();

        if (myDataNewsList != null && !myDataNewsList.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            noInternetConnection.setVisibility(View.GONE);
            mydatanewsadapter.insertAll(myDataNewsList);
        }
    }

    @Override
    public android.content.Loader<List<MyDataNews>> onCreateLoader(int i, Bundle bundle) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        Uri baseUri = Uri.parse(NEWS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("api-key", "798a6b7d-5df7-413c-b523-32d8d7c1f13a");
        if(searchQuery !=null)
            uriBuilder.appendQueryParameter("q", searchQuery);
        return new MyLoader(this, uriBuilder.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search_button));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<MyDataNews>> loader) {
        mydatanewsadapter.clean();
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchQuery = query;
        getLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);
        return true;
    }


}