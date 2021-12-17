package com.example.highlightapplication.ui.RoomDatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WorldTimeZone {
    @PrimaryKey(autoGenerate = true)
    public int city_id;

    @ColumnInfo(name = "city_name")
    public String city_name;

}
