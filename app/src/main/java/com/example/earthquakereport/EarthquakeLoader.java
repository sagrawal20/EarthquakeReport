package com.example.earthquakereport;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Data>> {

    private String mUrl;

    public EarthquakeLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<Data> loadInBackground() {

        if (mUrl.length() < 1 || mUrl == null) {
            return null;
        }

        ArrayList<Data> result = QueryUtils.extractEarthquakes(mUrl);
        return result;

    }
}
