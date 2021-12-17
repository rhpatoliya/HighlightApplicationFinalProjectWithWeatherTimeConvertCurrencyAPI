package com.example.highlightapplication.ui.RoomDatabase;

import androidx.room.Database;
import androidx.room.Query;
import androidx.room.RoomDatabase;

@Database(entities = {WeatherCity.class,WorldTimeZone.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract WeatherDao weatherDao();

    public abstract WorldtimeZoneDao worldtimeZoneDao();

}
