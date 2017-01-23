package com.example.camara.wheaterapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.FileAsyncHttpResponseHandler;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

/**
 * Created by camara on 22/01/2017.
 */

public class WeatherDetailtActivity extends AppCompatActivity implements Serializable {

    SimpleDateFormat formatter = new SimpleDateFormat("EEEE, d MMM yyyy", Locale.FRANCE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_detail);

        final ImageView UIIconDetail;

        // To retrieve object in second Activity
//        Weather w = ( Weather ) getIntent().getSerializableExtra("weather");


//        Log.d("wheater ", ""+w.temperature);
//        Log.d("test d serialize ", ""+ w.time);

        TextView temperature = (TextView) findViewById(R.id.UITemperatureDetail);
        TextView city = (TextView) findViewById(R.id.UICityDetail);
        TextView time = (TextView) findViewById(R.id.UIDateDetail);
        TextView tempMax = (TextView) findViewById(R.id.UITemperatureMaxDetail);
        TextView tempMin = (TextView) findViewById(R.id.UITemperatureMinDetail);
        TextView humidity = (TextView) findViewById(R.id.UIHumidityDetail);
        TextView windSpeed = (TextView) findViewById(R.id.UISpeedWindDetail);
        UIIconDetail = (ImageView) findViewById(R.id.UIIconDetail);


        temperature.setText("" + getIntent().getSerializableExtra("temperature") + "°");
        city.setText((String) getIntent().getSerializableExtra("city"));
        time.setText(formatter.format(getIntent().getSerializableExtra("time")));
        tempMax.setText("température max : " + getIntent().getSerializableExtra("temperature_max") +"°");
        tempMin.setText("température min : "+ getIntent().getSerializableExtra("temperature_min") +"°");
        humidity.setText("humidité : "+ getIntent().getSerializableExtra("humidity")+"%");
        windSpeed.setText("vitesse du vent : "+getIntent().getSerializableExtra("humidity")+"m/s");

        //get the icon
        WeaterApiRestClientUsage w = new WeaterApiRestClientUsage();
        w.getIcon(("" + getIntent().getSerializableExtra("icon_name")), new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                //
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                UIIconDetail.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
            }
        });



    }
}
