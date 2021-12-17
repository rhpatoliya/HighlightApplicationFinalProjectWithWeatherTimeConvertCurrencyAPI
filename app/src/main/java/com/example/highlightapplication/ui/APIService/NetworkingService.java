package com.example.highlightapplication.ui.APIService;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.highlightapplication.GlobalCity;
import com.example.highlightapplication.ui.RoomDatabase.WeatherCity;
import com.example.highlightapplication.ui.RoomDatabase.WorldTimeZone;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkingService {

    String TAG="NetworkingService";

    //this is for weather api
    String weatherURL = "https://api.openweathermap.org/data/2.5/weather?q=";
    String weatherURL2 = "&appid=f59b4e088282b559b972242f999207eb";

    //this is for stock
    public static String Stockurl="https://api.polygon.io/v2/aggs/grouped/locale/us/market/stocks/";
    public static String Stockurl_2="?adjusted=true&apiKey=Y_XFooJT4WQm_UuVQYJ13CZmPeUH9p__";


    //unused API
    public static String CurrencyAPI="https://www.amdoren.com/api/currency.php?api_key=ujdFNnTcWBq26gJYsDnRyYWKqE6WfA";
    public static String GoldAPI = "https://metals-api.com/api/latest?access_key=s25ms0f7hkbkguq27ovysqhoz5qvhmw3l58wiat5g3kryhk9kwp8254d4k6m";
  //  https://metals-api.com/api/latest?access_key=s25ms0f7hkbkguq27ovysqhoz5qvhmw3l58wiat5g3kryhk9kwp8254d4k6m

public static String GoldAPI_ = " https://metals-api.com/api/latest?access_key=s25ms0f7hkbkguq27ovysqhoz5qvhmw3l58wiat5g3kryhk9kwp8254d4k6m&base=USD&symbols=XAU";
    // this is for timezone


    //ConvertCurrency API
    public static String ConvertCurrencyAPI = "https://metals-api.com/api/latest?access_key=s25ms0f7hkbkguq27ovysqhoz5qvhmw3l58wiat5g3kryhk9kwp8254d4k6m&base=";
    public static String ConvertCurrencysymbols = "&symbols=INR";


    public static String WorldtimezoneAPI="http://worldtimeapi.org/api/timezone/";

    String url = "http://gd.geobytes.com/AutoCompleteCity?&q=";
    String url2 ="&appid=f59b4e088282b559b972242f999207eb";

    public static final ExecutorService networkingExecutor = Executors.newFixedThreadPool(4);
    static Handler networkHander = new Handler(Looper.getMainLooper());

    public NetworkingService(NetworkingListener networkingListener) {
        listener = networkingListener;
    }

    NetworkingListener listener;

    public interface NetworkingListener{
        void APINetworkListner(String jsonString);
        void WorlTimeNetworkListner(String jsonString, int position);
    }


    public void fetchCitiesData(String text) {
        String completeURL = url + text + url2;
        connect(completeURL,"cities", -1);
    }
    public void fetchWeatherData(GlobalCity selectedCity){
        String completeURL = weatherURL+selectedCity.getCityName()+weatherURL2;
        Log.d(TAG,"Method call="+completeURL);
        connect(completeURL,"weather", -1);
    }


    public void fetchTimezoneData() {
        String completeURL=WorldtimezoneAPI  ;
        connect(completeURL,"timezone", -1);
    }


    public void fetchWeatherHightlightData(WeatherCity selectedCity, int position){
        String completeURL = weatherURL+selectedCity.cityName+weatherURL2;
        Log.d(TAG,"Method call="+completeURL);
        connect(completeURL,"highlight",position);
    }

    public void fetchWorldTimeHighlightData(WorldTimeZone selectedCity, int position){
        String completeURL = WorldtimezoneAPI+selectedCity.city_name;
        Log.d(TAG,"Method call="+completeURL);
        connect(completeURL,"highlight",position);
    }

    public void fetchCurrencyConvert(String from,String To){
        String completeURL = ConvertCurrencyAPI+from+"&symbols="+To;
        Log.d(TAG,"Method call="+completeURL);
        connect(completeURL,"currency",-1);
    }


    public void fetchgold(String text) {
        String completeURL = GoldAPI;
        connect(completeURL, "metallist", -1);
    }



    private void connect(String url, String type, int position) {
        networkingExecutor.execute(new Runnable() {
            String jsonString = "";
            @Override
            public void run() {

                HttpURLConnection httpURLConnection = null;
                try {
                    URL urlObject = new URL(url);
                    httpURLConnection = (HttpURLConnection) urlObject.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type","application/json");
                    int statues = httpURLConnection.getResponseCode();

                    if ((statues >= 200) && (statues <= 299)) {
                        InputStream in = httpURLConnection.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(in);
                        int read = 0;
                        while ((read = inputStreamReader.read()) != -1) {// json integers ASCII
                            char c = (char) read;
                            jsonString += c;
                            Log.d(TAG,"Api response="+jsonString);
                        }// jsonString = ["Torbert, LA, United States","Torch, OH, United States","Toreboda, VG, Sweden","Torino, PI, Italy","Tornado, WV, United States","Tornillo, TX, United States","Tornio, LP, Finland","Toronto, KS, United States","Toronto, OH, United States","Toronto, ON, Canada","Toronto, SD, United States","Torquay, QL, Australia","Torrance, CA, United States","Torrance, PA, United States","torre del greco, CM, Italy","Torre Pellice, PI, Italy","Torrelles de Llobregat, CT, Spain","TORRENS CREEK, QL, Australia","Torreon, CA, Mexico","Torreon, NM, United States"]
                        // dataTask in ios

                        final String finalJson = jsonString;
                        networkHander.post(new Runnable() {
                            @Override
                            public void run() {
                                //send data to main thread
                                Log.d(TAG,"Api response="+finalJson);
                                if(type.equalsIgnoreCase("highlight")){
                                    listener.WorlTimeNetworkListner(finalJson,position);
                                } else{
                                    listener.APINetworkListner(finalJson);
                                }


                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    httpURLConnection.disconnect();
                }
            }
        });
    }
    }

