package com.example.camara.wheaterapp;


import android.util.Log;

import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;


/**
 * Created by camara on 20/01/2017.
 */

class WeaterApiRestClientUsage {

    void get(String city, JsonHttpResponseHandler responseHandler) throws JSONException {

        // get json weather
        WeaterApiRestClient.get("data/2.5/forecast/daily?q="+ city +"&units=metric&cnt=5&appid=8194ea842a9aef80a798c8ac0c320ec4" , null,  responseHandler );


    }

    void getIcon(String fileName, FileAsyncHttpResponseHandler responseHandler) {

        //get icon weather
        Log.d("strinf file name ", fileName);
        WeaterApiRestClient.get("img/w/"+fileName+".png", null , responseHandler);
       // WeaterApiRestClient.get("img/w/04d.png", null , responseHandler);

    }


}


