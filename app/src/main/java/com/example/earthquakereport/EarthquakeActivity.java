package com.example.earthquakereport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

    /** Adapter for the list of earthquakes */
    private DataAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

//        String earthquakesQuery = "{\"type\":\"FeatureCollection\",\"metadata\":{\"generated\":1672944770000,\"url\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02&limit=1\",\"title\":\"USGS Earthquakes\",\"status\":200,\"api\":\"1.13.6\",\"limit\":1,\"offset\":1,\"count\":1},\"features\":[{\"type\":\"Feature\",\"properties\":{\"mag\":1.29,\"place\":\"10km SSW of Idyllwild, CA\",\"time\":1388620296020,\"updated\":1457728844428,\"tz\":null,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/ci11408890\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=ci11408890&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":26,\"net\":\"ci\",\"code\":\"11408890\",\"ids\":\",ci11408890,\",\"sources\":\",ci,\",\"types\":\",cap,focal-mechanism,nearby-cities,origin,phase-data,scitech-link,\",\"nst\":39,\"dmin\":0.06729,\"rms\":0.09,\"gap\":51,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 1.3 - 10km SSW of Idyllwild, CA\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-116.7776667,33.6633333,11.008]},\"id\":\"ci11408890\"}]}";


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
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
    }
    private class EarthquakeAsyncTask extends AsyncTask<String, Void, ArrayList<Data>> {

        /**
         * This method runs on a background thread and performs the network request.
         * We should not update the UI from a background thread, so we return a list of

         */
        @Override
        protected ArrayList<Data> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            ArrayList<Data> result = QueryUtils.extractEarthquakes(urls[0]);
            return result;
        }

        /**
         * This method runs on the main UI thread after the background work has been
         * completed. This method receives as input, the return value from the doInBackground()
         * method. First we clear out the adapter, to get rid of earthquake data from a previous
         * query to USGS. Then we update the adapter with the new list of earthquakes,
         * which will trigger the ListView to re-populate its list items.
         */
        @Override
        protected void onPostExecute(ArrayList<Data> data) {
            // Clear the adapter of previous earthquake data
            mAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}