package com.example.highlightapplication.ui.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WeatherDao {

    @Query("SELECT * FROM WeatherCity")
    List<WeatherCity> getWeatherAll();

    @Insert
    void insertWeatherAll(WeatherCity... cityEntities);

    @Query("DELETE FROM WeatherCity WHERE city_id = :userId")
    void deleteByUserId(long userId);

    @Query("SELECT * FROM WeatherCity WHERE city_name = :userId")
    List<WeatherCity> getCityNamebyUser(String userId);


}
