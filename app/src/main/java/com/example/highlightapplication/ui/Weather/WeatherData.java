package com.example.highlightapplication.ui.Weather;

import android.location.Location;

public class WeatherData {

    public String getWorldtime() {
        return worldtime;
    }

    public void setWorldtime(String worldtime) {
        this.worldtime = worldtime;
    }

    public WeatherData(String worldtime) {
        this.worldtime = worldtime;
    }

    public String worldtime;
    public Double temp;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getTimezone() {
        return timezone;
    }

    public void setTimezone(Location timezone) {
        this.timezone = timezone;
    }

    public String main;

    public String description;

    public Location timezone;

    public WeatherData() {
    }

}
