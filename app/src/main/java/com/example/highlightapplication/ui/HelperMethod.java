package com.example.highlightapplication.ui;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class HelperMethod {
    public static String TAG="HelperMethod";
    public static String ConvertTemp(Double temp) {

        return "";
    }

    //2020-10-14
    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public static String getCurrentTimefromTimezone(String temp) {


        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone(temp));
        String formattedDate = df.format(date);



        return formattedDate;
    }
    public static String getTemptoC(double temp) {
        int cel=(int) (temp-273.15);
        return String.valueOf(cel);
    }
}
