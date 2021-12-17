package com.example.highlightapplication.ui.RoomDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WorldtimeZoneDao {

    @Query("SELECT * FROM WorldTimeZone")
    List<WorldTimeZone> getWorldTimeAll();

    @Insert
    void insertWorldTimeAll(WorldTimeZone... cityEntities);

    @Query("DELETE FROM WorldTimeZone WHERE city_id = :userId")
    void deleteByWorldTimeId(long userId);

    @Query("SELECT * FROM WorldTimeZone WHERE city_name = :userId")
    List<WorldTimeZone> getWorldTimebyName(String userId);
}
