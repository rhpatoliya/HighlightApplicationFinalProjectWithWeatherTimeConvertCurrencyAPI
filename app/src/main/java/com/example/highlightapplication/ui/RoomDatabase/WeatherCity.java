package com.example.highlightapplication.ui.RoomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WeatherCity {
    @PrimaryKey(autoGenerate = true)
    public int city_id;

    @ColumnInfo(name = "city_name")
    public String cityName;

    @ColumnInfo(name = "temp")
    public Double temp;

//    @ColumnInfo(name = "sunrise")
//    public String sunRise;
//
//    @ColumnInfo(name = "sunset")
//    public String sunSet;
}
