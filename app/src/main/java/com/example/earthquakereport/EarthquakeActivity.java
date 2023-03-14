package com.example.earthquakereport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Data>> {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

    /** Adapter for the list of earthquakes */
    private DataAdapter mAdapter;
    private static final int EARTHQUAKE_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        /**
         * Find a reference to the {@link ListView} in the layout
         */
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        /**
         * Create a new reference for the custom adapter{@link DataAdapter} of earthquakes
         */

        mAdapter = new DataAdapter(this, new ArrayList<Data>());

        /**
         * Set the adapter on the {@link earthquakeListView}
         * so the list can be populated in the user interface
         */

        earthquakeListView.setAdapter(mAdapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Data currentData = mAdapter.getItem(i);
                Uri earthquakeUri = Uri.parse(currentData.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(intent);
            }
        });

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID,null,this).forceLoad();
    }

    @NonNull
    @Override
    public Loader<ArrayList<Data>> onCreateLoader(int id, @Nullable Bundle args) {
        return new EarthquakeLoader(EarthquakeActivity.this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Data>> loader, ArrayList<Data> data) {
        mAdapter.clear();

        /**
         * If there is a valid list of {@link data}s, then add them to the adapter's
         * data set. This will trigger the ListView to update.
         */

        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Data>> loader) {
        mAdapter.clear();
    }
}