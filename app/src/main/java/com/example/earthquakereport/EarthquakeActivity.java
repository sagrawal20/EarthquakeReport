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


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1, earthquakes);

        mAdapter = new DataAdapter(this, new ArrayList<Data>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
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
//        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
//        task.execute(USGS_REQUEST_URL);
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

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Data>> loader) {
        mAdapter.clear();
    }


//    private class EarthquakeAsyncTask extends AsyncTask<String, Void, ArrayList<Data>> {
//
//        /**
//         * This method runs on a background thread and performs the network request.
//         * We should not update the UI from a background thread, so we return a list of
//         * {@link }s as the result.
//         */
//        @Override
//        protected ArrayList<Data> doInBackground(String... urls) {
//            // Don't perform the request if there are no URLs, or the first URL is null
//            if (urls.length < 1 || urls[0] == null) {
//                return null;
//            }
//
//            ArrayList<Data> result = QueryUtils.extractEarthquakes(urls[0]);
//            return result;
//        }
//
//        /**
//         * This method runs on the main UI thread after the background work has been
//         * completed. This method receives as input, the return value from the doInBackground()
//         * method. First we clear out the adapter, to get rid of earthquake data from a previous
//         * query to USGS. Then we update the adapter with the new list of earthquakes,
//         * which will trigger the ListView to re-populate its list items.
//         */
//        @Override
//        protected void onPostExecute(ArrayList<Data> data) {
//            // Clear the adapter of previous earthquake data
//            mAdapter.clear();
//
//            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
//            // data set. This will trigger the ListView to update.
//            if (data != null && !data.isEmpty()) {
//                mAdapter.addAll(data);
//            }
//        }
//    }
}