package com.example.highlightapplication.ui.APIService;

import android.util.Log;

import com.example.highlightapplication.GlobalCity;
import com.example.highlightapplication.ui.Weather.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonService {
    String TAG = "JsonService";

    public ArrayList<GlobalCity> parseCitiesAPIJson(String jsonCities) {
        Log.e(TAG, "Data=" + jsonCities);
        ArrayList<GlobalCity> allCitiesFromAPI = new ArrayList<>(0);
        try {//
            JSONArray jsonArray = new JSONArray(jsonCities);
            for (int i = 0; i < jsonArray.length(); i++) {
//                String cityName = jsonArray.getString(i);
//                City newCity = new City(jsonArray.getString(i));
                allCitiesFromAPI.add(new GlobalCity(jsonArray.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allCitiesFromAPI;
    }

    public WeatherData parseWeatherAPIData(String jsonWeatherString) {
        Log.e(TAG, "Data1=" + jsonWeatherString);
        WeatherData weatherData = new WeatherData();
        try {
            JSONObject jsonObject = new JSONObject(jsonWeatherString);// root
            JSONObject mainObject = jsonObject.getJSONObject("main");
            JSONArray weather = jsonObject.getJSONArray("weather");
            if (weather != null && weather.length() > 0) {
                weatherData.setMain(weather.getJSONObject(0).getString("main"));
            } else {
                weatherData.setMain("");
            }
            Double temp = mainObject.getDouble("temp");
            weatherData.setTemp(temp);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherData;
    }

    public ArrayList<GlobalCity> parseTimezoneAPIJson(String jsonCities) {
        Log.e(TAG, "Data=" + jsonCities);
        ArrayList<GlobalCity> allTimezonFromAPI = new ArrayList<>(0);
        try {//
            JSONArray jsonArray = new JSONArray(jsonCities);
            for (int i = 0; i < jsonArray.length(); i++) {
//                String cityName = jsonArray.getString(i);
//                City newCity = new City(jsonArray.getString(i));
                allTimezonFromAPI.add(new GlobalCity(jsonArray.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allTimezonFromAPI;
    }



    public ArrayList<GlobalCity> parseGoldJasonAPI(String jsonCities) {
        Log.e(TAG, "Data=" + jsonCities);
        ArrayList<GlobalCity> allCitiesFromAPI = new ArrayList<>(0);
        try {//
            JSONArray jsonArray = new JSONArray(jsonCities);
            for (int i = 0; i < jsonArray.length(); i++) {
//                String cityName = jsonArray.getString(i);
//                City newCity = new City(jsonArray.getString(i));
                allCitiesFromAPI.add(new GlobalCity(jsonArray.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allCitiesFromAPI;
    }

    public ArrayList<String> parseCurrencyJsonAPI(String jsonCities, String currency_To, String currency_From) {
        Log.e(TAG, "Data=" + jsonCities);
        ArrayList<String> allCitiesFromAPI = new ArrayList<>(0);
        try {//
            JSONObject jsonObject=new JSONObject(jsonCities);
            boolean is_Sucess=jsonObject.getBoolean("success");
            if(is_Sucess){
                JSONObject jsonObject1=jsonObject.getJSONObject("rates");
                allCitiesFromAPI.add(jsonObject1.getString(currency_To));
                allCitiesFromAPI.add(jsonObject1.getString(currency_From));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
      return allCitiesFromAPI;
    }

}
