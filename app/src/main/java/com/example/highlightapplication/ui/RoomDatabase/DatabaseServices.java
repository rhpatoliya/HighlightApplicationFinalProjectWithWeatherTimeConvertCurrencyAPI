package com.example.highlightapplication.ui.RoomDatabase;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseServices {

    public DatabaseServices(Context appContext) {

    }

    public void setListener(DatabaseListener listener) {
        this.listener=listener;
    }

    public interface DatabaseListener{
        void databaseCitiesListener(List<WeatherCity> weatherCities,String type);
        void databaseWorldTimeListener(List<WorldTimeZone> weatherCities, String type);
    }
    public DatabaseListener listener;
    public static AppDatabase dbInstance;

    ExecutorService citiesExecutor = Executors.newFixedThreadPool(4);
    Handler citiesHandler = new Handler(Looper.getMainLooper());

    private void buildDB(Context context){
        dbInstance = Room.databaseBuilder(context,
                AppDatabase.class, "highlight_database").build();
    }


    public AppDatabase getDbInstance(Context context){
        if (dbInstance == null)
            buildDB(context);
        return dbInstance;
    }


    public void  getAllCitiesFromDB(){
        //dbService.dbInstance.getDao().getAllCities();

        citiesExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<WeatherCity> cities = dbInstance.weatherDao().getWeatherAll();
                citiesHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.databaseCitiesListener(cities,"home");
                    }
                });
            }
        });
    }

    public void  getAllWorldTimeFromDB(){
        //dbService.dbInstance.getDao().getAllCities();

        citiesExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<WorldTimeZone> cities = dbInstance.worldtimeZoneDao().getWorldTimeAll();
                citiesHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.databaseWorldTimeListener(cities,"home");
                    }
                });
            }
        });
    }

    public void  getWeatherDatabyName(String weatherCity){
        //dbService.dbInstance.getDao().getAllCities();

        citiesExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<WeatherCity> cities = dbInstance.weatherDao().getCityNamebyUser(weatherCity);
                citiesHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.databaseCitiesListener(cities,"weather_detail");
                    }
                });
            }
        });
    }

    public void  getWorlTimeDatabyName(String weatherCity){
        //dbService.dbInstance.getDao().getAllCities();

        citiesExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<WorldTimeZone> cities = dbInstance.worldtimeZoneDao().getWorldTimebyName(weatherCity);
                citiesHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.databaseWorldTimeListener(cities,"weather_detail");
                    }
                });
            }
        });
    }

    public void saveNewCity(WeatherCity c){
        citiesExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dbInstance.weatherDao().insertWeatherAll(c);
            }
        });
    }

    public void saveWorldTimeData(WorldTimeZone c){
        citiesExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dbInstance.worldtimeZoneDao().insertWorldTimeAll(c);
            }
        });
    }

    public boolean DeleteWeatherData(WeatherCity c){
        citiesExecutor.execute(new Runnable() {
            @Override
            public void run() {
               dbInstance.weatherDao().deleteByUserId(c.city_id);
            }
        });
        return true;
    }


    public boolean DeleteWorldTimeData(WorldTimeZone c){
        citiesExecutor.execute(new Runnable() {
            @Override
            public void run() {
               dbInstance.worldtimeZoneDao().deleteByWorldTimeId(c.city_id);
            }
        });
        return true;
    }


}
