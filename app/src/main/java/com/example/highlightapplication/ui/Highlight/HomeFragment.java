package com.example.highlightapplication.ui.Highlight;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.highlightapplication.GlobalCity;
import com.example.highlightapplication.R;
import com.example.highlightapplication.ui.APIService.JsonService;
import com.example.highlightapplication.ui.APIService.NetworkingService;
import com.example.highlightapplication.ui.RoomDatabase.DatabaseServices;
import com.example.highlightapplication.ui.RoomDatabase.WeatherCity;
import com.example.highlightapplication.ui.RoomDatabase.WorldTimeZone;
import com.example.highlightapplication.ui.Weather.WeatherData;
import com.example.highlightapplication.ui.Weather.WeatherDetailsActivity;
import com.example.highlightapplication.ui.WorldTime.WorldTimeDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements DatabaseServices.DatabaseListener,
        NetworkingService.NetworkingListener, HomeWeatherAdapter.HomeWeatherInterface,
        View.OnClickListener, HomeWorldTimeAdapter.HomeWorldTimeInterface {

    RecyclerView recyclerview;
    RecyclerView recyclerView_Time;
    HomeWeatherAdapter homeWeatherAdapter;
    HomeWorldTimeAdapter homeWorldTimeAdapter;
    DatabaseServices dbService;
    NetworkingService networkingService;
    JsonService jsonService;
    WeatherData weatherData=new WeatherData();
    ArrayList<WeatherCity> weatherCities=new ArrayList<>();
    ArrayList<WorldTimeZone> worldTimeData = new ArrayList<>();
    TextView txt_no_weatherdata,txt_no_worldtimedata;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerview=view.findViewById(R.id.recyclerview);
        recyclerView_Time = view.findViewById(R.id.recyclerview_time);
        txt_no_weatherdata=view.findViewById(R.id.txt_no_weatherdata);
        txt_no_worldtimedata=view.findViewById(R.id.txt_no_worldtimedata);

        jsonService=new JsonService();
        dbService=new DatabaseServices(getActivity());
        networkingService = new NetworkingService(this);
        dbService.setListener(this);
        dbService.getDbInstance(getActivity());
        dbService.getAllCitiesFromDB();
        dbService.getAllWorldTimeFromDB();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dbService.getAllCitiesFromDB();
        dbService.getAllWorldTimeFromDB();
    }

    @Override
    public void databaseCitiesListener(List<WeatherCity> weatherCities1,String type) {
        if(type!=null && type.equalsIgnoreCase("home")) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            weatherCities=new ArrayList<>();
            weatherCities.addAll(weatherCities1);
            recyclerview.setLayoutManager(layoutManager);
            recyclerview.setNestedScrollingEnabled(false);
            homeWeatherAdapter = new HomeWeatherAdapter(getActivity(), weatherCities, this);
            recyclerview.setAdapter(homeWeatherAdapter);
            for (int i = 0; i < weatherCities.size(); i++) {
                networkingService.fetchWeatherHightlightData(weatherCities.get(i), i);
            }
        }

        if(weatherCities!=null && weatherCities.size()>0){
            recyclerview.setVisibility(View.VISIBLE);
            txt_no_weatherdata.setVisibility(View.GONE);
        }else{
            recyclerview.setVisibility(View.GONE);
            txt_no_weatherdata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void databaseWorldTimeListener(List<WorldTimeZone> worldtimeZones1, String type) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        worldTimeData =new ArrayList<>();
        worldTimeData.addAll(worldtimeZones1);
        recyclerView_Time.setLayoutManager(layoutManager);
        recyclerView_Time.setNestedScrollingEnabled(false);
        homeWorldTimeAdapter = new HomeWorldTimeAdapter(getActivity(), worldTimeData, this);
        recyclerView_Time.setAdapter(homeWorldTimeAdapter);

        if(worldTimeData !=null && worldTimeData.size()>0){
            recyclerView_Time.setVisibility(View.VISIBLE);
            txt_no_worldtimedata.setVisibility(View.GONE);
        }else{
            recyclerView_Time.setVisibility(View.GONE);
            txt_no_worldtimedata.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void APINetworkListner(String jsonString) {

    }

    @Override
    public void WorlTimeNetworkListner(String jsonString, int position) {
        weatherData = jsonService.parseWeatherAPIData(jsonString);
        homeWeatherAdapter.weatherCities.get(position).temp=weatherData.temp;
        homeWeatherAdapter.notifyDataSetChanged();

    }

    @Override
    public void onRecyclerViewClick(WeatherCity weatherCity) {
        Intent intent=new Intent(getActivity(), WeatherDetailsActivity.class);
        GlobalCity globalCity=new GlobalCity();
        globalCity.setCityName(weatherCity.cityName);
        intent.putExtra("data",globalCity);
        startActivity(intent);
    }

    @Override
    public void onClickForDelete(WeatherCity weatherCity, int adapterPosition) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to delete this item from Highlight Weather?")
                .setTitle("Weather Highlight")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbService.DeleteWeatherData(weatherCity);
                        weatherCities.remove(adapterPosition);
                        homeWeatherAdapter.weatherCities.remove(adapterPosition);
                        homeWeatherAdapter.notifyDataSetChanged();

                        if(weatherCities!=null && weatherCities.size()>0){
                            recyclerview.setVisibility(View.VISIBLE);
                            txt_no_weatherdata.setVisibility(View.GONE);
                        }else{
                            recyclerview.setVisibility(View.GONE);
                            txt_no_weatherdata.setVisibility(View.VISIBLE);
                        }


                        Toast.makeText(getActivity(), "Data removed successfully", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }

    @Override
    public void onRecyclerViewClick(WorldTimeZone weatherCity) {
        Intent intent=new Intent(getActivity(), WorldTimeDetailsActivity.class);
        GlobalCity globalCity=new GlobalCity();
        globalCity.setCityName(weatherCity.city_name);
        intent.putExtra("data",globalCity);
        startActivity(intent);
    }

    @Override
    public void onClickForDelete(WorldTimeZone weatherCity, int adapterPosition) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to delete this item from Highlight Worl Time?")
                .setTitle("World Time Highlight")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbService.DeleteWorldTimeData(weatherCity);
                        worldTimeData.remove(adapterPosition);
                        homeWorldTimeAdapter.weatherCities.remove(adapterPosition);
                        homeWorldTimeAdapter.notifyDataSetChanged();

                        if(worldTimeData !=null && worldTimeData.size()>0){
                            recyclerView_Time.setVisibility(View.VISIBLE);
                            txt_no_worldtimedata.setVisibility(View.GONE);
                        }else{
                            recyclerView_Time.setVisibility(View.GONE);
                            txt_no_worldtimedata.setVisibility(View.VISIBLE);
                        }

                        Toast.makeText(getActivity(), "Data removed successfully", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}