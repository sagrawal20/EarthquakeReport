package com.example.earthquakereport;

public class Data {

    private Double  mMagnitude;
    private String mLocation, mUrl;
    private long mDate;

    public Data(Double magnitude, String location, long date, String url){
        mMagnitude = magnitude;
        mLocation = location;
        mDate = date;
        mUrl = url;
    }

    public Double getMagnitude(){
        return mMagnitude;
    }
    public String getLocation(){
        return mLocation;
    }
    public long getDate(){
        return mDate;
    }
    public String getUrl(){
        return mUrl;
    }
}
