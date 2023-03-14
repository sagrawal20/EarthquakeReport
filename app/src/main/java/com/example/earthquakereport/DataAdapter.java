package com.example.earthquakereport;

import static java.lang.Math.round;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.drawable.GradientDrawable;

public class DataAdapter extends ArrayAdapter<Data> {
    public DataAdapter(Context context, ArrayList<Data> data) {
        super(context,0, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Data currentData = getItem(position);
        TextView textViewFirst = listItemView.findViewById(R.id.magnitude);
        String magnitude = formatMagnitude(currentData.getMagnitude());
        GradientDrawable magnitudeCircle = (GradientDrawable) textViewFirst.getBackground();
        int magnitudeColor = getMagnitudeColor(currentData.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);
        textViewFirst.setText(magnitude);

        String fullLocation = currentData.getLocation();
        String[] formattedLocation = formatLocation(fullLocation);
        TextView locationFirst = listItemView.findViewById(R.id.location_offset);
        locationFirst.setText(formattedLocation[0]);
        TextView locationSecond = listItemView.findViewById(R.id.primary_location);
        locationSecond.setText(formattedLocation[1]);


        Date dateObject = new Date(currentData.getDate());
        String formattedDate = formatDate(dateObject);
        TextView textViewThird = listItemView.findViewById(R.id.date);
        textViewThird.setText(formattedDate);

        String formattedTime = formatTime(dateObject);
        TextView textViewFourth = listItemView.findViewById(R.id.time);
        textViewFourth.setText(formattedTime);


        return listItemView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String[] formatLocation(String fullLocation) {
        String[] loc = new String[2];
        int len = fullLocation.length();
        if (fullLocation.contains("of")) {
            int i = fullLocation.indexOf("of");
            loc[0] = fullLocation.substring(0, i+2);
            loc[1] = fullLocation.substring(i+3, len);
        } else {
            loc[0] = "Near the";
            loc[1] = fullLocation;
        }
        return loc;
    }

    private String formatMagnitude(Double magnitude){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        String answer = decimalFormat.format(magnitude);
        return answer;
    }

    private int getMagnitudeColor(double magnitude){
        int mag = (int) magnitude;
        switch (mag){
            case 0:
            case 1:
                int magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude1);
                return magnitude1Color;
            case 2:
                int magnitude2Color = ContextCompat.getColor(getContext(), R.color.magnitude2);
                return magnitude2Color;
            case 3:
                int magnitude3Color = ContextCompat.getColor(getContext(), R.color.magnitude3);
                return magnitude3Color;
            case 4:
                int magnitude4Color = ContextCompat.getColor(getContext(), R.color.magnitude4);
                return magnitude4Color;
            case 5:
                int magnitude5Color = ContextCompat.getColor(getContext(), R.color.magnitude5);
                return magnitude5Color;
            case 6:
                int magnitude6Color = ContextCompat.getColor(getContext(), R.color.magnitude6);
                return magnitude6Color;
            case 7:
                int magnitude7Color = ContextCompat.getColor(getContext(), R.color.magnitude7);
                return magnitude7Color;
            case 8:
                int magnitude8Color = ContextCompat.getColor(getContext(), R.color.magnitude8);
                return magnitude8Color;
            case 9:
                int magnitude9Color = ContextCompat.getColor(getContext(), R.color.magnitude9);
                return magnitude9Color;
            case 10:
                int magnitude10Color = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
                return magnitude10Color;
            default:
                int magnitude11Color = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
                return magnitude11Color;
        }
    }
}
