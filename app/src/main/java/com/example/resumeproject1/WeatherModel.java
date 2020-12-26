package com.example.resumeproject1;

import android.util.Log;

import org.json.JSONObject;

public class WeatherModel {
    static String cityName="";
    static String LOG_TAG="WeatherModel";
    static double temperature;
    public static boolean decode(JSONObject jsonObject){
        try{
        cityName=jsonObject.getString("name");
        temperature=jsonObject.getJSONObject("main").getDouble("temp");
        return true;
        }catch (Exception e){
            Log.d(LOG_TAG,"jsonObject error:)");
            return false;
        }
    }
}
