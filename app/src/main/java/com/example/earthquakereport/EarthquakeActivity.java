package com.example.earthquakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

//        String earthquakesQuery = "{\"type\":\"FeatureCollection\",\"metadata\":{\"generated\":1672944770000,\"url\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02&limit=1\",\"title\":\"USGS Earthquakes\",\"status\":200,\"api\":\"1.13.6\",\"limit\":1,\"offset\":1,\"count\":1},\"features\":[{\"type\":\"Feature\",\"properties\":{\"mag\":1.29,\"place\":\"10km SSW of Idyllwild, CA\",\"time\":1388620296020,\"updated\":1457728844428,\"tz\":null,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/ci11408890\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=ci11408890&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":26,\"net\":\"ci\",\"code\":\"11408890\",\"ids\":\",ci11408890,\",\"sources\":\",ci,\",\"types\":\",cap,focal-mechanism,nearby-cities,origin,phase-data,scitech-link,\",\"nst\":39,\"dmin\":0.06729,\"rms\":0.09,\"gap\":51,\"magType\":\"ml\",\"type\":\"earthquake\",\"title\":\"M 1.3 - 10km SSW of Idyllwild, CA\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-116.7776667,33.6633333,11.008]},\"id\":\"ci11408890\"}]}";


        ArrayList<Data> earthquakes = QueryUtils.extractEarthquakes();

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_list_item_1, earthquakes);

        final DataAdapter adapter = new DataAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Data currentData = adapter.getItem(i);
                Uri earthquakeUri = Uri.parse(currentData.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(intent);
            }
        });
    }
}