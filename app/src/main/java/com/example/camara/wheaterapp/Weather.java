package com.example.camara.wheaterapp;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by camara on 20/01/2017.
 */

public class Weather implements Serializable {


    String city;
    int temperature;
    int temperature_max;
    int temperature_min;
    int humidity;
    Date time;
    int wind_speed;
    String icon_name;
    Bitmap icon;

    Weather(String city, int temperature, int temperature_min, int temperature_max, int humidity, Date time, int wind_speed, String icon_name )  {

        this.city = city;
        this.temperature = temperature;
        this.temperature_min = temperature_min;
        this.temperature_max = temperature_max;
        this.humidity = humidity;
        this.time = time;
        this.wind_speed = wind_speed;
        this.icon_name = icon_name;

    }


    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    /**
     * rounds (instead of truncating) a double to specified number of decimal places.
     * Thanks http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
     * @param value
     * @param places
     * @return
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
