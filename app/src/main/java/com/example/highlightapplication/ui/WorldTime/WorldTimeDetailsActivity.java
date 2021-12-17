package com.example.highlightapplication.ui.WorldTime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.highlightapplication.GlobalCity;
import com.example.highlightapplication.R;
import com.example.highlightapplication.ui.APIService.JsonService;
import com.example.highlightapplication.ui.APIService.NetworkingService;
import com.example.highlightapplication.ui.RoomDatabase.DatabaseServices;
import com.example.highlightapplication.ui.RoomDatabase.WeatherCity;
import com.example.highlightapplication.ui.RoomDatabase.WorldTimeZone;
import com.example.highlightapplication.ui.Weather.WeatherData;

import java.util.List;

public class WorldTimeDetailsActivity extends Activity implements NetworkingService.NetworkingListener,
        View.OnClickListener ,DatabaseServices.DatabaseListener{

    Context appContext;
    NetworkingService networkingService;
    JsonService jsonService;
    String TAG="WorldTimeDetailsActivity";
    TextView txt_screen;
    TextView city_id;
    GlobalCity globalCity ;
    DatabaseServices dbService;
    Button btn_savedata;
    WeatherData weatherData;
    Intent intent;
    ImageView iv_back;
    TextClock timeText;
    boolean is_saved=false;
    WorldTimeZone weatherDatabaseData=new WorldTimeZone();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_world_details_time);

        appContext= WorldTimeDetailsActivity.this;
        jsonService=new JsonService();

        networkingService = new NetworkingService(this);
        dbService=new DatabaseServices(WorldTimeDetailsActivity.this);
        dbService.setListener(this);
        dbService.getDbInstance(this);

        intent=getIntent();
        globalCity=intent.getParcelableExtra("data");

        dbService.getWorlTimeDatabyName(globalCity.getCityName());

        txt_screen = findViewById(R.id.txt_screen);
        txt_screen.setText("WorldTime Detail Screen");
        city_id = findViewById(R.id.city_id);
        iv_back = findViewById(R.id.iv_back);
        btn_savedata = findViewById(R.id.btn_savedata);
        timeText=findViewById(R.id.timeText);


        city_id.setText(globalCity.getCityName());
       // String timestamp = HelperMethod.getCurrentTimefromTimezone(globalCity.getCityName());
        timeText.setTimeZone(globalCity.getCityName());
        btn_savedata.setOnClickListener(this);
        iv_back.setOnClickListener(this);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void APINetworkListner(String jsonString) {
   /*     weatherData = jsonService.parseWeatherAPIData(jsonString);
        String c=HelperMethod.getTemptoC(weatherData.temp);
        worldtime.setText(c + " ");*/
    }

    @Override
    public void WorlTimeNetworkListner(String jsonString, int position) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_savedata:
                if(btn_savedata.getText().toString().equalsIgnoreCase("Remove Weather data")){
                    dbService.DeleteWorldTimeData(weatherDatabaseData);
                    Toast.makeText(this, "Data removed successfully", Toast.LENGTH_SHORT).show();
                    btn_savedata.setText("Save Weather data");
                    break;
                }else if(btn_savedata.getText().toString().equalsIgnoreCase("Save Weather data")){
                    WorldTimeZone weatherCity=new WorldTimeZone();
                    weatherCity.city_name=globalCity.getCityName();
                    dbService.saveWorldTimeData(weatherCity);
                    Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                    btn_savedata.setText("Remove Weather data");
                    break;
                }
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void databaseCitiesListener(List<WeatherCity> weatherCities, String type) {

    }

    @Override
    public void databaseWorldTimeListener(List<WorldTimeZone> weatherCities, String type) {

        if(type!=null && type.equalsIgnoreCase("weather_detail")){
            if(weatherCities!=null && weatherCities.size()>0){
                for(int i=0;i<weatherCities.size();i++){
                    if(weatherCities.get(i).city_name.equalsIgnoreCase(globalCity.getCityName())){
                        is_saved=true;
                        weatherDatabaseData=weatherCities.get(i);
                    }
                }
            }
        }

        if(is_saved){
            btn_savedata.setText("Remove Weather data");
        }else{
            btn_savedata.setText("Save Weather data");
        }
    }
}
