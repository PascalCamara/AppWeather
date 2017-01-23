package com.example.camara.wheaterapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity implements Serializable {



    JSONObject datas;

    TextView UICity;
    TextView UITemperature;
    TextView UITime;

    ListView weatherListView;
    ImageView UIIcon;
    List<Weather> weathers;

    SimpleDateFormat formatter = new SimpleDateFormat("EEEE, d MMM yyyy", Locale.FRANCE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        UICity = (TextView) findViewById(R.id.UICity);
        UITemperature = (TextView) findViewById(R.id.UITemperature);
        UITime = (TextView) findViewById(R.id.UITime);
        weatherListView = (ListView) findViewById(R.id.WeatherListView);
        UIIcon = (ImageView) findViewById(R.id.UIIcon);

        // get data api
        try {
            WeaterApiRestClientUsage wc = new WeaterApiRestClientUsage();

            wc.get("Paris", new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    datas = response;
                    // set the view
                    makeUIWeather();
                    makeIconsWeather();

                }

            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void makeUIWeather() {

        this.weathers = makeWeather();

        // set the main weather
        UICity.setText(weathers.get(0).city);
        UITemperature.setText(""+weathers.get(0).temperature+"°");
        UITime.setText(formatter.format(weathers.get(0).time));

        // set the listview
        WeatherAdaptater adapter = new WeatherAdaptater(MainActivity.this, this.weathers);

        weatherListView.setAdapter(adapter);

        weatherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("position click ", ""+ position);
                Log.d("id click ", ""+ id);

                Weather weatherSelected = weathers.get((int) id);

                Intent i = new Intent(MainActivity.this, WeatherDetailtActivity.class);
                i.putExtra("city",   weatherSelected.city);
                i.putExtra("humidity",   weatherSelected.humidity);
                i.putExtra("icon_name",  weatherSelected.icon_name);
                i.putExtra("temperature_min",   weatherSelected.temperature_min);
                i.putExtra("temperature_max",   weatherSelected.temperature_max);
                i.putExtra("temperature",   weatherSelected.temperature);
                i.putExtra("time",   weatherSelected.time);
                i.putExtra("wind_speed",   weatherSelected.wind_speed);
                startActivity(i);
                Log.d("weatherSelected : ", ""+ weatherSelected);
            }
        });


    }

    private List<Weather> makeWeather() {

        List<Weather> weathers = new ArrayList<Weather>();

        try {

            JSONArray weathersList = datas.getJSONArray("list");

            int weatherCount = weathersList.length();

            for (int i = 0; i < weatherCount ; i ++ ) {

                JSONObject data = (JSONObject) weathersList.get(i);

//                Log.d("data weather object ", ""+ data.getJSONArray("weather").getJSONObject(0).getString("icon"));
                weathers.add(new Weather(
                        datas.getJSONObject("city").getString("name"),
                        (int) Weather.round(data.getJSONObject("temp").getDouble("day"), 0),
                        (int) Weather.round(data.getJSONObject("temp").getDouble("min"), 0),
                        (int) Weather.round(data.getJSONObject("temp").getDouble("max"), 0),
                        data.getInt("humidity"),
                        new Date(data.getLong("dt")*1000),
                        (int) Weather.round(data.getDouble("speed"), 0),
                        data.getJSONArray("weather").getJSONObject(0).getString("icon")
                ));

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("WEATHERS ", "" + weathers);
        return weathers;
    }

    void makeIconsWeather() {

        for (int i = 0; i < this.weathers.size(); i++) {

            WeaterApiRestClientUsage w = new WeaterApiRestClientUsage();

            final int finalI = i;
            w.getIcon(weathers.get(i).icon_name, new FileAsyncHttpResponseHandler(this) {
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                    Log.d("on faillure ", ""+file);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, File file) {
                    Log.d("on sucess ", ""+file);
                    Log.d("weather iterator ",  "" + weathers.get(finalI));
                    weathers.get(finalI).setIcon( BitmapFactory.decodeFile(file.getPath()) );

                    if (finalI == 0) {
                        UIIcon.setImageBitmap(weathers.get(finalI).icon);
                    }

                    ((BaseAdapter) weatherListView.getAdapter()).notifyDataSetChanged();


                }
            });



        }
    }

}