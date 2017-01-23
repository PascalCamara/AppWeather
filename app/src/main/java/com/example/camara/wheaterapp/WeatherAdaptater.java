package com.example.camara.wheaterapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by camara on 22/01/2017.
 */

public class WeatherAdaptater extends ArrayAdapter<Weather>{

    SimpleDateFormat formatterForList = new SimpleDateFormat("EEEE d MMM", Locale.FRANCE);


    public WeatherAdaptater(Context context, List<Weather> weathers) {
        super(context, 0, weathers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_weather, parent, false);
        }

        WeatherViewHolder wvh = (WeatherViewHolder) convertView.getTag();

        if (wvh == null) {
            wvh = new WeatherViewHolder();
            wvh.UIRowDate = (TextView) convertView.findViewById(R.id.UIRowDate);
            wvh.UIRowTemperature = (TextView) convertView.findViewById(R.id.UIRowTemperature);
            wvh.UIIconDetail = (ImageView) convertView.findViewById(R.id.UIIconDetail);
            convertView.setTag(wvh);
        }

        Weather w = getItem(position);


        wvh.UIRowTemperature.setText(""+w.temperature+"°");
        wvh.UIRowDate.setText(formatterForList.format(w.time));
        if (w.icon != null) {
            wvh.UIIconDetail.setImageBitmap(w.icon);
        }


        return convertView;
    }


    private class WeatherViewHolder {
        public TextView UIRowDate;
        public TextView UIRowTemperature;
        public ImageView UIIconDetail;
    }
}
